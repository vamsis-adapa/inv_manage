package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.cache.UserCache;
import sai_adapa.projs.inv_management.model.orders.io.DisplayableOrder;
import sai_adapa.projs.inv_management.model.users.Users;
import sai_adapa.projs.inv_management.repositories.sql.UsersRepository;
import sai_adapa.projs.inv_management.tools.AuthTools;
import sai_adapa.projs.inv_management.tools.SortDetails;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private UserCache userCache;
    private OrderService orderService;
    private SessionIdentity sessionIdentity;
    private VendorService vendorService;
    private ItemService itemService;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setSessionIdentity(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void addUser(String name, String e_mail, String details, String password) {
        usersRepository.save(Users.builder().name(name).email(e_mail).details(details).passwdHash(AuthTools.encodePassword(password)).build());
    }

    public Users displayUser(String email) {
        return getReturnable(getUser(email));
    }

    public Users getReturnable(Users users) {
        users.setPasswdHash(null);
        users.setSessionToken(null);
        return users;
    }

    public void addUserToCache(Users users) {
        if (users == null) {
            return;
        }
        userCache.addUserFromUUID(users.getUserId(), users);
        userCache.addUserFromEmail(users.getEmail(), users);
        if (users.getSessionToken() != null)
            userCache.addUserFromSession(users.getSessionToken(), users);
    }

    public Users getUser(String e_mail) {
        Users users = userCache.getUserFromEmail(e_mail);
        if (users != null)
            return users;
        users = usersRepository.findByEmail(e_mail);
        addUserToCache(users);
        return users;
    }


    public Users getUser(UUID uuid) {
        Users users = userCache.getUserFromUUID(uuid);
        if (users != null)
            return users;
        users = usersRepository.findByUserId(uuid);
        addUserToCache(users);
        return users;
    }

    public Users getUsersBySession(String token) {
        Users users = userCache.getUserFromSession(token);
        if (users != null)
            return users;
        users = usersRepository.findBySessionToken(token);
        addUserToCache(users);
        return users;
    }

    public Boolean verifySession(String token) {
        Users users = getUsersBySession(token);
        if (users != null)
            return true;
        return false;
    }

    public void removeUserFromCache(Users users) {
        if (users == null)
            return;
        userCache.removeUserFromEmail(users.getEmail());
        userCache.removeUserFromUUID(users.getUserId());
        if (users.getSessionToken() != null)
            userCache.removeUserSession(users.getSessionToken());
    }

    public void deleteUser(Users users) {
        removeUserFromCache(users);
        usersRepository.delete(users);
    }


    public void editUser(Users users, String name, String email, String details, String password) {
        int check = 0;

        if (name != null) {
            users.setName(name);
            check = 1;
        }
        if (email != null) {
            users.setEmail(email);
            check = 1;
        }
        if (details != null) {
            users.setDetails(details);
            check = 1;
        }
        if (password != null) {
            users.setPasswdHash(AuthTools.encodePassword(password));
            check = 1;
        }
        if (check == 0) {
            return;//throw error
        }
        addUserToCache(users);
        usersRepository.save(users);
    }

    public Boolean verifyUser(String e_mail, String password) {
        String passwdHash = getUser(e_mail).getPasswdHash();
        return AuthTools.verifyPassword(password, passwdHash);
    }


    public String createSession(String e_mail) {
        String session = userCache.getSession(e_mail);
        if (session != null)
                return session;

        Users users = getUser(e_mail);

        String sessionToken = AuthTools.generateNewToken();
        users.setSessionToken(sessionToken);
        addUserToCache(users);
        usersRepository.save(users);
        return sessionToken;
    }

    public void removeSessionCache(Users users) {
        if (users.getSessionToken() != null)
            userCache.removeSession(users.getSessionToken());

        userCache.removeSessionToken(users.getEmail());
    }

    public void endSession(Users users) {

        removeSessionCache(users);
        users.setSessionToken(null);
        usersRepository.save(users);
    }

    public List<DisplayableOrder> getUserOrderReport(String email) {
        Users users = getUser(email);
        return orderService.findOrdersOfUser(users.getUserId()).stream().map(orders -> orderService.createDisplayableOrder(orders)).collect(Collectors.toList());
    }

    public List<DisplayableOrder> getUserOrderReportPaginated(String email, Integer pageSize, Integer pageNumber) {
        Users users = getUser(email);
        return orderService.findOrdersOfUserPaginated(users.getUserId(), pageNumber, pageSize).stream().map(orders -> orderService.createDisplayableOrder(orders)).collect(Collectors.toList());
    }

    public List<DisplayableOrder> getUserOrderReportPaginatedAndSorted(String email, Integer pageSize, Integer pageNumber, List<SortDetails> sortDetailsList) {
        Users users = getUser(email);
        return orderService.findOrdersOfUserPaginatedAndSorted(users.getUserId(), pageNumber, pageSize, sortDetailsList).stream().map(orders -> orderService.createDisplayableOrder(orders)).collect(Collectors.toList());
    }


}

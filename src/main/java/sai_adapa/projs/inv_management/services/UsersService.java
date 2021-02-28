package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.model.orders.io.DisplayableOrder;
import sai_adapa.projs.inv_management.model.users.Users;
import sai_adapa.projs.inv_management.repositories.sql.UsersRepository;
import sai_adapa.projs.inv_management.tools.AuthTools;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private OrderService orderService;
    private SessionIdentity sessionIdentity;
    private VendorService vendorService;
    private ItemService itemService;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
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

    public Users getUser(String e_mail) {
        return usersRepository.findByEmail(e_mail);
    }

    public Users getUser(UUID uuid) {
        return usersRepository.findByUserId(uuid);
    }

    public Users getUsersBySession(String token) {
        return usersRepository.findBySessionToken(token);
    }

    public Boolean verifySession(String token) {
        return usersRepository.existsUsersBySessionToken(token);
    }

    public void deleteUser(Users users) {
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
            //throw error
        }
        usersRepository.save(users);
    }

    public Boolean verifyUser(String e_mail, String password) {
        String passwdHash = getUser(e_mail).getPasswdHash();
        return AuthTools.verifyPassword(password, passwdHash);
    }

    public Boolean authIdentity(String email) {
        return sessionIdentity.verifyIdentity(email);
    }

    public String createSession(String e_mail) {
        Users users = getUser(e_mail);
        String sessionToken = AuthTools.generateNewToken();
        users.setSessionToken(sessionToken);
        usersRepository.save(users);
        return sessionToken;
    }

    public void endSession(Users users) {
        users.setSessionToken(null);
        usersRepository.save(users);
    }

    public List<DisplayableOrder> getUserOrderReport(String email) {
        Users users = getUser(email);
        return orderService.findOrdersOfUser(users.getUserId()).stream().map(orders -> orderService.createDisplayableOrder(orders)).collect(Collectors.toList());
    }


}

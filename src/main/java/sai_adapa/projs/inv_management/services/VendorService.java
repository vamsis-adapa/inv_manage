package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.cache.VendorCache;
import sai_adapa.projs.inv_management.exceptions.StockCreationUnsuccessfulException;
import sai_adapa.projs.inv_management.exceptions.StockNotFoundException;
import sai_adapa.projs.inv_management.exceptions.UserNotFoundException;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.orders.io.DisplayableOrderVendor;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.repositories.sql.VendorRepository;
import sai_adapa.projs.inv_management.tools.AuthTools;
import sai_adapa.projs.inv_management.tools.SortDetails;
import sai_adapa.projs.inv_management.tools.enums.OrderStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VendorService {
    VendorRepository vendorRepository;
    ItemService itemService;
    StockService stockService;
    OrderService orderService;
    VendorCache vendorCache;

    @Autowired
    public VendorService(VendorRepository vendorRepository, ItemService itemService) {
        this.vendorRepository = vendorRepository;
        this.itemService = itemService;
    }

    @Autowired
    public void setVendorCache(VendorCache vendorCache) {
        this.vendorCache = vendorCache;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public void addUser(String name, String email, String description, String passwd) throws DataIntegrityViolationException {
        vendorRepository.save(Vendor.builder().name(name).email(email).description(description).passwdHash(AuthTools.encodePassword(passwd)).build());
    }

    public void addVendorToCache(Vendor vendor) {
        if (vendor == null) {
            return;
        }
        vendorCache.addUserFromUUID(vendor.getVendorId(), vendor);
        vendorCache.addUserFromEmail(vendor.getEmail(), vendor);
        if (vendor.getSessionToken() != null)
            vendorCache.addUserFromSession(vendor.getSessionToken(), vendor);
    }

    public void deleteUser(Vendor vendor) {
        vendorRepository.delete(vendor);
    }

    public void editUser(Vendor vendor, String name, String email, String desc, String password) {
        int check = 0;
        if (name != null) {
            vendor.setName(name);
            check = 1;
        }
        if (email != null) {
            vendor.setEmail(email);
            check = 1;
        }
        if (desc != null) {
            vendor.setDescription(desc);
            check = 1;
        }
        if (password != null) {
            vendor.setPasswdHash(AuthTools.encodePassword(password));
            check = 1;
        }
        if (check == 0) {
            //throw
        }

        vendorRepository.save(vendor);
    }


    public Vendor getUserBySession(String token) throws UserNotFoundException {
        Vendor vendor = vendorCache.getUserFromSession(token);
        if (vendor != null)
            return vendor;

        vendor = vendorRepository.findBySessionToken(token);
        if (vendor == null) {
            throw new UserNotFoundException("no user with session: " + token);
        }
        addVendorToCache(vendor);
        return vendor;
    }


    public Boolean verifySession(String token) {
        return vendorRepository.existsVendorBySessionToken(token);
    }

    public Vendor getUser(String email) throws UserNotFoundException {
        Vendor vendor = vendorCache.getUserFromEmail(email);
        if (vendor != null)
            return vendor;
        vendor = vendorRepository.findByEmail(email);
        if (vendor == null) {
            throw new UserNotFoundException("no user with email: " + email);
        }
        addVendorToCache(vendor);
        return vendor;
    }

    public Vendor getUser(UUID vendorId) throws UserNotFoundException {
        Vendor vendor = vendorCache.getUserFromUUID(vendorId);
        if (vendor != null)
            return vendor;
        vendor = vendorRepository.findByVendorId(vendorId);
        if (vendor == null) {
            throw new UserNotFoundException("no user with id: " + vendorId);
        }
        addVendorToCache(vendor);
        return vendor;
    }

    public Stock getStock(String email, Long item_id) {
        try {
            return stockService.getParticularStock(email, item_id);
        } catch (StockNotFoundException e) {
            return null;
        }
    }

    public Boolean verifyUser(String email, String password) throws UserNotFoundException {
        return AuthTools.verifyPassword(password, getUser(email).getPasswdHash());
    }

    public Long addNewItem(String item_name, String description) {
        return itemService.addItem(item_name, description);
    }

    public List<Stock> getVendorStock(String email) throws UserNotFoundException {
        return stockService.getVendorStock(email);

    }

    public Long addStock(String vendor_email, Long item_id, Double price, Integer num_items) throws StockCreationUnsuccessfulException {
        if (stockService.checkExistingStock(vendor_email, item_id)) {
            //Todo: add exception msg
            throw new StockCreationUnsuccessfulException();
        }

        Stock stock = stockService.addNewStock(item_id, vendor_email, num_items, price);
        orderService.createVendorOrder(stock, num_items, OrderStatus.VendorStockNew);
        return stock.getId();
    }

    public void incrementVendorStock(String vendor_email, Long item_id, Integer inv_num) throws StockNotFoundException {
        Stock stock = stockService.getParticularStock(vendor_email, item_id);
        stockService.editStock(stock.getId(), inv_num + stock.getInv_num(), null);
        orderService.createVendorOrder(stock, inv_num, OrderStatus.VendorStockIncrement);
    }

    public void deleteStock(String vendor_email, Long item_id) throws StockNotFoundException {
        Stock stock = stockService.getParticularStock(vendor_email, item_id);
        stockService.deleteStock(vendor_email, item_id);
        orderService.createVendorOrder(stock, null, OrderStatus.VendorStockDelete);
    }

    public void editStock(String vendor_email, Long item_id, Integer inv_num, Double cost) throws StockNotFoundException {
        Stock stock = stockService.getParticularStock(vendor_email, item_id);
        if (stock == null) {
            throw new StockNotFoundException();
        }
        stockService.editStock(stock.getId(), inv_num, cost);
        orderService.createVendorOrder(stock, inv_num, OrderStatus.VendorStockEdit);
    }

    public Vendor getDisplayable(Vendor vendor) {
        vendor.setPasswdHash(null);
        vendor.setSessionToken(null);
        return vendor;

    }

    public Vendor displayUser(String email) throws UserNotFoundException {
        return getDisplayable(getUser(email));

    }


    public String createSession(String email) throws UserNotFoundException {
        String session = vendorCache.getSession(email);
        String sessionToken = null;
        if (session != null)
            return session;

        Vendor vendor = getUser(email);
        sessionToken = AuthTools.generateNewToken();
        vendor.setSessionToken(sessionToken);
        addVendorToCache(vendor);
        vendorRepository.save(vendor);


        return sessionToken;
    }

    public void removeSessionCache(Vendor vendor) {
        if (vendor.getSessionToken() != null) {
            vendorCache.removeSession(vendor.getSessionToken());
        }
        vendorCache.removeSessionToken(vendor.getEmail());
    }

    public void endSession(String email) throws UserNotFoundException {
        Vendor vendor = getUser(email);
        removeSessionCache(vendor);
        vendor.setSessionToken(null);
        vendorRepository.save(vendor);
    }

    public List<DisplayableOrderVendor> getOrderReport(String vendorEmail) throws UserNotFoundException {
        Vendor vendor = getUser(vendorEmail);
        return orderService.findOrdersOfVendor(vendor.getVendorId()).stream().map(orders -> orderService.createDisplayableOrderVendor(orders)).collect(Collectors.toList());
    }

    public List<DisplayableOrderVendor> getOrderReportPaginated(String vendorEmail, Integer pageSize, Integer pageNumber) throws UserNotFoundException {
        Vendor vendor = getUser(vendorEmail);
        return orderService.findOrdersOfVendorPaginated(vendor.getVendorId(), pageNumber, pageSize).stream().map(orders -> orderService.createDisplayableOrderVendor(orders)).collect(Collectors.toList());
    }

    public List<DisplayableOrderVendor> getOrderReportPaginatedAndSorted(String vendorEmail, Integer pageSize, Integer pageNumber, List<SortDetails> sortDetailsList) throws UserNotFoundException {
        Vendor vendor = getUser(vendorEmail);
        return orderService.findOrdersOfVendorPaginatedAndSorted(vendor.getVendorId(), pageNumber, pageSize, sortDetailsList).stream().map(orders -> orderService.createDisplayableOrderVendor(orders)).collect(Collectors.toList());
    }

}

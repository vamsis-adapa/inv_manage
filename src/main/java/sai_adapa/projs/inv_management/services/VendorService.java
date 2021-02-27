package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.repositories.sql.VendorRepository;
import sai_adapa.projs.inv_management.tools.AuthTools;

import java.util.List;

@Service
public class VendorService {
    VendorRepository vendorRepository;
    ItemService itemService;
    StockService stockService;

    @Autowired
    public VendorService(VendorRepository vendorRepository, ItemService itemService) {
        this.vendorRepository = vendorRepository;
        this.itemService = itemService;
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public void addUser(String name, String email, String description, String passwd) {
        vendorRepository.save(Vendor.builder().name(name).email(email).description(description).passwdHash(AuthTools.encodePassword(passwd)).build());
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


    public Vendor getUserBySession(String token) {
        return vendorRepository.findBySessionToken(token);
    }


    public Boolean verifySession(String token) {
        return vendorRepository.existsVendorBySessionToken(token);
    }

    public Vendor getUser(String email) {
        return vendorRepository.findByEmail(email);
    }

    public Stock getStock(String email, Long item_id) {
        return stockService.getParticularStock(email, item_id);
    }

    public Boolean verifyUser(String email, String password) {
        return AuthTools.verifyPassword(password, getUser(email).getPasswdHash());
    }

    public Long addNewItem(String item_name, String description) {
        return itemService.addItem(item_name, description);
    }

    public List<Stock> getVendorStock(String email) {
        return stockService.getVendorStock(email);
    }

    public Long addStock(String vendor_email, Long item_id, Double price, Integer num_items) {
        if (stockService.checkExistingStock(vendor_email, item_id)) {
            //throw error
        }
        return stockService.addNewStock(item_id, vendor_email, num_items, price);
    }

    public void deleteStock(String vendor_email, Long item_id) {
        stockService.deleteStock(vendor_email, item_id);
    }

    public void editStock(String vendor_email, Long item_id, Integer inv_num, Double cost) {
        Stock stock = stockService.getParticularStock(vendor_email, item_id);
        if (stock == null) {
            //throw error
        }

        stockService.editStock(stock.getId(), inv_num, cost);
    }

    public Vendor getDisplayable(Vendor vendor) {
        vendor.setPasswdHash(null);
        vendor.setSessionToken(null);
        return vendor;

    }

    public Vendor displayUser(String email) {
        return getDisplayable(getUser(email));

    }


    public String createSession(String email) {
        Vendor vendor = getUser(email);
        String sessionToken = AuthTools.generateNewToken();
        vendor.setSessionToken(sessionToken);
        vendorRepository.save(vendor);
        return sessionToken;
    }


    public void endSession(String email) {
        Vendor vendor = getUser(email);
        vendor.setSessionToken(null);
        vendorRepository.save(vendor);

    }
}

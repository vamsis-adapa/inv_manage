package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.exceptions.ItemNotFoundException;
import sai_adapa.projs.inv_management.exceptions.StockCreationUnsuccessfulException;
import sai_adapa.projs.inv_management.exceptions.StockNotFoundException;
import sai_adapa.projs.inv_management.exceptions.UserNotFoundException;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.repositories.sql.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    StockRepository stockRepository;
    VendorService vendorService;
    ItemService itemService;


    @Autowired
    public StockService(StockRepository stockRepository, ItemService itemService) {
        this.stockRepository = stockRepository;

        this.itemService = itemService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    public Stock addNewStock(Long item_id, String vendor_email, int inv_num, Double price) throws StockCreationUnsuccessfulException {
        Stock stock;
        try {
            stock = Stock.builder().item(itemService.getItemById(item_id)).vendor(vendorService.getUser(vendor_email)).inv_num(inv_num).cost(price).build();
            stockRepository.save(stock);
        } catch (ItemNotFoundException | DataIntegrityViolationException | UserNotFoundException e) {
            //TODO: add message to exception
            throw (new StockCreationUnsuccessfulException());
        }
        return stock;
    }

    public void deleteStock(String vendor_email, Long item_id) throws StockNotFoundException {
        try {


            stockRepository.delete(stockRepository.findStockByVendorAndItem(vendorService.getUser(vendor_email), itemService.getItemById(item_id)));
        } catch (ItemNotFoundException | UserNotFoundException e) {
            throw new StockNotFoundException();
        }
    }

    Stock getStockById(Long stock_id) {
        return stockRepository.findById(stock_id).get();
    }

    public void editStock(Long stock_id, Integer inv_num, Double price) {
        Stock stock = getStockById(stock_id);
        if (inv_num != null) {
            stock.setInv_num(inv_num);
        }
        if (price != null) {
            stock.setCost(price);
        }
        stockRepository.save(stock);
    }

    public void removeStock() {
        //implement
    }

    public void buyStock(Long stock_id, Integer num_stock) {
        Stock stock = getStockById(stock_id);
        stock.setInv_num(stock.getInv_num() - num_stock);
        stockRepository.save(stock);
    }

    public Stock getParticularStock(String vendor_email, Long item_id) throws StockNotFoundException {
        try {
            return stockRepository.findStockByVendorAndItem(vendorService.getUser(vendor_email), itemService.getItemById(item_id));
        } catch (ItemNotFoundException | UserNotFoundException exception) {
            throw new StockNotFoundException();
        }
    }

    public Boolean checkExistingStock(String vendor_email, Long item_id) {
        try {
            return stockRepository.existsStockByVendorAndItem(vendorService.getUser(vendor_email), itemService.getItemById(item_id));
        } catch (ItemNotFoundException | UserNotFoundException exception) {
            return false;
        }

    }

    public List<Stock> getStockOfItem(Long item_id) throws ItemNotFoundException {

        return stockRepository.findAllByItem(itemService.getItemById(item_id));
    }

    public List<Vendor> getItemVendors(Long item_id) throws ItemNotFoundException {

        return getStockOfItem(item_id).stream().map(stock -> stock.getVendor()).collect(Collectors.toList());
    }

    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    public boolean checkAvailability(Long item_id, String vendor_email, Integer required) throws StockNotFoundException {
//TODO : FIX
        return (required >= getParticularStock(vendor_email, item_id).getInv_num());
    }

    public List<Stock> getVendorStock(String vendor_email) throws UserNotFoundException {

        Vendor vendor = vendorService.getUser(vendor_email);
        return stockRepository.findAllByVendor(vendor);


    }
}

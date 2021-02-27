package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.repositories.sql.StockRepository;

import java.util.List;

@Service
public class StockService {

    StockRepository stockRepository;
    VendorService vendorService;
    ItemService itemService;


    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public StockService(StockRepository stockRepository, ItemService itemService) {
        this.stockRepository = stockRepository;

        this.itemService = itemService;
    }

    public Long addNewStock(Long item_id, String vendor_email, int inv_num, Double price)
    {
        Stock stock = Stock.builder().item(itemService.getItemById(item_id)).vendor(vendorService.getUser(vendor_email)).inv_num(inv_num).cost(price).build();
        stockRepository.save(stock);
        return stock.getId();
    }

    public void deleteStock(String vendor_email, Long item_id)
    {
        stockRepository.delete(stockRepository.findStockByVendorAndItem(vendorService.getUser(vendor_email),itemService.getItemById(item_id)));
    }
    public Stock getStockById(Long stock_id)
    {
        return  stockRepository.findById(stock_id).get();
    }
    public void editStock(Long stock_id, Integer inv_num, Double price)
    {
        Stock stock = getStockById(stock_id);
        if ( inv_num!= null)
        {
            stock.setInv_num(inv_num);
        }
        if ( price!= null)
        {
            stock.setCost(price);
        }
        stockRepository.save(stock);
    }

    public void removeStock()
    {
        //implement
    }
    public void buyStock(Long stock_id,Integer num_stock )
    {
        Stock stock = getStockById(stock_id);
        stock.setInv_num(stock.getInv_num()- num_stock);
        stockRepository.save(stock);
    }

    public Stock getParticularStock(String vendor_email, Long item_id)
    {
        return stockRepository.findStockByVendorAndItem(vendorService.getUser(vendor_email), itemService.getItemById(item_id));
    }

    public Boolean checkExistingStock(String vendor_email, Long item_id)
    {
        return stockRepository.existsStockByVendorAndItem(vendorService.getUser(vendor_email), itemService.getItemById(item_id));
    }

    public List<Stock> getVendorStock(String vendor_email)
    {
        Vendor vendor = vendorService.getUser(vendor_email);
        return stockRepository.findAllByVendor(vendor);
    }
}

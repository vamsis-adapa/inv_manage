package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.repositories.StockRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class StockService {

    StockRepository stockRepository;
    VendorService vendorService;
    ItemService itemService;

    public StockService(StockRepository stockRepository, VendorService vendorService, ItemService itemService) {
        this.stockRepository = stockRepository;
        this.vendorService = vendorService;
        this.itemService = itemService;
    }

    public void addNewStock(long item_id, String vendor_email, int inv_num, int price)
    {
        Stock stock = Stock.builder().item(itemService.getItemById(item_id)).vendor(vendorService.getUser(vendor_email)).inv_num(inv_num).cost(price).build();
        stockRepository.save(stock);
    }
}

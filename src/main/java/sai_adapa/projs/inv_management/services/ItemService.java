package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.io.ItemDetails;
import sai_adapa.projs.inv_management.model.items.io.VendorCostMap;
import sai_adapa.projs.inv_management.repositories.sql.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    ItemRepository itemRepository;
    StockService stockService;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public Long addItem(String name, String description) {
        Item item = Item.builder().name(name).description(description).build();
        itemRepository.save(item);
        return item.getItem_id();
    }

    public Item getItemById(Long item_id) {
        return itemRepository.findById(item_id).get();
    }

    public ItemDetails getItemDetails(Long item_id) {
        Item item = getItemById(item_id);
        List<VendorCostMap> listOfVendors = stockService.getStockOfItem(item_id).stream().map(stock -> new VendorCostMap(stock.getVendor().getEmail(), stock.getCost(), stock.getInv_num())).collect(Collectors.toList());
        return ItemDetails.builder().Details(item.getDescription()).name(item.getName()).listOfVendors(listOfVendors).build();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void editItem(Long item_id, String name, String description) {
        Item item = getItemById(item_id);
        if (name != null) {
            item.setName(name);
        }
        if (description != null) {
            item.setDescription(description);
        }
        itemRepository.save(item);
    }

    //    public void updateItem(Long item_id)
    public void deleteItem(Long item_id) {
        itemRepository.delete(getItemById(item_id));
    }
}

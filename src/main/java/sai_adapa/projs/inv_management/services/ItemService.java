package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.exceptions.ItemNotFoundException;
import sai_adapa.projs.inv_management.exceptions.StockNotFoundException;
import sai_adapa.projs.inv_management.model.io.ItemWithPrice;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.items.io.ItemDetails;
import sai_adapa.projs.inv_management.model.items.io.VendorCostMap;
import sai_adapa.projs.inv_management.repositories.sql.ItemRepository;

import java.util.List;
import java.util.Objects;
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

    public Item getItemById(Long item_id) throws ItemNotFoundException {
        Item item = itemRepository.findById(item_id).get();
        if (item == null)
            throw (new ItemNotFoundException("requested item (itemId: " + item_id + ") not found"));
        return item;
    }

    public List<VendorCostMap> listOfVendors(Long item_id) throws ItemNotFoundException {
        return stockService.getStockOfItem(item_id).stream().map(stock -> new VendorCostMap(stock.getVendor().getEmail(), stock.getCost(), stock.getInv_num())).collect(Collectors.toList());
    }

    public Double getMinPrice(Long item_id) throws ItemNotFoundException, StockNotFoundException {
        return stockService.getStockOfItem(item_id).stream().mapToDouble(Stock::getCost).min().orElseThrow(StockNotFoundException::new);
    }


    public ItemDetails getItemDetails(Long item_id) throws ItemNotFoundException {
        Item item = getItemById(item_id);
        List<VendorCostMap> listOfVendors = listOfVendors(item_id);
        return ItemDetails.builder().Details(item.getDescription()).name(item.getName()).listOfVendors(listOfVendors).build();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<ItemWithPrice> getAllItemsWithLeastPrice() {
        return getAllItems().stream().map(item -> {
            try {
                return ItemWithPrice.builder().productName(item.getName()).product_price(getMinPrice(item.getItem_id())).build();
            } catch (ItemNotFoundException | StockNotFoundException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }


    public void editItem(Long item_id, String name, String description) throws ItemNotFoundException {
        Item item = getItemById(item_id);
        if (name != null) {
            item.setName(name);
        }
        if (description != null) {
            item.setDescription(description);
        }
        itemRepository.save(item);
    }

    //Todo change to query
    public Integer totalNumberOfItems() {
        return getAllItems().size();
    }

    public Slice<Item> paginatedGetSearchedItems(Integer pageNumber, Integer pageSize, String searchQuery) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        searchQuery = "%" + searchQuery + "%";
        return itemRepository.findAllByNameLikeOrDescriptionLike(searchQuery, searchQuery, pageRequest);
    }


    public Slice<Item> paginatedGetAllItem(Integer pageNumber, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return itemRepository.findAll(pageRequest);
    }

    //    public void updateItem(Long item_id)
    public void deleteItem(Long item_id) throws ItemNotFoundException {
        itemRepository.delete(getItemById(item_id));
    }
}

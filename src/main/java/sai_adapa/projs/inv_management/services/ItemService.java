package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.repositories.ItemRepository;

import java.util.List;

@Service
public class ItemService {
    ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void addItem(String name, String description) {
        itemRepository.save(Item.builder().name(name).description(description).build());
    }

    public Item getItemById(Long item_id) {
        return itemRepository.findById(item_id).get();
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

    public void deleteItem(Long item_id) {
        itemRepository.delete(getItemById(item_id));
    }
}

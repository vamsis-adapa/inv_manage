package sai_adapa.projs.inv_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.services.ItemService;

import java.util.List;

@RestController
public class ItemController {

    ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = {"/items"})
    public List<Item> listAllItems()
    {
        return itemService.getAllItems();
    }
    @RequestMapping(value = {"/items/{id}"})
    public Item getItem(@PathVariable Long id)
    {
        return itemService.getItemById(id);
    }

}

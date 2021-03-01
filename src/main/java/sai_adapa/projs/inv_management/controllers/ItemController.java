package sai_adapa.projs.inv_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.io.ItemDetails;
import sai_adapa.projs.inv_management.services.ItemService;

import java.util.List;

@RestController
public class ItemController {

    ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //Todo get params into query
    @RequestMapping(value = {"/items"}, params = {"pageSize", "pageNumber", "searchWord"})
    public List<Item> listAllItems() {
        System.out.println("straw ");
        //add case for default nulls
        return itemService.paginatedGetAllItem(2, 2).getContent();
    }


    //TODO CHANGE VALUE ACCORDING TO COMMON PRACTICE
    @RequestMapping(value = {"/items?pagesize={pageSize}&pagenumber={pageNumber}"})
    public Slice<Item> listAllItemsPaginated(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        System.out.println("straw");
        return itemService.paginatedGetAllItem(pageNumber, pageSize);
    }

    //possibly edit tos how stocks
    @RequestMapping(value = {"/items/{id}"})
    public ItemDetails getItem(@PathVariable Long id) {
        return itemService.getItemDetails(id);
    }

}

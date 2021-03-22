package sai_adapa.projs.inv_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sai_adapa.projs.inv_management.exceptions.ItemNotFoundException;
import sai_adapa.projs.inv_management.model.io.ItemDetails;
import sai_adapa.projs.inv_management.model.io.ItemWithPrice;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.services.ItemService;
import sai_adapa.projs.inv_management.tools.ResponseHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ItemController {

    ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @RequestMapping(value = {"/items"})
    public List<Item> listAllItems(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) String searchWord) {
        if (pageSize == null)
            pageSize = 7;
        if (pageNumber == null)
            pageNumber = 0;
        if (searchWord == null)
            return itemService.paginatedGetAllItem(pageNumber, pageSize).getContent();
        else
            return itemService.paginatedGetSearchedItems(pageNumber, pageSize, searchWord).getContent();
    }


    @RequestMapping(value = {"/items/min_price"})
    public List<ItemWithPrice> listAllItemWithMinPrice() {
        return itemService.getAllItemsWithLeastPrice();
    }

    //possibly edit tos how stocks
    @RequestMapping(value = {"/items/{id}"})
    public ItemDetails getItem(@PathVariable Long id, HttpServletResponse response) {
        try {
            return itemService.getItemDetails(id);
        } catch (ItemNotFoundException e) {
            ResponseHandler.resourceNotFound(response, "requested item not found");
        }
        return null;
    }

}

package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.orders.Orders;
import sai_adapa.projs.inv_management.model.orders.io.DisplayableOrder;
import sai_adapa.projs.inv_management.repositories.mongo.OrderRepository;

import java.util.UUID;

@Service
public class OrderService {
    OrderRepository orderRepository;
    ItemService itemService;
    UsersService usersService;
    VendorService vendorService;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    public String createOrder(Stock stock, UUID userID, Integer numberOfItems) {
        Double total_cost = numberOfItems * stock.getCost();
        Orders orders = Orders.builder().userId(userID).vendorId(stock.getVendor().getVendor_id()).itemId(stock.getItem().getItem_id()).numberOfItems(numberOfItems).individualCost(stock.getCost()).totalCost(total_cost).build();
        orderRepository.save(orders);
        return orders.getId();
    }

    public DisplayableOrder createDisplayableOrder(Orders orders) {
        String vendorEmail = vendorService.getUser(orders.getVendorId()).getEmail();
        String userEmail = usersService.getUser(orders.getUserId()).getEmail();
        String itemName = itemService.getItemById(orders.getItemId()).getName();
        return  DisplayableOrder.builder().id(orders.getId()).vendorEmail(vendorEmail).userEmail(userEmail).itemId(orders.getItemId()).itemName(itemName).numberOfItems(orders.getNumberOfItems()).individualCost(orders.getIndividualCost()).transactionDate(orders.getTransactionDate()).totalCost(orders.getTotalCost()).build();
    }


}

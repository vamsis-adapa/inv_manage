package sai_adapa.projs.inv_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.orders.Orders;
import sai_adapa.projs.inv_management.repositories.mongo.OrderRepository;

import java.util.UUID;

@Service
public class OrderService {
    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String createOrder(Stock stock, UUID userID, Integer numberOfItems)
    {
        Double total_cost = numberOfItems * stock.getCost();
        Orders orders = Orders.builder().userId(userID).vendorId(stock.getVendor().getVendor_id()).itemId(stock.getItem().getItem_id()).numberOfItems(numberOfItems).individualCost(stock.getCost()).totalCost(total_cost).build();
        orderRepository.save(orders);
        return orders.getId();
    }



}

package sai_adapa.projs.inv_management.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.enums.OrderStatus;
import sai_adapa.projs.inv_management.model.enums.PaymentStatus;
import sai_adapa.projs.inv_management.model.io.DisplayableOrder;
import sai_adapa.projs.inv_management.model.io.DisplayableOrderVendor;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.orders.Orders;
import sai_adapa.projs.inv_management.repositories.mongo.OrderRepository;
import sai_adapa.projs.inv_management.tools.SortDetails;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    OrderRepository orderRepository;
    ItemService itemService;
    UsersService usersService;
    VendorService vendorService;
    StockService stockService;
    PaymentService paymentService;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
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


    public String createVendorOrder(Stock stock, Integer numberOfItems, OrderStatus orderStatus) {
        Orders orders = Orders.builder().vendorId(stock.getVendor().getVendorId()).numberOfItems(numberOfItems).orderStatus(orderStatus).build();
        orderRepository.save(orders);
        return orders.getId();
    }

    public void changeOrderStatus(Orders orders, PaymentStatus s) {
        if (s == PaymentStatus.Successful)
            orders.setOrderStatus(OrderStatus.Confirmed);
        else if (s == PaymentStatus.Failed)
            orders.setOrderStatus(OrderStatus.Cancelled);
        orderRepository.save(orders);
    }


    public Orders createOrder(Stock stock, UUID userID, Integer numberOfItems) {
        Double total_cost = numberOfItems * stock.getCost();
        stockService.buyStock(stock.getId(), numberOfItems);
        Orders orders = Orders.builder().orderStatus(OrderStatus.Pending).userId(userID).vendorId(stock.getVendor().getVendorId()).itemId(stock.getItem().getItem_id()).numberOfItems(numberOfItems).individualCost(stock.getCost()).totalCost(total_cost).build();
        orderRepository.save(orders);
        return orders;
    }

    //Todo: check sneaky throw usage
    @SneakyThrows
    public DisplayableOrder createDisplayableOrder(Orders orders) {
        String vendorEmail = vendorService.getUser(orders.getVendorId()).getEmail();
        String userEmail = usersService.getUser(orders.getUserId()).getEmail();
        String itemName = itemService.getItemById(orders.getItemId()).getName();
        return DisplayableOrder.builder().id(orders.getId()).vendorEmail(vendorEmail).userEmail(userEmail).itemId(orders.getItemId()).itemName(itemName).numberOfItems(orders.getNumberOfItems()).individualCost(orders.getIndividualCost()).transactionDate(orders.getTransactionDate()).totalCost(orders.getTotalCost()).build();
    }

    @SneakyThrows
    DisplayableOrderVendor createDisplayableOrderVendor(Orders orders) {
        String vendorEmail = vendorService.getUser(orders.getVendorId()).getEmail();
        String itemName = itemService.getItemById(orders.getItemId()).getName();
        return DisplayableOrderVendor.builder().id(orders.getId()).vendorEmail(vendorEmail).userID(orders.getUserId()).itemId(orders.getItemId()).itemName(itemName).numberOfItems(orders.getNumberOfItems()).individualCost(orders.getIndividualCost()).transactionDate(orders.getTransactionDate()).totalCost(orders.getTotalCost()).build();
    }

    public List<Orders> findOrdersOfUser(UUID uuid) {
        return orderRepository.findAllByUserId(uuid);
    }

    public List<Orders> findOrdersOfVendor(UUID uuid) {
        return orderRepository.findAllByVendorId(uuid);
    }

    public Sort declareSort(String criteria, String direction, Sort preSort) {
        Sort sort;
        if (preSort == null) {
            sort = Sort.by(criteria);
            if (direction.equals("des"))
                sort = sort.descending();
            else if (direction.equals("asc"))
                sort = sort.ascending();
            return sort;
        }

        sort = preSort.and(Sort.by(criteria));
        if (direction.equals("des"))
            sort = sort.descending();
        else if (direction.equals("asc"))
            sort = sort.ascending();
        return sort;
    }

    public Sort generateSort(List<SortDetails> sortDetailsList) {
        Sort finalSort = null;
        for (SortDetails sortDetails : sortDetailsList) {
            finalSort = declareSort(sortDetails.getCriteria(), sortDetails.getDirection(), finalSort);
        }
        return finalSort;
    }

    public List<Orders> findOrdersOfUserPaginated(UUID uuid, Integer pageNumber, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAllByUserId(uuid, pageRequest).getContent();
    }

    public List<Orders> findOrdersOfUserPaginatedAndSorted(UUID uuid, Integer pageNumber, Integer pageSize, List<SortDetails> sortDetailsList) {
        Sort sort = generateSort(sortDetailsList);
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return orderRepository.findAllByUserId(uuid, pageRequest).getContent();
    }

    public List<Orders> findOrdersOfVendorPaginated(UUID uuid, Integer pageNumber, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAllByVendorId(uuid, pageRequest).getContent();
    }

    public List<Orders> findOrdersOfVendorPaginatedAndSorted(UUID uuid, Integer pageNumber, Integer pageSize, List<SortDetails> sortDetailsList) {
        Sort sort = generateSort(sortDetailsList);
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return orderRepository.findAllByVendorId(uuid, pageRequest).getContent();
    }
}

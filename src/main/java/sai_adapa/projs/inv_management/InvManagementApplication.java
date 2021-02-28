package sai_adapa.projs.inv_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.repositories.mongo.OrderRepository;
import sai_adapa.projs.inv_management.services.*;

import java.util.List;

@SpringBootApplication
@EnableCaching
@EnableMongoRepositories(basePackageClasses = OrderRepository.class)
@EnableMongoAuditing
@EnableJpaRepositories(basePackages = "sai_adapa.projs.inv_management.repositories.sql", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = OrderRepository.class))
public class InvManagementApplication {

    @Autowired
    ItemService itemService;

    @Autowired
    StockService stockService;
    @Autowired
    VendorService vendorService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    UsersService usersService;

    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("strawberry");

        Long it1 = itemService.addItem("choc", "brown and hard");
        Long it2 = itemService.addItem("choc 2", "black and bitter");

        vendorService.addUser("hit", "mail", "dlak;j", "choc");
        vendorService.addUser("meow cat shop", "post", "drifkdlf;j; ;dalkfj", "fire");
        usersService.addUser("fire", "gif", "dalk;jf", "yare");
//        stockService.addNewStock(it1, "mail", 55, 12.0);
//        stockService.addNewStock(it2, "post", 34, 99.0);
//        stockService.addNewStock(it1, "mail", 55, 13.0);

        vendorService.addStock("mail", itemService.getAllItems().get(0).getItem_id(), 32.0, 5);
        vendorService.addStock("post",itemService.getAllItems().get(0).getItem_id(),12.0,32);
        List<Stock>allStock=  stockService.getAllStock();
        List<Vendor> allVendor = stockService.getItemVendors(itemService.getAllItems().get(0).getItem_id());;
        orderService.createOrder(stockService.getVendorStock("mail").get(0), usersService.getUser("gif").getUser_id(), 32);
        System.out.println("coffee");
    }


}

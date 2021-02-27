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
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import sai_adapa.projs.inv_management.model.orders.Orders;
import sai_adapa.projs.inv_management.repositories.mongo.OrderRepository;
import sai_adapa.projs.inv_management.services.ItemService;
import sai_adapa.projs.inv_management.services.StockService;
import sai_adapa.projs.inv_management.services.VendorService;

@SpringBootApplication
@EnableCaching
@EnableMongoRepositories(basePackageClasses = OrderRepository.class)
@EnableJpaRepositories(basePackages = "sai_adapa.projs.inv_management.repositories.sql", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = OrderRepository.class))
//@EnableJpaRepositories(basePackageClasses = {UsersRepository.class,VendorRepository.class, StockRepository.class, ItemRepository.class, AdminRepository.class})
//@EnableJpaRepositories(basePackages = "sai_adapa.projs.inv_management.repositories.sql")
public class InvManagementApplication {

    @Autowired
    ItemService itemService;

    @Autowired
    StockService stockService;
    @Autowired
    VendorService vendorService;

    @Autowired
    OrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("strawberry");
//        System.out.println(AuthTools.encodePassword(null));
//        Long it1 = itemService.addItem("choc", "brown and hard");
//        Long it2 = itemService.addItem("choc 2", "black and bitter");
//        vendorService.addUser("hit", "mail", "dlak;j", "choc");
//        vendorService.addUser("meow cat shop", "post", "drifkdlf;j; ;dalkfj", "fire");
//
//        stockService.addNewStock(it1, "mail", 55, 12.0);
//        stockService.addNewStock(it2, "post", 34, 99.0);
//        stockService.addNewStock(it1, "mail", 55, 13.0);

        Orders orders = Orders.builder().orderGroup(Long.getLong("1")).itemId(Long.getLong("1")).build();
        orderRepository.save(orders);
        orderRepository.save(Orders.builder().itemId(Long.getLong("8643")).build());


        System.out.println("coffee");
    }


}

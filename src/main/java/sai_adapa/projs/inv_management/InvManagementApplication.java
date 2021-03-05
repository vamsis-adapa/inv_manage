package sai_adapa.projs.inv_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import sai_adapa.projs.inv_management.repositories.mongo.OrderRepository;
import sai_adapa.projs.inv_management.services.*;

import java.util.stream.Collectors;

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
    @Autowired
    private CacheManager cacheManager;

    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("strawberry");

        cacheManager.getCacheNames()
                .parallelStream()
                .forEach(n -> cacheManager.getCache(n).clear());
        Long it1 = itemService.addItem("choc", "brown and hard chocolate");
        Long it2 = itemService.addItem("choc 2", "black and bitter chocolate");
        Long ite = itemService.addItem("straw", "yare yareada chocolate");
        Long ite2 = itemService.addItem("dkjs;a", ";ldjaf_");
        Long ite3 = itemService.addItem("dfja;", "chds;f");
        Long ite4 = itemService.addItem("sdlfjchocolate;", "jdfaksljfla chocolate");
        Long ite5 = itemService.addItem("trex", "dljkfsa;chocolate");

//        vendorService.addUser("hit", "mail", "dlak;j", "choc");
//        vendorService.addUser("meow cat shop", "post", "drifkdlf;j; ;dalkfj", "fire");
//        usersService.addUser("fire", "gif", "dalk;jf", "yare");
//        stockService.addNewStock(it1, "mail", 55, 12.0);
//        stockService.addNewStock(it2, "post", 34, 99.0);
//        stockService.addNewStock(it1, "mail", 55, 13.0);

//        vendorService.addStock("mail", itemService.getAllItems().get(0).getItem_id(), 32.0, 5);
//        vendorService.addStock("post", itemService.getAllItems().get(0).getItem_id(), 12.0, 32);
//        List<Stock> allStock = stockService.getAllStock();
//        List<Vendor> allVendor = stockService.getItemVendors(itemService.getAllItems().get(0).getItem_id());
        ;
//        orderService.createOrder(stockService.getVendorStock("mail").get(0), usersService.getUser("gif").getUserId(), 32);
//        System.out.println(itemService.paginatedGetAllItem(1, 2).get().map(item -> item.getName()).collect(Collectors.toList()));
//        System.out.println(itemService.paginatedGetAllItem(2, 3));
//        System.out.println(itemService.paginatedGetAllItem(2, 3).stream().collect(Collectors.toList()));
        System.out.println(itemService.paginatedGetSearchedItems(0, 6, "choc").getContent().stream().map(item -> item.getName()).collect(Collectors.toList()));
        System.out.println("coffee");
    }


}

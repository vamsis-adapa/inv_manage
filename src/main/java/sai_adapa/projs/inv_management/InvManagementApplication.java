package sai_adapa.projs.inv_management;

import lombok.SneakyThrows;
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
import sai_adapa.projs.inv_management.exceptions.InvalidRequestException;
import sai_adapa.projs.inv_management.exceptions.StockCreationUnsuccessfulException;
import sai_adapa.projs.inv_management.exceptions.StockNotFoundException;
import sai_adapa.projs.inv_management.exceptions.UserAlreadyExistsException;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.orders.Orders;
import sai_adapa.projs.inv_management.repositories.mongo.OrderRepository;
import sai_adapa.projs.inv_management.services.*;

import java.util.InvalidPropertiesFormatException;
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

    @Autowired
    private PaymentService paymentService;


    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }


    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("strawberry");


    }


}

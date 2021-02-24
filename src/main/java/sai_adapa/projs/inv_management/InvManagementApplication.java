package sai_adapa.projs.inv_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
import sai_adapa.projs.inv_management.services.ItemService;
import sai_adapa.projs.inv_management.services.StockService;
import sai_adapa.projs.inv_management.services.VendorService;

@SpringBootApplication
@EnableCaching
public class InvManagementApplication {

    @Autowired
    ItemService itemService;

    @Autowired
    StockService stockService;
    @Autowired
    VendorService vendorService;

    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("strawberry");
//        System.out.println(AuthTools.encodePassword(null));
        Long it1= itemService.addItem("choc", "brown and hard");
        Long it2= itemService.addItem("choc 2", "black and bitter");
        vendorService.addUser("hit","mail","dlak;j","choc");
        vendorService.addUser("meow cat shop", "post","drifkdlf;j; ;dalkfj", "fire" );

        stockService.addNewStock(it1,"mail",55, 12);
        stockService.addNewStock(it2, "post", 34, 99);
        System.out.println("coffee");
    }


}

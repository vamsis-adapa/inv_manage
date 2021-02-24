package sai_adapa.projs.inv_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
import sai_adapa.projs.inv_management.services.ItemService;

@SpringBootApplication
@EnableCaching
public class InvManagementApplication {

    @Autowired
    ItemService itemService;

    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("strawberry");
//        System.out.println(AuthTools.encodePassword(null));
        itemService.addItem("choc", "brown and hard");
        itemService.addItem("choc 2", "black and bitter");
        System.out.println("coffee");


    }


}

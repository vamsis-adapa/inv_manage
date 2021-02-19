package sai_adapa.projs.inv_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.zalando.logbook.Logbook;
import sai_adapa.projs.inv_management.auth.AuthTools;
import sai_adapa.projs.inv_management.item.Item;
import sai_adapa.projs.inv_management.item.ItemRepository;
import sai_adapa.projs.inv_management.item.stock.Stock;
import sai_adapa.projs.inv_management.item.stock.StockRepository;
import sai_adapa.projs.inv_management.users.admin.Admin;
import sai_adapa.projs.inv_management.users.admin.AdminRepository;
import sai_adapa.projs.inv_management.users.user.PreUsers;
import sai_adapa.projs.inv_management.users.user.Users;
import sai_adapa.projs.inv_management.users.user.UsersRepository;
import sai_adapa.projs.inv_management.users.user.UsersService;
import sai_adapa.projs.inv_management.users.vendor.Vendor;
import sai_adapa.projs.inv_management.users.vendor.VendorRepository;

@SpringBootApplication
public class InvManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }




    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        Logbook logbook = Logbook.create();
    }



}

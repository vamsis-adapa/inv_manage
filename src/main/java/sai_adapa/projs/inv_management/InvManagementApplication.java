package sai_adapa.projs.inv_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import sai_adapa.projs.inv_management.item.Item;
import sai_adapa.projs.inv_management.item.ItemRepository;
import sai_adapa.projs.inv_management.users.admin.Admin;
import sai_adapa.projs.inv_management.users.admin.AdminRepository;
import sai_adapa.projs.inv_management.users.user.Users;
import sai_adapa.projs.inv_management.users.user.UsersRepository;
import sai_adapa.projs.inv_management.users.vendor.Vendor;
import sai_adapa.projs.inv_management.users.vendor.VendorRepository;

@SpringBootApplication
public class InvManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }


    //Test bed
    VendorRepository vendorRepository ;
    AdminRepository adminRepository;
    UsersRepository usersRepository;
    ItemRepository itemRepository;

    @Autowired
    protected void setItemRepository(ItemRepository itemRepository) { this.itemRepository = itemRepository;}
    @Autowired
    protected void getVendorRepository(VendorRepository vendorRepository)
    {
        this.vendorRepository = vendorRepository;
    }
    @Autowired
    protected void getAdminRepository(AdminRepository adminRepository) {this.adminRepository = adminRepository;}
    @Autowired
    protected void getUsersRepository(UsersRepository usersRepository){this.usersRepository = usersRepository;}



    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("Strawberry");
        Vendor vendor = new Vendor("klae","straw","cjhp","my","yare");
        vendorRepository.save(vendor);

        Admin admin = new Admin("meow","dam","choc","straw");
        adminRepository.save(admin);

        Users users = new Users("k","choc","prince","hot","king");
        usersRepository.save(users);

        Item item = new Item("klaw","damn","hard");
        itemRepository.save(item);

        System.out.println("King");

    }

}

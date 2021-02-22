package sai_adapa.projs.inv_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
<<<<<<< Updated upstream
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
=======
import org.zalando.logbook.Logbook;
>>>>>>> Stashed changes

@SpringBootApplication
@EnableCaching
public class InvManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvManagementApplication.class, args);
    }



////    Test bed
//
//        @Autowired
//        UsersService usersService;
//    VendorRepository vendorRepository;
//    AdminRepository adminRepository;
//    UsersRepository usersRepository;
//    ItemRepository itemRepository;
//    StockRepository stockRepository;
//
//    @Autowired
//    protected void setStockRepository(StockRepository stockRepository) {
//        this.stockRepository = stockRepository;
//    }
//
//    @Autowired
//    protected void setItemRepository(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
//
//    @Autowired
//    protected void getVendorRepository(VendorRepository vendorRepository) {
//        this.vendorRepository = vendorRepository;
//    }
//
//    @Autowired
//    protected void getAdminRepository(AdminRepository adminRepository) {
//        this.adminRepository = adminRepository;
//    }
//
//    @Autowired
//    protected void getUsersRepository(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }
//
//
=======
>>>>>>> Stashed changes
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
//
//        System.out.println(AuthTools.encodePassword("printer"));
//
//        PreUsers preUsers = new PreUsers("dam","lef","coee","hard");
//
//        usersService.addUser(preUsers);
//        usersService.addUser("choc","draw","kdjf;lkdjf fdlakj; ", "meow");
//
//
//        System.out.println(usersService.verifyUser("draw","meow"));
//        System.out.println(usersService.verifyUser("draw","myadf"));
//        Users k = usersService.getUser("draw");
//        System.out.println(usersService.createSession(k));
//        usersService.endSession(k);
//        System.out.println("Strawberry");


    }

//        Vendor vendor = new Vendor("vend", "straw", "cjhp", "my", "yare");
//        vendorRepository.save(vendor);
//
//        Admin admin = new Admin("admi", "dam", "choc", "straw");
//        adminRepository.save(admin);
//
//        Users users = new Users("thuser", "choc", "prince", "hot", "king");
//        usersRepository.save(users);
//
//        Item item = new Item("meow_item", "damn", "hard");
//        itemRepository.save(item);
//
//        Stock stock = new Stock("calc_meow",vendor,item,3,new java.util.Date());
//        stockRepository.save(stock);

}

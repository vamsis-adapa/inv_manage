package sai_adapa.projs.inv_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.exceptions.StockNotFoundException;
import sai_adapa.projs.inv_management.exceptions.UserNotFoundException;
import sai_adapa.projs.inv_management.model.orders.Orders;
import sai_adapa.projs.inv_management.model.users.Users;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.model.users.io.PreUserWithOrder;
import sai_adapa.projs.inv_management.services.*;
import sai_adapa.projs.inv_management.tools.ResponseHandler;

import javax.servlet.http.HttpServletResponse;

@RestController
public class OrderController {

    StockService stockService;
    VendorService vendorService;
    UsersService usersService;
    ItemService itemService;
    SessionIdentity sessionIdentity;
    OrderService orderService;

    @Autowired
    public OrderController(StockService stockService, VendorService vendorService, UsersService usersService, ItemService itemService) {
        this.stockService = stockService;
        this.vendorService = vendorService;
        this.usersService = usersService;
        this.itemService = itemService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setSessionIdentity(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    //add role auth users
    @RequestMapping(method = RequestMethod.POST, value = {"/order"})
    public Orders handleOrder(@RequestBody PreUserWithOrder preUserWithOrder, HttpServletResponse response) {
        if( !sessionIdentity.verifyIdentity(preUserWithOrder.getEmail()))
        {
            //throw
            return null ;
        }

        try {
            Users users = usersService.getUser(preUserWithOrder.getEmail());
            Vendor vendor = vendorService.getUser((preUserWithOrder.getVendorEmail()));

            if (stockService.checkAvailability(preUserWithOrder.getItemId(), vendor.getEmail(), preUserWithOrder.getNumberOfItems())) {
                return orderService.createOrder(stockService.getParticularStock(vendor.getEmail(), preUserWithOrder.getItemId()), users.getUserId(), preUserWithOrder.getNumberOfItems());
            }
            //throw
            return null;
        }
        catch (UserNotFoundException e)
        {
            ResponseHandler.actionFailed(response, "User not found");
        }
        catch (StockNotFoundException e)
        {
            ResponseHandler.resourceNotFound(response);
        }

        return null;
    }


}

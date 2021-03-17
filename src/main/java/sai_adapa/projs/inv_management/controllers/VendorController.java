package sai_adapa.projs.inv_management.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.exceptions.StockCreationUnsuccessfulException;
import sai_adapa.projs.inv_management.exceptions.StockNotFoundException;
import sai_adapa.projs.inv_management.exceptions.UserNotFoundException;
import sai_adapa.projs.inv_management.model.io.DisplayableOrderVendor;
import sai_adapa.projs.inv_management.model.io.PreVendor;
import sai_adapa.projs.inv_management.model.io.PreVendorWithItem;
import sai_adapa.projs.inv_management.model.io.VendorWithSort;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.users.Vendor;
import sai_adapa.projs.inv_management.services.VendorService;
import sai_adapa.projs.inv_management.tools.ResponseHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class VendorController {

    VendorService vendorService;
    SessionIdentity sessionIdentity;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setEmailVerifier(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @Autowired
    public void setSessionIdentity(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/vendor/logout"})
    public void signOut(@RequestBody PreVendor preVendor, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preVendor.getEmail(), response))
            return;
        try {
            vendorService.endSession(preVendor.getEmail());
        } catch (UserNotFoundException e) {
            ResponseHandler.actionFailed(response, "no user found with email: " + preVendor.getEmail());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/new"})
    public void signUp(@RequestBody PreVendor preVendor, HttpServletResponse response) {
        if (preVendor.getEmail() == null || preVendor.getEmail().equals("")) {
            ResponseHandler.insufficientDetailsInRequest(response);//add message no email
            return;
        }
        try {
            vendorService.addUser(preVendor.getName(), preVendor.getEmail(), preVendor.getDescription(), preVendor.getPasswd());
        } catch (NullPointerException e) {
            ResponseHandler.insufficientDetailsInRequest(response);
            return;
        } catch (DataIntegrityViolationException e) {
            ResponseHandler.userAlreadyExists(response);
        }
        return;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/login"})
    public String signIn(@RequestBody PreVendor preVendor, HttpServletResponse response) {
        try {
            if (vendorService.verifyUser(preVendor.getEmail(), preVendor.getPasswd())) {
                return vendorService.createSession(preVendor.getEmail());
            } else
                ResponseHandler.userVerificationFailed(response);
            return "login failed";
        } catch (UserNotFoundException e) {
            ResponseHandler.userVerificationFailed(response);
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/vendor"})
    public void editVendor(@RequestBody PreVendor preVendor, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preVendor.getEmail(), response))
            return;

        try {
            vendorService.editUser(vendorService.getUser(preVendor.getEmail()), preVendor.getName(), preVendor.getChanged_email(), preVendor.getDescription(), preVendor.getPasswd());
        } catch (UserNotFoundException e) {
            ResponseHandler.actionFailed(response, "user not found");
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/items"})
    public void addItem(@RequestBody PreVendorWithItem preVendorWithItem) {
        vendorService.addNewItem(preVendorWithItem.getItem_name(), preVendorWithItem.getItem_description());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/vendor/stock"})
    public void addStock(@RequestBody PreVendorWithItem preVendorWithItem, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preVendorWithItem.getEmail(), response))
            return;
        try {
            vendorService.addStock(preVendorWithItem.getEmail(), preVendorWithItem.getItem_id(), preVendorWithItem.getCost(), preVendorWithItem.getNum_stock());
        } catch (StockCreationUnsuccessfulException e) {
            ResponseHandler.resourceNotCreated(response);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = {"/vendor/stock/"})
    public void incrementStock(@RequestBody PreVendorWithItem preVendorWithItem, @PathVariable Long id, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preVendorWithItem.getEmail(), response))
            return;

        try {
            vendorService.incrementVendorStock(preVendorWithItem.getEmail(), preVendorWithItem.getItem_id(), preVendorWithItem.getNum_stock());
        } catch (StockNotFoundException e) {
            ResponseHandler.resourceNotFound(response);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/vendor"})
    public Vendor displayVendor(HttpServletResponse response) {
        try {

            return vendorService.displayUser(sessionIdentity.getEmail());
        } catch (UserNotFoundException e) {
            ResponseHandler.actionFailed(response, "User does not exist in system");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = {"/vendor/stock"})
    public void editStock(@RequestBody PreVendorWithItem preVendorWithItem, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preVendorWithItem.getEmail(), response))
            return;
        try {
            vendorService.editStock(preVendorWithItem.getEmail(), preVendorWithItem.getItem_id(), preVendorWithItem.getNum_stock(), preVendorWithItem.getCost());
        } catch (StockNotFoundException e) {
            ResponseHandler.resourceNotFound(response);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/vendor/stock"})
    public void deleteStock(@RequestBody PreVendorWithItem preVendorWithItem, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preVendorWithItem.getEmail(), response))
            return;
        try {
            vendorService.deleteStock(preVendorWithItem.getEmail(), preVendorWithItem.getItem_id());
        } catch (StockNotFoundException e) {
            ResponseHandler.resourceNotFound(response);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/vendor/stock"})
    public List<Stock> viewStock(@RequestBody PreVendorWithItem preVendorWithItem, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preVendorWithItem.getEmail(), response))
            return null;
        try {
            return vendorService.getVendorStock(preVendorWithItem.getEmail());
        } catch (UserNotFoundException e) {
            ResponseHandler.actionFailed(response, "user Not found");
        }
        return null;
    }

    @RequestMapping(value = {"/vendor/orders"})
    public List<DisplayableOrderVendor> viewAllOrders(@RequestBody VendorWithSort vendorWithSort, @RequestParam Integer pageNumber, @RequestParam Integer pageSize, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, vendorWithSort.getVendorEmail(), response))
            return null;
        if (pageNumber == null)
            pageNumber = 0;
        if (pageSize == null)
            pageSize = 0;
        try {


            if (vendorWithSort.getSortDetailsList() == null)
                return vendorService.getOrderReportPaginated(vendorWithSort.getVendorEmail(), pageSize, pageNumber);
            return vendorService.getOrderReportPaginatedAndSorted(vendorWithSort.getVendorEmail(), pageSize, pageNumber, vendorWithSort.getSortDetailsList());
        } catch (UserNotFoundException e) {
            ResponseHandler.actionFailed(response, "getting orders failed");
        }
        return null;
    }
}

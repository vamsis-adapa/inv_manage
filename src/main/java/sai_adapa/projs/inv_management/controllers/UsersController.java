package sai_adapa.projs.inv_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;
import sai_adapa.projs.inv_management.exceptions.*;
import sai_adapa.projs.inv_management.model.io.DisplayableOrder;
import sai_adapa.projs.inv_management.model.io.PreUsers;
import sai_adapa.projs.inv_management.model.io.UserWithRating;
import sai_adapa.projs.inv_management.model.io.UserWithSort;
import sai_adapa.projs.inv_management.model.users.Users;
import sai_adapa.projs.inv_management.services.ItemService;
import sai_adapa.projs.inv_management.services.RatingService;
import sai_adapa.projs.inv_management.services.UsersService;
import sai_adapa.projs.inv_management.tools.ResponseHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UsersController {

    RatingService ratingService;
    SessionIdentity sessionIdentity;
    UsersService usersService;
    ItemService itemService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setEmailVerifier(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @Autowired
    public void setSessionIdentity(SessionIdentity sessionIdentity) {
        this.sessionIdentity = sessionIdentity;
    }

    @RequestMapping(value = {"app_user"})
    public Users getUserDetails(HttpServletResponse response) {
        try {
            return usersService.displayUser(sessionIdentity.getEmail());
        } catch (UserNotFoundException e) {
            ResponseHandler.userDoesNotExist(response);
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/app_user/new"})
    public void signUp(@RequestBody PreUsers preUser, HttpServletResponse response) {
        try {
            usersService.addUser(preUser.getName(), preUser.getEmail(), preUser.getDetails(), preUser.getPasswd());
            ResponseHandler.successfulCreate(response);
        } catch (UserAlreadyExistsException e) {
            ResponseHandler.userAlreadyExists(response);
        } catch (InvalidRequestException e) {
            ResponseHandler.insufficientDetailsInRequest(response);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/app_user/login"})
    public String signIn(@RequestBody PreUsers preUsers, HttpServletResponse response) {
        try {
            if (usersService.verifyUser(preUsers.getEmail(), preUsers.getPasswd())) {
                return usersService.createSession(preUsers.getEmail());
            } else
                ResponseHandler.userVerificationFailed(response);
        } catch (SessionCreateFailedException e) {
            ResponseHandler.userVerificationFailed(response);
        }
        return null;
    }



    @RequestMapping(method = RequestMethod.PATCH, value = {"/app_user"})
    public void editUser(@RequestBody PreUsers preUsers, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preUsers.getEmail(), response))
            return;
        try {
            usersService.editUser(usersService.getUser(preUsers.getEmail()), preUsers.getName(), preUsers.getChanged_email(), preUsers.getDetails(), preUsers.getPasswd());
            ResponseHandler.successfulEdit(response);
            if ( preUsers.getChanged_email()!=null)
            {
                usersService.endSession(usersService.getUser(preUsers.getChanged_email()));
                ResponseHandler.successfulLogOut(response);
            }
        } catch (UserNotFoundException e) {
            ResponseHandler.userDoesNotExist(response);
        }
        catch (DataIntegrityViolationException e)
        {
            ResponseHandler.userAlreadyExists(response,"a user exists with the following data");
        }
        try {
            usersService.endSession(usersService.getUser(preUsers.getEmail()));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = {"/app_user/orders"})
    public List<DisplayableOrder> getOrderReport(@RequestBody UserWithSort userWithSort, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageNumber, HttpServletResponse response) {


        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, userWithSort.getUserEmail(), response))
            return null;
        if (pageNumber == null)
            pageNumber = 0;
        if (pageSize == null)
            pageSize = 5;
        try {
            if (userWithSort.getSortDetailsList() == null) {
                return usersService.getUserOrderReportPaginated(userWithSort.getUserEmail(), pageSize, pageNumber);
            }
            return usersService.getUserOrderReportPaginatedAndSorted(userWithSort.getUserEmail(), pageSize, pageNumber, userWithSort.getSortDetailsList());
        } catch (UserNotFoundException e) {
            ResponseHandler.userDoesNotExist(response);
            return null;
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = {"/app_user/rate"})
    public void rate (@RequestBody UserWithRating user,HttpServletResponse response )
    {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, user.getEmail(), response))
            return;
        try {
            ratingService.rateItem(itemService.getItemById(user.getItemId()),user.getRating(),usersService.getUser(user.getEmail()));
            ResponseHandler.successfulCreate(response);
        }catch (ItemNotFoundException | UserNotFoundException e)
        {
            ResponseHandler.actionFailed(response,"could not process rating");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"/app_user/logout"})
    public void signOut(@RequestBody PreUsers preUsers, HttpServletResponse response) {
        if (!ResponseHandler.verifyUserIdentity(sessionIdentity, preUsers.getEmail(), response))
            return;
        try {
            usersService.endSession(usersService.getUser(preUsers.getEmail()));
        } catch (UserNotFoundException e) {
            ResponseHandler.userDoesNotExist(response);
        }
    }
}

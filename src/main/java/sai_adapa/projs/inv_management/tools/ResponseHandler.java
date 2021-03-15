package sai_adapa.projs.inv_management.tools;

import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;

import javax.servlet.http.HttpServletResponse;

public class ResponseHandler {


    public static void successfulCreate(HttpServletResponse response) {
        response.setStatus(201);
        try {
            response.getWriter().write("Successfully created");
        } catch (Exception e) {
        }
        return;
    }

    public static void userAlreadyExists(HttpServletResponse response) {
        response.setStatus(409);
        try {
            response.getWriter().write("User already exists");
        } catch (Exception e) {
        }
        return;
    }



    public static void resourceNotFound(HttpServletResponse response) {
        response.setStatus(404);
        return;
    }

    public static void resourceNotCreated(HttpServletResponse response) {
        response.setStatus(409);
        return;
    }

    public static void insufficientDetailsInRequest(HttpServletResponse response) {
        response.setStatus(409);
        try {
            response.getWriter().write("Check Request Body");
        } catch (Exception e) {
        }
        return;
    }


    public static void userDoesNotExist(HttpServletResponse response) {
        response.setStatus(401);
        try {
            response.getWriter().write("user does not exist");
        } catch (Exception e) {
        }
        return;
    }

    public static void userVerificationFailed(HttpServletResponse response) {
        response.setStatus(401);
        try {
            response.getWriter().write("verification failed");
        } catch (Exception e) {
        }
        return;
    }

    public static void userIdentityNotValid(HttpServletResponse response) {
        response.setStatus(403);
        try {
            response.getWriter().write("You do not have the permission to do this action");
        } catch (Exception e) {
        }
    }

    public static Boolean verifyUserIdentity(SessionIdentity sessionIdentity, String email, HttpServletResponse response) {
        if (sessionIdentity.verifyIdentity(email)) {
            return true;
        } else {
            userIdentityNotValid(response);
            return false;
        }

    }


}

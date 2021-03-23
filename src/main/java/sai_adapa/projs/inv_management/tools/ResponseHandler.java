package sai_adapa.projs.inv_management.tools;

import sai_adapa.projs.inv_management.auth.identity.SessionIdentity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseHandler {


    public static void successfulCreate(HttpServletResponse response) {
        response.setStatus(201);
        try {
            response.getWriter().write("Successfully created");
        } catch (Exception ignored) {
        }
    }

    public static void successfulEdit(HttpServletResponse response) {
        response.setStatus(200);
        try {
            response.getWriter().write("successfulEdit\n");
        } catch (IOException ignored) {
        }
    }

    public static void successfulLogOut(HttpServletResponse re)
    {
        re.setStatus(200);
        try {
            re.getWriter().write("successfully logged out\n");
        } catch (IOException ignored) {
        }
    }


    public static void userAlreadyExists(HttpServletResponse response) {
        response.setStatus(409);
        try {
            response.getWriter().write("User already exists");
        } catch (Exception e) {
        }
        return;
    }
    public static void userAlreadyExists(HttpServletResponse response, String msg) {
        response.setStatus(409);
        try {
            response.getWriter().write(msg);
        } catch (Exception e) {
        }
        return;
    }


    public static void notEnoughResources(HttpServletResponse response, String msg) {
        response.setStatus(400);
        try {
            response.getWriter().write(msg);
        } catch (Exception ignored) {
        }
    }

    public static void resourceNotFound(HttpServletResponse response,String message) {
        response.setStatus(404);
        try {
            response.getWriter().write(message);
        } catch (Exception ignored) {
        }

    }

    public static void resourceNotCreated(HttpServletResponse response, String msg) {
        response.setStatus(409);
        try {
            response.getWriter().write(msg);
        } catch (Exception ignored) {
        }
    }

    public static void insufficientDetailsInRequest(HttpServletResponse response) {
        response.setStatus(409);
        try {
            response.getWriter().write("Check Request Body");
        } catch (Exception e) {
        }
        return;
    }

    public static void actionFailed(HttpServletResponse response, String msg) {
        response.setStatus(500);
        try {
            response.getWriter().write(msg);
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
      try {


          if (sessionIdentity.verifyIdentity(email)) {
              return true;
          } else {
              userIdentityNotValid(response);
              return false;
          }
      }catch (NullPointerException e)
      {
          userIdentityNotValid(response);
          return false;
      }

    }


}

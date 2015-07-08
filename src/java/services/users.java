/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import db.UserManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import model.Constants;
import model.json.User;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class users {

    /**
     * Creates a new instance of GenericResource
     */
    public users() {
    }

    /**
     * Check username and password
     * @param email
     * @param password
     * @param request
     * @return String token if username and password is valid, else returns Status code Not Acceptable(406)
     */
    @POST
    @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password, @Context HttpServletRequest request) {
        User user = UserManager.getInstance().login(email, password);
        if(user == null){
            return Response.status(Status.NOT_ACCEPTABLE).entity("Invalide username or password").build();
        }
        
        request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
        return Response.status(Status.OK).build();
        
    }

    /**
     * Register new user
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param primaryNumber
     * @param cardNumber
     * @param mobileNumber
     * @param request
     * @return String token if everything is okay, else returns status code Conflict(409)
     */
    @POST
    @Path("/register")
    public Response register(@FormParam("email") String email, 
                            @FormParam("password") String password, 
                            @FormParam("first_name") String firstName,
                            @FormParam("last_name") String lastName,
                            @FormParam("primary_number") String primaryNumber,
                            @FormParam("mobile_bumber") String mobileNumber,
                            @FormParam("card_number") String cardNumber,
                            @Context HttpServletRequest request) {
        User user = UserManager.getInstance().register(email, password, firstName, lastName, mobileNumber, primaryNumber, cardNumber);
        if(user == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
        return Response.status(Status.OK).build();
    }
    
    /**
     * 
     * @param request
     * @return 
     */
    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request){
        request.getSession().removeAttribute(Constants.SESSION_USER_KEY);
        return Response.ok().build();
    }
    
}

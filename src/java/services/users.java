/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import db.UserManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import logic.AuthManager;
import model.json.User;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class users {

    /**
     * Creates a new instance of GenericResource
     */
    public users() {
    }

    /**
     * Check username and password
     * @param user
     * @return String token if username and password is valid, else returns Status code Not Acceptable(406)
     */
    @POST
    @Path("/login")
    public Response login(User user) {
        Status status = UserManager.getInstance().login(user);
        System.out.println("status: "+status);
        Response.ResponseBuilder builder = Response.status(status);
        if(status == Status.OK){
            String token = AuthManager.getInstance().loginUser(user);
            NewCookie c = new NewCookie("token", token, "/", "localhost", "", 1*60*60*24, false);
            builder.cookie(c);
        }else{
            builder.entity("Invalide username or password");
        }
        return builder.build();
    }

    /**
     * Register new user
     * @param user
     * @return String token if everything is okay, else returns status code Conflict(409)
     */
    @POST
    @Path("/register")
    public Response register(User user) {
        Status status = UserManager.getInstance().register(user);
        Response.ResponseBuilder builder = Response.status(status);
        if(status == Status.CREATED){
            String token = AuthManager.getInstance().loginUser(user);
            builder.cookie(new NewCookie("token", token));
        }
        return builder.build();
    }
    
    /**
     * 
     * @param token
     * @return 
     */
    @POST
    @Path("/logout")
    public Response logout(@CookieParam("token") String token){
        if(token == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        
        AuthManager.getInstance().logoutUser(token);
        return Response.ok().build();
    }
    
}

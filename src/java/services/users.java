/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import model.json.User;
import model.json.UserResponse;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class users {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public users() {
    }

    /**
     * Check username and password
     * @return String token if username and password is valid, else returns Status code Not Acceptable(406)
     */
    @POST
    @Path("/login")
    public Response login(User user) {
        // check username and password
        // generate new UUID token
        UserResponse res = new UserResponse();
        res.token = "user token";
        return Response.ok(res).build();
    }

    /**
     * Register new user
     * @return String token if everything is okay, else returns status code Conflict(409)
     */
    @POST
    @Path("/register")
    public Response register(User user) {
        // register user
        System.out.println("into register");
        UserResponse res = new UserResponse();
        res.description = "username already in use";
        return Response.status(Response.Status.CONFLICT).entity(res).build();
    }
    
    /**
     * Get user info with token
     * @param token
     * @return Status OK(200) and User data or Status Not Found(404)
     */
    @GET
    @Path("/{token}")
    public Response checkToken(@PathParam("token") String token){
        return Response.ok(/* User object here */).build();
    }
    
}

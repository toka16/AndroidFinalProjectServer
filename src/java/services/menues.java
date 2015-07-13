/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.ConnectToDB;
import db.MenuManager;
import db.ProductManager;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Constants;
import model.json.Menu;
import model.json.Product;
import model.json.User;
import model.json.User.Role;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class menues {

    /**
     * Creates a new instance of menues
     */
    public menues() {
    }

    /**
     * Retrieves representation of an instance of services.menues
     * @param request
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/version")
    public Response getVersion(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(String.valueOf(MenuManager.getInstance().getVersion())).build();
    }

    /**
     * PUT method for updating or creating an instance of menues
     * @param request
     * @return 
     */
    @GET
    public Response getMenues(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        System.out.println("get all menus");
        Menu[] menus = ConnectToDB.getMenus();
        System.out.println("after all");
        return Response.status(Response.Status.OK).entity(menus).build();
    }
    
    
    @GET
    @Path("/{id}/containing_products")
    public Response getMenuContainingProducts(@PathParam("id") int id, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        
        return Response.ok(MenuManager.getInstance().getMenuContainingProducts(id)).build();
    }
    
    @DELETE
    @Path("/{menuID}/remove_product/{productID}")
    public Response removeMenuProduct(@PathParam("menuID") int menuID, @PathParam("productID") int productID, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(user.role != Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        
        System.out.println("removing product (" + productID + ") from menu (" + menuID + ")");
        
        return Response.ok().build();
    }
    
    @POST
    @Path("/{menuID}/add_product/{productID}")
    public Response addMenuProduct(@PathParam("menuID") int menuID, @PathParam("productID") int productID, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(user.role != Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        
        System.out.println("adding product (" + productID + ") from menu (" + menuID + ")");
        
        return Response.ok().build();
    }
}

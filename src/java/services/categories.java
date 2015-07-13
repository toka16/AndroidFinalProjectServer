/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.ConnectToDB;
import db.CategoryManager;
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
import model.json.Category;
import model.json.User;
import model.json.User.Role;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/category")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class categories {


    /**
     * Creates a new instance of categories
     */
    public categories() {
    }

    /**
     * Retrieves representation of an instance of services.categories
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
        return Response.ok(String.valueOf(ConnectToDB.getVersionNumber(ConnectToDB.VERSION_CATEGORY))).build();
    }

    /**
     * PUT method for updating or creating an instance of categories
     * @param request
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    public Response getCategories(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        Category[] categories = ConnectToDB.getCategories();

        return Response.status(Response.Status.OK).entity(categories).build();
    }
    
    @POST
    @Path("/{catID}/add_product/{productID}")
    public Response addCategoryProduct(@PathParam("catID") int categoryID, @PathParam("productID") int productID, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(user.role != Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        
        ConnectToDB.fillMapCategoryProduct(categoryID, productID);
        System.out.println("add product(" + productID + ") to category (" + categoryID + ")");
        
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{catID}/remove_product/{productID}")
    public Response removeCategoryProduct(@PathParam("catID") int categoryID, @PathParam("productID") int productID, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(user.role != Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        
        ConnectToDB.deleteProductFromCategory(categoryID, productID);
        System.out.println("remove product(" + productID + ") from category (" + categoryID + ")");
        return Response.ok().build();
    }
}

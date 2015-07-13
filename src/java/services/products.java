/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.ConnectToDB;
import db.CategoryManager;
import db.ProductManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Constants;
import model.json.Category;
import model.json.Product;
import model.json.User;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class products {

    /**
     * Creates a new instance of products
     */
    public products() {
    }

    /**
     * Retrieves representation of an instance of services.products
     * @param request
     * @return version number
     */
    @GET
    @Path("/version")
    public Response getVersion(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(String.valueOf(ConnectToDB.getVersionNumber(ConnectToDB.VERSION_PRODUCT))).build();
    }

    /**
     * PUT method for updating or creating an instance of products
     * @param request
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    public Response getProducts(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        Product[] products = ConnectToDB.allProducts();
        System.out.println("products: "+products);

        return Response.ok(products).build();
    }
    
    @GET
    @Path("/{productID}/addable_categories")
    public Response getProductCategories(@PathParam("productID") int productID, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        Category[] categories = ConnectToDB.allCategoriesOutOfProduct(productID);
        
        return Response.ok(categories).build();
    }
    
    @GET
    @Path("/{productID}/removable_categories")
    public Response getProductRemovableCategories(@PathParam("productID") int productID, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        Category[] categories = ConnectToDB.allCategoriesByProduct(productID);
        
        return Response.ok(categories).build();
    }
    
}

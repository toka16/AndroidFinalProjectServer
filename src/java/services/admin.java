/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import logic.AuthManager;
import model.json.Product;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/admin")
@Consumes("application/json")
@Produces("application/json")
public class admin {

    /**
     * Creates a new instance of admin
     */
    public admin() {
    }

    @POST
    @Path("/products")
    public Response addProduct(@CookieParam("token") String token, Product product){
        if(!AuthManager.getInstance().checkAdminToken(token))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        
        if(!validateProduct(product))
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        
        // save product
        
        return Response.ok().build();
    }

    private boolean validateProduct(Product product) {
        return true;
    }
}

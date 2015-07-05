/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import logic.AuthManager;
import model.json.Product;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public class products {

    /**
     * Creates a new instance of products
     */
    public products() {
    }

    /**
     * Retrieves representation of an instance of services.products
     * @param token
     * @return version number
     */
    @GET
    @Path("/version")
    public Response getVersion(@CookieParam("token") String token) {
        if(!AuthManager.getInstance().checkUserToken(token)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok("16").build();
    }

    /**
     * PUT method for updating or creating an instance of products
     * @param token
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    public Response getProducts(@CookieParam("token") String token) {
        if(!AuthManager.getInstance().checkUserToken(token))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        
        Product[] arr = new Product[]{new Product("name1", "desc1asdfghjklasdfghjklasdf", 1.0), new Product("name2", "desc2", 2.0)};
        
        return Response.ok(arr).build();
    }
}

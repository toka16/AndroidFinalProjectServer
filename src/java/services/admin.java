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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
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

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of admin
     */
    public admin() {
    }

    @POST
    public Response addProduct(@CookieParam("token") String token, Product product){
        // check token and save product
        return Response.ok().build();
    }
}

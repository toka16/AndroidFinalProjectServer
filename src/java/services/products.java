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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public class products {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of products
     */
    public products() {
    }

    /**
     * Retrieves representation of an instance of services.products
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/version")
    public Response getVersion() {
        //TODO return proper representation object
        return Response.ok("16").build();
    }

    /**
     * PUT method for updating or creating an instance of products
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    public Response getProducts() {
        
        return Response.ok(/* full json object goes here */).build();
    }
}

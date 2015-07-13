/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Constants;
import model.json.Order;
import model.json.User;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("shop")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class shop {

    /**
     * Creates a new instance of shop
     */
    public shop() {
    }

    /**
     * Make new order
     * @param order
     * @param request
     * @return an instance of java.lang.String
     */
    @POST
    public Response orderProducts(Order order, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        System.out.println("user: "+user.first_name+" ordered products: "+order.toString());
        
        return Response.ok().build();
    }

}

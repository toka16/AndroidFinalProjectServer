/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.ConnectToDB;
import db.NewsManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Constants;
import model.json.News;
import model.json.User;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class news {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of news
     */
    public news() {
    }

    /**
     * Retrieves representation of an instance of services.news
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
        return Response.ok(String.valueOf(NewsManager.getInstance().getVersion())).build();
    }

    /**
     * PUT method for updating or creating an instance of news
     * @param request
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    public Response getNews(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        System.out.println("get all news");
        News[] news = ConnectToDB.getNews();
        System.out.println("after all");
        return Response.status(Response.Status.OK).entity(news).build();
    }
}

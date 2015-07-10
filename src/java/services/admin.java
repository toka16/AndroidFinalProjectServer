/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import db.CategoryManager;
import db.MenuManager;
import db.NewsManager;
import db.ProductManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Constants;
import model.json.Category;
import model.json.Menu;
import model.json.News;
import model.json.Product;
import model.json.User;

/**
 * REST Web Service
 *
 * @author toka
 */
@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class admin {

    /**
     * Creates a new instance of admin
     */
    public admin() {
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password, @Context HttpServletRequest request){
        if(username.equals("admin") && password.equals("admin")){
            User user = new User();
            user.role = User.Role.ADMIN;
            user.first_name = "tornike";
            request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalide Username or Password").build();
    }
    
    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request){
        request.getSession().removeAttribute(Constants.SESSION_USER_KEY);
        return Response.ok().build();
    }

    @POST
    @Path("/product")
    public Response addProduct(Product product, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can add new Product").build();
        
        if(!validateProduct(product))
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
        
        if(ProductManager.getInstance().addProduct(product))
            return Response.ok(product).build();
        
        return Response.status(Response.Status.CONFLICT).entity("'" + product.name + "' is already in use").build();
    }
    
    @PUT
    @Path("/product")
    public Response updateProduct(Product product, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can change Product").build();
        
        if(!validateProduct(product))
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
        
        if(ProductManager.getInstance().updateProduct(product))
            return Response.ok(product).build();
        
        return Response.status(Response.Status.CONFLICT).entity("can not update product").build();
    }
    
    @DELETE
    @Path("/product/{id}")
    public Response deleteProduct(@PathParam("id") int id, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can delete Product").build();
        
        ProductManager.getInstance().delete(id);
        return Response.ok().build();
    }
    
    @POST
    @Path("/menu")
    public Response addMenu(Menu menu, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can add new Menu").build();
        
        if(!validateMenu(menu))
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
        
        if(MenuManager.getInstance().addMenu(menu))
            return Response.ok(menu).build();
        
        return Response.status(Response.Status.CONFLICT).entity("'" + menu.name + "' is already in use").build();
    }
    
    @PUT
    @Path("/menu")
    public Response updateMenu(Menu menu, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can change Menu").build();
        
        if(!validateMenu(menu))
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
        
        if(MenuManager.getInstance().updateMenu(menu))
            return Response.ok(menu).build();
        
        return Response.status(Response.Status.CONFLICT).entity("can not update menu").build();
    }
    
    @DELETE
    @Path("/menu/{id}")
    public Response deleteMenu(@PathParam("id") int id, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can delete Menu").build();
        
        MenuManager.getInstance().delete(id);
        return Response.ok().build();
    }
    
    
    @POST
    @Path("/news")
    public Response addNews(News news, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can add new News").build();
        
        if(!validateNews(news))
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
        
        if(NewsManager.getInstance().addNews(news))
            return Response.ok(news).build();
        
        return Response.status(Response.Status.CONFLICT).entity("'" + news.name + "' is already in use").build();
    }
    
    @PUT
    @Path("/news")
    public Response updateNews(News news, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can change News").build();
        
        if(!validateNews(news))
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
        
        if(NewsManager.getInstance().updateNews(news))
            return Response.ok(news).build();
        
        return Response.status(Response.Status.CONFLICT).entity("can not update news").build();
    }
    
    @DELETE
    @Path("/news/{id}")
    public Response deleteNews(@PathParam("id") int id, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can delete News").build();
        
        NewsManager.getInstance().delete(id);
        return Response.ok().build();
    }
    
    @POST
    @Path("/category")
    public Response addCategory(Category category, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can add new Category").build();
        
        if(!validateCategory(category))
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
        
        if(CategoryManager.getInstance().addCategory(category))
            return Response.ok(category).build();
        
        return Response.status(Response.Status.CONFLICT).entity("'" + category.name + "' is already in use").build();
    }
    
    @PUT
    @Path("/category")
    public Response updateCategory(Category category, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can change Category").build();
        
        if(!validateCategory(category))
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields are required").build();
        
        if(CategoryManager.getInstance().updateCategory(category))
            return Response.ok(category).build();
        
        return Response.status(Response.Status.CONFLICT).entity("can not change category").build();
    }
    
    
    @DELETE
    @Path("/category/{id}")
    public Response deleteCategory(@PathParam("id") int id, @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can delete Category").build();
        
        CategoryManager.getInstance().delete(id);
        return Response.ok().build();
    }

    private boolean validateProduct(Product product) {
        return true;
    }

    private boolean validateMenu(Menu menu) {
        return true;
    }

    private boolean validateNews(News news) {
        return true;
    }

    private boolean validateCategory(Category category) {
        return true;
    }
}

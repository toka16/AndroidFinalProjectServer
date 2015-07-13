/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.ConnectToDB;
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
//        User user = ConnectToDB.getUser(username, password);
//        if(user == null || user.role != User.Role.ADMIN)
//            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalide Username or Password").build();
        User user = new User();
        user.role = User.Role.ADMIN;
        
        request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);

        return Response.ok().build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/register")
    public Response register(@FormParam("email") String email, 
                            @FormParam("password") String password, 
                            @FormParam("first_name") String firstName,
                            @FormParam("last_name") String lastName,
                            @FormParam("primary_number") String primaryNumber,
                            @FormParam("mobile_number") String mobileNumber,
                            @FormParam("card_number") String cardNumber,
                            @FormParam("role") String role,
                            @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        if(user == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.role != User.Role.ADMIN)
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Only Admin can add new Product").build();
        
        User new_user = new User();
        new_user.email = email;
        new_user.password = password;
        new_user.first_name = firstName;
        new_user.last_name = lastName;
        new_user.mobile_number = mobileNumber;
        new_user.card_number = cardNumber;
        new_user.primary_number = primaryNumber;
        new_user.role = User.Role.valueOf(role);
        ConnectToDB.insertNewUser(new_user);
        System.out.println("new user: "+new_user+", role: "+role);
        
        return Response.ok().build();
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
        System.out.println("new product: "+product);
        ConnectToDB.insertNewProduct(product);
        System.out.println("product added: "+product);
        
        return Response.ok(product).build();
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
        
        System.out.println("inserting product");
        ConnectToDB.updateProduct(product);
        System.out.println("after insert");
        return Response.ok(product).build();        
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
        
        ConnectToDB.deleteProduct(id);
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
        menu.products = new int[0];
        ConnectToDB.insertNewMenu(menu);
        return Response.ok(menu).build();
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
        
        ConnectToDB.updateMenu(menu);
        return Response.ok(menu).build();        
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
        
        ConnectToDB.deleteMenu(id);
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
        
        ConnectToDB.insertNews(news);
        return Response.ok(news).build();
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
        
        ConnectToDB.updateNews(news);
        return Response.ok(news).build();
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
        
        ConnectToDB.deleteNews(id);
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
        category.products = new int[0];
        ConnectToDB.insertNewCategory(category);
        return Response.ok(category).build();
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
        
        ConnectToDB.updateCategory(category);
        return Response.ok(category).build();
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
        
        ConnectToDB.deleteCategory(id);
        return Response.ok().build();
    }

    private boolean validateProduct(Product product) {
        return !(product.name == null || product.description == null || product.price <= 0);
    }

    private boolean validateMenu(Menu menu) {
        return !(menu.name == null || menu.description == null || menu.price <= 0);
    }

    private boolean validateNews(News news) {
        return !(news.name == null || news.description == null || news.from_date > news.to_date);
    }

    private boolean validateCategory(Category category) {
        return category.name != null;
    }
}

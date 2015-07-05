/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import javax.ws.rs.core.Response.Status;
import model.json.User;

/**
 *
 * @author toka
 */
public class UserManager {
    
    private static UserManager manager;
    private UserManager(){}
    
    public static UserManager getInstance(){
        if(manager == null)
            manager = new UserManager();
        
        return manager;
    }
    
    public Status login(User user){
        if(user == null || user.username == null || user.password == null){
            return Status.BAD_REQUEST;
        }
        if(user.username.equals("bla")){
            return Status.NOT_ACCEPTABLE;
        }
        user.email = "temp@bla.com";
        user.mobileNumber = "555555555";
        user.primaryNumber = "12345678910";
        user.role = User.Role.ADMIN;
        
        return Status.OK;
    }

    public Status register(User user) {
        return Status.CONFLICT; // Status.CREATED;
    }
    
    
}

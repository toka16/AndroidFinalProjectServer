/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

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
    

    public User login(String email, String password) {
        if(!email.contains("@"))
            return null;
        
        User user = new User();
        user.email = email;
        user.password = password;
        user.mobile_number = "555555555";
        user.primary_number = "12345678910";
        user.first_name = "saxeli";
        user.last_name = "gvari";
        user.card_number = "1234-5678-8765-4321";
        
        return user;
    }

    public User register(String password, String email, String firstName, String lastName, String mobileNumber, String primaryNumber, String cardNumber) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.first_name = firstName;
        user.last_name = lastName;
        user.mobile_number = mobileNumber;
        user.primary_number = primaryNumber;
        user.card_number = cardNumber;
        
        return user;
    }
    
    
}

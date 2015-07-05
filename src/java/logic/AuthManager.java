/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import model.json.User;

/**
 *
 * @author toka
 */
public class AuthManager {
    
    private final Map<String, User> users;
    
    private static AuthManager manager;
    private AuthManager(){
        users = new ConcurrentHashMap<>();
    }
    
    public static AuthManager getInstance(){
        if(manager == null)
            manager = new AuthManager();
        
        return manager;
    }
    
    public String loginUser(User user){
        String token = UUID.randomUUID().toString();
        users.put(token, user);
        return token;
    }
    
    public User getUser(String token){
        return users.get(token);
    }

    public void logoutUser(String token) {
        users.remove(token);
    }
    
    public boolean checkUserToken(String token){
        return users.containsKey(token);
    }
    
    public boolean checkAdminToken(String token){
        User user = users.get(token);
        return (user != null && user.role == User.Role.ADMIN);
    }
}

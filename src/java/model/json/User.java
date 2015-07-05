/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.json;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author toka
 */
@XmlRootElement
public class User {
    @XmlElement
    public String username;
    
    @XmlElement
    public String password;
    
    @XmlElement
    public String email;
    
    @XmlElement
    public String primaryNumber;
    
    @XmlElement
    public String mobileNumber;
    
    public Role role;
            
    public static enum Role{
        USER,
        ADMIN
    }
    
}

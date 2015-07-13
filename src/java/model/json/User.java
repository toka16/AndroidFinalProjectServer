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
    public String email;
    
    @XmlElement
    public String password;
    
    @XmlElement
    public String first_name;
    
    @XmlElement
    public String last_name;
    
    @XmlElement
    public String primary_number;
    
    @XmlElement
    public String card_number;
    
    @XmlElement
    public String mobile_number;
    
    public Role role;
    
    
    public static enum Role{
        ADMIN,
        USER
    }
    
    @Override
    public String toString(){
        return email+", "+first_name+", "+last_name+", "+role;
    }
}

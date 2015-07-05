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
public class Product {
    @XmlElement
    public String name;
    
    @XmlElement
    public String description;
    
    @XmlElement
    public double price;
    
    public Product(){}
    
    public Product(String name, String desc, double price){
        this.name = name;
        description = desc;
        this.price = price;
    }
}

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
    public int id;
    
    @XmlElement
    public String name;
    
    @XmlElement
    public String description;
    
    @XmlElement
    public double price;
    
    @XmlElement
    public String image_link;
    
    public Product(){}
    
    public Product(int id, String name, String desc, double price){
        this.id = id;
        this.name = name;
        description = desc;
        this.price = price;
    }
    
}

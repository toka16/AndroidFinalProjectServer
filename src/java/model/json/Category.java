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
public class Category {
    @XmlElement
    public String name;
    
    @XmlElement
    public String[] products;
    
    public Category(){}
    public Category(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return "category => " + name + " ["+products.length+"]";
    }
}

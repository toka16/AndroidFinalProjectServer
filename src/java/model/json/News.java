/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.json;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author toka
 */
@XmlRootElement
public class News {
    @XmlElement
    public int id;
    
    @XmlElement
    public String name;
    
    @XmlElement
    public String description;
    
    @XmlElement
    public long from_date;
    
    @XmlElement
    public long to_date;
    
    public News(){}
    
    public News(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    public Date getFromDate(){
        return new Date(from_date);
    }
    
    public Date getToDate(){
        return new Date(to_date);
    }
    
    @Override
    public String toString(){
        return "news => " + "( " + name+" : "+description+" ) "+getFromDate()+" --> "+getToDate();
    }
}

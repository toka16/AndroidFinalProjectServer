/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Administrator
 */
public class News {
    
    private String name;
    private String description;
    private String fromDate;
    private String toDate;
    
    public News(String name){
        
    }
    
    public void setName(String newName){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setDescription(String newDescription){
        description = newDescription;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setFromDate(String newFromDate){
        fromDate = newFromDate;
    }
    
    public String getFromDate(){
        return fromDate;
    }
    
    public void setToDate(String newToDate){
        toDate = newToDate;
    }
    
    public String getToDate(){
        return toDate;
    }
    
    @Override
    public String toString(){
        return name + " \n " +  description + " \n " + fromDate + " -- " + toDate;
    }
}

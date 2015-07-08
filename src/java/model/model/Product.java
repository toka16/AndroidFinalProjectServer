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
public class Product implements MenuComponent {
    
    private String name;
    private String description;
    private double price;
    private String imageLink;
    private String categoryName;

    public Product(String name){
        this.name = name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setPrice(double newPrice){
        price = newPrice;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public String getName(){
        return name;
    }
    
    public void setImageLink(String imageLink){
        this.imageLink = imageLink;
    }
    
    public String getImageLink(){
        return imageLink;
    }
    
    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }
    
    public String getCategoryName(){
        return categoryName;
    }
    
    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString(){
        return name + " \n " + description + " \n " + price;
    }
}

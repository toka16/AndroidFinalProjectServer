/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Menu implements MenuComponent {
    
    private String name;
    private String description;
    private int menuID;
    private List<MenuComponent> menuComponentList;
    
    public Menu(String name){
        menuComponentList = new ArrayList<>();
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void addMenuComponent(MenuComponent component){
        menuComponentList.add(component);
    }
    
    public void removeMenuComopent(MenuComponent component){
        menuComponentList.remove(component);
    }
    
    public void removeMenuComponent(int index){
        menuComponentList.remove(index);
    }
    
    public MenuComponent getComponent(int index){
        return menuComponentList.get(index);
    }
    
    public Iterator<MenuComponent> getComponentsIterator(){
        return menuComponentList.iterator();
    }
    
    public void setMenuID(int menuID){
        this.menuID = menuID;
    }
    
    public int getMenuID(){
        return menuID;
    }
    
    @Override
    public double getPrice() {
        double totalPrice = 0;
        for(int i = 0; i < menuComponentList.size(); i++){
            MenuComponent component = menuComponentList.get(i);
            totalPrice += component.getPrice();
        }
        return totalPrice;
    }
    
    @Override
    public String toString(){
        return name + " \n " + description;
    }
}

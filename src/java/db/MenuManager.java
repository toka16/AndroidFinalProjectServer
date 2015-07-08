/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import model.json.Menu;

/**
 *
 * @author toka
 */
public class MenuManager {
    private ArrayList<Menu> menus;
    
    private static MenuManager manager;
    
    private int version;
    
    private MenuManager(){
        version = 1;
        
        menus = new ArrayList<>();
        int num = 25;
        for(int i=0; i<num; i++){
            Menu m = new Menu("name"+i, "description"+i, i);
            m.image_link = "http://cdn.elite-strategies.com/wp-content/uploads/2014/04/symbol-icon-for-menu-navigation.png";
            menus.add(m);
        }
        
    }

    public static MenuManager getInstance() {
        if(manager == null)
            manager = new MenuManager();
        
        return manager;
    }
    
    public int getVersion(){
        return version;
    }
    

    public Menu[] getMenues() {
        Menu[] arr = new Menu[menus.size()];
        return menus.toArray(arr);
    }
    
    public boolean addMenu(Menu menu){
        Menu m = findMenu(menu.name);
        if(m != null)
            return false;
        
        version++;
        return menus.add(menu);
    }
    
    public void delete(String name){
        Menu menu = findMenu(name);
        if(menu != null){
            menus.remove(menu);
            version++;
        }
    }
    
    
    private Menu findMenu(String name){
        for (Menu menu : menus) {
            if (menu.name.equals(name)) {
                return menu;
            }
        }
        return null;
    }
    
}

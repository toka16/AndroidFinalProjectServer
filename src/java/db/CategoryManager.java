/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import model.json.Category;

/**
 *
 * @author toka
 */
public class CategoryManager{
    private ArrayList<Category> categories;
    
    private static CategoryManager manager;
    
    private int version;
    
    private CategoryManager(){
        version = 1;
        
        categories = new ArrayList<>();
        int num = 25;
        for(int i=0; i<num; i++)
            categories.add(new Category("name"+i));
        
    }
    
    public int getVersion(){
        return version;
    }

    public static CategoryManager getInstance() {
        if(manager == null)
            manager = new CategoryManager();
        
        return manager;
    }
    
    public Category[] getCategories() {
        Category[] arr = new Category[categories.size()];
        return categories.toArray(arr);
    }
    
    public boolean addCategory(Category category){
        Category cat = findCategory(category.name);
        if(cat != null)
            return false;
        
        version++;
        return categories.add(category);
    }

    public void delete(String name) {
        Category category = findCategory(name);
        if(category != null){
            categories.remove(category);
            version++;
        }
    }
    
    private Category findCategory(String name){
        for (Category category : categories) {
            if (category.name.equals(name)) {
                return category;
            }
        }
        return null;
    }

}

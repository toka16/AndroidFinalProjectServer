/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import model.json.Product;

/**
 *
 * @author toka
 */
public class ProductManager {
    private ArrayList<Product> products = new ArrayList<>();
    
    private static ProductManager manager;
    
    private int version;
    
    private ProductManager(){
        version = 1;
        
        int num = 25;
        for(int i=0; i<num; i++)
            products.add(new Product(i, "name"+i, "description"+i, i));
        
    }

    public static ProductManager getInstance() {
        if(manager == null)
            manager = new ProductManager();
        
        return manager;
    }
    
    public int getVersion(){
        return version;
    }
    

    public synchronized Product[] getProducts() {
        Product[] arr = new Product[products.size()];
        return products.toArray(arr); 
    }
    
    
    public synchronized boolean addProduct(Product product){
        Product pro = findProduct(product.name);
        if(pro != null)
            return false;
        
        version++;
        return products.add(product);
    }
    
    public synchronized void delete(int id){
        Product pro = findProduct(id);
        if(pro != null){
            products.remove(pro);
            version++;
        }
    }
    
    private Product findProduct(String name){
        for (Product product : products) {
            if (product.name.equals(name)) {
                return product;
            }
        }
        return null;
    }
    
    private Product findProduct(int id){
        for (Product product : products) {
            if (product.id == id) {
                return product;
            }
        }
        return null;
    }

    public boolean updateProduct(Product product) {
        Product p = findProduct(product.id);
        if(p == null)
            return false;
        
        products.remove(p);
        products.add(product);
        version++;
        return true;
    }
    
}

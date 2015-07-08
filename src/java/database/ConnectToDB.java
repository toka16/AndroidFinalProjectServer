/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.model.Category;
import model.model.Menu;
import model.model.News;
import model.model.Product;
import model.model.User;

/**
 *
 * @author Administrator
 */
public class ConnectToDB {
    
    private static ConnectionPoolToDB connectionPool = new ConnectionPoolToDB(DBInfo.DRIVER_CLASS_NAME, 
                                                DBInfo.DB_SERVER_URL, DBInfo.DB_USER_NAME, DBInfo.DB_PASSWORD);
    
    /**
     * The method adds new user into database;
     * @param user 
     */
    public static void insertNewUser(User user){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call insert_user(?, ?, ?, ?, ?, ?, ?)");
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getCardNumber());
            stmt.setString(7, user.getPrimaryNumebr());
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * The method adds new user into database. It is synchronized because id of product must couple to id of category,
     * this is another functionality and may thread manager stops current thread at this time and start other product 
     * saving thread. After the old thread continues. So numeric of id will not be correct.
     * @param product 
     */
    public static synchronized void insertNewProduct(Product product){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call insert_product(?, ?, ?, ?, ?)");
            
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getImageLink());
            stmt.setString(5, product.getCategoryName());
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * The method adds new category into database.
     * @param category 
     */
    public static void insertNewCategory(Category category){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call insert_category(?)");
            
            stmt.setString(1, category.getName());
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * The method adds new menu into database.
     * @param menu 
     */
    public static void insertNewMenu(Menu menu){
        
    }
    
    /**
     * The method maps menu_id and product_id into helper table.
     * @param menuID
     * @param productID 
     */
    private static void fillMapMenuProduct(int menuID, int productID){
        
    }
    
    /**
     * The method maps category_id and product_id into helper table.
     * @param categoryID
     * @param productID 
     */
    private static void fillMapCategoryProduct(int categoryID, int productID){
        
    }
    
    /**
     * The method inserts news into database.
     * @param news 
     */
    public static void insertNews(News news){
        
    }
    
    /**
     * The method updates product entry.
     * @param product 
     */
    public static void updateProduct(Product product){
        
    }
    
    /**
     * The method updates menu entry.
     * @param menu 
     */
    public static void updateMenu(Menu menu){
        
    }
    
    /**
     * The method updates category entry.
     * @param category 
     */
    public static void updateCategory(Category category){
        
    }
    
    public static void updateNews(News news){
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.json.Category;
import model.json.Menu;
import model.json.News;
import model.json.Product;
import model.json.User;

/**
 *
 * @author Administrator
 */
public class ConnectToDB {
    
    public static final String VERSION_PRODUCT = "product";
    public static final String VERSION_MENU = "menu";
    public static final String VERSION_NEWS = "news";
    public static final String VERSION_CATEGORY = "category";
    
    private static final ConnectionPoolToDB connectionPool = new ConnectionPoolToDB(DBInfo.DRIVER_CLASS_NAME, 
                                                DBInfo.DB_SERVER_URL, DBInfo.DB_USER_NAME, DBInfo.DB_PASSWORD);
    
    // Inserts:
    
    /**
     * The method adds new user into database;
     * @param user 
     * @return boolean add or not add a new user.
     */
    public static boolean insertNewUser(User user){
        boolean result = true;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call insert_user(?, ?, ?, ?, ?, ?, ?, ?)");
            
            stmt.setString(1, user.first_name);
            stmt.setString(2, user.last_name);
            stmt.setString(3, user.email);
            stmt.setString(4, user.password);
            stmt.setString(5, user.mobile_number);
            stmt.setString(6, user.card_number);
            stmt.setString(7, user.primary_number);
            stmt.setInt(8, user.role.ordinal());
            
            System.out.println("name: " + user.first_name + "  lastname: " + user.last_name + " role: " + user.role.ordinal());
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    
    /**
     * The method adds new user into database. It is synchronized because id of product must couple to id of category,
     * this is another functionality and may thread manager stops current thread at this time and start other product 
     * saving thread. After the old thread continues. So numeric of id will not be correct.
     * @param product 
     * @return 
     */
    public static synchronized boolean insertNewProduct(Product product){
        boolean result = true;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call insert_product(?, ?, ?, ?, ?)");
            
            stmt.registerOutParameter(1, Types.INTEGER); // this is an out parameter for sql procedure.
            stmt.setString(2, product.name);
            stmt.setString(3, product.description);
            stmt.setDouble(4, product.price);
            stmt.setString(5, product.image_link);
            stmt.execute();
            
            product.id = stmt.getInt(1);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * The method adds new category into database.
     * @param category 
     * @return
     */
    public static synchronized  boolean insertNewCategory(Category category){
        boolean result = true;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call insert_category(?, ?)");
            
            stmt.registerOutParameter(1, Types.INTEGER); // this is an out parameter for sql procedure.
            stmt.setString(2, category.name);
            stmt.execute();
            
            category.id = stmt.getInt(1);
            
            connectionPool.returnConnection(connection);
            stmt.close();
            
//            for(int product: category.products){
//                fillMapCategoryProduct(category.id, product);
//            }
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    /**
     * The method adds new menu into database.
     * @param menu 
     * @return
     */
    public static synchronized boolean insertNewMenu(Menu menu){
        boolean result = true;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call insert_menu(?, ?, ?, ?, ?)");
            
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, menu.name);
            stmt.setString(3, menu.description);
            stmt.setDouble(4, menu.price);
            stmt.setString(5, menu.image_link);
            stmt.execute();
            
            menu.id = stmt.getInt(1);
            
            connectionPool.returnConnection(connection);
            stmt.close();
            
            for(int product: menu.products){
                fillMapMenuProduct(menu.id, product);
            }
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    // The method makes connection between category id and product id.
    public static boolean fillMapCategoryProduct(int categoryID, int productID){
        boolean result = true;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call fill_map_category_product(?, ?)");
            
            stmt.setInt(1, categoryID);
            stmt.setInt(2, productID);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    // The method makes connection between menu id and product id.
    public static boolean fillMapMenuProduct(int menuID, int productID){
        boolean result = true;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call fill_map_menu_product(?, ?)");
            
            stmt.setInt(1, menuID);
            stmt.setInt(2, productID);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * The method inserts news into database.
     * @param news
     * @return
     */
    public static boolean insertNews(News news){
        boolean result = true;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call insert_news(?, ?, ?, ?, ?)");
            
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, news.name);
            stmt.setString(3, news.description);
            stmt.setLong(4, news.from_date);
            stmt.setLong(5, news.to_date);
            stmt.execute();
            
            news.id = stmt.getInt(1);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    // Updates:
    
    /**
     * The method updates product entry.
     * @param product 
     */
    public static boolean updateProduct(Product product){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call update_product(?, ?, ?, ?, ?)");
            
            stmt.setInt(1, product.id);
            stmt.setString(2, product.name);
            stmt.setString(3, product.description);
            stmt.setDouble(4, product.price);
            stmt.setString(5, product.image_link);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * The method updates menu entry.
     * @param menu 
     */
    public static boolean updateMenu(Menu menu){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call update_menu(?, ?, ?, ?, ?)");
            
            stmt.setInt(1, menu.id);
            stmt.setString(2, menu.name);
            stmt.setString(3, menu.description);
            stmt.setDouble(4, menu.price);
            stmt.setString(5, menu.image_link);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * The method updates category entry.
     * @param category 
     */
    public static boolean updateCategory(Category category){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call update_category(?, ?)");
            
            stmt.setInt(1, category.id);
            stmt.setString(2, category.name);
            
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
            
//            for(int product: category.products){
//                fillMapCategoryProduct(category.id, product);
//            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * The method updates news.
     * @param news
     */
    public static boolean updateNews(News news){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call update_news(?, ?, ?, ? ,?)");
            
            stmt.setInt(1, news.id);
            stmt.setString(2, news.name);
            stmt.setString(3, news.description);
            stmt.setLong(4, news.from_date);
            stmt.setLong(5, news.to_date);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static void updateVersion(String versionItemName){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call increase_version_number(?)");
            
            stmt.setString(1, versionItemName);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    
    // Gets objects:
    
    public static int getVersionNumber(String versionItemName){
        int versionNumber = -1;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall(" call select_version_number(?, ?)");
            
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, versionItemName);
            
            stmt.execute();
            
            versionNumber = stmt.getInt(1);
            System.out.println(versionItemName+" version number is: "+versionNumber);
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return versionNumber;
    }
    
    /**
     * The method returns all products which is contained by category with a given id.
     * @param categoryID
     * @return Product[]
     */
    public static Product[] allProductsByCategory(int categoryID){
        List<Product> productsList = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call all_products_by_category(?)");
            
            stmt.setInt(1, categoryID);
            
            ResultSet set = stmt.executeQuery();
            fillProductsList(set, productsList);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productsList.toArray(new Product[productsList.size()]);
    }
    
    /**
     * The method returns all products which are not contained by category with a given id.
     * @param categoryID
     * @return Product[]
     */
    public static Product[] allProductsOutOfCategory(int categoryID){
        List<Product> productsList = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call all_products_out_of_category(?)");
            
            stmt.setInt(1, categoryID);
            
            ResultSet set = stmt.executeQuery();
            fillProductsList(set, productsList);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productsList.toArray(new Product[productsList.size()]);
    }
    
    public static void fillProductsList(ResultSet set, List<Product> products) throws SQLException{
        while(set.next()){
            int id = set.getInt(1);
            String name = set.getString(2);
            String description = set.getString(3);
            double price = set.getDouble(4);
            String imageLink = set.getString(5);

            Product product = new Product(name, description, price);
            product.id = id;
            product.image_link = imageLink;

            products.add(product);
        }
    }
    
    
    public static User getUser(String email, String password){
        User user = null;
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_user(?, ?)");
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                user = new User();
                user.first_name = resultSet.getString(2);
                user.last_name = resultSet.getString(3);
                user.email = resultSet.getString(4);
                user.password = resultSet.getString(5);
                user.mobile_number = resultSet.getString(6);
                user.card_number = resultSet.getString(7);
                user.primary_number = resultSet.getString(8);
                User.Role[] roles = User.Role.values();
                user.role = roles[resultSet.getInt(9)];
            }
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    

    public static Category[] getCategories(){
        List<Category> categories = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_categories()");
            
            ResultSet categoryInfoSet = stmt.executeQuery();
            while(categoryInfoSet.next()){
                Category category = new Category();
                
                category.id = categoryInfoSet.getInt(1);
                category.name = categoryInfoSet.getString(2);

                //if(category.id == 6){  //  ---- this  if statement line is only testing, after test it must remove
                    fillCategoryByProducts(category);
                    categories.add(category);
                //}
            }
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories.toArray(new Category[categories.size()]);
    }
    
    
    private static void fillCategoryByProducts(Category category) throws SQLException{
        Connection connection = connectionPool.getConnection();
        CallableStatement stmt = connection.prepareCall("call all_products_by_category(?)");
        
        stmt.setInt(1, category.id);
        
        ResultSet categoryProductSet = stmt.executeQuery();
        List<Integer> productsList = new ArrayList<>();
        
        while(categoryProductSet.next()){
            Product product = new Product();
            product.id = categoryProductSet.getInt(1);
            product.name = categoryProductSet.getString(2);
            product.description = categoryProductSet.getString(3);
            product.price = categoryProductSet.getDouble(4);
            product.image_link = categoryProductSet.getString(5);

            productsList.add(product.id);
        }
        category.products = new int[productsList.size()];
        for(int i=0; i<productsList.size(); i++)
            category.products[i] = productsList.get(i);
            
    }
    
    
    public static News[] getNews(){
        List<News> newsList = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_news()");
            
            ResultSet set = stmt.executeQuery();
            while(set.next()){
                News news = new News();
                news.id = set.getInt(1);
                news.name = set.getString(2);
                news.description = set.getString(3);
                news.from_date = set.getLong(4);
                news.to_date = set.getLong(5);
                
                newsList.add(news);
            }
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newsList.toArray(new News[newsList.size()]);
    }
    
    public static Menu[] getMenus(){
        List<Menu> menus = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_menus()");
            
            ResultSet menusInfoSet = stmt.executeQuery();
            while(menusInfoSet.next()){
                Menu menu = new Menu();
                menu.id = menusInfoSet.getInt(1);
                menu.name = menusInfoSet.getString(2);
                menu.description = menusInfoSet.getString(3);
                menu.price = menusInfoSet.getDouble(4);
                menu.image_link = menusInfoSet.getString(5);
                
                fillMenuByProducts(menu);
                menus.add(menu);
            }
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return menus.toArray(new Menu[menus.size()]);
    }
    
    
    private static void fillMenuByProducts(Menu menu) throws SQLException{
        Connection connection = connectionPool.getConnection();
        CallableStatement stmt = connection.prepareCall("call select_menus_products(?)");

        stmt.setInt(1, menu.id);
        ResultSet menuProductsSet = stmt.executeQuery();
        List<Product> productsList = new ArrayList<>();
        
        while(menuProductsSet.next()){
            Product product = new Product();
            product.id = menuProductsSet.getInt(1);
            product.name = menuProductsSet.getString(2);
            product.description = menuProductsSet.getString(3);
            product.price = menuProductsSet.getDouble(4);
            product.image_link = menuProductsSet.getString(5);

            productsList.add(product);
        }
        
        menu.products = new int[productsList.size()];
        for(int i=0; i<productsList.size(); i++)
            menu.products[i] = productsList.get(i).id;

        connectionPool.returnConnection(connection);
        stmt.close();
    }
    
    public static Product[] allProductsByMenu(int menuID){
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_products_by_menu(?)");
            
            stmt.setInt(1, menuID);
            
            ResultSet resultSet = stmt.executeQuery();
            fillProductsList(resultSet, products);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products.toArray(new Product[products.size()]);
    }
    
    public static Product[] allProductsOutOfMenu(int menuID){
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_products_out_of_menu(?)");
            
            stmt.setInt(1, menuID);
            
            ResultSet resultSet = stmt.executeQuery();
            fillProductsList(resultSet, products);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products.toArray(new Product[products.size()]);
    }
    
    
    public static Category[] allCategoriesByProduct(int productID){
        List<Category> categories = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_categories_by_product(?)");
            
            stmt.setInt(1, productID);
            
            ResultSet resultSet = stmt.executeQuery();
            fillCategoriesList(resultSet, categories);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return categories.toArray(new Category[categories.size()]);
    }
    
    private static void fillCategoriesList(ResultSet resultSet, List<Category> categories) throws SQLException{
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            
            Category category = new Category(name);
            category.id = id;
            
            fillCategoryByProducts(category);
            categories.add(category);
        }
    }
    
    
    public static Category[] allCategoriesOutOfProduct(int productID){
        List<Category> categories = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_categories_out_of_product(?)");
            
            stmt.setInt(1, productID);
            
            ResultSet resultSet = stmt.executeQuery();
            fillCategoriesList(resultSet, categories);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return categories.toArray(new Category[categories.size()]);
    }
    
    public static Product[] allProducts(){
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call select_produts()");
            
            ResultSet resultSet = stmt.executeQuery();
            fillProductsList(resultSet, products);
            
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products.toArray(new Product[products.size()]);
    }
    
    
    // Removes:
    
    public static void deleteProductFromCategory(int categoryID, int productID){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call remove_product_from_category(?, ?)");
            
            stmt.setInt(1, categoryID);
            stmt.setInt(2, productID);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteProductFromMenu(int menuID, int productID){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call remove_product_from_menu(?, ?)");
            
            stmt.setInt(1, menuID);
            stmt.setInt(2, productID);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void deleteUser(int userID){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call remove_user(?)");
            
            stmt.setInt(1, userID);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteProduct(int productID){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call remove_product(?)");
            
            stmt.setInt(1, productID);

            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteCategory(int categoryID){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call remove_category(?)");
            
            stmt.setInt(1, categoryID);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void deleteMenu(int menuID){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call remove_menu(?)");
            
            stmt.setInt(1, menuID);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteNews(int newsID){
        try {
            Connection connection = connectionPool.getConnection();
            CallableStatement stmt = connection.prepareCall("call remove_news(?)");
            
            stmt.setInt(1, newsID);
            
            stmt.execute();
            connectionPool.returnConnection(connection);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

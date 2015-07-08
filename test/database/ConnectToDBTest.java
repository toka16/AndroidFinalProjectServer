/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import model.model.Category;
import model.model.Menu;
import model.model.News;
import model.model.Product;
import model.model.User;
import org.junit.Test;

public class ConnectToDBTest {
    
    public ConnectToDBTest() {
    }

    /**
     * Test of insertNewUser method, of class ConnectToDB.
     */
    @Test
    public void testInsertNewUser() {
        System.out.println("insertNewUser");
        User user = new User("თოკა", "აბრამიშვილი");
        user.setEmail("rame5@gmail.com");
        user.setPassword("testPassword");
        user.setPhone("598140289");
        user.setCardNumber("1234");
        user.setPrimaryNumber("12345678");
//        ConnectToDB.insertNewUser(user);
    }

    /**
     * Test of insertNewProduct method, of class ConnectToDB.
     */
    @Test
    public void testInsertNewProduct() {
        System.out.println("insertNewProduct");
        Product product = new Product("ნუვო");
        product.setDescription("შოკოლადით, ვანილით და კარამელით");
        product.setPrice(1.20);
        product.setImageLink("some image link");
        product.setCategoryName("ნაყინი");
        ConnectToDB.insertNewProduct(product);
    }

    /**
     * Test of insertNewCategory method, of class ConnectToDB.
     */
    @Test
    public void testInsertNewCategory() {
        System.out.println("insertNewCategory");
        Category category = new Category("ნაყინი");
//        ConnectToDB.insertNewCategory(category);
    }

    /**
     * Test of insertNewMenu method, of class ConnectToDB.
     */
    @Test
    public void testInsertNewMenu() {
        System.out.println("insertNewMenu");
        Menu menu = null;
        ConnectToDB.insertNewMenu(menu);
    }

    /**
     * Test of insertNews method, of class ConnectToDB.
     */
    @Test
    public void testInsertNews() {
        System.out.println("insertNews");
        News news = null;
        ConnectToDB.insertNews(news);
    }

    /**
     * Test of updateProduct method, of class ConnectToDB.
     */
    @Test
    public void testUpdateProduct() {
        System.out.println("updateProduct");
        Product product = null;
        ConnectToDB.updateProduct(product);
    }

    /**
     * Test of updateMenu method, of class ConnectToDB.
     */
    @Test
    public void testUpdateMenu() {
        System.out.println("updateMenu");
        Menu menu = null;
        ConnectToDB.updateMenu(menu);
    }

    /**
     * Test of updateCategory method, of class ConnectToDB.
     */
    @Test
    public void testUpdateCategory() {
        System.out.println("updateCategory");
        Category category = null;
        ConnectToDB.updateCategory(category);
    }

    /**
     * Test of updateNews method, of class ConnectToDB.
     */
    @Test
    public void testUpdateNews() {
        System.out.println("updateNews");
        News news = null;
        ConnectToDB.updateNews(news);
    }
    
}

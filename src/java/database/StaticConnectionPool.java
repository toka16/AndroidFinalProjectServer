/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author toka
 */
public class StaticConnectionPool {
    // Instance variables:
    private static ConnectionPoolToDB datasource;
    
    private static void initConnectionPool(){
        datasource = new ConnectionPoolToDB(DBInfo.DRIVER_CLASS_NAME, DBInfo.DB_SERVER_URL, DBInfo.DB_USER_NAME, DBInfo.DB_PASSWORD);
    }
    
    

    /**
     * The method returns connectionPool object.
     * @return
     */
    public static Connection getConnection() {
        if(datasource == null){
            initConnectionPool();
        }
        
        return datasource.getConnection();
    }
    
    
    
    public static void putConnection(Connection con){
        if (con != null) 
            datasource.returnConnection(con);
    }
}

package database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayDeque;

public class ConnectionPoolToDB {
    private int connectionCount = 100;
//    private int connectionMaxCount = 100;
    private final String dbServerURL;
    private final String dbUserName;
    private final String dbPassword;
    
    private final ArrayDeque<Connection> freeConnections = new ArrayDeque<>();
    private final ArrayDeque<Connection> usedConnections = new ArrayDeque<>();

    // The Constructor of 
    public ConnectionPoolToDB(String classForName, String dbServerURL, String dbUserName, String dbPassword){
        try {
            Class.forName(classForName);
        } catch (ClassNotFoundException ex) { 
            System.err.println("ClassNotFoundException in constructor of <ConnectionPoolToDB> class!");
        }
        this.dbServerURL = dbServerURL;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
    }
	
    // The method gets free connection.
    public Connection getConnection (){
        Connection conn = null;
        while(conn == null){
            if(!freeConnections.isEmpty()){
                conn = freeConnections.poll();
                usedConnections.add(conn);
            } else if (usedConnections.size() < connectionCount){  
                try {
                    conn = DriverManager.getConnection(dbServerURL, dbUserName, dbPassword);
                    usedConnections.add(conn);
//                    conn = DriverManager.getConnection(dbServerURL, dbUserName, dbPassword);
//                    usedConnections.add(conn);
                } catch (SQLException ex) { 
                    System.err.println("SQL error in <getConnection> method to the <ConnectionPoolToDB> class!");
                }
            }
        }
        return conn;
    }
    
    // The method pushes free connection to the back.
    public synchronized void returnConnection (Connection conn){
        if (conn != null){
            usedConnections.remove(conn);
            freeConnections.add(conn);
        }
    }    
    
    // The method sets the freeConnections quantity.   
    public synchronized void setConnectionCount(int count){
        if(count > connectionCount)
            connectionCount = count;
//        connectionCount = Math.min(count, connectionMaxCount);
    }
}

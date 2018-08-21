package com.ubp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static Connection con;
    private static String username ="";
    private static String password = "";
    private static String url = "";
    private static String driverClass = "";
    
    
   
    static {
        try {
        	PropFileUtils.loadProps("databaseDetails.properties");
        	driverClass = PropFileUtils.getValueFromProperties("databaseDetails.properties", "driverClass");
            Class.forName(driverClass);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
   
    public static Connection getConnection() throws SQLException{ 
    	
    	PropFileUtils.loadProps("databaseDetails.properties");
    	username = PropFileUtils.getValueFromProperties("databaseDetails.properties", "username");
    	password = PropFileUtils.getValueFromProperties("databaseDetails.properties", "password");
    	url = PropFileUtils.getValueFromProperties("databaseDetails.properties", "url");
    	
        return con = DriverManager.getConnection(url, username, password);
    }

    public static void beginTransaction(){
        if(con!=null){
            try {
                con.setAutoCommit(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void commit(){
        if(con!=null){
            try {
                con.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void rollback(){
        if(con!=null){
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void closeDBUtil(ResultSet rs, Statement stmt, Connection con){
        try {
            if(rs!=null) {
                rs.close();
                rs =null;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
       
        try {
            if(stmt!=null) {
                stmt.close();
                stmt =null;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
       
        try {
            if(con!=null) {
                con.close();
                con =null;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
       
    }    
}
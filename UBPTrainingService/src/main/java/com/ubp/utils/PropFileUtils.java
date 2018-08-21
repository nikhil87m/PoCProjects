package com.ubp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropFileUtils {

	private static Map mapOfProps = new HashMap();


	/**
	 * Load a properties file 
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadProps(String fileName) {
		
		Properties props = new Properties();
		InputStream inS = null;
		if (fileName == null || "".equals(fileName.trim())) {
			props = null;
		} else {
			try {
				inS = PropFileUtils.class.getClassLoader().getResourceAsStream(fileName);
				if (inS == null) {
					String errMsg = "PropFileUtils.loadProps failed to load properties file:"
							+ fileName + " due to null InputSream";
					System.out.println(errMsg);
					props = null;
				} else {
					props.load(inS);
					mapOfProps.put(fileName, props);
				}

			} catch (Throwable th) {
				String errMsg = "PropFileUtils.loadProps failed to load properties file:"
						+ fileName + " Reason:" + th.getLocalizedMessage();
				System.out.println(errMsg);
				props = null;
			} finally {
				if (inS != null) {
					try {
						inS.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return props;
	}

	/**
	 * Get a Properties instance given a filename
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties getInstance(String fileName, boolean reload) {
		Properties props = (Properties) mapOfProps.get(fileName);
		if (props == null && !reload) {
			props = loadProps(fileName);
		} else if (props != null && reload) {
			mapOfProps.remove(fileName);
			props = loadProps(fileName);
		}
		return props;
	}

	/**
	 * Retrieve value from the given properties file 
	 * 
	 * @param propsFileName
	 * @param key
	 * @return null if properties file not found or key not found
	 */
	public static String getValueFromProperties(String propsFileName, String key) {
				
		String retVal = null;
		Properties props = loadProps(propsFileName);
		if (props != null) {
			retVal = props.getProperty(key);
				String logMsg = "PropFileUtils.getNonCachedValueFromProperties successfully loaded property " + retVal + " with key " + key + " from cache";
				System.out.println(logMsg);
		}
		return retVal;
	}
	
	public static void main(String args[]){
		String studentId = "3372353";
		PropFileUtils.loadProps("databaseDetails.properties");
		System.out.println("proppp "+getValueFromProperties("databaseDetails.properties", "username"));
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
		try {
			 
		    Connection conn = DriverManager.getConnection("jdbc:mysql://sl-eu-lon-2-portal.11.dblayer.com:27965/UBP", "superuser", "UBP@123");
		 
		    if (conn != null) {
		        System.out.println("Connected");
		        
		        String sql = "SELECT * FROM G42 WHERE PMP_ID = " + studentId;
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(sql);
		        
		        if (rs.next()) {
		            System.out.println((rs.getString(1)));
		           // conn.commit();
		        } else {
		            conn.rollback();
		        }
		        
		    }else
		    	System.out.println("not connected");
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
	}

}

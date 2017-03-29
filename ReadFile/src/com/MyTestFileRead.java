package com;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MyTestFileRead {
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@oxdora61cl.elsevier.co.uk:1521:D3APPOB";
//	private static final String DB_CONNECTION = "jdbc:oracle:thin:@oxdora61al.elsevier.co.uk:1521:D1APPOB";
	private static final String DB_USER = "OBII_NXML";
	private static final String DB_PASSWORD = "OBII_NXML";

	public static void main(String[] args)  {
//		String filePath = "/temp/abc.txt";
		String filePath ="C:/Migration/file/UPD_ISSN.txt";

		Connection dbConnection = null;
		CallableStatement callableStatement = null;
		try {
			try{
			dbConnection = getDBConnection();
			System.out.println("dbConnection"+dbConnection);
			byte[] bFile = Files.readAllBytes(new File(filePath).toPath());
			System.out.println(bFile);
			String ss = new String(bFile);
			System.out.println(ss);
			String lines[] = ss.split("\\r?\\n");
			for (String line : lines){
				System.out.println("Each line>>"+line);
				String spCall = "{call SP_NAME(?,?,?,?)}";
				String newspCall = spCall.replace("SP_NAME", "OBII.Updating_chg_cont_date_pr");
				System.out.println("New String"+newspCall);
				callableStatement = dbConnection.prepareCall(newspCall);
				callableStatement.setInt(1, 1234);
				callableStatement.setInt(2, 1234);
				callableStatement.setString(3, line);
				callableStatement.setInt(4, 007);
				callableStatement.addBatch();
			}
			callableStatement.executeBatch();
			dbConnection.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
				dbConnection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;

	}
	private static Connection getDBConnectionDS() {
		System.out.println("Entry: getDBConnectionDS ");
		InitialContext initialContext = null;
		Connection dbConnection = null;
		try {
			initialContext = new InitialContext();
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		try {
			DataSource dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/BulkSourceDS");
			try {
				dbConnection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		System.out.println("Exit: getDBConnectionDS ");
		return dbConnection;
	}

}

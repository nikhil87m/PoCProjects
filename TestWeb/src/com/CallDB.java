package com;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CallDB {

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@oxdora61cl.elsevier.co.uk:1521:D3APPOB";
	private static final String DB_USER = "OBII_NXML";
	private static final String DB_PASSWORD = "OBII_NXML";

	public void batchInsertRecordsEachSP() throws SQLException {

		System.out.println("ENTRY ::batchInsertRecordsEachSP : MYTEST_EACH:: Using Batch: Timestamp: "+getCurrentTimeStamp());
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		CallableStatement callableStatement = null;
		try {
			dbConnection = getDBConnection();
			System.out.println("DB connection"+dbConnection);
			for(int i=0; i<500; i++) {
			callableStatement = dbConnection.prepareCall("{call MYTEST_EACH()}");
			callableStatement.addBatch();
			}
			callableStatement.executeBatch();
			dbConnection.commit();
			System.out.println("Exit :: batchInsertRecordsEachSP: MYTEST_EACH:: Using Batch: Timestamp:"+getCurrentTimeStamp());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}

	}

	public void insertAllSP() throws SQLException {

		System.out.println("ENTRY :: insertAllSP(): MYTEST :Wthout Batch::  Timestamp: "+getCurrentTimeStamp());
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		CallableStatement callableStatement = null;
		try {
			dbConnection = getDBConnection();
			System.out.println("DB connection"+dbConnection);
			callableStatement = dbConnection.prepareCall("{call MYTEST()}");
			callableStatement.execute();
			dbConnection.commit();
			System.out.println("Exit :: insertAllSP(): MYTEST : Wthout Batch::  Timestamp:"+getCurrentTimeStamp());
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}

	}
	
	public void insertTableBatch() throws SQLException {

		System.out.println("ENTRY :: insertTableBatch():  Timestamp: "+getCurrentTimeStamp());
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		String insertTableSQL = "INSERT INTO EMPLOYEE" + "(EMPID, EMPNAME, DEPTID, DEPTNAME, COMPANY ) VALUES"
				+ "(?,?,?,?,?)";
		try {
			dbConnection = getDBConnection();
			System.out.println("DB connection"+dbConnection);
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			dbConnection.setAutoCommit(false);
			for(int i=0;i<500;i++) {
			preparedStatement.setInt(1, 1000+i);
			preparedStatement.setString(2, "Nikhilendu Mandal");
			preparedStatement.setString(3, "12");
			preparedStatement.setString(4, "IT");
			preparedStatement.setString(5, "IBM");
			preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			dbConnection.commit();
			System.out.println("Exit :: insertTableBatch()::  Timestamp:"+getCurrentTimeStamp());
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			dbConnection.rollback();
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
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

	private static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
}

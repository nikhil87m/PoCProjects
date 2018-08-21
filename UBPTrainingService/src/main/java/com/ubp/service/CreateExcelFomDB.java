package com.ubp.service;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateExcelFomDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			
			String filename="c:/UBPTrainingNeed1.xls" ;
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("Training Details");

			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Training ID");
			rowhead.createCell((short) 1).setCellValue("Course Name");
			rowhead.createCell((short) 2).setCellValue("Course ID");
			rowhead.createCell((short) 3).setCellValue("Course Type");
			rowhead.createCell((short) 4).setCellValue("Sequence");
			rowhead.createCell((short) 5).setCellValue("Remarks");
			rowhead.createCell((short) 6).setCellValue("Created_Date");

			Class.forName("com.mysql.jdbc.Driver");
			
			//portnumber and service name has to be verified
			Connection con = DriverManager.getConnection("jdbc:mysql://sl-eu-lon-2-portal.11.dblayer.com:27965/UBP","superuser","UBP@123");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select * from Training");
			int i=1;
			while(rs.next()){
			HSSFRow row=   sheet.createRow((short)i);
			row.createCell((short) 0).setCellValue(rs.getString("Training_ID"));
			row.createCell((short) 1).setCellValue(rs.getString("Course_Name"));
			row.createCell((short) 2).setCellValue(rs.getString("Course_ID"));
			row.createCell((short) 3).setCellValue(rs.getString("Course_Type"));
			row.createCell((short) 4).setCellValue(rs.getString("Sequence"));
			row.createCell((short) 5).setCellValue(rs.getString("Remarks"));
			row.createCell((short) 6).setCellValue(rs.getDate("Created_Date"));
			i++;
			}
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");

			} catch ( Exception ex ) {
			    System.out.println(ex);

			}

	}

}

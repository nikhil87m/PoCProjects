package com.ubp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.jdbc.PreparedStatement;
//import com.mysql.jdbc.PreparedStatement;
import com.ubp.bo.AddTrainingBo;
import com.ubp.utils.DBUtil;

@Component
public class AddTrainingDetailsDao {

	 public void insertTrainingMasterData(AddTrainingBo trainingBo) throws SQLException {
	        String sql = "INSERT into Training (course_type, course_ID, course_name,sequence, remarks, created_date) VALUES (?, ?,?,?,?,?)";
	        //DBUtil dbutil = new DBUtil();
	        Connection con = DBUtil.getConnection();
	        PreparedStatement pstmttmt = (PreparedStatement) con.prepareStatement(sql);
	        
	        
	        //Temporary changes for Training ID
	       /* 
	        String trainingId = getTrainingId();
	        System.out.println("trainingId:"+trainingId);
	        //
	        pstmttmt.setString(1,  trainingId);*/
	        pstmttmt.setString(1, trainingBo.getCourseType());
	        pstmttmt.setString(2, trainingBo.getCourseId());
	        pstmttmt.setString(3, trainingBo.getCourseName());
	        if(trainingBo.getSequence() !=null)
	        pstmttmt.setInt(4, trainingBo.getSequence());
	        else
	        	 pstmttmt.setInt(4, 0);	
	        pstmttmt.setString(5, trainingBo.getRemark());
	        
	        long millis=System.currentTimeMillis();  
	        java.sql.Date date=new java.sql.Date(millis);  
	        pstmttmt.setDate(6, date);
	        
	        
	        DBUtil.beginTransaction();
	        int result = pstmttmt.executeUpdate();
	        if (result != 0) {
	            DBUtil.commit();
	        } else {
	            DBUtil.rollback();
	        }
	        DBUtil.closeDBUtil(null, pstmttmt, con);
	    }
	 
	 
	 
	 public void readFromExcelAndSavetoDB(MultipartFile uploadedFile) throws IOException, SQLException, ClassNotFoundException {
		 System.out.println("DAO uploadedFile::"+uploadedFile.getOriginalFilename());
		 File convFile = new File( uploadedFile.getOriginalFilename());
		 uploadedFile.transferTo(convFile);	
		 FileInputStream file = new FileInputStream(convFile);
		 System.out.println("IO File ::"+convFile.getName());

			String uploadedFileLocation = convFile.getAbsolutePath();
			String fileName = convFile.getName();
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Row row;

			int result = 0;
			String queryTraining = "INSERT INTO Training VALUES (?, ?, ?, ?, ?, ?, ?) ";
			Connection con = DBUtil.getConnection();
		    PreparedStatement pstmt= (PreparedStatement) con.prepareStatement(queryTraining);
		    DBUtil.beginTransaction();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
				row = (Row) sheet.getRow(i); 

				String courseType;
				if (row.getCell(0) == null) {
					courseType = "0";
				} else
					courseType = row.getCell(0).toString();

				String courseName; 
				if (row.getCell(1) == null) {
					courseName = "null";
				} 
				else
					courseName = row.getCell(1).toString(); 

				String courseId;
				if (row.getCell(2) == null) {
					courseId = "null";
				} else
					courseId = row.getCell(2).toString();

				String sequence;
				if (row.getCell(3) == null) {
					sequence = "null";
				} else
					sequence = row.getCell(3).toString();

				String remarks;
				if (row.getCell(4) == null) {
					remarks = "null";
				} else
					remarks = row.getCell(4).toString();

				java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());



			    pstmt.setString(1, null);
			    pstmt.setString(2, courseName);
			    pstmt.setString(3, courseId);
			    pstmt.setString(4, courseType);
			    pstmt.setString(5, sequence);
			    pstmt.setString(6, remarks);
			    pstmt.setDate(7, sqlDate);
			    result =  pstmt.executeUpdate();

			}

			if (result != 0) {
	            DBUtil.commit();
	        } else {
	            DBUtil.rollback();
	        }
	        DBUtil.closeDBUtil(null, pstmt, con);
			file.close();
		}
	 
	 
	/* public String getTrainingId() throws SQLException{
		 String sql = "SELECT MAX(CAST(Training_ID AS UNSIGNED)) FROM UBP.Training";
	        Connection con = DBUtil.getConnection();
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        DBUtil.beginTransaction();
	        
	        int maxID = 0;
	        if (rs.next()) {
	        	maxID = rs.getInt(1);
	        	maxID = maxID+1;
	        }
	      //  DBUtil.closeDBUtil(null, stmt, con);
	        System.out.println("maxID:"+maxID);
	        return String.valueOf(maxID);

	        
	 }*/
	 
	 public String writeToXlsx(){

		 String pathForGeneratedReport=null;
		 File dirPath = null;
		 String fileName = null;

		 try{
		 			System.out.println("inside writeToXlsx");

		 			pathForGeneratedReport = ("c:\\TrainingDetails");
		 			dirPath = new File(pathForGeneratedReport);
		 			if(!dirPath.exists()){
		 				dirPath.mkdir();
		 			}

		 			fileName = dirPath+"\\"+"EmployeeTrainingStatusReport.xlsx" ;
		 			XSSFWorkbook xwb=new XSSFWorkbook();
		 			XSSFSheet sheet =  xwb.createSheet("Training Details");

		 			XSSFRow rowhead=   sheet.createRow((short)0);
		 			rowhead.createCell((short) 0).setCellValue("PMP ID");
		 			rowhead.createCell((short) 1).setCellValue("Employee Name");
		 			rowhead.createCell((short) 2).setCellValue("Course Name");
		 			rowhead.createCell((short) 3).setCellValue("Course ID");
		 			rowhead.createCell((short) 4).setCellValue("Status");
		 			rowhead.createCell((short) 5).setCellValue("Course Completion Date");

		 			String query = "select u.PMP_ID, u.Name,  t.Course_Name, e.Training_id, e.Status,e.Course_Completion_date from UBP_WFM u, Employee_training_mapping e, Training t where u.PMP_ID=e.PMP_ID and e.Training_ID=t.Training_ID";
		 			Connection con = DBUtil.getConnection();
				    PreparedStatement pstmt= (PreparedStatement) con.prepareStatement(query);
				    DBUtil.beginTransaction();
		 			ResultSet rs=pstmt.executeQuery();
		 			int i=1;
		 			while(rs.next()){
		 			XSSFRow row=   sheet.createRow((short)i);
		 			row.createCell((short) 0).setCellValue(rs.getString("PMP_ID"));
		 			row.createCell((short) 1).setCellValue(rs.getString("Name"));
		 			row.createCell((short) 2).setCellValue(rs.getString("Course_Name"));
		 			row.createCell((short) 3).setCellValue(rs.getString("Training_ID"));
		 			row.createCell((short) 4).setCellValue(rs.getString("Status"));
		 			row.createCell((short) 5).setCellValue(rs.getString("Course_Completion_date"));

		 			i++;
		 			}
		 			FileOutputStream fileOut =  new FileOutputStream(fileName);
		 			xwb.write(fileOut);
		 			fileOut.close();
		 			DBUtil.closeDBUtil(null, pstmt, con);
		 			System.out.println("Your excel file has been generated!");

		 			} catch ( Exception ex ) {
		 			    System.out.println(ex);

		 			}

		     return fileName;
		 	}
	    
}

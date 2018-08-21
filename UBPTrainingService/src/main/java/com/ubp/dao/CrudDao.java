package com.ubp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.PreparedStatement;
//import com.mysql.jdbc.PreparedStatement;
import com.ubp.bo.EmployeeBo;
import com.ubp.bo.EmployeeTrainingMappingBo;
import com.ubp.bo.TrainingBo;
import com.ubp.bo.TrainingDetailsBO;
import com.ubp.utils.DBUtil;

public class CrudDao {
//	 private static void deleteStudent(long studentId) throws SQLException {
//	        Connection con = DBUtil.getConnection();
//	        String sql = "DELETE FROM STUDENT WHERE StudentID = ?";
//	        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
//	        pstmt.setLong(1, studentId);
//	        DBUtil.beginTransaction();
//	        int res = pstmt.executeUpdate();
//	        if (res != 0) {
//	            DBUtil.commit();
//	        } else {
//	            DBUtil.rollback();
//	        }
//	        DBUtil.closeDBUtil(null, pstmt, con);
//	    }
/** Method used to update employee training status by respective employee**/
	    private static void updateTraining_Emp_Mapping(EmployeeTrainingMappingBo employee_training_mapping) throws SQLException {
	        String sql = "UPDATE Employee_training_mapping SET Status=? WHERE EMP_ID =? and Training_ID=?";
	        DBUtil dbutil = new DBUtil();
	        Connection con = dbutil.getConnection();
	        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
	        pstmt.setString(1, employee_training_mapping.getStatus());
	        pstmt.setString(2, employee_training_mapping.getEmpId());
	        pstmt.setString(3, employee_training_mapping.getPmpId());
	        DBUtil.beginTransaction();
	        int result = pstmt.executeUpdate();
	        if (result != 0) {
	            DBUtil.commit();
	        } else {
	            DBUtil.rollback();
	        }
	        DBUtil.closeDBUtil(null, pstmt, con);
	    }
	    
	    
	    
	    
/** Method used for employee vs training mapping, where empid or pmp id used to fetch training related data**/	    
	    
	    public static List<EmployeeBo> getEmpIdAndPMPID() throws SQLException {
	        String sql = "SELECT * FROM UBP_WFM";
	        Connection con = DBUtil.getConnection();
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        DBUtil.beginTransaction();
	        EmployeeBo employeeBo = null;
	        List<EmployeeBo> emp_List = new ArrayList<EmployeeBo>();
	        while (rs.next()) {
	        	employeeBo = new EmployeeBo();
	        	employeeBo.setPmpid(rs.getString(1));
	        	employeeBo.setEmpid(rs.getString(2));
	        	employeeBo.setName(rs.getString(4));
	        	
	        	
	        	emp_List.add(employeeBo);
	        	
	           // DBUtil.commit();
	        } 
//	        else {
//	            DBUtil.rollback();
//	        }
	        DBUtil.closeDBUtil(rs, stmt, con);
	        return emp_List;
	    }
	    
	    
	    
	    
/** based on the empid all the training related data will come on this method **/
	    
	    public static List<EmployeeTrainingMappingBo> getTrainingDetailsForEmp(String empId) throws SQLException {
	        String sql = "SELECT * FROM Employee_training_mapping WHERE empId = " + empId;
	        Connection con = DBUtil.getConnection();
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        DBUtil.beginTransaction();
	        EmployeeTrainingMappingBo trainingEmployeeBo = null;
	        List<EmployeeTrainingMappingBo> training_emp_List = new ArrayList<EmployeeTrainingMappingBo>();
	        while (rs.next()) {
	        	trainingEmployeeBo = new EmployeeTrainingMappingBo();
	        	trainingEmployeeBo.setPmpId(rs.getString(1));
	        	trainingEmployeeBo.setEmpId(rs.getString(2));
	        	trainingEmployeeBo.setTrainingId(rs.getString(3));
	        	
	        	trainingEmployeeBo.setCourse_Name(rs.getString(4));
	        	trainingEmployeeBo.setCourse_Completion_Date(rs.getDate(5));
	        	trainingEmployeeBo.setNominated_By(rs.getString(6));
	        	
	        	trainingEmployeeBo.setExternal(rs.getBoolean(7));
	        	trainingEmployeeBo.setRequired(rs.getBoolean(8));
	        	trainingEmployeeBo.setStatus(rs.getString(9));
	        	
	        	training_emp_List.add(trainingEmployeeBo);
	        	
	           // DBUtil.commit();
	        } 
//	        else {
//	            DBUtil.rollback();
//	        }
	        DBUtil.closeDBUtil(rs, stmt, con);
	        return training_emp_List;
	    }
/**  below method used for insert master training details in TrainingMster table**/
	    private static void createTrainingData(TrainingBo trainingMster) throws Exception {
	        //long studentId = "";
	        String sql = "INSERT INTO Training(Training_ID , Course_Name , Course_ID , Course_Type ,Sequence , Remarks , Created_date ) VALUES(?, ?, ?, ?, ?, ?, ?)";
	        Connection con = DBUtil.getConnection();
	        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
	        pstmt.setString(1, trainingMster.getTraining_Id());
	        pstmt.setString(2, trainingMster.getCourse_Name());
	        pstmt.setString(3, trainingMster.getCourse_Id());
	        pstmt.setString(4, trainingMster.getCourseType());
	        pstmt.setInt(5, trainingMster.getSequence());
	        pstmt.setString(6, trainingMster.getRemarks());
	        
	        long millis=System.currentTimeMillis();  
	        java.sql.Date date=new java.sql.Date(millis);  
	        pstmt.setDate(7, date);
	        
	        DBUtil.beginTransaction();
	        int result = pstmt.executeUpdate();
	        if (result != 0) {
	            DBUtil.commit();
	        } else {
	            DBUtil.rollback();
	        }
	        DBUtil.closeDBUtil(null, pstmt, con);
	    }


	    
	    /**  below method used for insert data -- Manager or PMO wants to add training for particular resource **/
	    public static void createTraining_Emp_Mapping(TrainingDetailsBO trainingDetailsBO) throws Exception {
	        //long studentId = "";
	        String sql = "INSERT INTO Employee_training_mapping(PMP_ID , EMP_ID , Course_Type , Training_ID ,External_Y_N , IsRequired_Y_N , Status, Remarks,Expected_Completion_Date,Created_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?,?, current_date)";
	        Connection con = DBUtil.getConnection();
	        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
	        
	     String pmpid_empid = trainingDetailsBO.getEmp();
	   	 
	    	String[] pmpid_empidArray = pmpid_empid.split("_");
	    	
	    	List<String> trainingList = trainingDetailsBO.getTraining();
	    	
	    	
	    	
	   // Date	date1 = new SimpleDateFormat("mm/dd/yy").parse(trainingDetailsBO.getExpectedCompletionDate());
	    
	    
	    
	    
	    
	    /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;;
		try {
			date = sdf.parse(trainingDetailsBO.getExpectedCompletionDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date sqlDate = new Date(date.getTime());
	    	
	    	*/
	    	for (String trainingid : trainingList) {
				
	    		pstmt.setString(1, pmpid_empidArray[0]);
		        pstmt.setString(2, pmpid_empidArray[1]);
		        pstmt.setString(3, trainingDetailsBO.getType());
		        pstmt.setString(4, trainingid);
		        pstmt.setInt(5, (null!=trainingDetailsBO.getIsExternal() && trainingDetailsBO.getIsExternal().equalsIgnoreCase("Y") ? 1 :0));
		        pstmt.setInt(6, (null!=trainingDetailsBO.getIsRequired() && trainingDetailsBO.getIsRequired().equalsIgnoreCase("Y") ? 1 :0));
		        
//		        long millis=System.currentTimeMillis();  
//		        java.sql.Date date=new java.sql.Date(millis);  
		        pstmt.setString(7, "notCompleted");
		        pstmt.setString(8, trainingDetailsBO.getRemark());
		        pstmt.setDate(9, trainingDetailsBO.getExpectedCompletionDate());
		        DBUtil.beginTransaction();
		        int result = pstmt.executeUpdate();
		        if (result != 0) {
		            DBUtil.commit();
		        } else {
		            DBUtil.rollback();
		        }
	    		
			}
	        
	        
	        DBUtil.closeDBUtil(null, pstmt, con);
	    }
	    
	    
	    /**  below method used for get training data based on training type **/
	    public static List<TrainingBo> getTrainingListOnTrainingType(String course_type, String empId) throws Exception {
	        //long studentId = "";
	    	
	    	
	    	String sql = "select * from Training where Course_Type='"+ course_type+"' and Training_ID not in (select Training_ID from Employee_training_mapping where EMP_ID='"+empId+"')";
	    	
	        //String sql = "SELECT * FROM Training WHERE Course_Type = '" + course_type+"'";
	        Connection con = DBUtil.getConnection();
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        DBUtil.beginTransaction();
	        TrainingBo trainingBo = null;
	        List<TrainingBo> training_List = new ArrayList<TrainingBo>();
	        while (rs.next()) {
	        	trainingBo = new TrainingBo();
	        	trainingBo.setCourse_Name(rs.getString(2));
	        	trainingBo.setTraining_Id(rs.getString(1));
//	        	trainingBo.setTrainingId(rs.getString(3));
//	        	
//	        	trainingBo.setCourse_Name(rs.getString(4));
//	        	trainingBo.setCourse_Completion_Date(rs.getDate(5));
//	        	trainingBo.setNominated_By(rs.getString(6));
//	        	
//	        	trainingBo.setExternal(rs.getBoolean(7));
//	        	trainingBo.setRequired(rs.getBoolean(8));
//	        	trainingBo.setStatus(rs.getString(9));
	        	
	        	training_List.add(trainingBo);
	        	
	           // DBUtil.commit();
	        } 
//	        else {
//	            DBUtil.rollback();
//	        }
	        DBUtil.closeDBUtil(rs, stmt, con);
	        
	        return training_List;
	    }
	    
	    
	}


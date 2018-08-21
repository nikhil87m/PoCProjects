package com.ubp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ubp.bo.EmployeeTrainingBo;
import com.ubp.bo.EmployeeTrainingMappingBo;
import com.ubp.bo.Training;
import com.ubp.utils.DBUtil;

@Repository
public class EmployeeTrainingDAO implements IEmployeeTraningDAO {

	@Override
	public EmployeeTrainingBo getTrainingDetailsForEmp(String emplId) {
		
		long startTime=System.currentTimeMillis();
		System.out.println("EmployeeTrainingDAO :: getTrainingDetailsForEmp() :: input parameter "+ " emplId "+ emplId  );
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		EmployeeTrainingBo trainingEmployeeBo = new EmployeeTrainingBo();
		try {
			List<String> lst=new ArrayList<String>();
			String sql = "SELECT DISTINCT (Course_Type), name  FROM UBP.Employee_training_mapping  etm , UBP.UBP_WFM  uw WHERE  etm. EMP_ID = '" + emplId+"'"
					+ " and etm.EMP_ID = uw.IBM_Emp_ID  and Status !='completed'";
	        con = DBUtil.getConnection();
	        stmt = con.createStatement();
	        rs = stmt.executeQuery(sql);
	        trainingEmployeeBo.setEmp(emplId);
	        
	        while (rs.next()) {
	        	 lst.add(rs.getString(1)); 
	        	 trainingEmployeeBo.setEmployeName(rs.getString(2));
	        } 
	     
	        trainingEmployeeBo.setCourseType(lst);
	       // System.out.println("EmployeeTrainingDAO :: getTrainingDetailsForEmp() :: Output listEmplTrainingBO list size"+ trainingEmployeeBo.getCourseType().size());
	        
	        
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			DBUtil.closeDBUtil(rs, stmt, con);
		}
		
		 System.out.println("EmployeeTrainingDAO :: getTrainingDetailsForEmp() :: Output listEmplTrainingBO  1st block "+ trainingEmployeeBo.getCourseType().size() + " total time :: "+ (System.currentTimeMillis() - startTime)+ " miliseconds ");
		
		 if (trainingEmployeeBo.getEmployeName() !=null) {
			 System.out.println("EmployeeTrainingDAO :: getTrainingDetailsForEmp() :: Employee Name is fetched "+ trainingEmployeeBo.getEmployeName());
				
		 }
		 else {
		
			 long startTime1=System.currentTimeMillis();
				
		     try {
		        	
		        	String sqlName = "select Name from UBP.UBP_WFM where IBM_Emp_ID = '" + emplId +"'";
		        	con = DBUtil.getConnection();
			        stmt = con.createStatement();
			        rs = stmt.executeQuery(sqlName);
			        while (rs.next()) {
			        	trainingEmployeeBo.setEmployeName(rs.getString(1));  
			        	System.out.println("EmployeeTrainingDAO :: getTrainingDetailsForEmp() :: emplName " + trainingEmployeeBo.getEmployeName());
			        	System.out.println("EmployeeTrainingDAO :: getTrainingDetailsForEmp() :: Output listEmplTrainingBO 2nd blook "+ trainingEmployeeBo.getCourseType().size() + " total time :: "+ (System.currentTimeMillis() - startTime1)+ " miliseconds ");
			     }
			        
		        }catch(SQLException e) {
		        	e.printStackTrace();
		        }finally {
		        	DBUtil.closeDBUtil(rs, stmt, con);
		        }
		         
		 }
		 
	     
	    return trainingEmployeeBo;		
		
	}

	@Override
	public List<EmployeeTrainingMappingBo> getAllEmployeeTrainingByTrainingType(String emplId,  String trainingType) {
		
		System.out.println("EmployeeTrainingDAO :: getAllEmployeeTrainingByTrainingType() :: input parameter "+ " emplId "+ emplId + " traningType "+ trainingType);
		
		Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        List<EmployeeTrainingMappingBo> training_emp_List = new ArrayList<EmployeeTrainingMappingBo>();
            
		try {
			con = DBUtil.getConnection();
			//String sql = "SELECT * FROM Employee_training_mapping WHERE empId = " + emplId +" and  Course_Type =" + trainingType;
			
			String sql = " select etm.PMP_ID,etm.EMP_ID,etm.Course_Type,etm.Training_ID,traning.Course_Name FROM UBP.Employee_training_mapping etm, UBP.Training traning WHERE etm.EMP_ID ='"+emplId+"' and etm.Course_Type= '"+trainingType+"' and etm.Training_ID=traning.Training_ID and Course_Completion_date is null";
			stmt = con.createStatement();
	        rs = stmt.executeQuery(sql);
	        DBUtil.beginTransaction();
	        EmployeeTrainingMappingBo trainingEmployeeBo = null;
	        while (rs.next()) {
	        	
	        	trainingEmployeeBo = new EmployeeTrainingMappingBo();
	        	trainingEmployeeBo.setPmpId(rs.getString(1));
	        	trainingEmployeeBo.setEmpId(rs.getString(2));
	        	trainingEmployeeBo.setTrainingType(rs.getString(3));
	        	trainingEmployeeBo.setTrainingId(rs.getString(4));
	        	trainingEmployeeBo.setCourse_Name(rs.getString(5));
	        	   	
	        	training_emp_List.add(trainingEmployeeBo);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
        
			DBUtil.closeDBUtil(null, stmt, con);	
        }	
		
		return training_emp_List;
		
	}

	@Override
	public boolean updateTraining_Emp_Mapping(EmployeeTrainingBo emplTrainingBO) {

		
		System.out.println("EmployeeTrainingDAO :: updateTraining() :: Output status "+ emplTrainingBO.getStatus());
		//return true;
		PreparedStatement pstmt=null;
		Connection con =null;
		boolean isUpdated=false;
		Iterator<String> iter=emplTrainingBO.getTraining().iterator();
		
		String sql = "UPDATE Employee_training_mapping SET Status=?, Course_Completion_Date=?, Remarks=? WHERE EMP_ID =? and Training_ID=?";
	       
		while(iter.hasNext()) {
			 
		try {
			
			String trainingId=iter.next();
			System.out.println("EmployeeTrainingDAO :: updateTraining() :: Input status "
					+ " emplId " +emplTrainingBO.getEmp() +" trainingId "+trainingId 
					+" status "+emplTrainingBO.getStatus());
			con = DBUtil.getConnection();
			pstmt = (PreparedStatement) con.prepareStatement(sql);
	        pstmt.setString(1, emplTrainingBO.getStatus());
	        if("completed".equalsIgnoreCase(emplTrainingBO.getStatus())) {
	        	pstmt.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
	        }else {
	        	pstmt.setDate(2, null);
	        }
	        if(emplTrainingBO.getRemark() !=null)
	        pstmt.setString(3, emplTrainingBO.getRemark());
	        else
	        pstmt.setString(3, null);
	        pstmt.setString(4, emplTrainingBO.getEmp());
	        pstmt.setString(5, trainingId);
	        DBUtil.beginTransaction();
	        int result = pstmt.executeUpdate();
	        if (result != 0) {
	        	isUpdated=true;
	            DBUtil.commit();
	        } else {
	            DBUtil.rollback();
	        }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			   DBUtil.closeDBUtil(null, pstmt, con);	
		}
                
     
		}
		return isUpdated;
	}

	@Override
	public List<Training> fetchAllTraining() {
		
		System.out.println("EmployeeTrainingDAO :: fetchAllTraining() :: Entry ");
		//Connection con=null;
        Statement stmt=null;
        //ResultSet rs=null;
        List<Training> training_emp_List = new ArrayList<Training>();
        
        
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://sl-eu-lon-2-portal.11.dblayer.com:27965/UBP","superuser","UBP@123");
			Statement st=con.createStatement();
			//con = DBUtil.getConnection();
			
			String sql = "select e.emp_id, e.status, e.Course_Completion_date, u.name, tr.course_name, tr.course_id, t.application_name, t.technology from Employee_training_mapping e,UBP_WFM u,Training tr,Transition t where u.PMP_ID=e.PMP_ID and e.Training_ID=tr.Training_ID";
			//stmt = con.createStatement();
	        //rs = stmt.executeQuery(sql);
	        ResultSet rs=st.executeQuery(sql);
	        DBUtil.beginTransaction();
	        Training trainingBo = null;
	        while (rs.next()) {
	        	
	        	trainingBo = new Training();
	        	trainingBo.setEmployeeId(rs.getString(1));
	        	trainingBo.setStatus(rs.getString(2));
	        	trainingBo.setCourseCompletionDate(rs.getString(3));
	        	trainingBo.setEmployeeName(rs.getString(4));
	        	trainingBo.setCourseName(rs.getString(5));
	        	trainingBo.setCourseId(rs.getString(6));
	        	trainingBo.setApplication(rs.getString(7));
	        	trainingBo.setTechnology(rs.getString(8));
	        	   	
	        	training_emp_List.add(trainingBo);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
        
			//DBUtil.closeDBUtil(null, stmt, con);	
        }	
		System.out.println("EmployeeTrainingDAO :: fetchAllTraining() :: Exit : List size"+training_emp_List.size());
		return training_emp_List;
		/*List<Training> list = new ArrayList<Training>();
		Training training = new Training("employeeId", "Nikhilendu", "course id", "courcse Name", "status", "10/10/17", "UBP", "Java");
		Training training1 = new Training("employeeId", "Nikhilendu", "course id", "courcse Name", "status", "10/10/17", "UBP", "Java");
		Training training2 = new Training("employeeId", "Nikhilendu", "course id", "courcse Name", "status", "10/10/17", "UBP", "Java");
		list.add(training);
		list.add(training1);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		list.add(training);
		list.add(training1);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		list.add(training);
		list.add(training1);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		list.add(training2);
		
		return list;*/
	}

}

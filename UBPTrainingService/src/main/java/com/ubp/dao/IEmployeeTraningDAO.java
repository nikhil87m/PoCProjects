package com.ubp.dao;

import java.util.List;

import com.ubp.bo.EmployeeTrainingBo;
import com.ubp.bo.EmployeeTrainingMappingBo;
import com.ubp.bo.Training;

public interface IEmployeeTraningDAO {

	EmployeeTrainingBo getTrainingDetailsForEmp(String emplId);
	List<EmployeeTrainingMappingBo> getAllEmployeeTrainingByTrainingType(String emplId,String trainingType);	
	boolean updateTraining_Emp_Mapping(EmployeeTrainingBo emplTrainingBO);
	List<Training> fetchAllTraining();
	
}

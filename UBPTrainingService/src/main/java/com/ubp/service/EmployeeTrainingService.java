package com.ubp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubp.bo.EmployeeTrainingBo;
import com.ubp.bo.EmployeeTrainingMappingBo;
import com.ubp.bo.Training;
import com.ubp.dao.IEmployeeTraningDAO;

@Service
public class EmployeeTrainingService implements IEmployeeTrainingService {

	@Autowired
	IEmployeeTraningDAO employeeTrainingDAO;
	
	@Override
	public EmployeeTrainingBo getTrainingDetailsForEmp(String emplId) {
		
		EmployeeTrainingBo EmplTraningBo=employeeTrainingDAO.getTrainingDetailsForEmp(emplId);
		
		return EmplTraningBo;
	}

	@Override
	public List<EmployeeTrainingMappingBo> getAllEmployeeTrainingByTrainingType(String emplId,
			String trainingType) {
		
		List<EmployeeTrainingMappingBo> listEmplTraningByTraningType=employeeTrainingDAO.getAllEmployeeTrainingByTrainingType(emplId,  trainingType);
		
		return listEmplTraningByTraningType;	
	}

	@Override
	public boolean updateTraining_Emp_Mapping(EmployeeTrainingBo emplTrainingBO) {
		
		 return employeeTrainingDAO.updateTraining_Emp_Mapping(emplTrainingBO);
		
		
	}

	@Override
	public List<Training> fetchAllTraining() {
		return employeeTrainingDAO.fetchAllTraining();
	}

}

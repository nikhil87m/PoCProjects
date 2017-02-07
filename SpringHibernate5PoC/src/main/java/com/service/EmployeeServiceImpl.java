package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.EmployeeDAO;
import com.dto.Employee;

@Service("employeeService1")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
    private EmployeeDAO dao;
     
    public void saveEmployee(Employee employee) {
    	System.out.println("Inside save employee: employee"+employee);
        dao.saveEmployee(employee);
    }
 
    public List<Employee> findAllEmployees() {
        return dao.findAllEmployees();
    }
 
    public void deleteEmployeeBySsn(String ssn) {
        dao.deleteEmployeeBySsn(ssn);
    }
 
    public Employee findBySsn(String ssn) {
        return dao.findBySsn(ssn);
    }
 
    public void updateEmployee(Employee employee){
        dao.updateEmployee(employee);
    }
    
    public void callStoreProcedure(){
    	dao.callStoreProcedure();
    }
}

package com.service;

import java.util.List;

import com.dto.Employee;

public interface EmployeeService {
	void saveEmployee(Employee employee);
    List<Employee> findAllEmployees();
    void deleteEmployeeBySsn(String ssn);
    Employee findBySsn(String ssn);
    void updateEmployee(Employee employee);
    void callStoreProcedure();
}

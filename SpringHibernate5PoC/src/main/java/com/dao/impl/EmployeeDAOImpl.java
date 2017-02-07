package com.dao.impl;

import java.sql.Date;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dao.AbstructDAO;
import com.dao.EmployeeDAO;
import com.dto.Employee;
import com.dto.TestResult;

@Repository("employeeDAO11")
public class EmployeeDAOImpl extends AbstructDAO implements EmployeeDAO{
	public void saveEmployee(Employee employee) {
		System.out.println("Inside Employee DAO Impl: employee"+employee);
        persist(employee);
    }
 
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<Employee> findAllEmployees() {
//    	entityManager.getCriteriaBuilder().createQuery(Employee.class);
        Criteria criteria = getSession().createCriteria(Employee.class);
        return (List<Employee>) criteria.list();
    }
 
    public void deleteEmployeeBySsn(String ssn) {
//    	entityManager.getCriteriaBuilder().
        Query query = getSession().createSQLQuery("delete from Employee where ssn = :ssn");
        query.setString("ssn", ssn);
        query.executeUpdate();
    }
 
     
    public Employee findBySsn(String ssn){
    	getSession().getNamedQuery("");
        Criteria criteria = getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("ssn",ssn));
        return (Employee) criteria.uniqueResult();
    }
     
    public void updateEmployee(Employee employee){
        getSession().update(employee);
    }
    
    public void callStoreProcedure(){
    	
    	StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("Script_Status_pr")
                .registerStoredProcedureParameter(1 , String.class , ParameterMode.IN)
                .registerStoredProcedureParameter(2 , Date.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(3 , String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(4 , Date.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(5 , Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(6 , Integer.class, ParameterMode.OUT);
        storedProcedure .setParameter(1, "Nikhil");
        storedProcedure.execute();
        System.out.println(storedProcedure.getOutputParameterValue(1));
        
        StoredProcedureQuery storedProcedure1 = entityManager.createStoredProcedureQuery("Dummy_pr")
//                .registerStoredProcedureParameter(0 , String.class , ParameterMode.IN)
                .registerStoredProcedureParameter(1 , void.class, ParameterMode.REF_CURSOR);
        List<Employee> books = (List<Employee>) storedProcedure1.getResultList();
        System.out.println(books.size());
//        System.out.println(storedProcedure1.getOutputParameterValue(1));
//    	entityManager.createStoredProcedureQuery("Script_Status_pr").execute();
//    	entityManager.create
    	
    }
}

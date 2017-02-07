package com.ob2m.SpringHibernate4PoC;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.dto.Employee;
import com.service.EmployeeService;
import com.spring.configuration.AppConfig;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public static void main (String ...l){
	AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	 
    EmployeeService service = (EmployeeService) context.getBean("employeeService1");
    /*
     * Create Employee1
     */
    Employee employee1 = new Employee();
    employee1.setId(5);
    employee1.setName("Nikhilendu Mandal");
    employee1.setSalary(new BigDecimal(1000));
    employee1.setSsn("12345");

    /*
     * Persist both Employees
     */
//    service.saveEmployee(employee1); 
    //service.saveEmployee(employee2);

    /*
     * Get all employees list from database
     */
   /* List<Employee> employees = service.findAllEmployees();
    for (Employee emp : employees) {
        System.out.println(emp);
    }*/

    /*
     * delete an employee
     */
//    service.deleteEmployeeBySsn("ssn00000002");

    /*
     * update an employee
     */

    /*Employee employee = service.findBySsn("ssn00000001");
    employee.setSalary(new BigDecimal(50000));
    service.updateEmployee(employee);*/

    /*
     * Get all employees list from database
     */
    /*List<Employee> employeeList = service.findAllEmployees();
    for (Employee emp : employeeList) {
        System.out.println(emp);
    }*/

    service.callStoreProcedure();
    context.close();
	}
}
	

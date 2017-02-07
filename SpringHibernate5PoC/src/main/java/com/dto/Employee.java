package com.dto;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
 
@NamedNativeQueries({
    @NamedNativeQuery(
            name    =   "getAllEmployee",
            query   =   "SELECT id, name, salary, ssn" +
                        "FROM employee",
                        resultClass = Employee.class
    )
})
@NamedStoredProcedureQueries({
@NamedStoredProcedureQuery(
        name="Dummy_pr",
        procedureName="Dummy_pr",
        resultClasses = { Employee.class },
        parameters={
//            @StoredProcedureParameter(name="i_input", type=String.class, mode=ParameterMode.IN),
            @StoredProcedureParameter(name="o_output", type=void.class, mode=ParameterMode.REF_CURSOR)
        }
),
@NamedStoredProcedureQuery(
        name="Script_Status_pr",
        procedureName="Script_Status_pr",
        resultClasses = { void.class },
        parameters={
            @StoredProcedureParameter(name="i_script_name", type=String.class, mode=ParameterMode.IN),
            @StoredProcedureParameter(name="o_1", type=Date.class, mode=ParameterMode.OUT),
            @StoredProcedureParameter(name="o_2", type=String.class, mode=ParameterMode.OUT),
            @StoredProcedureParameter(name="o_3", type=Date.class, mode=ParameterMode.OUT),
            @StoredProcedureParameter(name="o_4", type=Integer.class, mode=ParameterMode.OUT),
            @StoredProcedureParameter(name="o_5", type=Integer.class, mode=ParameterMode.OUT)
        }
)
})
@Entity
@Table(name="EMPLOYEE11")
public class Employee {
 
    @Id
    @Column(name="ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    @Column(name = "NAME")
    private String name;
 
    /*@Column(name = "JOINING_DATE", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate joiningDate;*/
 
    @Column(name = "SALARY")
    private BigDecimal salary;
     
    @Column(name = "SSN")
    private String ssn;
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
   /* public LocalDate getJoiningDate() {
        return joiningDate;
    }
 
    public void setJoiningDate(Date date) {
        this.joiningDate = date;
    }*/
 
    public BigDecimal getSalary() {
        return salary;
    }
 
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
 
    public String getSsn() {
        return ssn;
    }
 
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
 
   /* @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Employee))
            return false;
        Employee other = (Employee) obj;
        if (id != other.id)
            return false;
        if (ssn == null) {
            if (other.ssn != null)
                return false;
        } else if (!ssn.equals(other.ssn))
            return false;
        return true;
    }*/
 
    @Override
	public String toString() {
		return "Employee [name=" + name + ", salary=" + salary + ", ssn=" + ssn + "]";
	}
     
     
     
 
}
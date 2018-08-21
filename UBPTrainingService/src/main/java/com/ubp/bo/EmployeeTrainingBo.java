package com.ubp.bo;

import java.io.Serializable;
import java.util.List;

public class EmployeeTrainingBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4205680645211248607L;
	
	private String emp;
	private String status;
	private List<String> training;
	private List<String> courseType;
	private String	employeName;
	private String remark;
	
	public String getEmployeName() {
		return employeName;
	}
	public void setEmployeName(String employeName) {
		this.employeName = employeName;
	}
	public List<String> getCourseType() {
		return courseType;
	}
	public void setCourseType(List<String> courseType) {
		this.courseType = courseType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmp() {
		return emp;
	}
	public void setEmp(String emp) {
		this.emp = emp;
	}
	public List<String> getTraining() {
		return training;
	}
	public void setTraining(List<String> training) {
		this.training = training;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}

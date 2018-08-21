package com.ubp.bo;

public class Training {

	public String employeeId;
	public String employeeName;
	public String courseName;
	public String courseId;
	public String status;
	public String courseCompletionDate;
	public String application;
	public String technology;
	
	/*public Training(String employeeId, String employeeName, String courseName, String courseId, String status,
			String courseCompletionDate, String application, String technology) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.courseName = courseName;
		this.courseId = courseId;
		this.status = status;
		this.courseCompletionDate = courseCompletionDate;
		this.application = application;
		this.technology = technology;
	}*/
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCourseCompletionDate() {
		return courseCompletionDate;
	}
	public void setCourseCompletionDate(String courseCompletionDate) {
		this.courseCompletionDate = courseCompletionDate;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	
}

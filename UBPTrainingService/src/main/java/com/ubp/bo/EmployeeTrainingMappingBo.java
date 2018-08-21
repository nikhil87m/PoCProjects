package com.ubp.bo;

import java.sql.Date;

public class EmployeeTrainingMappingBo {
private String empId;
private String	pmpId;
//private String	employeName;
private String	trainingId;
private String	trainingType;
private boolean isRequired;
//private String	isCompleted;
private String	comment;
private Date	course_Completion_Date ;
private String	nominated_By ;
private String course_Name;
private boolean isExternal;
private String status;
private String remark;

public String getEmpId() {
	return empId;
}
public void setEmpId(String empId) {
	this.empId = empId;
}
public String getPmpId() {
	return pmpId;
}
public void setPmpId(String pmpId) {
	this.pmpId = pmpId;
}
public String getTrainingId() {
	return trainingId;
}
public void setTrainingId(String trainingId) {
	this.trainingId = trainingId;
}
public String getTrainingType() {
	return trainingType;
}
public void setTrainingType(String trainingType) {
	this.trainingType = trainingType;
}
public boolean isRequired() {
	return isRequired;
}
public void setRequired(boolean isRequired) {
	this.isRequired = isRequired;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public Date getCourse_Completion_Date() {
	return course_Completion_Date;
}
public void setCourse_Completion_Date(Date course_Completion_Date) {
	this.course_Completion_Date = course_Completion_Date;
}
public String getNominated_By() {
	return nominated_By;
}
public void setNominated_By(String nominated_By) {
	this.nominated_By = nominated_By;
}
public String getCourse_Name() {
	return course_Name;
}
public void setCourse_Name(String course_Name) {
	this.course_Name = course_Name;
}
public boolean isExternal() {
	return isExternal;
}
public void setExternal(boolean isExternal) {
	this.isExternal = isExternal;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}



}

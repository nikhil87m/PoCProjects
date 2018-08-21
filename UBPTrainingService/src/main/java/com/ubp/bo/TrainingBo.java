package com.ubp.bo;

import java.util.Date;

public class TrainingBo {
	
private String 	training_Id;
private String	course_Name;
private String	course_Id;
private String	courseType;
private int	sequence;
private String	remarks;
private Date CreatedDate;


public String getTraining_Id() {
	return training_Id;
}
public void setTraining_Id(String training_Id) {
	this.training_Id = training_Id;
}
public String getCourse_Name() {
	return course_Name;
}
public void setCourse_Name(String course_Name) {
	this.course_Name = course_Name;
}
public String getCourse_Id() {
	return course_Id;
}
public void setCourse_Id(String course_Id) {
	this.course_Id = course_Id;
}
public String getCourseType() {
	return courseType;
}
public void setCourseType(String courseType) {
	this.courseType = courseType;
}
public int getSequence() {
	return sequence;
}
public void setSequence(int sequence) {
	this.sequence = sequence;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public Date getCreatedDate() {
	return CreatedDate;
}
public void setCreatedDate(Date createdDate) {
	CreatedDate = createdDate;
}







}

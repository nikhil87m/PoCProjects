package com.ubp.bo;

public class AddTrainingBo {
	private String courseType;
	private String courseId;
	private Integer sequence;
	private String remark;
	private String courseName;
	
	
	
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	@Override
	public String toString() {
		return "AddTrainingBo [courseType=" + courseType + ", courseId=" + courseId + ", sequence=" + sequence + ", remark=" + remark + ", courseName=" + courseName + "]";
	}
	
	
}

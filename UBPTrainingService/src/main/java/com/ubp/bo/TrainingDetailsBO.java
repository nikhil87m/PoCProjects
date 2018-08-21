package com.ubp.bo;

import java.sql.Date;
import java.util.List;

public class TrainingDetailsBO {

	
	private String emp;
	private String type;
	//private String training;
	private List<String> training;
	private String status;
	private String remark;
	private String isRequired;
	private String isExternal;
	private Date expectedCompletionDate;
	
	
	public Date getExpectedCompletionDate() {
		return expectedCompletionDate;
	}
	public void setExpectedCompletionDate(Date expectedCompletionDate) {
		this.expectedCompletionDate = expectedCompletionDate;
	}
	public String getIsExternal() {
		return isExternal;
	}
	public void setIsExternal(String isExternal) {
		this.isExternal = isExternal;
	}
	public String getEmp() {
		return emp;
	}
	public void setEmp(String emp) {
		this.emp = emp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getTraining() {
		return training;
	}
	public void setTraining(List<String> training) {
		this.training = training;
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
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	@Override
	public String toString() {
		return "TrainingDetails [emp=" + emp + ", type=" + type + ", training=" + training + ", status=" + status
				+ ", remark=" + remark + ", isRequired=" + isRequired + "]";
	}

	
}

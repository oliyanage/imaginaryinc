package ashan.himalka.imaginaryinc.model;

import java.sql.Date;

public class Tasks {
	
	private int taskID;
	private String taskDate;
	private int hours;
	private String HoursType;
	private String Description;
	private int projectID;
	private int developerID;
	
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public String getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getHoursType() {
		return HoursType;
	}
	public void setHoursType(String hoursType) {
		HoursType = hoursType;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public int getDeveloperID() {
		return developerID;
	}
	public void setDeveloperID(int developerID) {
		this.developerID = developerID;
	}
	
	
	

}

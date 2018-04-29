package ashan.himalka.imaginaryinc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import ashan.himalka.imaginaryinc.model.Tasks;

public class TaskService extends Tasks{

	Connection con = null;
	
	public TaskService() {
		
		 String url = "jdbc:mysql://localhost:3306/imaginaryinc";
		 String username = "root";
		 String password = "";
		 
		 try {
			 Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection(url, username, password);
		 }
		 catch(Exception e) {
			 System.out.println(e);
		 }
	}
	
	public void addTask(Tasks task) {
		
		String sql = "insert into tasks values (?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setInt(1, task.getTaskID());
			st.setString(2, task.getTaskDate());
			st.setInt(3, task.getHours());
			st.setString(4, task.getHoursType());
			st.setString(5, task.getDescription());
			st.setInt(6, task.getProjectID());
			st.setInt(7, task.getDeveloperID());
			
			st.executeUpdate();
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	public Response getAllTakss(){
		
		JSONArray jarray = new JSONArray();
		String sql = "select * from tasks";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql); 
			
			while(rs.next()) {
				JSONObject tasks = new JSONObject();
				tasks.put("TaskID",rs.getInt(1));
				tasks.put("TaskDate",rs.getString(2));
				tasks.put("Hours",rs.getInt(3));
				tasks.put("HoursType",rs.getString(4));
				tasks.put("Description",rs.getString(5));
				tasks.put("ProjectID",rs.getInt(6));
				tasks.put("DeveloperID",rs.getInt(7));
				jarray.put(tasks);
		
			}
			System.out.println(jarray);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		String result = jarray.toString();
		return  Response.status(200).entity(result).build();
	}
	public Response getTasksByManager(int managerID){
		
		JSONArray jarray = new JSONArray();
		String sql = "SELECT projects.Projectname,developers.Developername,tasks.Hours from tasks JOIN projects on projects.ProjectID=tasks.ProjectID and projects.ManagerID="+managerID+" JOIN developers on tasks.DeveloperID = developers.DeveloperID";
		String totHours = "select projects.Projectname,SUM(tasks.Hours) as total from tasks JOIN projects on projects.ProjectID=tasks.ProjectID and projects.ManagerID="+managerID+" GROUP BY projects.Projectname";
		
		System.out.println(sql);
		System.out.println(totHours);
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql); 
			Statement st2 = con.createStatement();
			ResultSet totrs = st2.executeQuery(totHours); 
			double total=0;
			
			while(rs.next()) {
				JSONObject tasks = new JSONObject();
				tasks.put("Projectname",rs.getString(1));
				tasks.put("Developername",rs.getString(2));
				tasks.put("Hours",rs.getInt(3));
				if(rs.getInt(3)>8)
					tasks.put("OverTime",rs.getInt(3)-8);
				else
					tasks.put("OverTime",0);
				
				while(totrs.next()) {
					if(rs.getString(1).equals(totrs.getString(1))) {
						total=totrs.getInt(2);
					}
				}
				
				tasks.put("Contribution",rs.getDouble(3)/total*100);
				
				jarray.put(tasks);
		
			}
			System.out.println(jarray);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		String result = jarray.toString();
		return  Response.status(200).entity(result).build();
	}
	
	public Response updateTask(Tasks tasks) {
		
		String sql = "update tasks set TaskDate=?, Hours=? where TaskID=? ";
		String result=null;
		try {
			PreparedStatement st = con.prepareStatement(sql);
			System.out.println("xxxx "+tasks.getTaskID());
			st.setString(1, tasks.getTaskDate());
			st.setInt(2, tasks.getHours());
			st.setInt(3, tasks.getTaskID());
			
			System.out.println(sql);
			int x=st.executeUpdate();
			JSONObject jsonObject = new JSONObject();
			if(x==1){
				jsonObject.put("status", "Done"); 
			}
				
			else 
				jsonObject.put("status", "Error");
		

			 result = jsonObject.toString();
			return  Response.status(200).entity(result).build();
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		return  Response.status(200).entity(result).build();
	}

	public void removeTask(Tasks task) {
		String sql = "delete from tasks where TaskID=?";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setInt(1, task.getTaskID());
			
			st.executeUpdate();
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
}

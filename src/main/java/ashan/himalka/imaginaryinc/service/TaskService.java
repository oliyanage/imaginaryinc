package ashan.himalka.imaginaryinc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

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
		JSONObject tasks = new JSONObject();
		String sql = "select * from tasks";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql); 
			int count=1;
			while(rs.next()) {
				
				tasks.put("TaskID"+count,rs.getInt(1));
				tasks.put("TaskDate"+count,rs.getString(2));
				tasks.put("Hours"+count,rs.getInt(3));
				tasks.put("HoursType"+count,rs.getString(4));
				tasks.put("Description"+count,rs.getString(5));
				tasks.put("ProjectID"+count,rs.getInt(6));
				tasks.put("DeveloperID"+count,rs.getInt(7));
				
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		String result = tasks.toString();
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

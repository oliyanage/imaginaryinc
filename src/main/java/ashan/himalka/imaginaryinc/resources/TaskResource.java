package ashan.himalka.imaginaryinc.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ashan.himalka.imaginaryinc.model.Tasks;
import ashan.himalka.imaginaryinc.service.TaskService;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource extends Tasks {
	
	TaskService taskService = new TaskService();
	@POST
	@Path("/add-tasks")
	public Tasks addMessage(Tasks t1) {
		 taskService.addTask(t1);
		 
		 return t1;
	}
	
	@GET
	@Path("/get-tasks")
	public Response getTasks() {
		return taskService.getAllTakss();
	}
	@GET
	@Path("/get-tasksbymanager/{managerID}")
	public Response getTasksByManager(@PathParam("managerID") int managerID) {
		return taskService.getTasksByManager(managerID);
	}
	@PUT
	@Path("/update-tasks/{id}/{date}/{hours}")
	public Response updateTask(@PathParam("id") int taskId ,@PathParam("date") String date,@PathParam("hours") int hours) {
		Tasks tasks=new Tasks();
		System.out.println("yyy "+taskId);
		tasks.setTaskID(taskId);
		tasks.setTaskDate(date);
		tasks.setHours(hours);
		return taskService.updateTask(tasks);
		
	}
	
	@DELETE
	@Path("/delete-tasks/{id}")
	public Tasks removeTask(@PathParam("id") int taskId, Tasks t1) {
		t1.setTaskID(taskId);
		taskService.removeTask(t1);
		return t1;
	}

}

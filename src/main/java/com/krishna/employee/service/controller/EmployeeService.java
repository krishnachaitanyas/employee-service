package com.krishna.employee.service.controller;

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
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishna.employe.service.model.Employee;
import com.krishna.employee.service.dao.EmployeeDAO;

@Path("/v1/employee")
public class EmployeeService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getEmployeeDetails(@PathParam("id") String id) throws Exception{
		EmployeeDAO employeeDao = new EmployeeDAO();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(employeeDao.getEmployeeDetails(id));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String getAllEmployeeDetails(@PathParam ("id") String id) throws Exception{
		EmployeeDAO employeeDao = new EmployeeDAO();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(employeeDao.getEmployeeDetails());
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEmployee(String employee)throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		Employee employeeObj = mapper.readValue(employee, Employee.class);
		Response response = null;
		EmployeeDAO employeeDao = new EmployeeDAO();
		int count = employeeDao.addEmployeeDetails(employeeObj);
		response.status(Status.CREATED);
		return response;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEmployee(String employee)throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		Employee employeeObj = mapper.readValue(employee, Employee.class);
		Response response = null;
		EmployeeDAO employeeDao = new EmployeeDAO();
		int count = employeeDao.updateEmployeeDetails(employeeObj);
		response.status(Status.OK);
		return response;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteEmployeeDetails(@PathParam ("id") String id) throws Exception{
		EmployeeDAO employeeDao = new EmployeeDAO();
		ObjectMapper mapper = new ObjectMapper();
		int count = employeeDao.deleteEmployeeDetails(id);
		return Response.ok().build();
	}

}

package com.krishna.employee.service.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.krishna.employe.service.model.Employee;
import com.krishna.employee.service.util.FileWriterUtil;

public class EmployeeDAO {

	public List<Employee> getEmployeeDetails() throws Exception {
		return getEmployeeDetails("all");
	}

	public List<Employee> getEmployeeDetails(String id) throws Exception {
		Connection dbConnection = getDBConnection();
		Statement testStatement = dbConnection.createStatement();
		String testQuery = null;
		if ("all".equals(id))
			testQuery = "select * from employee_details;";
		else
			testQuery = "select * from employee_details " + "where id='" + id + "'";
		ResultSet resultSet = testStatement.executeQuery(testQuery);
		List<Employee> employeesList = new ArrayList<Employee>();
		while (resultSet.next()) {
			Employee employee = new Employee();
			employee.setId(resultSet.getString("id"));
			employee.setName(resultSet.getString("name"));
			employeesList.add(employee);
		}
		testStatement.close();
		dbConnection.close();
		return employeesList;
	}

	public int addEmployeeDetails(Employee employee) throws Exception {
		Connection dbConnection = getDBConnection();
		String testQuery = "insert into employee_details(id,name) values(?,?);";
		PreparedStatement testStatement = dbConnection.prepareStatement(testQuery);
		testStatement.setString(1, employee.getId());
		testStatement.setString(2, employee.getName());
		int count = testStatement.executeUpdate();
		testStatement.close();
		dbConnection.close();
		File file = new File("employeeRecord.txt");
		FileOutputStream outStream = new FileOutputStream(file);
		String recordToBeWritten = employee.getId() + "," + employee.getName();
		outStream.write(recordToBeWritten.getBytes());
		outStream.close();
		String status = FileWriterUtil.sendFileToRemoteLocation("employeeRecord.txt");
		if("Done".equals(status))
		return count;
		else
			return -1;
	}

	public int updateEmployeeDetails(Employee employee) throws Exception {
		Connection dbConnection = getDBConnection();
		String testQuery = "update employee_details set name=? where id=?;";
		PreparedStatement testStatement = dbConnection.prepareStatement(testQuery);
		testStatement.setString(1, employee.getName());
		testStatement.setString(2, employee.getId());
		int count = testStatement.executeUpdate();
		testStatement.close();
		dbConnection.close();
		return count;
	}

	public int deleteEmployeeDetails(String employeeId) throws Exception {
		Connection dbConnection = getDBConnection();
		String testQuery = "delete from employee_details where id=?;";
		PreparedStatement testStatement = dbConnection.prepareStatement(testQuery);
		testStatement.setString(1, employeeId);
		int count = testStatement.executeUpdate();
		testStatement.close();
		dbConnection.close();
		return count;
	}

	private Connection getDBConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Properties properties = new Properties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		properties.load(inputStream);
		Connection dbConnection = DriverManager.getConnection(properties.getProperty("url"),
				properties.getProperty("username"), properties.getProperty("password"));
		return dbConnection;
	}

}

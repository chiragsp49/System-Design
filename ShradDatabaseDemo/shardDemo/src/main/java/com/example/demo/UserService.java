package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	Connection[] connections = new Connection[2];
	public UserService() {
		try {
			connections[0]=getDbConnection();
			connections[1]=getDbConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Connection getDbConnection() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/office", "root", "admin");
			return conn; 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
	}
	
	public String getUserName(int userId) throws Exception {
		Connection conn = null;
		if(userId==2) {
			 conn = connections[0];
		}else if(userId==3) {
			 conn = connections[0];
		}else {
			throw new Exception("No DB found for this user Id");
		}
		
		Statement stmt = conn.createStatement();
		ResultSet rst = stmt.executeQuery("SELECT * from employees where id = " + userId);
		String name = "";
		while(rst.next()) {
			name = rst.getString("name");
		}
		
		return name;
	}
	
	
	
}

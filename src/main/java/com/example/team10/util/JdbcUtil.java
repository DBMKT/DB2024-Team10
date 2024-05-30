package main.java.com.example.team10.util;

import java.sql.*;
public class JdbcUtil {

	public static Connection getConnection() {
		Connection conn = null;
		
		//JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/db2024team10";
		//Database credentials
		//MySQL 계정과 암호 입력
		final String USER = "root"; 
		final String PASS = "root";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			
			System.out.println("커넥션이 성공적으로 맺어졌습니다.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			conn.close();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet res) {
		try {
			res.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {
		try {
			conn.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			conn.rollback();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

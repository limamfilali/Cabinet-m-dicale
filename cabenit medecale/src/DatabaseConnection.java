package DAO;

import java.sql.*;

public class DatabaseConnection {
	private static DatabaseConnection Instance;
	private Connection connection;
	//thin==driver type for Oracle
	private String url = "jdbc:sqlserver://127.0.0.1\\SQLExpress;database=Hospital;integratedSecurity=false;username=sa;password=070512";
	private DatabaseConnection()  {
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection=DriverManager.getConnection(url);  

		} 
		catch (SQLException ex) {
			System.out.println("Database Connection Creation Failed SQLException : " + ex.getMessage());
		} catch (ClassNotFoundException ex) {
			System.out.println("Database Connection Creation Failed ClassNotFoundException : " + ex.getMessage());
		}

	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static  DatabaseConnection getInstance() {
		if(Instance==null) return new DatabaseConnection();
		return Instance;
	}
}

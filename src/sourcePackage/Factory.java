package sourcePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Factory {
	
	public static Database getDBObject() {
		return new Database();
	}
	
	public static User getUser(String username, String passwordHash) {
		return new User(username, passwordHash);
	}
	
	// Potentially temporary method for establishing a connection.
	public static Connection getDBConnection(){
		Connection connection;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver missing.");
			e.printStackTrace();
			return null;
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pass");
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return null;
		}
		
		try{
			PreparedStatement statement = connection.prepareStatement("USE quiz_website;");
			statement.execute();
		} catch (SQLException e) {
			System.out.println("Connection failed to execute use querry.");
			closeConnection(connection);
			return null;
		}
		
		return(connection);
	}
	
	// Potentially temporary method for closing a connection.
	public static boolean closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}

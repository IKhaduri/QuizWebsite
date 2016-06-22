package sourcePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

public class Factory_Database {

	public static Database getDBObject() {
		return new Database();
	}
	
	// Potentially temporary method for closing a connection.
	public static boolean closeConnection(Connection connection){
			
		if (connection == null) return false;
		
		try {
			connection.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * returns a mySQL connection connecting to 
	 * database with credentials in MyDBInfo.java
	 * @return mySql connection
	 * 
	 * */
	public static Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME,
					MyDBInfo.MYSQL_PASSWORD);
			connection.setCatalog(MyDBInfo.MYSQL_DATABASE_NAME);
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver not found");
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void cleanSession(HttpSession session) {
		if (session != null) {
			session.removeAttribute(SessionListener.USER_IN_SESSION);
		    session.invalidate();
		}
	}
	
}



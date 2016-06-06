package sourcePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Factory {
	
	public static Database getDBObject() {
		return new Database();
	}
	
	public static User getUser(String username, String passwordHash) {
		return new User(username, passwordHash);
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

	public static Quiz getQuiz(String name, Timestamp date, String author){
		return new Quiz(new Quiz.Header(name, date, author), new Quiz.Parameters(false), new ArrayList<Question>());
	}
	
	public static QuizBase getQuizBase(String name,Timestamp date,String author,int totalScore, 
			int numSubmissions, int quizScore){
		
		
		return null;
	}
	

}

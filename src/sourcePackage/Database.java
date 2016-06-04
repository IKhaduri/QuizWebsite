package sourcePackage;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Database {

	/**
	 * Adds a user in database
	 * @param  user - user that must be added in database
	 * @return whether new user added to database or not.
	 * */
	public boolean addUser(User user, Connection connection){
		if(connection == null || user == null) return false;
		try {
			String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1,  user.getName());
			statement.setString(2,  user.getPasswordHash());
			statement.execute();
		} catch (SQLException e){
			return false;
		}
		return true;
	}
	/**
	 * Adds a quiz in database
	 * @param quiz - quiz that must be added in database
	 * @return whether new quiz added to database or not.
	 * */
	public boolean addQuiz(Quiz quiz, Connection connection){
		if(connection == null || quiz == null) return false;
		try{
			String sql = "";
			PreparedStatement statement = connection.prepareStatement(sql);
			
		} catch (SQLException e){
			return false;
		}
		return true;
	}
	/**
	 * @param name - name of quiz, unique
	 * @return Quiz type object which you're searching for or null if doesn't exist
	 * 
	 * */
	public Quiz getQuiz(String name){ 
		return null;
	}
	
	/**
	 * @param name - username for user we're searching for
	 * @return matching user or null if not found
	 * */
	public User getUser(String name, String password_hash){
		
		
		return null;
	}
	
	public List<User> getTopUsers(){
		
		
		return null;
	}
	/**
	 * @param num - number of top popular quizzes you want
	 * @return list of popular quizzes, size is num or all quizzes available, whichever bigger
	 * 
	 * */
	public List<Quiz> getPopularQuizzes(int num){
		
		
		return null;
	}
	/**
	 * @param num - number of recently added quizzes you want
	 * @return list of recently added quizzes, size is num or all quizzes available, whichever bigger
	 * 
	 * */
	public List<Quiz> recentlyAddedQuizzes(int num){
		
		return null;
	}
	
	
	@SuppressWarnings("unused")
	private Question getQuestions( String id){
		
		
		return null;
	}
	
}

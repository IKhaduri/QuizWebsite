package sourcePackage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
	private static final int NO_ID = -1;
	
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
			addQuizBase(quiz, connection);
			try{
				addQuestions(quiz, connection);
			}catch (Exception e){
				removeQuizFromDB(quiz.getName(), connection);
				return(true);
			}
		} catch (SQLException e){
			return false;
		}
		return true;
	}
	
	private int getIdForName(String name, String tableName, String nameField, Connection connection) throws SQLException{
		if(name == null) return(NO_ID);
		PreparedStatement statement = connection.prepareStatement("SELECT (id) FROM " + tableName + " WHERE " + nameField + " = ?");
		statement.setString(1, name);
		ResultSet res = statement.executeQuery();
		if(res.next()){
			return(res.getInt("id"));
		}else return NO_ID;
	}
	
	private int getUserId(String username, Connection connection) throws SQLException{
		return(getIdForName(username, "users", "username", connection));
	}
	
	private int getQuizId(String nuizName, Connection connection) throws SQLException{
		return(getIdForName(nuizName, "quizes", "quiz_name", connection));
	}
	
	private void addQuizBase(Quiz quiz, Connection connection) throws SQLException{
		int autorId = getUserId(quiz.getAuthor(), connection);
		String sql = "INSERT INTO quizes (quiz_name, creation_date, random_shuffle, question_cap, time_limit, author_id) VALUES (?, ?, ?, ?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, quiz.getName());
		statement.setTimestamp(2, quiz.getCreationDate());
		statement.setBoolean(3, quiz.shouldShuffle());
		statement.setInt(4, quiz.getQuestionCap());
		statement.setInt(5, quiz.getQuizTime());
		statement.setInt(6,  autorId);
		statement.execute();
	}
	
	private void removeQuizFromDB(String quizName, Connection connection) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM quizes WHERE quiz_name = ?;");
		statement.setString(1, quizName);
		statement.execute();
	}
	
	private void addQuestions(Quiz quiz, Connection connection) throws SQLException, IOException{
		if(quiz.getQuestionCount() <= 0) return;
		int quizId = getQuizId(quiz.getName(), connection);
		String sql = "INSERT INTO questions (quiz_id, index_in_quiz, serialized_object, score) VALUES";
		for(int i = 0; i < quiz.getQuestionCount(); i++){
			sql += "(?, ?, ?, ?)";
			if(i < (quiz.getQuestionCount() - 1)){
				sql += ", ";
			}else sql += ";";
		}
		PreparedStatement statement = connection.prepareStatement(sql);
		for(int i = 0; i < quiz.getQuestionCount(); i++){
			int startParam = i*4;
			Question question = quiz.getQuestion(i);
			statement.setInt(startParam + 1, quizId);
			statement.setInt(startParam + 2, i);
			statement.setString(startParam + 3, Serialization.toString(question));
			statement.setInt(startParam + 4, 1); // This may be changed in the future.
		}
		statement.execute();
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
	 * @return list of recently added quizzes, size is num or all quizzes available, whichever smaller
	 * 
	 * */
	public List<Quiz> recentlyAddedQuizzes(int num,Connection connection){
		List<Quiz> res = null;
		ResultSet set = null;
		if (connection==null||num==0)
			return res;
		try{
			String stmt = "select * from " + MyDBInfo.MYSQL_DATABASE_NAME+
					". quizes order by creation_date DESC limit "+num;
			set = connection.prepareStatement(stmt).executeQuery();
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		if (set!=null){
			res = new ArrayList<Quiz>();
			try {
				do{
					Quiz curQuiz;
					res.add(Factory.getQuiz(set.getString(2),set.getTimestamp(3),getAuthorName(set.getInt(4))));
				}
				while(set.next());
								
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		
		return res;
	}
	
	
	private String getAuthorName(int authorId){
		
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private Question getQuestions( String id){
		
		
		return null;
	}
	
}

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
			String sql = "INSERT INTO " + MyDBInfo.MYSQL_DATABASE_NAME + ".users (username, password_hash) VALUES (?, ?);";
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
				return false;
			}
		} catch (SQLException e){
			return false;
		}
		return true;
	}
	
	private int getIdForName(String name, String tableName, String nameField, Connection connection) throws SQLException{
		if(name == null) return(NO_ID);
		PreparedStatement statement = connection.prepareStatement("SELECT (id) FROM " + MyDBInfo.MYSQL_DATABASE_NAME + "." + tableName + " WHERE " + nameField + " = ?");
		statement.setString(1, name);
		ResultSet res = statement.executeQuery();
		if(res.next()){
			return(res.getInt("id"));
		}else return NO_ID;
	}
	
	private int getUserId(String username, Connection connection) throws SQLException{
		return(getIdForName(username, "users", "username", connection));
	}
	
	private int getQuizId(String quizName, Connection connection) throws SQLException{
		return(getIdForName(quizName, "quizes", "quiz_name", connection));
	}
	
	private void addQuizBase(Quiz quiz, Connection connection) throws SQLException{
		int autorId = getUserId(quiz.getAuthor(), connection);
		String sql = "INSERT INTO " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizes (quiz_name, creation_date, random_shuffle, question_cap, time_limit, author_id, quiz_score) VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, quiz.getName());
		statement.setTimestamp(2, quiz.getCreationDate());
		statement.setBoolean(3, quiz.shouldShuffle());
		statement.setInt(4, quiz.getQuestionCap());
		statement.setInt(5, quiz.getQuizTime());
		statement.setInt(6, autorId);
		statement.setInt(7, quiz.getQuizScore());
		statement.execute();
	}
	
	private void removeQuizFromDB(String quizName, Connection connection) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizes WHERE quiz_name = ?;");
		statement.setString(1, quizName);
		statement.execute();
	}
	
	private void addQuestions(Quiz quiz, Connection connection) throws SQLException, IOException{
		if(quiz.getQuestionCount() <= 0) return;
		int quizId = getQuizId(quiz.getName(), connection);
		String sql = "INSERT INTO " + MyDBInfo.MYSQL_DATABASE_NAME + ".questions (quiz_id, index_in_quiz, serialized_object) VALUES";
		for(int i = 0; i < quiz.getQuestionCount(); i++){
			sql += "(?, ?, ?)";
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
		}
		statement.execute();
	}
	
	/**
	 * @param name - name of quiz, unique
	 * @return Quiz type object which you're searching for or null if doesn't exist
	 * 
	 * */
	public Quiz getQuiz(String name, Connection connection){ 
		try {
			String sql = "SELECT * from " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizes WHERE quiz_name = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet res = statement.executeQuery();
			if(res.next()){
				Timestamp date = res.getTimestamp("creation_date");
				String author = getAuthorName(res.getInt("author_id"), connection);
				int totalScore = res.getInt("total_score");
				int numSubmissions = res.getInt("total_submittions");
				int quizScore = res.getInt("quiz_score");
				boolean shouldShaffle = res.getBoolean("random_shuffle");
				int questionCap = res.getInt("question_cap");
				int timeLimit = res.getInt("time_limit");
				List<Question> questions = getQuizQuestions(res.getInt("id"), connection);
				return Factory.getQuiz(name, date, author, totalScore, numSubmissions, quizScore, shouldShaffle, questionCap, timeLimit, questions);
			} else return null;
		} catch (SQLException ex) {
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
			
	}
	
	private List<Question> getQuizQuestions(int quizId, Connection connection) throws SQLException, ClassNotFoundException, IOException {
		String sql = "SELECT * FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".questions WHERE quiz_id = ? ORDER BY index_in_quiz ASC;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, quizId);
		ResultSet res = statement.executeQuery();
		List<Question> questions = new ArrayList<Question>();
		while(res.next()){
			questions.add((Question)Serialization.fromString(res.getString("serialized_object")));
		}
		return questions;
	}

	/**
	 * @param connection - connection object
	 * @param id - quizz id
	 * @return creation date of the specified quiz
	 * @throws SQLException
	 */
	private Timestamp getQuizDate(Connection connection, int id) throws SQLException {
		if (connection == null) return null;
		
		String query = "select (creation_date) from quizes where id = ?";
		
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs == null) return null;
		
		rs.next();
		Timestamp ts = rs.getTimestamp(1);
		
		return ts;
	}
	
	/**
	 * @param name - username for user we're searching for
	 * @return matching user or null if not found
	 * */
	public User getUser(String name, String password_hash, Connection connection){
		try {
			String sql = "select password_hash from users where username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs == null) 
				return null;
			rs.next();
			String password_from_db = rs.getString(1);
			if (!password_hash.equals(password_from_db)) 
				return null;
			return Factory.getUser(name, password_from_db);
		} catch (Exception ex) {
			return null;
		}
		
	}

	/**
	 * gets popular quizzes of all time, to display 
	 * or simply get information.
	 * @param num - number of top popular quizzes you want
	 * @return list of popular quizzes, size is num or all quizzes available, whichever bigger
	 * 
	 * */
	public List<QuizBase> getPopularQuizzes(int num, Connection connection) {
		if (connection == null||num == 0) return null;
		
		List<QuizBase> res = null;
		
		ResultSet set = null;
		try{
			String sql = "select * from "+ MyDBInfo.MYSQL_DATABASE_NAME +" .quizes q"+ 
					"order by (select count(*) from"+ MyDBInfo.MYSQL_DATABASE_NAME+
					".event_log e where e.quiz_id = q.id ) DESC "+ 
					"limit ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, num);
			set = ps.executeQuery();
			if (set == null) 
				return null;
		} catch(Exception e){
			return null;
		}
		try {
			res = getQuizBaseList(set, connection);			
		} catch (Exception e) {
			return null;
		}
		
		return res;
	}
	
	/**
	 * @param num - number of recently added quizzes you want (should NOT be negative)
	 * @return list of recently added quizzes, size is num or all quizzes available, whichever smaller
	 * 
	 * */
	public List<QuizBase> recentlyAddedQuizzes(int num,Connection connection){
		try{
			ResultSet set = null;
			if (connection==null||num<=0)
				return null;
			String stmt = "select * from " + MyDBInfo.MYSQL_DATABASE_NAME+
						".quizes order by creation_date DESC limit ?";
			PreparedStatement ps = connection.prepareStatement(stmt);
			ps.setInt(1, num);
			set = ps.executeQuery();	
			
			if (set!=null)
				return getQuizBaseList(set, connection);
			
			return null;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private QuizBase getQuizBase(ResultSet res, Connection connection) throws SQLException{
		String name = res.getString("quiz_name");
		Timestamp date = res.getTimestamp("creation_date");
		String author = getAuthorName(res.getInt("author_id"), connection);
		int totalScore = res.getInt("total_score");
		int numSubmissions = res.getInt("total_submittions");
		int quizScore = res.getInt("quiz_score");
		return (Factory.getQuizBase(name, date, author, totalScore, numSubmissions, quizScore));
	}
	
	private List<QuizBase> getQuizBaseList(ResultSet res, Connection connection) throws SQLException{
		List<QuizBase> list = new ArrayList<QuizBase>();
		while(res.next())
			list.add(getQuizBase(res, connection));
		return list;
	}
	
	/*
	 * returns author name, a unique identifier of
	 * quiz author
	 * */
	private String getAuthorName(int authorId, Connection connection) throws SQLException{
		String authorName = null;
		ResultSet set = null;
		String stmt = "select * from " + MyDBInfo.MYSQL_DATABASE_NAME+ ".users where id = "+authorId;
		set = connection.prepareStatement(stmt).executeQuery();
		if (set!=null){
			set.next();
			authorName = set.getString(1);
		}
		return authorName;
	}
	
	private String getAuthorname(int quizId, Connection connection) throws Exception {
		String authorName = null;
		// ResultSet set = null;
		int authorId = getAuthorId(quizId, connection);
		authorName = getAuthorName(authorId, connection);
		return authorName;
	}
	
	private int getAuthorId(int quizId, Connection connection) throws Exception {
		int authorId = -1;
		String stmt = "select author_id from " + MyDBInfo.MYSQL_DATABASE_NAME+ ".quizes where quiz_id = "+quizId;
		ResultSet set = connection.prepareStatement(stmt).executeQuery();
		set.next();
		authorId =  set.getInt(0);
		return authorId;
	}
	
	
	/**
	 * Gets the user's submissions
	 * @param user - user
	 * @param limit - maximal amount of the submissions to be returned
	 * @param connection - connection
	 * @return list of user's submissions
	 */
	public List<Submission> getUserSubmissions(User user, int limit, Connection connection){
		try {
			int userId = getUserId(user.getName(), connection);
			String sql = "SELECT * from " + MyDBInfo.MYSQL_DATABASE_NAME + ".event_log"
					+ " INNER JOIN " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizes ON id = quiz_id"
					+ " WHERE user_id = ? ORDER BY end_time DESC LIMIT ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.setInt(2, limit);
			ResultSet res = statement.executeQuery();
			if(res == null) return null;
			
			List<Submission> submissions = new ArrayList<Submission>();
			while(res.next()){
				QuizBase quiz = getQuizBase(res, connection);
				Timestamp start = res.getTimestamp("start_time");
				Timestamp end = res.getTimestamp("end_time");
				int score = res.getInt("score");
				submissions.add(Factory.getSubmission(quiz, start, end, score,user.getName()));
			}
			return(submissions);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package sourcePackage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
	public static final int NO_ID = -1;
	public static final int NO_CONNECTION = -2;
	public static final int FAIL_EXPECTED_INT = -3;
	
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
	 * Sets user status
	 * @param username - user
	 * @param status - new status
	 * @return true, if successful
	 */
	public boolean setUserStatus(String username, String status, Connection connection){
		
		if (connection == null || status == null || username == null) return false;
		
		try{
			String sql = "UPDATE " + MyDBInfo.MYSQL_DATABASE_NAME + ".users SET user_status = ? WHERE username = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, status);
			statement.setString(2, username);
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Fetches the user's status
	 * @param username - user name
	 * @return status (null in case of a failure)
	 */
	public String getUserStatus(String username, Connection connection){
		
		if (connection == null || username == null) return null;
		
		try{
			String sql = "SELECT user_status FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".users WHERE username = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet res = statement.executeQuery();
			if(res != null && res.next()){
				return res.getString("user_status");
			}else return "";
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Sets an image for the given user
	 * @param username - user name
	 * @param imageUrl - image address
	 * @return true, if successful
	 */
	public boolean setUserImage(String username, String imageUrl, Connection connection){
		if(username == null || imageUrl == null || connection == null) return false;
		try{
			String sql = "UPDATE " + MyDBInfo.MYSQL_DATABASE_NAME + ".users SET user_image = ? WHERE username = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, imageUrl);
			statement.setString(2, username);
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Gets image address for the given user
	 * @param username - user name
	 * @return address or null, if there's none/base is dead or any exception occurred
	 */
	public String getUserImage(String username, Connection connection){
		if(username == null || connection == null) return null;
		try{
			String sql = "SELECT user_image FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".users WHERE username = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet res = statement.executeQuery();
			if(res != null && res.next()){
				return res.getString("user_image");
			}else return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Tells, if the user's quizzes are shared to everyone
	 * @param username - user name
	 * @return - true, if the quizzes are shared (failure yields false as well)
	 */
	public boolean userSharesQuizzes(String username, Connection connection){
		if (connection == null || username == null) return false;
		try{
			String sql = "SELECT shares_quizzes FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".users WHERE username = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet res = statement.executeQuery();
			if(res.next())
				return(res.getBoolean("shares_quizzes"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Changes shares_quizzes value in DB for the given user
	 * @param username - user name
	 * @param passwordHash - hash for the password
	 * @param newValue - new value for shares_quizzes
	 * @return true, if successful
	 */
	public boolean changeQuizSharing(String username, String passwordHash, boolean newValue, Connection connection){
		if (connection == null || username == null || passwordHash == null) return false;
		if (getUser(username, passwordHash, connection) == null) return false;
		try{
			String sql = "UPDATE " + MyDBInfo.MYSQL_DATABASE_NAME + ".users SET shares_quizzes = ? WHERE username = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBoolean(1, newValue);
			statement.setString(2, username);
			statement.execute();
		} catch (SQLException e) {
			return false;
		} catch (NullPointerException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Tells, whether the Quiz name is used or not
	 * @param quizName - name of the quiz
	 * @return true, if the name is not used and NO exception occurred (dead DB makes the method yield false as well)
	 */
	public boolean quizNameAvailable(String quizName, Connection connection){
		if(quizName == null || connection == null) return false;
		try{
			String sql = "SELECT id FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes WHERE quiz_name = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, quizName);
			ResultSet res = statement.executeQuery();
			return (!res.next());
		} catch(SQLException e){
			return false;
		} catch(NullPointerException e){
			return false;
		}
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
		return(getIdForName(quizName, "quizzes", "quiz_name", connection));
	}
	
	private void addQuizBase(Quiz quiz, Connection connection) throws SQLException{
		int autorId = getUserId(quiz.getAuthor(), connection);
		String sql = "INSERT INTO " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes "
				+ "(quiz_name, creation_date, random_shuffle, question_cap, time_limit, author_id, quiz_score, description, is_single_page) "
				+ "VALUES ( ?, 			   ?, 			   ?,			  ?,		  ?, 	 	?, 			?, 			 ?, 			 ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, quiz.getName());
		statement.setTimestamp(2, quiz.getCreationDate());
		statement.setBoolean(3, quiz.shouldShuffle());
		statement.setInt(4, quiz.getQuestionCap());
		statement.setInt(5, quiz.getQuizTime());
		statement.setInt(6, autorId);
		statement.setInt(7, quiz.getQuizScore());
		statement.setString(8, quiz.getDescription());
		statement.setBoolean(9, quiz.isSinglePage());
		statement.execute();
	}
	
	private void removeQuizFromDB(String quizName, Connection connection) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes WHERE quiz_name = ?;");
		statement.setString(1, quizName);
		statement.execute();
	}
	
	private void addQuestions(Quiz quiz, Connection connection) throws SQLException, IOException{
		if(quiz.getQuestionCount() <= 0) return;
		int quizId = getQuizId(quiz.getName(), connection);
		if(quizId == NO_ID) return;
		String sql = "INSERT INTO " + MyDBInfo.MYSQL_DATABASE_NAME + ".questions (quiz_id, index_in_quiz, serialized_object) VALUES";
		for(int i = 0; i < quiz.getQuestionCount(); i++){
			sql += "(?, ?, ?)";
			if(i < (quiz.getQuestionCount() - 1)){
				sql += ", ";
			}else sql += ";";
		}
		PreparedStatement statement = connection.prepareStatement(sql);
		for(int i = 0; i < quiz.getQuestionCount(); i++){
			int startParam = i*3;
			QuestionAbstract question = quiz.getQuestion(i);
			statement.setInt(startParam + 1, quizId);
			statement.setInt(startParam + 2, i);
			statement.setString(startParam + 3, Serialization.toString(question));
		}
		statement.execute();
	}
	
	/**
	 * Saves the submission results to DB
	 * (startTime and endTime will be set to "now");
	 * @param quizName - quiz name
	 * @param username - user name
	 * @param score - user's score
	 * @param quizScore - total score for the quiz
	 * @return true, if successful
	 */
	public boolean logSubmission(String quizName, String username, int score, int quizScore, Connection connection){
		return logSubmission(quizName, username, score, quizScore, now(), now(), connection);
	}
	
	/**
	 * Saves the submission results to DB
	 * (endTime will be set to "now");
	 * @param quizName - quiz name
	 * @param username - user name
	 * @param score - user's score
	 * @param quizScore - total score for the quiz
	 * @param startTime - test start date
	 * @return true, if successful
	 */
	public boolean logSubmission(String quizName, String username, int score, int quizScore, Timestamp startTime, Connection connection){
		return logSubmission(quizName, username, score, quizScore, startTime, now(), connection);
	}
	
	
	/**
	 * Saves the submission results to DB
	 * @param quizName - quiz name
	 * @param username - user name
	 * @param score - user's score
	 * @param quizScore - total score for the quiz
	 * @param startTime - test start date
	 * @param endTime - test end date
	 * @return true, if successful
	 */
	public boolean logSubmission(String quizName, String username, int score, int quizScore, Timestamp startTime, Timestamp endTime, Connection connection){
		try{
			int quizId = getQuizId(quizName, connection);
			int userId = getUserId(username, connection);
			if(quizId == NO_ID || userId == NO_ID) return false;
			updateQuiz(quizId, score, connection);
			updateUserStatistics(userId, score, quizScore, connection);
			logSubmission(quizId, userId, score, startTime, endTime, connection);
		} catch (SQLException ex) {
			return false;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void updateUserStatistics(int userId, int score, int quizScore, Connection connection) {
		if (score <= 0) return;
		
		updateUserTotalScore(userId, score, connection);
		updateUserMaxScore(userId, score, quizScore, connection);
	}

	private void updateUserTotalScore(int userId, int score, Connection connection) {
		try {
			String query = "update " + MyDBInfo.MYSQL_DATABASE_NAME + ".users set total_score = ? where id = ?;";
			PreparedStatement st = connection.prepareStatement(query);
			st.setInt(1, score  + getUserTotalScore(getUserName(userId, connection), connection));
			st.setInt(2, userId);
			st.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void updateUserMaxScore(int userId, int score, int quizScore, Connection connection) {
		double score_in_percentage = percentage(quizScore, score);
		try {
			if (getUserMaxScore(getUserName(userId, connection), connection) >= score_in_percentage)
				return;
			
			String query = "update " + MyDBInfo.MYSQL_DATABASE_NAME + ".users set max_score = ? where id = ?;";
			PreparedStatement st = connection.prepareStatement(query);
			st.setDouble(1, score_in_percentage);
			st.setInt(2, userId);
			st.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/*private int getQuizMaxPossibleScore(int quizId, Connection connection) {
		try {
			String query = "select quiz_score from " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes " +
					"where id = ?;";
			PreparedStatement st = connection.prepareStatement(query);
			st.setInt(1, quizId);
			ResultSet set = st.executeQuery();
			if (set == null || !set.next()) return -1;
			return set.getInt(1);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}
	}*/

	private Timestamp now(){
		return new Timestamp(new Date().getTime());
	}
	
	private void updateQuiz(int quizId, int newScore, Connection connection) throws SQLException{
		String sql = "UPDATE " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes "
				+ "SET total_score = total_score + ?, total_submittions = total_submittions + 1 "
				+ "WHERE id = ?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, newScore);
		statement.setInt(2, quizId);
		statement.execute();
	}
	
	private void logSubmission(int quizId, int userId, int score, Timestamp startTime, Timestamp endTime, Connection connection) throws SQLException{
		String sql = "INSERT INTO " + MyDBInfo.MYSQL_DATABASE_NAME + ".event_log "
				+ " (quiz_id, user_id, score, start_time, end_time) "
				+ "VALUES (?,		?, 	   ?,		   ?, 		 ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, quizId);
		statement.setInt(2, userId);
		statement.setInt(3, score);
		statement.setTimestamp(4, startTime);
		statement.setTimestamp(5, endTime);
		statement.execute();
	}
	
	/**
	 * @param name - name of quiz, unique
	 * @return Quiz type object which you're searching for or null if doesn't exist
	 * 
	 * */
	public Quiz getQuiz(String name, Connection connection){ 
		try {
			String sql = "SELECT * FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes WHERE quiz_name = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet res = statement.executeQuery();
			if(res.next()){
				QuizBase base = getQuizBase(res, connection);
				boolean shouldShaffle = res.getBoolean("random_shuffle");
				int questionCap = res.getInt("question_cap");
				int timeLimit = res.getInt("time_limit");
				boolean isSinglePage = res.getBoolean("is_single_page");
				List<QuestionAbstract> questions = getQuizQuestions(res.getInt("id"), connection);
				return Factory_Quiz.getQuiz(base, shouldShaffle, questionCap, timeLimit, isSinglePage, questions);
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
	
	private List<QuestionAbstract> getQuizQuestions(int quizId, Connection connection) throws SQLException, ClassNotFoundException, IOException {
		String sql = "SELECT * FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".questions WHERE quiz_id = ? ORDER BY index_in_quiz ASC;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, quizId);
		ResultSet res = statement.executeQuery();
		List<QuestionAbstract> questions = new ArrayList<QuestionAbstract>();
		while(res.next()){
			questions.add((QuestionAbstract)Serialization.fromString(res.getString("serialized_object")));
		}
		return questions;
	}
	
	
	/**
	 * @param name - name of the quiz
	 * @return Base of the quiz, that has the specified name 
	 */
	public QuizBase getQuizBase(String name, Connection connection){
		try {
			String sql = "SELECT * from " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes WHERE quiz_name = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet res = statement.executeQuery();
			if(res.next())
				return(getQuizBase(res, connection));
			else return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param name - username for user we're searching for
	 * @param password_hash - hashed password
	 * @param connection - Connection object
	 * @return matching user or null if not found
	 * */
	public User getUser(String name, String password_hash, Connection connection){
		
		if (connection == null) return null;
		
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
			return Factory_User.getUser(name, password_from_db);
		} catch (Exception ex) {
			return null;
		}
		
	}
	
	public User getUser(String username, Connection connection) {
		if (connection == null) return null;
		
		try {
			String sql = "select password_hash from users where username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs == null || !rs.next()) 
				return null;
			
			String password_from_db = rs.getString(1);
			
			return Factory_User.getUser(username, password_from_db);
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
			String sql = "SELECT * FROM "+ MyDBInfo.MYSQL_DATABASE_NAME +" .quizzes q"+ 
					" ORDER BY (SELECT COUNT(*) FROM "+ MyDBInfo.MYSQL_DATABASE_NAME+
					".event_log e WHERE e.quiz_id = q.id ) DESC "+ 
					"LIMIT ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, num);
			set = ps.executeQuery();
			if (set == null)
				return null;
		} catch(Exception e){
			return null;
		}
		try {
			res = getQuizBaseList(set, connection, "q");			
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
						".quizzes order by creation_date DESC limit ?";
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
	
	private QuizBase getQuizBase(ResultSet res, String authorName, Connection connection, String tableName) throws SQLException{
		if(tableName == null) tableName = "";
		if(!tableName.equals("")) tableName+=".";
		
		String name = res.getString(tableName + "quiz_name");
		Timestamp date = res.getTimestamp(tableName + "creation_date");
		String author = authorName;
		if(author == null)
			author = getAuthorName(res.getInt(tableName + "author_id"), connection);
		String description = res.getString(tableName + "description");
		int totalScore = res.getInt(tableName + "total_score");
		int numSubmissions = res.getInt(tableName + "total_submittions");
		int quizScore = res.getInt(tableName + "quiz_score");
		return (Factory_Quiz.getQuizBase(name, date, author, description, totalScore, numSubmissions, quizScore));
	}
	
	private QuizBase getQuizBase(ResultSet res, Connection connection) throws SQLException{
		return getQuizBase(res, null, connection, "");
	}
	
	/*
	private QuizBase getQuizBase(ResultSet res, String authorName, Connection connection) throws SQLException{
		return getQuizBase(res, authorName, connection, "");
	}
	
	private QuizBase getQuizBase(ResultSet res, Connection connection, String tableName) throws SQLException{
		return getQuizBase(res, null, connection, tableName);
	}
	*/
	
	private List<QuizBase> getQuizBaseList(ResultSet res, String author, Connection connection, String tableName) throws SQLException{
		List<QuizBase> list = new ArrayList<QuizBase>();
		while(res.next())
			list.add(getQuizBase(res, author, connection, tableName));
		return list;
	}
	
	private List<QuizBase> getQuizBaseList(ResultSet res, Connection connection) throws SQLException{
		return getQuizBaseList(res,  null, connection, "");
	}
	
	private List<QuizBase> getQuizBaseList(ResultSet res, String authorName, Connection connection) throws SQLException{
		return getQuizBaseList(res,  authorName, connection, "");
	}
	
	private List<QuizBase> getQuizBaseList(ResultSet res, Connection connection, String tableName) throws SQLException{
		return getQuizBaseList(res,  null, connection, tableName);
	}
	
	/*
	 * returns author name, a unique identifier of
	 * quiz author
	 * */
	private String getAuthorName(int authorId, Connection connection) throws SQLException{
		String authorName = null;
		ResultSet set = null;
		String stmt = "select username from " + MyDBInfo.MYSQL_DATABASE_NAME+ ".users where id = "+authorId;
		set = connection.prepareStatement(stmt).executeQuery();
		if (set!=null){
			set.next();
			authorName = set.getString("username");
		}
		return authorName;
	}
	/*
	private int getAuthorId(int quizId, Connection connection) throws Exception {
		int authorId = -1;
		String stmt = "select author_id from " + MyDBInfo.MYSQL_DATABASE_NAME+ ".quizzes where quiz_id = "+quizId;
		ResultSet set = connection.prepareStatement(stmt).executeQuery();
		set.next();
		authorId =  set.getInt(0);
		return authorId;
	}
	*/
	
	/**
	 * Gets the user's submissions
	 * @param user - user
	 * @param limit - maximal amount of the submissions to be returned
	 * @param connection - connection
	 * @return list of user's submissions
	 */
	public List<Submission> getUserSubmissions(String username, int limit, Connection connection){
		try {
			int userId = getUserId(username, connection);
			String sql = "SELECT * from " + MyDBInfo.MYSQL_DATABASE_NAME + ".event_log"
					+ " INNER JOIN " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes ON id = quiz_id"
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
				submissions.add(Factory_User.getSubmission(quiz, start, end, score, username));
			}
			return(submissions);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * @param username - username
	 * @param limit - maximal amount of the submissions to be returned
	 * @param connection - Connection oject
	 * @return list of quizzes that the user has created
	 */
	public List<QuizBase> getUserCreatedQuizzes(String username, int limit, Connection connection){
		try {
			int userId = getUserId(username, connection);
			if(userId == NO_ID) return null;
			
			String sql = "SELECT * FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes"
					+ " WHERE author_id = ? ORDER BY creation_date DESC LIMIT ?;";
					
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.setInt(2, limit);
			ResultSet res = statement.executeQuery();
			if(res == null) return null;
			
			return getQuizBaseList(res, username, connection);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * @param username - username of the user
	 * @param connection - Connection object
	 * @return max score reached in some quiz specified in percentage terms
	 */
	public double getUserMaxScore(String username, Connection connection) {
		
		if (connection == null) return NO_CONNECTION;
		
		try {
			PreparedStatement ps = connection.prepareStatement("select max_score from "
					+ MyDBInfo.MYSQL_DATABASE_NAME + ".users where id = ?;");
			ps.setInt(1, getUserId(username, connection));
			ResultSet rs = ps.executeQuery();
			
			if (rs == null) return 0.0;
			rs.next();
			
			return rs.getDouble(1);
		} catch (Exception e) {
			return NO_CONNECTION;
		}
	}
	
	/**
	 * @param username - username of the user
	 * @param connection - Connection object
	 * @return sum of all quizzes' scores
	 */
	public int getUserTotalScore(String username, Connection connection) {
		
		if (connection == null) return NO_CONNECTION;
		
		try {
			PreparedStatement ps = connection.prepareStatement("select total_score from "
					+ MyDBInfo.MYSQL_DATABASE_NAME + ".users where id = ?;");
			ps.setInt(1, getUserId(username, connection));
			ResultSet rs = ps.executeQuery();
			
			if (rs == null || !rs.next()) return NO_CONNECTION;
			
			return rs.getInt(1);
		} catch (Exception e) {
			return NO_CONNECTION;
		}
	}
	
	private double percentage(int totalScore, int score){
		return (100.0 * (((double)score) / ((double)totalScore)));
	}
	/**
	 * returns number of submission have been 
	 * made with given username 
	 * @param username - name of the user
	 * @param connection - database connection
	 * @return number of submissions user made
	 * 
	 * */
	public int getNumOfSubmissions(String username, Connection connection){
		
		if (connection == null) 
			return NO_CONNECTION;
		try {
			int userId = getUserId(username, connection);
			String sql = "SELECT count(*) from " + MyDBInfo.MYSQL_DATABASE_NAME + ".event_log"
					+ " INNER JOIN " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes ON id = quiz_id"
					+ " WHERE user_id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet res = statement.executeQuery();
			if(res == null) 
				return 0;
			res.next();
			return res.getInt(1);
		} catch (Exception ex) {
			return 0;
		}
	}
	/**
	 * returns number of quizzes created by 
	 * the user. Database will throw an 
	 * SQLexception if the user is not found
	 * but this method will simply return 0,
	 * despite it being an incorrect query
	 * @param username - name of the user/Author
	 * @param connection - database connection
	 * @return number of quizzes user created
	 * */
	public int getNumOfCreatedQuizzes(String username, Connection connection){
		
		if (connection == null) return NO_CONNECTION;
		
		try {
			int userId = getUserId(username, connection);
			String sql = "SELECT count(*) from " + MyDBInfo.MYSQL_DATABASE_NAME + ".quizzes"
					+ "where author_id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet res = statement.executeQuery();
			if(res == null) return 0;
			res.next();
			return res.getInt(1);
		} catch (SQLException e) {
			return 0;
		}
	}

	
	/**
	 * Returns last scores for the given user and the given quiz
	 * @param username - user
	 * @param quizName - quiz
	 * @param num - number of important submissions
	 * @return Last num submission results the user got for the given quiz
	 */
	public List<Touple<Double, Timestamp,Timestamp> > getScores(String username, String quizName, int num, Connection connection){
		List<Touple<Double, Timestamp,Timestamp> > list = new ArrayList<Touple<Double, Timestamp,Timestamp> >();
		try {
			int totalScore = getQuizBase(quizName, connection).getQuizScore();
			String sql = "SELECT score, start_time, end_time FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".event_log "
					+ " WHERE quiz_id = ? AND user_id = ? ORDER BY start_time DESC LIMIT ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, getQuizId(quizName, connection));
			ps.setInt(2, getUserId(username, connection));
			ps.setInt(3, num);
			ResultSet res = ps.executeQuery();
			while(res.next()){
				double score = percentage(totalScore, res.getInt("score"));
				Timestamp startTime = res.getTimestamp("start_time");
				Timestamp endTime = res.getTimestamp("end_time");
				list.add(Factory_Quiz.makeTouple(score, startTime, endTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}
	
	private static String getUserName(int id, Connection connection) throws SQLException{
		String sql = "SELECT username FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".users WHERE id = ?;";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet res = ps.executeQuery();
		if(res.next()){
			return res.getString("username");
		}else return null;
	}
	
	private List<Touple<String, Double, Timestamp>> getSubmissionList(int totalScore, ResultSet res, Connection connection) throws SQLException{
		List<Touple<String, Double, Timestamp>> list = new ArrayList<Touple<String, Double, Timestamp>>();
		while(res.next()){
			String name = getUserName(res.getInt("user_id"), connection);
			double score = percentage(totalScore, res.getInt("score"));
			Timestamp time = res.getTimestamp("start_time");
			list.add(Factory_Quiz.makeTouple(name, score, time));
		}
		return list;
	}
	
	/**
	 * Highest scoring submissions for the given quiz
	 * @param quizName - quiz
	 * @param startDate - date, before which the submissions become irrelevant
	 * @param num - max number of returned submissions
	 * @return Highest scoring submissions for the given quiz
	 */
	public List<Touple<String, Double, Timestamp>> getHighestPerformers(String quizName, Timestamp startDate, int num, Connection connection){
		try{
			int totalScore = getQuizBase(quizName, connection).getQuizScore();
			String sql = "SELECT user_id, score, start_time FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".event_log "
					+ "WHERE quiz_id = ? and start_time > ? ORDER BY score DESC LIMIT ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, getQuizId(quizName, connection));
			ps.setTimestamp(2, startDate);
			ps.setInt(3, num);
			return getSubmissionList(totalScore, ps.executeQuery(), connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns list of the last submissions for the given quiz
	 * @param quizName - name of the quiz
	 * @param num - max number of returned submissions
	 * @return last num submissions for the given quiz
	 */
	public List<Touple<String, Double, Timestamp>> getLastSubmissions(String quizName, int num, Connection connection){
		try{
			int totalScore = getQuizBase(quizName, connection).getQuizScore();
			String sql = "SELECT user_id, score, start_time FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".event_log "
					+ "WHERE quiz_id = ? ORDER BY start_time DESC LIMIT ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, getQuizId(quizName, connection));
			ps.setInt(2, num);
			return getSubmissionList(totalScore, ps.executeQuery(), connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Inserts a message into the Database
	 * @param message - message
	 * @return true, if insertion was a success
	 */
	public boolean addMessage(Message message, Connection connection){
		try{
			String sql = "INSERT INTO " + MyDBInfo.MYSQL_DATABASE_NAME + ".messages"
					+ " (sender_id, receiver_id, message_string, delivery_date, message_seen) "
					+ " VALUES (  ?, 		  ?, 			  ?, 			 ?, 		   ?);";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, getUserId(message.getSender(), connection));
			ps.setInt(2, getUserId(message.getReceiver(), connection));
			ps.setString(3, message.getMessage());
			ps.setTimestamp(4, message.getDate());
			ps.setBoolean(5, message.isSeen());
			ps.execute();
			return true;
		} catch (SQLException e) {
			return false;
		} catch (NullPointerException e){
			e.printStackTrace();
			return false;
		}
	}
	
	private List<Message> getMessages(String sender, String receiver, ResultSet res, Connection connection) throws SQLException{
		List<Message> list = new ArrayList<Message>();
		while(res.next()){
			String senderName = sender;
			if(senderName == null) senderName = getUserName(res.getInt("sender_id"), connection);
			String receiverName = receiver;
			if(receiverName == null) receiverName = getUserName(res.getInt("receiver_id"), connection);
			String messageString = res.getString("message_string");
			Timestamp date = res.getTimestamp("delivery_date");
			boolean seen = res.getBoolean("message_seen");
			list.add(Factory_User.getMessage(receiverName, senderName, messageString, date, seen));
		}
		return list;
	}
	
	/**
	 * Fetches unread messages for the given user
	 * @param username - receiver name
	 * @param num - limit for the returned list size
	 * @return List of unread messages
	 */
	public List<Message> getUnreadMessages(String username, int num, Connection connection){
		return getMessages(username, null, true, num, connection);
	}
	
	/**
	 * Fetches unread messages for the given user
	 * @param username - receiver name
	 * @param senderName - sender name
	 * @param num - limit for the returned list size
	 * @return List of unread messages
	 */
	public List<Message> getUnreadMessages(String username, String senderName, int num, Connection connection){
		return getMessages(username, senderName, true, num, connection);
	}
	
	/**
	 * Fetches unread messages for the given user
	 * @param username - receiver name
	 * @param senderName - sender name
	 * @param unreadOnly - true, if user wants only unread messages
	 * @param num - limit for the returned list size
	 * @return List of unread messages
	 */
	public List<Message> getMessages(String username, String senderName, boolean unreadOnly, int num, Connection connection){
		try{
			String sql = "SELECT * FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".messages"
					+ " WHERE receiver_id = ?";
			if (senderName != null) sql += " AND sender_id = ?"; 
			if (unreadOnly) sql += " AND message_seen = false"; 
			sql += " ORDER BY delivery_date ASC LIMIT ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, getUserId(username, connection));
			if (senderName != null){
				ps.setInt(2, getUserId(senderName, connection));
				ps.setInt(3, num);
			} else ps.setInt(2, num);
			return(getMessages(null, username, ps.executeQuery(), connection));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param username - receiver name
	 * @param connection - Connection object
	 * @return number of unread messages for the specified user
	 */
	public int getNumOfUnreadMessages(String username, Connection connection) {
		try {
			String query = "SELECT count(*) FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".messages"
					+ " WHERE receiver_id = ? AND message_seen = false;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, getUserId(username, connection));
			ResultSet set = ps.executeQuery();
			
			if (set == null) return FAIL_EXPECTED_INT;
			set.next();
			return set.getInt(1);
		} catch (SQLException ex) {
			return FAIL_EXPECTED_INT;
		}
	}
	/**
	 * @param username - receiver name
	 * @param friendName - sender name
	 * @param connection - Connection object
	 * @return number of unread messages for the 
	 * specified user from specified user
	 */
	public int getNumOfUnreadMessages(String username, String friendName, Connection connection){
		try {
			String query = "SELECT count(*) FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".messages"
					+ " WHERE receiver_id = ? AND sender_id = ? AND message_seen = false;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, getUserId(username, connection));
			ps.setInt(2, getUserId(friendName,connection));
			ResultSet set = ps.executeQuery();
			
			if (set == null) 
				return FAIL_EXPECTED_INT;
			set.next();
			return set.getInt(1);
		} catch (SQLException ex) {
			return FAIL_EXPECTED_INT;
		} 
	}
	
	
	/**
	 * Marks given message read
	 * @param message - message
	 * @return true, if successful
	 */
	public boolean markMessageRead(Message message, Connection connection){
		return markMessagesRead(message.getSender(), message.getReceiver(), message.getDate(), connection);
	}
	
	/**
	 * Marks all messages between given users as read
	 * @param sender - sender
	 * @param receiver receiver
	 * @return true, if successful
	 */
	public boolean markMessagesRead(String sender, String receiver, Connection connection){
		return markMessagesRead(sender, receiver, null, connection);
	}
	
	private boolean markMessagesRead(String sender, String receiver, Timestamp time, Connection connection){
		try{
			String sql = "UPDATE " + MyDBInfo.MYSQL_DATABASE_NAME + ".messages "
					+ "SET message_seen = true "
					+ "WHERE sender_id = ? AND receiver_id = ?";
			if (time != null) sql += " AND delivery_date = ?;";
			else sql += ";";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, getUserId(sender, connection));
			ps.setInt(2, getUserId(receiver, connection));
			if(time != null) ps.setTimestamp(3, time);
			ps.execute();
			return true;
		} catch (SQLException ex) {
			return false;
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Fetches friends for the given user
	 * @param username - user name
	 * @param num - limit
	 * @return list of friends
	 */
	public List<String> getFriendList(String username, int num, Connection connection){
		if(username == null || connection == null);
		try{
			int userId = getUserId(username, connection);
			String query = "SELECT username FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".friends, " + MyDBInfo.MYSQL_DATABASE_NAME + ".users "
					+ "WHERE ((first_id = ? and second_id = id) OR (first_id = id and second_id = ?)) AND type = true LIMIT ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.setInt(3, num);
			
			ResultSet set = ps.executeQuery();
			if (set == null) return null;
			
			List<String> list = new ArrayList<String>();
			while(set.next())
				list.add(set.getString(username));
			return list;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Adds friends to the database
	 * @param firstUser - first user name
	 * @param secondUser - second user name
	 * @return true, if successful
	 */
	public boolean addFriends(String firstUser, String secondUser, boolean type, Connection connection){
		if(firstUser == null || secondUser == null || connection == null) return false;
		if(firstUser.equals(secondUser)) return false;
		try{
			int first = getUserId(firstUser, connection);
			int second = getUserId(secondUser, connection);
			if(first == NO_ID || second == NO_ID) return false;
			
			String sql = "INSERT INTO " + MyDBInfo.MYSQL_DATABASE_NAME + ".friends (first_id, second_id, type) VALUES (?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, first);
			ps.setInt(2, second);
			ps.setBoolean(3, type);
			ps.execute();
			return true;
		}catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean unfriend(String firstUser, String secondUser, Connection connection) {
		if(firstUser == null || secondUser == null || connection == null) return false;
		if(firstUser.equals(secondUser)) return false;
		try{
			int first = getUserId(firstUser, connection);
			int second = getUserId(secondUser, connection);
			if(first == NO_ID || second == NO_ID) return false;
			
			String sql = "DELETE FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".friends WHERE "
					+ "(first_id = ? and second_id = ?) OR (first_id = ? and second_id = ?);";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, first);
			ps.setInt(2, second);
			ps.setInt(3, second);
			ps.setInt(4, first);
			ps.execute();
			return true;
		}catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * Tells, if the given users are friends or not
	 * @param firstUser - first user name
	 * @param secondUser - second user name
	 * @return true, if the users are friends and no SQL exception occurred
	 */
	public boolean areFriends(String firstUser, String secondUser, boolean type, Connection connection){
		if(firstUser == null || secondUser == null || connection == null) return false;
		if(firstUser.equals(secondUser)) return false;
		try{
			int first = getUserId(firstUser, connection);
			int second = getUserId(secondUser, connection);
			if(first == NO_ID || second == NO_ID) return false;
			
			String sql = "SELECT count(*) FROM " + MyDBInfo.MYSQL_DATABASE_NAME + ".friends"
					+ " WHERE type = ? AND ((first_id = ? and second_id = ?) OR (first_id = ? and second_id = ?));";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setBoolean(1, type);
			ps.setInt(2, first);
			ps.setInt(3, second);
			ps.setInt(4, second);
			ps.setInt(5, first);
			ResultSet set = ps.executeQuery();
			
			if(set == null) return false;
			set.next();
			return set.getInt("count(*)") > 0;
		}catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * @param username - user who we are going to update password for.
	 * @param newPasswordHash - new password hash
	 * @param connection - Connection object
	 * @return true if password changes successfully, false - otherwise
	 */
	public boolean updateUserPassword(String username, String newPasswordHash, Connection connection) {
		
		if (username == null || newPasswordHash == null || connection == null)
			return false;
		
		try {
			int userId = getUserId(username, connection);
			String query = "update " + MyDBInfo.MYSQL_DATABASE_NAME + ".users set password_hash = ?"
					+ " where id = ?;";

			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, newPasswordHash);
			ps.setInt(2, userId);
			
			if (!ps.execute()) return false;
		} catch (SQLException ex) {
			return false;
		}
		
		return true;
	}
	
	public List<String> getFriendRequestsList(String username, Connection connection) {
		if (connection == null || username == null || username.length() <= 0) return null;
		
		try {
			String query = "select first_id from " + MyDBInfo.MYSQL_DATABASE_NAME + ".friends "
					+ "where second_id = ? and type = false;";
			PreparedStatement st = connection.prepareStatement(query);
			st.setInt(1, getUserId(username, connection));
			ResultSet set = st.executeQuery();
			if (set == null) return null;
			
			List<String> res = new ArrayList<String>();
			while (set.next())
				res.add(getUserName(set.getInt("first_id"), connection));
			
			return res;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}




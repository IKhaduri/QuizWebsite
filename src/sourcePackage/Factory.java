package sourcePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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
	
	/**
	 * Builds and returns a Quiz object
	 * @param name - name of the quiz
	 * @param date - date of creation
	 * @param author - user name of the author
	 * @param totalScore - combined score the users got from their submissions
	 * @param numSubmissions - number of submissions
	 * @param shouldShuffle - true, if the questions should be shuffled
	 * @param questionCap -  maximal number of questions that will be asked during a single session (restriction; user of the class can feel free to ignore this parameter)
	 * @param timeLimit - time limit of the given quiz (restriction; user of the class can feel free to ignore this parameter)
	 * @param questions - the questions
	 * @return Quiz object
	 */
	public static Quiz getQuiz(
			String name, Timestamp date, String author,
			int totalScore, int numSubmissions,
			boolean shouldShuffle, int questionCap, int timeLimit,
			List<Question> questions){
		Quiz.Header header = new Quiz.Header(name, date, author);
		Quiz.Statistics statistics = new Quiz.Statistics(totalScore, numSubmissions);
		Quiz.Parameters parameters = new Quiz.Parameters(shouldShuffle, questionCap, timeLimit);
		return new Quiz(header, statistics, parameters, questions);
	}
	
	/**
	 * Builds and returns a QuizBase object for lists
	 * @param name - name of the quiz
	 * @param date - date of creation
	 * @param author - user name of the author
	 * @param totalScore - combined score the users got from their submissions
	 * @param numSubmissions - number of submissions
	 * @param quizScore - maximal score the user can get thought this quiz
	 * @return QuizBase object
	 */
	public static QuizBase getQuizBase(String name,Timestamp date,String author,int totalScore, 
			int numSubmissions, int quizScore){
		return new QuizBase(new QuizBase.Header(name, date, author), new QuizBase.Statistics(numSubmissions, totalScore), quizScore);
	}
	
	public static Submission getSubmission(QuizBase quiz, Timestamp timeStart, Timestamp timeEnd, int score, String userName){
		return new Submission(quiz, timeStart, timeEnd, score,userName);
	}
}

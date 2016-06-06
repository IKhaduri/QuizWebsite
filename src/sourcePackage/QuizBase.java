package sourcePackage;

import java.sql.Timestamp;
import java.util.Date;

public class QuizBase {

	/**
	 * Header subclass is used to help initialize a quiz
	 * and can store name, creation date and the author for a quiz.
	 */
	public static class Header{
		private String name;
		private Timestamp creationData;
		private String authorName;
		
		/**
		 * Complete constructor
		 * @param name - quiz name
		 * @param date - quiz creation date
		 * @param author - author of the quiz
		 */
		public Header(String name, Timestamp date, String author){
			this.name = name;
			this.creationData = date;
			this.authorName = author;
		}
		/**
		 * Constructs header for a quiz that was created "now"
		 * @param name - quiz name
		 * @param author - author of the quiz
		 */
		public Header(String name, String author){
			this(name, new Timestamp(new Date().getTime()), author);
		}
		/**
		 * Constructs header for a quiz that was created "now" and does not have an author
		 * @param name - quiz name
		 */
		public Header(String name){
			this(name, null);
		}
	}
	private Header header;
	
	/**
	 * Statistics is a subclass, storing the total number of submissions
	 * and their combined score, helping to determine the difficulty level for the quiz.
	 */
	public static class Statistics{
		private int totalScore;
		private int totalSubmissions;
		
		/**
		 * Constructor
		 * @param numSubmissions - number of submissions.
		 * @param totalScore - sum of the submitted scores.
		 */
		public Statistics(int numSubmissions, int totalScore){
			this.totalScore = totalScore;
			this.totalSubmissions = numSubmissions;
		}
		/**
		 * default constructor
		 * (Suitable for newly created quizzes)
		 */
		public Statistics(){
			this(0, 0);
		}
	}
	private Statistics statistics;
	
	private int quizScore;
	
	/**
	 * Constructor
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 * @param questions - question list
	 * @param statistics - submission statistics
	 * @param quizScore - total score of the quiz
	 */
	public QuizBase(Header header, Statistics statistics, int quizScore){
		this.header = header;
		this.statistics = statistics;
		this.quizScore = quizScore;
	}
	
	/**
	 * @return name of the quiz
	 */
	public String getName(){
		return header.name;
	}
	
	/**
	 * @return creation date
	 */
	public Timestamp getCreationDate(){
		return header.creationData;
	}
	
	/**
	 * @return author of the quiz
	 */
	public String getAuthor(){
		return header.authorName;
	}
	
	/**
	 * @return total score of all the submissions
	 */
	public int getTotalScore(){
		return statistics.totalScore;
	}
	
	/**
	 * @return number of submissions
	 */
	public int getSubmissionCount(){
		return statistics.totalSubmissions;
	}
	
	/**
	 * @return average score from all submissions or 0, if there are none
	 */
	public double getAverageScore(){
		if(statistics.totalSubmissions <= 0) return(0);
		return (((double)statistics.totalScore)/((double)statistics.totalSubmissions));
	}
	
	/**
	 * @return total score, one can gain thought this quiz
	 */
	public int getQuizScore(){
		return(quizScore);
	}
	
	/**
	 * @return average percentage of the users' scores (some number between 0 1nd 1)
	 */
	public double getAverageScoreScaled(){
		return(getAverageScore() / ((double)quizScore));
	}
}

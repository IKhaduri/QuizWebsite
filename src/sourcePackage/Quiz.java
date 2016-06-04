package sourcePackage;

import java.sql.Timestamp;
import java.util.*;

public class Quiz {
	
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
	 * Parameters subclass is used to help initialize a quiz
	 * and can store the parameters for the quiz, such as:
	 * whether it should shuffle the questions randomly or not,
	 * maximum amount of questions that can be asked during a single session
	 * and the time limit for the quiz.
	 */
	public static class Parameters{
		/** Default question cap */
		public static final int DEFAULT_QUESTION_CAP = 1024000;
		/** Default quiz time */
		public static final int DEFAULT_TIME_LIMIT = 1800;
		
		private boolean randomShuffle;
		private int questionCap;
		private int timeLimit;
		
		/**
		 * Constructor
		 * @param shuffle - tells, whether it should shuffle the questions randomly or not
		 * @param maxQuestions - maximum amount of questions that can be asked during a single session
		 * @param quizTime - the time limit for the quiz
		 */
		public Parameters(boolean shuffle, int maxQuestions, int quizTime){
			randomShuffle = shuffle;
			questionCap = maxQuestions;
			timeLimit = quizTime;
		}
		/**
		 * Constructor
		 * Note: Other parameters will be set to default values
		 * @param shuffle - tells, whether it should shuffle the questions randomly or not
		 */
		public Parameters(boolean shuffle){
			this(shuffle, DEFAULT_QUESTION_CAP, DEFAULT_TIME_LIMIT);
		}
	}
	private Parameters parameters;
	
	private List<Question> questions;
	
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
	
	/**
	 * Constructor
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 * @param questions - question list
	 * @param statistics - submission statistics
	 */
	public Quiz(Header header, Parameters parameters, List<Question> questions, Statistics statistics){
		this.header = header;
		this.parameters = parameters;
		this.questions = questions;
		this.statistics = statistics;
	}
	/**
	 * Constructor
	 * (statistics will be empty)
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 * @param questions - question list
	 */
	public Quiz(Header header, Parameters parameters, List<Question> questions){
		this(header, parameters, questions, new Statistics());
	}
	/**
	 * Constructor
	 * (statistics and the question list will be empty)
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 */
	public Quiz(Header header, Parameters parameters){
		this(header, parameters, new ArrayList<Question>());
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
	 * @return true, if the questions are supposed to be asked out of order
	 */
	public boolean shouldShuffle(){
		return parameters.randomShuffle;
	}
	
	/**
	 * @return maximal number of questions that can be asked during as ingle session
	 */
	public int getQuestionCap(){
		return parameters.questionCap;
	}
	
	/**
	 * @return duration of the quiz(time limit)
	 */
	public int getQuizTime(){
		return parameters.timeLimit;
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
	public double getaverageScore(){
		if(statistics.totalSubmissions <= 0) return(0);
		return (((double)statistics.totalScore)/((double)statistics.totalSubmissions));
	}
	
	/**
	 * @return number of questions in the quiz
	 */
	public int getQuestionCount(){
		return questions.size();
	}
	
	/**
	 * @param index - index of the question
	 * @return "index-th" question
	 */
	public Question getQuestion(int index){
		return questions.get(index);
	}
	
	/**
	 * Adds a question to the quiz
	 * @param question - question to be added to the question list
	 */
	public void addQuestion(Question question){
		questions.add(question);
	}
}

package sourcePackage;

import java.util.*;

public class Quiz extends QuizBase implements Iterable<Question>{
	
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
	 * Constructor
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 * @param questions - question list
	 * @param statistics - submission statistics
	 */
	public Quiz(Header header, Statistics statistics, int quizScore, Parameters parameters, List<Question> questions){
		super(header, statistics, quizScore);
		this.parameters = parameters;
		this.questions = questions;
	}
	
	/**
	 * @return true, if the questions are supposed to be asked out of order
	 */
	public boolean shouldShuffle(){
		return parameters.randomShuffle;
	}
	
	/**
	 * @return maximal number of questions that can be asked during as single session
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
	 * @return iterator for the questions
	 */
	@Override
	public Iterator<Question> iterator() {
		return questions.iterator();
	}
}

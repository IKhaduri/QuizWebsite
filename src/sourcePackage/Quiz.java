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
		
		private boolean randomShuffle;
		private int questionCap;
		private int timeLimit;
		private boolean isSinglePage;
		
		/**
		 * Constructor
		 * @param shuffle - tells, whether it should shuffle the questions randomly or not
		 * @param maxQuestions - maximum amount of questions that can be asked during a single session
		 * @param quizTime - the time limit for the quiz
		 */
		public Parameters(boolean shuffle, int maxQuestions, int quizTime, boolean singlePage){
			randomShuffle = shuffle;
			questionCap = maxQuestions;
			timeLimit = quizTime;
			isSinglePage = singlePage;
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
	 * @return true, if the quiz is of a single page type
	 */
	public boolean isSinglePage(){
		return parameters.isSinglePage;
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

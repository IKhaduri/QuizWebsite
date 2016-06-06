package sourcePackage;

import java.util.*;

public class Quiz extends QuizBase implements Iterable<Question>{

	private List<Question> questions;
	
	/**
	 * Constructor
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 * @param questions - question list
	 * @param statistics - submission statistics
	 */
	public Quiz(Header header, Parameters parameters, Statistics statistics, List<Question> questions){
		super(header, parameters, statistics);
		this.questions = questions;
	}
	
	/**
	 * Constructor
	 * (statistics will be empty)
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 * @param questions - question list
	 */
	public Quiz(Header header, Parameters parameters, List<Question> questions){
		this(header, parameters, new Statistics(), questions);
	}
	
	/**
	 * Constructor
	 * (the question list will be empty)
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 * @param statistics - submission statistics
	 */
	public Quiz(Header header, Parameters parameters, Statistics statistics){
		this(header, parameters, statistics, new ArrayList<Question>());
	}
	
	/**
	 * Constructor
	 * (statistics and the question list will be empty)
	 * @param header - name, date and author
	 * @param parameters - quiz parameters
	 */
	public Quiz(Header header, Parameters parameters){
		this(header, parameters, new Statistics());
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
	
	/**
	 * @return iterator for the questions
	 */
	@Override
	public Iterator<Question> iterator() {
		return questions.iterator();
	}
}

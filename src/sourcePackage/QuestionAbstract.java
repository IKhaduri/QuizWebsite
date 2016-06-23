package sourcePackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class QuestionAbstract implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7610801638460142197L;
	protected String question_text;
	protected ArrayList<String> correct_answers;	
	
	
	public QuestionAbstract(String text,  ArrayList<String> answers) {
		question_text = text;
		correct_answers = answers;
	}
	
	
	/**
	 * @return ArrayList of answers, since it might have multiple answers
	 */
	public List<String> getAnswers() { 
		return correct_answers; 
	}
	
	/**
	 * @return Question text to be displayed
	 */
	public String getQuestion() {
		return question_text;
	}
	
	/**
	 * This method must be implemented in 
	 * any child class. It must return
	 * HTML code of the given class,
	 * ready to display, no CSS formatting.
	 * 
	 * */
	public abstract String toHTML();
	/**
	 * This method must be implemented in 
	 * any child class. It must return
	 * HTML code of the given class,
	 * ready to display, no CSS formatting.
	 * @param questionNum - simple ordering
	 * */
	public abstract String toHTML(int questionNum);
	/**given some string, this methods
	 * tells if it is a correct response
	 * for the question.
	 * @param answer - answer to be tested
	 * @return true or false, depending on answer
	 * */
	public abstract boolean isCorrectAnswer(String answer);
	 
	
	public abstract int getScore();
}

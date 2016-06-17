package sourcePackage;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestionAbstract {
	protected String question_text;
	protected String picture_link;	// for picture-response type questions
	protected ArrayList<String> correct_answers;	
	
	
	public QuestionAbstract(String text, String picture_link, ArrayList<String> answers) {
		question_text = text;
		this.picture_link = picture_link;
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
	 * @return Image source or null if not exists 
	 */
	public String getImage() {
		return picture_link;
	}
	/**
	 * This method must be implemented in 
	 * any child class. It must return
	 * HTML code of the given class,
	 * ready to display, no CSS formatting.
	 * 
	 * */
	public abstract String toHTML();
	
	
}

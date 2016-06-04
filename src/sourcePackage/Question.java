package sourcePackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unused")
public class Question implements Serializable {
	/**
	 * Enumeration for the question type
	 */
	public enum QuestionType{
		
	}
	
	
	/**
	 * In case some changes are needed in the project,
	 * a good style dictates to have a generated serialVersionUID
	 */
	private static final long serialVersionUID = 7284061932398022613L;
	private QuestionType type;
	private String question_text;
	private String picture_link;	// for picture-response type questions
	private ArrayList<String> correct_answers;	
	
	
	/**
	 * @return ArrayList of answers, since it might have multiple answers
	 */
	public List<String> getAnswers() {
		
		return null;
	}
	
	/**
	 * @return Question text to be displayed
	 */
	public String getQuestion() {
		
		return "";
	}
	
	/**
	 * @return Image source or null if not exists 
	 */
	public String getImage() {
		
		return "";
	}
}

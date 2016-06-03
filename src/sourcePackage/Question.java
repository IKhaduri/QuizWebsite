package sourcePackage;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unused")
public class Question {
	
	
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

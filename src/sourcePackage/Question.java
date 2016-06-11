package sourcePackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
	
	/**
	 * In case some changes are needed in the project,
	 * a good style dictates to have a generated serialVersionUID
	 */
	private static final long serialVersionUID = 7284061932398022613L;
	private QuestionType type;
	private String question_text;
	private String picture_link;	// for picture-response type questions
	private ArrayList<String> correct_answers;	
	
	
	public Question(QuestionType type, String text, String picture_link, ArrayList<String> answers) {
		
		this.type = type;
		question_text = text;
		this.picture_link = picture_link;
		correct_answers = answers;
	}
	
	/**
	 * optional constructor where type isn't specific, and is taken 
	 * 'TEXT_RESPONSE' type as default.
	 * @param text - Question text.
	 * @param answers - correct answer(s).
	 */
	public Question(String text, ArrayList<String> answers) {
		this(QuestionType.TEXT_RESPONSE, text, null, answers);
	}
	/**
	 * returns maximum score in this question.
	 * this is necessary because multiple choice-multiple 
	 * answer questions can earn you more points than 1 
	 * @return the maximum score which can be gained in this question
	 * */
	public int getScore(){
		switch(type){
			case MULTIPLE_CHOICE:
				return correct_answers.size();
			case FILL_BLANK: case TEXT_RESPONSE: case PICTURE_RESPONSE:
				return 1;
			default:
				return -1;
		
		}	
	}
	/**
	 * @return type of question, enum
	 * */
	public QuestionType getQuestionType(){
		return type;
		
	}
	
	/**
	 * @return ArrayList of answers, since it might have multiple answers
	 */
	public List<String> getAnswers() { return correct_answers; }
	
	/**
	 * @return Question text to be displayed
	 */
	public String getQuestion() { return question_text; }
	
	/**
	 * @return Image source or null if not exists 
	 */
	public String getImage() {
		
		if (type == QuestionType.PICTURE_RESPONSE)
			return picture_link;
		
		return null;
	}
}

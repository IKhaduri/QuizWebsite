package sourcePackage;

import java.util.ArrayList;

public interface Question {
	
	/**
	 * @return ArrayList of answers, since it might have multiple answers
	 */
	public ArrayList<String> getAnswers();
	/**
	 * @return Question text to be displayed
	 */
	public String getQuestion();
	/**
	 * @return Image source or null if not exists 
	 */
	public String getImage();
}

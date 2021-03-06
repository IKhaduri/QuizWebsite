package sourcePackage;

import java.io.Serializable;
import java.util.ArrayList;

public class TextResponseQuestion extends QuestionAbstract implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6788816017483593461L;

	/**
	 * standard public constructor. Takes
	 * text as question text, picture link
	 * that will be null if it's not necessary,
	 * and answer list that contains every 
	 * correct answer which will be accepted
	 * */
	public TextResponseQuestion(String text, ArrayList<String> answers) {
		super(text, answers);

	}
	@Override
	public String toHTML(int questionNum) {
		return QuestionDrawer.toHTML(this,questionNum);
	}
	
	@Override
	public String toHTML() {
		return QuestionDrawer.toHTML(this);
	}

	@Override
	public int getScore() {

		return 1;
	}
	@Override
	public boolean isCorrectAnswer(String answer) {
		return correct_answers.contains(answer);
	}
}
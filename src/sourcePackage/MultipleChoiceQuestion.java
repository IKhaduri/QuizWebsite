package sourcePackage;

import java.io.Serializable;
import java.util.ArrayList;

public class MultipleChoiceQuestion extends QuestionAbstract implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8722208834807445149L;

	/**
	 * standard public constructor. Takes
	 * text as question text, picture link
	 * that will be null if it's not necessary,
	 * and answer list that must have it's first 
	 * element as the correct answer and others
	 * wrong.
	 * */
	public MultipleChoiceQuestion(String text, ArrayList<String> answers) {
		super(text, answers);
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
	public String toHTML(int questionNum) {
		return QuestionDrawer.toHTML(this,questionNum);
	}

	@Override
	public boolean isCorrectAnswer(String answer) {
		if (correct_answers.get(0).equals(answer))
			return true;
		return false;
	}
	

}

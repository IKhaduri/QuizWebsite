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
	public MultipleChoiceQuestion(String text, String picture_link, ArrayList<String> answers) {
		super(text, picture_link, answers);
	}

	@Override
	public String toHTML(String servletToCall) {
		return QuestionDrawer.toHTML(this, servletToCall);
	}

	@Override
	public int getScore() {

		return 1;
	}
	

}

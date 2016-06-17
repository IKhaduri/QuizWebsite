package sourcePackage;

import java.util.ArrayList;

public class MultipleChoiceQuestion extends QuestionAbstract{
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
	public String toHTML() {
		return QuestionDrawer.toHTML(this);
	}


}

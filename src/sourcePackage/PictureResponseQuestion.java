package sourcePackage;

import java.util.ArrayList;

public class PictureResponseQuestion extends QuestionAbstract {
	/**
	 * standard public constructor. Takes
	 * text as question text, picture link
	 * that will be null if it's not necessary,
	 * and answer list that contains every 
	 * correct answer which will be accepted
	 * */
	public PictureResponseQuestion(String text, String picture_link, ArrayList<String> answers) {
		super(text, picture_link, answers);

	}

	@Override
	public String toHTML() {
		return QuestionDrawer.toHTML(this);
	}

}

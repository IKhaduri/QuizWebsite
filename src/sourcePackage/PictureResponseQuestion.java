package sourcePackage;

import java.io.Serializable;
import java.util.ArrayList;

public class PictureResponseQuestion extends QuestionAbstract implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -728405850700635572L;

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
	public String toHTML(String servletToCall) {
		return QuestionDrawer.toHTML(this, servletToCall);
	}

	@Override
	public int getScore() {
		return 1;
	}
	/**
	 * @return Image source or null if not exists 
	 */
	public String getImage() {
		return picture_link;
	}
}

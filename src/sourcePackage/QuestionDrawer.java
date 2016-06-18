package sourcePackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionDrawer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1502012453665347740L;
	/**
	 * returns HTML formatted multiple
	 * choice question, ready to display
	 * 
	 * */
	public static String toHTML(MultipleChoiceQuestion question){
		String res = "";
		ArrayList<String> toShuffle = new ArrayList<String>(question.getAnswers());
		Collections.shuffle(toShuffle);
		for (String answer: toShuffle){
			res+=("<input type=\"radio\" name=\'answer\' value=\""+answer+"\" >")+"\n";
		}
		return res;
	}
	/**
	 * returns HTML formatted multiple
	 * choice question, ready to display
	 * with different naming to help
	 * with name tag search and differentiation
	 * */
	public static String toHTML(MultipleChoiceQuestion question, int questionNum){
		String res = "";
		ArrayList<String> toShuffle = new ArrayList<String>(question.getAnswers());
		Collections.shuffle(toShuffle);
		for (String answer: toShuffle){
			res+=("<input type=\"radio\" name=\'answer"+questionNum+"\' value=\""+answer+"\" >")+"\n";
		}
		return res;
	}
	
	
	/**
	 * returns HTML formatted picture
	 * response question, ready to display
	 * 
	 * */
	public static String toHTML(PictureResponseQuestion question){
		String res = "";
		res+=("<img src=\"" + question.getImage()+ "\">")+"\n";
		res+=("<input type = \"text\" name =\"answer \">")+"\n";
		return res;
	}
	/**
	 * returns HTML formatted picture response	 
	 * question, ready to display
	 * with different naming to help
	 * with name tag search and differentiation
	 * */
	public static String toHTML(PictureResponseQuestion question, int questionNum){
		String res = "";
		res+=("<img src=\"" + question.getImage()+ "\">")+"\n";
		res+=("<input type = \"text\" name =\"answer"+questionNum+" \">")+"\n";
		return res;
	}
	
	
	/**
	 * returns HTML formatted text
	 * response question, ready to display
	 * */
	public static String toHTML(TextResponseQuestion question){
		String res = "";
		res+=("<input type = \"text\" name =\"answer\">")+"\n";
		return res;
	}
	
	/**
	 * returns HTML formatted text
	 * response question, ready to display
	 * with different formatting of naming to help
	 * with name tag search and differentiation
	 * */
	public static String toHTML(TextResponseQuestion question, int questionNum){
		String res = "";
		res+=("<input type = \"text\" name =\"answer"+questionNum+" \">")+"\n";
		return res;
	}
	
}

package sourcePackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import jdk.nashorn.internal.runtime.QuotedStringTokenizer;

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
	public static String toHTML(MultipleChoiceQuestion question, String servletToCall){
		String res = "";
		ArrayList<String> toShuffle = new ArrayList<String>(question.getAnswers());
		Collections.shuffle(toShuffle);
		res+=("<form action = \""+servletToCall+"\" method = \"post\">")+"\n";
		for (String answer: toShuffle){
			res+=("<input type=\"radio\" name=answer value=\""+answer+"\" >")+"\n";
		}
		res+=("</form>")+"\n";
		return res;
	}
	/**
	 * returns HTML formatted picture
	 * response question, ready to display
	 * 
	 * */
	public static String toHTML(PictureResponseQuestion question, String servletToCall){
		String res = "";
		res+=("<img src=\"" + question.getImage()+ "\">")+"\n";
		res+=("<form action = \""+servletToCall+"\" method = \"post\">")+"\n";
		res+=("<input type = \"text\" name =\"answer \">")+"\n";
		res+=("<p><button type = \" submit \" value = \" Submit/Next Question \" >   </p>")+"\n";
		res+=("</form>");
		return res;
	}
	/**
	 * returns HTML formatted text
	 * response question, ready to display
	 * */
	public static String toHTML(TextResponseQuestion question, String servletToCall){
		String res = "";
		res+=("<form action = \""+servletToCall+"\" method = \"post\">")+"\n";
		res+=("<input type = \"text\" name =\"answer\">")+"\n";
		res+=("<p><button type = \" submit \" value = \" Submit/Next Question \" >   </p>")+"\n";
		res+=("</form>")+"\n";
		return res;
	}
	
}

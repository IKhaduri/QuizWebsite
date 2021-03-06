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
		res+="<p>"+question.getQuestion()+"</p>";
		ArrayList<String> toShuffle = new ArrayList<String>(question.getAnswers());
		Collections.shuffle(toShuffle);
		for (String answer: toShuffle){
			res+=("<input type=\"radio\" name=\'answer\' value=\""+answer+"\" >")+ answer+"<br> \n";
		}
		res += "<br><br>";
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
		res+="<p>"+question.getQuestion()+"<p>";
		ArrayList<String> toShuffle = new ArrayList<String>(question.getAnswers());
		Collections.shuffle(toShuffle);
		for (String answer: toShuffle){
			res+=("<input type=\"radio\" name=\'answer"+questionNum+"\' value=\""+answer+"\" >")+ answer+" <br> \n";
		}
		res += "<br><br>";
		return res;
	}
	
	
	/**
	 * returns HTML formatted picture
	 * response question, ready to display
	 * 
	 * */
	public static String toHTML(PictureResponseQuestion question){
		String res = "";
		res+="<p>"+question.getQuestion()+"<p>";
		res+=("<img src=\"" + question.getImage()+ "\" width=\"500\" height=\"375\" alt=\"Picture should be here... :/\">")+"<br><br>\n";
		res+=("<textarea class=\"area\" name=\"answer\" rows = \"5\" cols=\"68\" placeholder=\"answer here...\"></textarea><br><br>")+"\n";
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
		res+="<p>"+question.getQuestion()+"<p>";
		res+=("<img src=\"" + question.getImage()+ "\" width=\"500\" height=\"375\" alt=\"Picture should be here... :/\">")+"<br><br>\n";
		res+=("<textarea class=\"area\" name=\"answer"+questionNum+"\" rows = \"5\" cols=\"68\" placeholder=\"answer here...\"></textarea><br><br>")+"\n";
		return res;
	}
	
	
	/**
	 * returns HTML formatted text
	 * response question, ready to display
	 * */
	public static String toHTML(TextResponseQuestion question){
		String res = "";
		res+="<p>"+question.getQuestion()+"</p>";
		res+=("<textarea class=\"area\" name=\"answer\" rows = \"5\" cols=\"68\" placeholder=\"answer here...\"></textarea><br><br>")+"\n";
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
		res+="<p>"+question.getQuestion()+"</p>";
		res+=("<textarea class=\"area\" name=\"answer"+questionNum+"\" rows = \"5\" cols=\"68\" placeholder=\"answer here...\"></textarea><br><br>")+"\n";
		return res;
	}
	
	public static String toHTML(String paragraph) {
		return "<!Doctype html><html><head><title>Failed</title></head><body><p>" + paragraph + "</p></body></html>";
	}
	
}

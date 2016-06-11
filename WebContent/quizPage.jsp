<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sourcePackage.Question"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Have fun! </title>
<% 
	int curQuestionNum = (int) request.getAttribute(ServletConstants.QUIZ_QUESTION_NUMBER);
	ArrayList<Question> questions = (ArrayList<Question>) request.getAttribute(ServletConstants.QUIZ_QUESTION_LIST);
	String servletToCall = questions.size()-1>=curQuestionNum?"nextQuestion":"quizFinished";
	Question curQuestion = questions.get(curQuestionNum);
%>
</head>
<body>
<input type="hidden" name=<%=ServletConstants.QUIZ_QUESTION_NUMBER %> value="<%= request.getParameter(ServletConstants.QUIZ_QUESTION_NUMBER) %>" >
<input type="hidden" name=<%=ServletConstants.QUIZ_QUESTION_LIST %> value="<%= request.getParameter(ServletConstants.QUIZ_QUESTION_LIST) %>" >
<input type="hidden" name=<%=ServletConstants.HIDDEN_CORRECT_ANSWER %> value="<%= curQuestion.getAnswers().get(0) %>" >

<% 
	out.println("<h3>"+curQuestion.getQuestion() +"</h3>");
	switch(curQuestion.getQuestionType()){
		case TEXT_RESPONSE:case FILL_BLANK:{ 
			out.println("<form action = \""+servletToCall+"\" method = \"post\">");
			out.println("<input type = text>");
			out.println("<p><button type = \" submit \" value = \" Submit/Next Question \" >   </p>");
			out.println("</form>");
			break;
		}
		case MULTIPLE_CHOICE:{
			ArrayList<String> toShuffle = new ArrayList<String>(curQuestion.getAnswers());
			Collections.shuffle(toShuffle);
			out.println("<form action = \""+servletToCall+"\" method = \"post\">");
			for (String answer: toShuffle){
				out.println("<input type=\"radio\" name=answer value=\""+answer+"\" >");
			}
			out.println("</form>");
			break;
		}
		case PICTURE_RESPONSE:{
			out.println("<img src=\"" + curQuestion.getImage()+ "\">");
			out.println("<form action = \""+servletToCall+"\" method = \"post\">");
			out.println("<input type = text>");
			out.println("<p><button type = \" submit \" value = \" Submit/Next Question \" >   </p>");
			out.println("</form>");
			break;
		}

	}
%>
</body>
</html>
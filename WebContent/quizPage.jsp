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
	boolean finished = questions.size()>curQuestionNum?true:false;
	Question curQuestion = questions.get(curQuestionNum);
%>
</head>
<body>
<input type="hidden" name="myhiddenvalue1" value="<%= request.getParameter(ServletConstants.QUIZ_QUESTION_NUMBER) %>" />
<input type="hidden" name="myhiddenvalue2" value="<%= request.getParameter(ServletConstants.QUIZ_QUESTION_LIST) %>" />
<% 
	out.println("<h3>"+curQuestion.getQuestion() +"</h3>");
	switch(curQuestion.getQuestionType()){
		case TEXT_RESPONSE:case FILL_BLANK:{ 
			out.println("<form action = \"nextQuestion\" method = \"post\">");
			out.println("<input type = text>");
			out.println("<p><button type = \" submit \" value = \" Submit/Next Question \" >   </p>");
		}
		
		case MULTIPLE_CHOICE:{
			
			
		}
		case PICTURE_RESPONSE:{
			
			
			
			
		}
	
	
	}






%>




</body>
</html>
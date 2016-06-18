<%@page import="sourcePackage.QuestionAbstract"%>
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
	ArrayList<QuestionAbstract> questions = (ArrayList<QuestionAbstract>) request.getAttribute(ServletConstants.QUIZ_QUESTION_LIST);
	String servletToCall = questions.size()-1>=curQuestionNum?"NextQuestion":"QuizFinished";
	QuestionAbstract curQuestion = questions.get(curQuestionNum);
%>
</head>
<body>
<input type="hidden" name=<%=ServletConstants.QUIZ_QUESTION_NUMBER %> value="<%= request.getParameter(ServletConstants.QUIZ_QUESTION_NUMBER) %>" >
<input type="hidden" name=<%=ServletConstants.QUIZ_QUESTION_LIST %> value="<%= request.getParameter(ServletConstants.QUIZ_QUESTION_LIST) %>" >
<input type="hidden" name=<%=ServletConstants.HIDDEN_CORRECT_ANSWER %> value="<%= curQuestion.getAnswers().get(0) %>" >

<% 
	out.println("<h3>"+curQuestion.getQuestion() +"</h3>");
	out.print(curQuestion.toHTML(servletToCall)); 
%>
</body>
</html>
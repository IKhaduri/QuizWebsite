<%@page import="sourcePackage.Quiz"%>
<%@page import="sourcePackage.Factory_Database"%>
<%@page import="java.sql.Connection"%>
<%@page import="sourcePackage.Database"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.QuestionAbstract"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%! @SuppressWarnings("unchecked") %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Have fun! </title>
<% 
	
	boolean quizStarted = (Boolean)session.getAttribute(ServletConstants.QUIZ_STARTED);
	if (!quizStarted){
		Connection con = Factory_Database.getConnection();
		Database base =(Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		Quiz quiz = base.getQuiz(request.getParameter(ServletConstants.QUIZ_PARAMETER_NAME), con);
		session.setAttribute(ServletConstants.QUIZ_QUESTION_LIST, quiz.getQuestions());
		session.setAttribute(ServletConstants.QUIZ_PARAMETER_NAME, quiz.getName());
		session.setAttribute(ServletConstants.QUIZ_QUESTION_NUMBER,"0");
		session.setAttribute(ServletConstants.CURRENT_SCORE, "0");
		session.setAttribute(ServletConstants.QUIZ_STARTED,true);
	}
	int curQuestionNum = (Integer.parseInt((String)session.getAttribute(ServletConstants.QUIZ_QUESTION_NUMBER)));
	ArrayList<QuestionAbstract> questions = (ArrayList<QuestionAbstract>) session.getAttribute(ServletConstants.QUIZ_QUESTION_LIST);
	String servletToCall = questions.size()-1>=curQuestionNum?"NextQuestion":"QuizFinished";
	QuestionAbstract curQuestion = questions.get(curQuestionNum);
%>
</head>
<body>
<input type="hidden" name=<%="'"+ServletConstants.QUIZ_QUESTION_NUMBER+"'" %> value="<%= request.getParameter(ServletConstants.QUIZ_QUESTION_NUMBER) %>" >
<% 

	out.println("<h3>"+curQuestion.getQuestion() +"</h3>");
	out.println("<form action = \""+servletToCall+"\" method = \"post\">");
	out.print(curQuestion.toHTML()); 
	out.println("<p><button type = \" submit \" value = \" Submit/Next Question \" >   </p>");
	out.println("</form>");
%>
</body> 
</html>
<%@page import="sourcePackage.Factory_Database"%>
<%@page import="sourcePackage.Quiz"%>
<%@page import="java.util.Collections"%>
<%@page import="sourcePackage.QuestionAbstract"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.Database"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/samepage.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enjoy your quiz!</title>
<%
	Database base = (Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	Quiz quiz = base.getQuiz(request.getParameter(ServletConstants.QUIZ_PARAMETER_NAME), Factory_Database.getConnection());
	ArrayList<QuestionAbstract> questionList = new ArrayList<QuestionAbstract>( quiz.getQuestions());
	if (quiz.shouldShuffle())
		Collections.shuffle(questionList);
	session.setAttribute(ServletConstants.QUIZ_QUESTION_SHUFFLED_LIST, questionList);
%>
</head>
<body>
<form action = "QuizFinished" method = post>
	<% 
		for (int i=0;i<questionList.size();i++){
			out.println(questionList.get(i).toHTML(i));
		}
	
	%>
	<input type = "submit">
</form>

</body>
</html>
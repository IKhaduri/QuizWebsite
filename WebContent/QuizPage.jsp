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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/quizpage_style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Have fun! </title>
<% 
	Connection con = Factory_Database.getConnection();
	int minutesLeft,secondsLeft;
	boolean quizStarted = (Boolean)session.getAttribute(ServletConstants.QUIZ_STARTED);
	System.out.println(quizStarted);
	if (!quizStarted){
		Database base =(Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		Quiz quiz = base.getQuiz(request.getParameter(ServletConstants.QUIZ_PARAMETER_NAME), con);
		session.setAttribute(ServletConstants.QUIZ_QUESTION_LIST, quiz.getQuestions());
		session.setAttribute(ServletConstants.QUIZ_PARAMETER_NAME, quiz.getName());
		session.setAttribute(ServletConstants.QUIZ_QUESTION_NUMBER,"0");
		session.setAttribute(ServletConstants.CURRENT_SCORE, "0");
		session.setAttribute(ServletConstants.QUIZ_STARTED,true);
		minutesLeft = quiz.getQuizTime();
		secondsLeft = 0;
	}else{
		minutesLeft = (Integer)request.getAttribute("minutesLeft");
		secondsLeft = (Integer)request.getAttribute("secondsLeft");
	}
	int curQuestionNum = (Integer.parseInt((String)session.getAttribute(ServletConstants.QUIZ_QUESTION_NUMBER)));
	ArrayList<QuestionAbstract> questions = (ArrayList<QuestionAbstract>) session.getAttribute(ServletConstants.QUIZ_QUESTION_LIST);
	String servletToCall = questions.size()+1>=curQuestionNum?"NextQuestion":"QuizFinished";
	QuestionAbstract curQuestion = questions.get(curQuestionNum);
	
	
%>
</head>
<body>
<% 
	out.println("<form action = \""+servletToCall+"\" method = \"post\">");
	out.println("<input id = 'minutesLeft' type='hidden' name='minutesLeft' value='100'>");
	out.println("<input id = 'secondsLeft' type='hidden' name='secondsLeft' value='100'>");
	out.print(curQuestion.toHTML()); 
	out.println("<br><input id = \"clickInput\"type = \"submit\" value = \"submit / Next Question\">");
	out.println("</form>");
%>
<script>
	var minutesLeft = <%=minutesLeft%>;
	var secondsLeft = <%=secondsLeft%>;
	function timer(){
		if (minutesLeft<=0&&secondsLeft<=0){
			document.getElementById("minutesLeft").value = 0;
			document.getElementById("secondsLeft").value = 0;	
			document.getElementById('clickInput').click();
			return;
		}
		if (secondsLeft==0){
			secondsLeft=59;
			minutesLeft--;
		}else 
			if (minutesLeft>=0 && secondsLeft>0){
				secondsLeft--;
			}
		document.getElementById("minutesLeft").value = minutesLeft;
		document.getElementById("secondsLeft").value = secondsLeft;
		var timerDisplay = document.getElementById("timer");
		timerDisplay.innerHTML = "Time left: "+minutesLeft+"minutes : "+secondsLeft+" seconds";
	}
	setInterval(timer, 1000);
</script>
<p id = "timer">Time left:</p>
</body> 
<%con.close(); %>
</html>


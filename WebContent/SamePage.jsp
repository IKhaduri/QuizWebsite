<%@page import="java.sql.Connection"%>
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
	<style type="text/css">body {background-color:rgba(215, 40, 40, 0.9);}</style>
	<link rel="stylesheet" href="css/quizpage_style.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Enjoy your quiz!</title>
	<%
		boolean quizStarted = (Boolean)session.getAttribute(ServletConstants.QUIZ_STARTED);
	
		Database base = (Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		Connection con;
		Quiz quiz = base.getQuiz(request.getParameter(ServletConstants.QUIZ_PARAMETER_NAME), (con = Factory_Database.getConnection()));
		Factory_Database.closeConnection(con);
		if (!quizStarted){	
			session.setAttribute(ServletConstants.QUIZ_PARAMETER_NAME, quiz.getName());
			session.setAttribute(ServletConstants.CURRENT_SCORE, "0");
			session.setAttribute(ServletConstants.QUIZ_STARTED,true);
		}
		ArrayList<QuestionAbstract> questionList = new ArrayList<QuestionAbstract>( quiz.getQuestions());
		if (quiz.shouldShuffle())
			Collections.shuffle(questionList);
		session.setAttribute(ServletConstants.QUIZ_QUESTION_SHUFFLED_LIST, questionList);
		int time  = quiz.getQuizTime();
	%>
</head>
<body>
<form id = "submitForm" action = "QuizFinished" method = post>
	<% 
		for (int i=0;i<questionList.size();i++){
			out.println(questionList.get(i).toHTML(i));
		}
	
	%>
	<input id ="clickInput" type = "submit" value="Submit All">
</form>
<script>
	var minutesLeft = <%=time%>;
	var secondsLeft = 0;
	function timer(){
		if (minutesLeft<=0&&secondsLeft<=0){
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
		
		var timerDisplay = document.getElementById("timer");
		timerDisplay.innerHTML = "Time left: "+minutesLeft+"minutes : "+secondsLeft+" seconds";
	}
	setInterval(timer, 1000);
</script>

<p id = "timer">Time left:</p>

</body>
</html>
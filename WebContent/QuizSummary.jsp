<%@page import="sourcePackage.User"%>
<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.Factory_Database"%>
<%@page import="sourcePackage.Touple"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.sql.Connection"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.Quiz"%>
<%@page import="sourcePackage.QuizBase"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="sourcePackage.Database"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	Database base = (Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	String quizName = (String) request.getParameter(ServletConstants.QUIZ_PARAMETER_NAME);
	User user = (User) session.getAttribute(SessionListener.USER_IN_SESSION);
	String userName = null;
	if(user!=null)
		userName = user.getName();
	Connection con = Factory_Database.getConnection();
	QuizBase quizBase = base.getQuizBase(quizName, con);
	Quiz quiz = base.getQuiz(quizName, con);
%>

<link rel="stylesheet" href="css/quiz_summary_style.css">
<title><%=quizBase.getName()%></title>

</head>
<body>
	<div id="Description">
		<p>
			<%=quizBase.getDescription()%>
		</p>
	</div>
		<h4>The Magnificent Author - 
		<%
		if (session.getAttribute(SessionListener.USER_IN_SESSION) != null)
			out.print("<a href=userpage.jsp?username=" + quizBase.getAuthor() + ">" + quizBase.getAuthor() + "</a>");
		else
			out.println(quizBase.getAuthor());%>
		</h4>
	
	<h2>Your Past Results
		<%
			if (session.getAttribute(SessionListener.USER_IN_SESSION) == null) {
				out.println(" - Not for Guests :)</h2>");
			} else {
				out.println("</h2><ul>");
				for (Touple<Double, Timestamp, Timestamp> result : base.getScores(userName, quizName, ServletConstants.LISTS_LIMIT, con)) {
				
				out.println(
						"<li>" + result.getSecond() + " " + result.getThird() + " " + result.getFirst() + "% </li>");
				}
				out.println("</ul>");
			}
		%>

	<h2>The Glorious Hall Of Fame</h2>

	<ul>
		<%
			for (Touple<String, Double, Timestamp> result : base.getHighestPerformers(quizName, 
					new Timestamp(ServletConstants.BEGINNING_DATE),ServletConstants.LISTS_LIMIT, con)) {
				out.println(
						"<li>" + result.getFirst() + " " + result.getThird() + " " + result.getSecond() + "% </li>");
			}
		%>
	</ul>
	<h2>The Glorious Hall Of Fame, Of Last Day!</h2>

	<ul>
		<%
			for (Touple<String, Double,Timestamp> result : base.getHighestPerformers(quizName,
					new Timestamp(System.currentTimeMillis()-
					ServletConstants.TWENTY_FOUR_HOURS_IN_MILLIS), ServletConstants.LISTS_LIMIT, con)) {
				out.println(
						"<li>" + result.getFirst() + " " + result.getThird() + " " + result.getSecond() + "% </li>");
			}
		%>
	</ul>
	<h2>The Glorious Hall Of Last Quiz Takers!</h2>

	<ul>
		<%
			for (Touple<String,  Double,Timestamp> result : base.getLastSubmissions(quizName, ServletConstants.LISTS_LIMIT, con)) {
				out.println(
						"<li>" + result.getFirst() + " " + result.getThird() + " " + result.getSecond() + "% </li>");
			}
		%>
	</ul>
	<h2>User Statistics On This Glorious Quiz!</h2>
	<% 
		out.println("<p>Maximum Score of this quiz:" +quizBase.getQuizScore()+"</p>");
		out.println("<p>Average Score for this quiz:" +quizBase.getAverageScore()+"</p>");
		out.println("<p>Average Score for this quiz:" +quizBase.getAverageScoreScaled()*100+"% </p>");
		out.println("<p>Number of users that took the quiz: "+quizBase.getSubmissionCount()+"</p><br><br>");
	
		if (session.getAttribute(SessionListener.USER_IN_SESSION) != null && 
				(base.areFriends(userName, quizBase.getAuthor(), true, con) || base.userSharesQuizzes(quizBase.getAuthor(), con))||userName.equals(quizBase.getAuthor())  ) {			
			out.println("<form action ='"+(quiz.isSinglePage()?"SamePage.jsp":"QuizPage.jsp")+"' method ='get' onsubmit = 'return validateForm();'>");
			session.setAttribute(ServletConstants.QUIZ_QUESTION_LIST, quiz.getQuestions());
			session.setAttribute(ServletConstants.QUIZ_PARAMETER_NAME, quiz.getName());
			session.setAttribute(ServletConstants.QUIZ_QUESTION_NUMBER,"0");
			session.setAttribute(ServletConstants.CURRENT_SCORE, "0");
			out.println("<button type = 'submit' value ='Start Quiz' class='start'>");
			out.println("Start Quiz</button>");

			out.println("<input type ='hidden' name ='"+ServletConstants.QUIZ_QUESTION_NUMBER+"' value ='0'>");
			out.println("<input type ='hidden' name ='"+ServletConstants.QUIZ_PARAMETER_NAME+"' value ='"+quiz.getName()+"'>");
			out.println("</form>");
			
			out.println("<form action=\"ChallengeServlet\" method=\"post\" class=\"challenge\"'>");
			out.println("<input type='hidden' name='quiz_name' value ='"+quiz.getName()+ "'>");
			out.println("<input type='text' name='friend_name' placeholder='friend name:'>");
			out.println("<input type='submit' value='Challenge a Friend!'></form>");
		}
	%> 
	<br><a href="homepage.jsp" class="start">Back to Home</a>
	<script>
		function validateForm(){
			var quizStarted;
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					quizStarted = xhttp.responseText;
					console.log("quiz started? "+quizStarted);
					}
				};
				xhttp.open("GET", "SessionGetter", false);
				xhttp.send();
			if (quizStarted=="true"){
				alert("You can't write multiple quizzes at the same time!");
				return false;
			}
		}

	</script>
	<% Factory_Database.closeConnection(con);%>
</body>




</html>
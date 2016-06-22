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
	String userName = (String) request.getParameter(ServletConstants.USER_PARAMETER_NAME);
	Connection con = Factory_Database.getConnection();
	QuizBase quizBase = base.getQuizBase(quizName, con);
	Quiz quiz = base.getQuiz(quizName, con);
%>
<title><%=quizBase.getName()%></title>

<style>
#Description {
	width: 750px;
	padding: 10px;
	border: 5px solid gray;
	margin: 0;
}
</style>
</head>
<body>

	<h1>Description</h1>
	<div id="Description">
		<p>
			<%=quizBase.getDescription()%>
		</p>
	</div>
	<% if (session.getAttribute(SessionListener.USER_IN_SESSION) != null)
		out.println("<a href=userpage.jsp?username=" + quizBase.getAuthor());%>
		<h4>
			The Magnificent Author
			<%=quizBase.getAuthor()%>
		</h4>
	</a>
	<h2>Your Past Results
		<%
			if (session.getAttribute(SessionListener.USER_IN_SESSION) == null) {
				out.println(" - Not for Guests :)</h2>");
			} else {
				out.println("</h1><ul>");
				for (Touple<Double, Timestamp, Timestamp> result : base.getScores(userName, quizName, ServletConstants.LISTS_LIMIT, con)) {
				out.println(
						"<li>" + result.getSecond() + " " + result.getThird() + " " + result.getFirst() + "% </li>");
				}
			}
		%>
	</ul>

	<h2>The Glorious Hall Of Fame</h2>

	<ul>
		<%
			for (Touple<String, Double, Timestamp> result : base.getHighestPerformers(quizName, 
					new Timestamp(ServletConstants.BEGINNING_DATE),ServletConstants.LISTS_LIMIT, con)) {
				out.println(
						"<li>" + result.getFirst() + " " + result.getSecond() + " " + result.getThird() + "% </li>");
			}
		%>
	</ul>
	<h2>The Glorious Hall Of Fame, Of Last Day!</h2>

	<ul>
		<%
			for (Touple<String, Double,Timestamp> result : base.getHighestPerformers(quizName,
					new Timestamp(System.currentTimeMillis()-
					ServletConstants.TWENTY_FOUR_HOURS_IN_MILLIS), ServletConstants.LISTS_LIMIT, con)) {
				out.println("<li>" + result.getFirst() + " " + result.getSecond() + " " + result.getThird() + "% </li>");
			}
		%>
	</ul>
	<h2>The Glorious Hall Of Last Quiz Takers!</h2>

	<ul>
		<%
			for (Touple<String,  Double,Timestamp> result : base.getLastSubmissions(quizName, ServletConstants.LISTS_LIMIT, con)) {
				out.println(
						"<li>" + result.getFirst() + " " + result.getSecond() + " " + result.getThird() + "% </li>");
			}
		%>
	</ul>
	<h2>User Statistics On This Glorious Quiz!</h2>
	<% 
		out.println("<p>Maximum Score of this quiz:" +quizBase.getQuizScore()+"</p>");
		out.println("<p>Average Score for this quiz:" +quizBase.getAverageScore()+"</p>");
		out.println("<p>Average Score for this quiz:" +quizBase.getAverageScoreScaled()*100+"% </p>");
		out.println("<p>Number of users that took the quiz: "+quizBase.getSubmissionCount()+"</p>");
	
		if (session.getAttribute(SessionListener.USER_IN_SESSION) != null) {
			out.println("<a href=" + (quiz.isSinglePage()?"SamePage.jsp":"QuizPage.jsp")+"?"+ServletConstants.QUIZ_QUESTION_NUMBER+"="+0
			+"&"+ServletConstants.QUIZ_PARAMETER_NAME+"="+quiz.getName());
		
			out.println("<p>Start the Quiz</p></a>");
		}
	%> 
	
	

	<%con.close(); %>
</body>




</html>
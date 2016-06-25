<%@page import="java.sql.Connection"%>
<%@page import="sourcePackage.Factory_Database"%>
<%@page import="sourcePackage.Database"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<% 
		String userName = (String)session.getAttribute(ServletConstants.USER_PARAMETER_NAME);
		Database base = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		String senderName = request.getParameter("username");
		Connection con = Factory_Database.getConnection();
		int uMessages = base.getNumOfUnreadMessages(userName, senderName, con);
	%>

<title>Insert title here</title>
</head>
<body>
	<h1><title>You have <%=uMessages%> unread messages</title> </h1>
<link rel="stylesheet" href="css/quizpage_style.css">
<title><%=request.getParameter("username") %></title>
</head>
<body>
<form action="SendMessage" method="post">
	<textarea class="area" name="message_text" rows="5" cols="68" placeholder="type here..."> </textarea><br><br>
	<input type="submit" value="Reply">
</form>	
</body>
<%con.close(); %>
</html>
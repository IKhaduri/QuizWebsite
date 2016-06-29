<%@page import="sourcePackage.User"%>
<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.Message"%>
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
		HttpSession ses = request.getSession();
		Database base = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		Connection con = Factory_Database.getConnection();
		if (ses == null || base == null || con == null) {
			Factory_Database.closeConnection(con);
			out.println("<script> function Redirect() { window.location=\"homepage.jsp\";} Redirect();	</script>");
			return;
		}
		
		String userName = ((User)ses.getAttribute(SessionListener.USER_IN_SESSION)).getName();
		String senderName = request.getParameter("username");

		int uMessages = base.getNumOfUnreadMessages(userName, senderName, con);
	%>

<title><%=uMessages%> Unread Messages</title>
<link rel="stylesheet" href="css/quizpage_style.css">
<link rel="stylesheet" href="css/inbox_style.css">
</head>
<body>
	<% 
		for (Message message:base.getUnreadMessages(userName, senderName, uMessages, con)){
			out.println("<p><a href=userpage.jsp?username=" + message.getSender() + ">" + message.getSender() + "</a>:  " + message.getMessage() + "</p>");
			
		}
		base.markMessagesRead(senderName, userName, con);
	%>
	
	<form action="SendMessage" method="post">
		<input type="hidden" name="dest" value=<%="'"+senderName+"'"%>>
		<textarea class="area" name="message_text" rows="5" cols="68" placeholder="type here..."> </textarea><br><br>
		<input type="submit" value="Reply">
	</form><br>

	<a href = 'homepage.jsp' class="home" value = 'Back To Home'>Back to Home</a><br>
	<a href = 'Inbox.jsp' class="home" value = 'Back To Home'>Back to Inbox</a>
</body>
<%Factory_Database.closeConnection(con); %>
</html>
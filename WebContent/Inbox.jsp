<%@page import="java.util.HashSet"%>
<%@page import="sourcePackage.Message"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Set"%>
<%@page import="sourcePackage.Factory_Database"%>
<%@page import="sourcePackage.User"%>
<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.Database"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% 
	String userName = ((User) session.getAttribute(SessionListener.USER_IN_SESSION)).getName();
	Database base = (Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	Connection con = Factory_Database.getConnection();
	int uMessages = base.getNumOfUnreadMessages(userName, con);
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>You have <%=uMessages%> unread messages</title>
</head>
	<body>
		<%
			Set<String> set = new HashSet<String>();
			for(Message message : base.getUnreadMessages(userName,  10000000, con)){
				set.add(message.getSender());
			}
		
			for (String friendName:set){

				out.println("<a href=\"SeeMessage.jsp?username=" + friendName + "\"><h3>You have "+base.getNumOfUnreadMessages(userName, friendName, con)
					+" Unread Message(s) from "+friendName+"</h3></a>");
			}
		%>
		
		<br><br><br>
		<form action="SendMessage" method="post">
			<input type="text" name="dest" placeholder="To:">
			<textarea class="area" name="message_text" rows="5" cols="68" placeholder="type here..."></textarea><br><br>
			<input type="submit" value="Send">
		</form>
	</body>
	<%con.close();%>
</html>
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
	User user = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
	Database base = (Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	Connection con = Factory_Database.getConnection();
	if (user == null || base == null || con == null) {
		out.println("<h1>Redirecting to Login page...</h1>");
		out.println("<script> setTimeout(function() { document.location = \"login.html\";}, 3000);	</script>");
		return;
	}
	
	String userName = user.getName();
	int uMessages = base.getNumOfUnreadMessages(userName, con);
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>You have <%=uMessages%> unread messages</title>
</head>
	<body>
		<%
			Set<String> friend_requests = new HashSet<String>();
			for (String req : base.getFriendRequestsList(userName, con))
				friend_requests.add(req);
		
			Set<String> set = new HashSet<String>();
			for(Message message : base.getUnreadMessages(userName,  10000000, con)){
				set.add(message.getSender());
			}
		
			for (String friendName : friend_requests) {
				out.println("<form action=\"FriendRequest\" method=\"post\">");
	           	out.println("<input type=\"hidden\" name=\"username\" value=\"" + friendName + "\">");
	           	out.println("<input type=\"hidden\" name=\"action\" value=\"Confirm\">");
	           	out.println("<h3><a href=userpage.jsp?username=" + friendName + ">" + friendName + "</a> sent you a friend request</h3>");
	           	out.println("<input type=\"submit\" name = \"first_button\" value=\" Confirm \" class=\"friend\">");
	           	out.println("<input type=\"submit\" name = \"decline\" value=\" Decline \" class=\"friend\"></form><br>");
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
		<a href = 'homepage.jsp' value = 'Back To Home'>Back to Home</a>
	
	</body>
	<%con.close();%>
</html>
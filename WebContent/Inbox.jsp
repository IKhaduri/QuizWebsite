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

%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inbox of <%=userName%></title>
<%
	Database base = (Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);

	
%>

</head>
	<body>
		




	</body>
</html>
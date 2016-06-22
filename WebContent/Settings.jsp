<%@page import="sourcePackage.Factory_Database"%>
<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="css/settings_style.css">
		<link rel="stylesheet" href="css/settings_privacy_style.css">
		<title>Settings</title>
	</head>
	<body>
		<%
	    	if (session == null || session.getAttribute(SessionListener.USER_IN_SESSION) == null) {
	    		Factory_Database.cleanSession(session);
	    		
	    		out.println("<style>body {background: #1f253d;} </style><h1>Redirecting to Login page...</h1>");
	    		out.println("<script> setTimeout(function() { document.location = \"login.html\";}, 3000);	</script>");
	    		return;
	    	}
    	%>
	    
		<form action="SettingsServlet" method="post">
			<div class="privacy">
				<h1>Privacy</h1>
				<div>
				    <input id="radio1" type="radio" name="radio" value="global" checked="checked"><label for="global"><span><span></span></span>Global</label>
				</div>
				<div>
				   	<input id="radio2" type="radio" name="radio" value="locked"><label for="locked"><span><span></span></span>Only Friends</label>
				</div>
	   		</div>
		
			<h1>Change Password</h1>
			<div class="inset">
			<p>
			    <label for="old">OLD</label>
			    <input type="text" name="old_password">
			</p>
			<p>
			    <label for="new">NEW</label>
			    <input type="password" name="new_password">
			</p>
			<p>
			    <label for="repeat">REPEAT</label>
			    <input type="password" name="repeat_password">
			</p>
			</div>
			<p class="p-container">
			    <input type="submit" name="go" id="go" value="UPDATE"> <!-- maybe js validation here -->
			</p>
		</form>
	</body>
</html>
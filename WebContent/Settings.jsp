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
	    <script>
	    	function validateForm(){
	    		var x = document.getElementById("new_password").value;
	    		var y = document.getElementById("repeat_password").value;
	 			var z = document.getElementById("old_password").value;
	    		if ((z==null||z=="")&&(x!=null||x!="")&&(y!=null||y!="")){
	    			alert("To change passwords enter old password")
	    			return false;
	    		}
	    			
	 			if (x==null&&y!==null){
	    			alert("Passwords do not match");
	    			return false;
	    		}
	    		if (y==null&&x!==null){
	    			alert("Passwords do not match");
	    			return false;
	    		}
	    		if (x!==y){
	    			alert("Passwords do not match");
	    			return false;
	    		}
	    		if ((x==null||x=="")&&(y==null||y=="")&&(z!=null||z!="")){
	    			alert("You cannot set password to be empty!");
	    			return false;
	    		}
	    	}
	    </script>
		<form action="SettingsServlet" method="post" onsubmit="return validateForm();">
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
			    <input id = "old_password" type="text" name="old_password">
			</p>
			<p>
			    <label for="new">NEW</label>
			    <input id = "new_password" type="password" name="new_password">
			</p>
			<p>
			    <label for="repeat">REPEAT</label>
			    <input id = "repeat_password" type="password" name="repeat_password">
			</p>
			</div>
			<p class="p-container">
			    <input type="submit" name="go" id="go" value="UPDATE"> <!-- maybe js validation here -->
			</p>
		</form>
	</body>
</html>
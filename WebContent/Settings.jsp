<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="css/settings_style.css">
		<title>Settings</title>
	</head>
	<body>
		<form>
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
		    <input type="submit" name="go" id="go" value="UPDATE">
		  </p>
		</form>
	</body>
</html>
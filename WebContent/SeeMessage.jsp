<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/quizpage_style.css">
<title><%=request.getParameter("username") %></title>
</head>
<body>
<form action="SendMessage" method="post">
	<textarea class="area" name="message_text" rows="5" cols="68" placeholder="type here..."> </textarea><br><br>
	<input type="submit" value="Reply">
</form>	
</body>
</html>
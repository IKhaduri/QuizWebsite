<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
</head>
<body>
	<h1>We're sorry but <%=(String)request.getAttribute("error_message") %></h1>
	<h2>Redirecting to home page...</h2>
	 <script> setTimeout(function(){ 
						 document.location = "homepage.jsp";}, 
						 3000);	
	 </script>);

</body>
</html>
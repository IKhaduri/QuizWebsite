<%@page import="sourcePackage.ServletConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
<% 
	String errorMessage = (String)request.getAttribute(ServletConstants.ERROR_MESSAGE);
	String redirectPage = (String)request.getAttribute(ServletConstants.REDIRECT_PAGE);
%>
</head>
<body>
	<h1>We're sorry but <%=errorMessage %></h1>
	<h2>Redirecting</h2>
	 <script> setTimeout(function(){ 
		 				 var page =	"<%=redirectPage%>";
						 document.location =page;}, 
						 3000);	
	 </script>);

</body>
</html>
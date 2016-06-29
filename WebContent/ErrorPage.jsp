<%@page import="sourcePackage.ServletConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
	body {
		font-family: "HelveticaNeue-Light","Helvetica Neue Light","Helvetica Neue",Helvetica,Arial,"Lucida Grande",sans-serif;
		text-align:center;
		padding:70px 0;
		color:red;
	}
	h3 {
		font-style:italic;
	}
</style>
<title>Error Page</title>
<% 
	String errorMessage = (String)request.getAttribute(ServletConstants.ERROR_MESSAGE);
	String redirectPage = (String)request.getAttribute(ServletConstants.REDIRECT_PAGE);
%>
</head>
<body>
	<h2>&nbsp;</h2>
	<h2>We're sorry but <%=errorMessage %></h2>
	<h3>Redirecting</h3>
	 <script> setTimeout(function(){ 
		 				 var page =	"<%=redirectPage%>";
						 document.location =page;}, 
						 3000);	
	 </script>);

</body>
</html>
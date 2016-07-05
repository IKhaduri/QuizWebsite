<%@page import="java.sql.Timestamp"%>
<%@page import="sourcePackage.Touple"%>
<%@page import="java.util.List"%>
<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.User"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.Factory_Database"%>
<%@page import="java.sql.Connection"%>
<%@page import="sourcePackage.Database"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My submissions</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
</head>
<script>
	 var count = 17;
	 var add = 9;
	 $(window).scroll(function(){
		    if($(window).scrollTop() == $(document).height() - $(window).height()){
		    	{
		            var http_request = new XMLHttpRequest();
		            http_request.onreadystatechange = function(){
		               if (http_request.readyState == 4  ){
		                  var json = JSON.parse(http_request.responseText);
		                  for (var i=0;i<json["size"];i++){
		              		$("ol").append("<font size='6'><li>"+json["quizName"+i]+" "+json["startTime"+i]+" "+json["score"+i]+"</li></font>");
		              	}        
		               }
		            }
		            http_request.open("POST", "MySubmissionsServlet", true);
		            http_request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		            http_request.send("from="+count+"&to="+(count+add));
		         }    	
		    	count+=add;
		    }
	 });  	
		    	
</script>
<% 
	Database base = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	Connection con = Factory_Database.getConnection();
	String userName = ((User)request.getSession().getAttribute(SessionListener.USER_IN_SESSION)).getName();
%>
<body>
	<h1>Here we have your past submissions!</h1>
	<ol><font size = "6">
		<% 
			List<Touple<String, Timestamp, Double>> submissions = base.getMySubmissionsFromTo(userName, 1, 16, con);
			for (Touple<String, Timestamp, Double> sub:submissions){
				out.println("<li>"+sub.getFirst()+"  "+sub.getSecond()+"  "+sub.getThird().intValue()+"</li>");
			}
			Factory_Database.closeConnection(con);
		%>
	</ol>
	</font>
	
</body>
</html>


<%@page import="sourcePackage.ServletConstants"%>
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
<link rel="stylesheet" href="css/homepage_style.css">
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
		              		$("ul").append("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?quizName=" + json["quizName"+i] + "\"><span class=\"icon entypo-check scnd-font-color\"></span>" +
		              				json["quizName"+i]+" "+json["startTime"+i]+" "+json["score"+i]+"</a></li>");
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
	List<Touple<String, Timestamp, Double>> submissions = base.getMySubmissionsFromTo(userName, 1, 16, con);
%>
<body>
	
	 <div class="main-container">
		 <div class="left-container container" style="width: 950px;">
			<div class="menu-box block">
	            <h2 class="titular">Here we have your past submissions!</h2>
	            <ul class="menu-box-menu">
	            <% 
					for (Touple<String, Timestamp, Double> sub:submissions){
						out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "=" + sub.getFirst() + "\"><span class=\"icon entypo-check scnd-font-color\"></span>" +
							sub.getFirst()+"  "+sub.getSecond()+"  "+sub.getThird().intValue()+"</a></li>");
					}
					Factory_Database.closeConnection(con);
				%>
	            </ul>
	        </div>
		</div>
	</div>
</body>
</html>


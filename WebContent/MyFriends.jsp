<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.QuizBase"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="sourcePackage.Touple"%>
<%@page import="java.util.List"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.User"%>
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
<link rel="stylesheet" href="css/seemore_back_style.css">
<title>My Friends</title>
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
		              		$("ul").append("<li><a class=\"menu-box-tab\" href=\"userpage.jsp?username=" + json["friend_name"+i] +
		              				"\"><span class=\"icon entypo-user scnd-font-color\"></span>" + json["friend_name"+i]+"</a></li>");		              	
		                  }        
		               }
		            }
		            http_request.open("POST", "MyFriendsServlet", true);
		            http_request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		            http_request.send("from="+count+"&to="+(count+add));
		         }    	
		    	count+=add;
		    }
	 });  	
		    	
</script>
<body>
<% 
	Database base = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	Connection con = Factory_Database.getConnection();
	User user = (User)request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
	
	if (base == null || con == null || user == null) {
		out.println("<h1>Redirecting to Login page...</h1>");
		Factory_Database.closeConnection(con);
		out.println("<script> setTimeout(function() { document.location = \"login.html\";}, 3000);	</script>");
		return;
	}
	String userName = user.getName();
	List<String> friends = base.getFriendList(userName, 0, 16, con);
%>
<body>
	<div class="main-container">
		 <div class="left-container container" style="width: 950px;">
			<div class="menu-box block">
	            <h2 class="titular">Here we have your created quizzes!</h2>
	            <ul class="menu-box-menu">
	            <% 
	            for (String friend:friends){
						out.println("<li><a class=\"menu-box-tab\" href=\"userpage.jsp?username=" +
								friend + "\"><span class=\"icon entypo-user scnd-font-color\"></span>"+friend+"</li>");
					}
					Factory_Database.closeConnection(con);
				%>
	            </ul>
	        </div>
		</div>
	</div>
	<a href="homepage.jsp" class="back">Back to Home</a>
</body>
</html>


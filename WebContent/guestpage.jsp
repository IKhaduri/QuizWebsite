<%@page import="java.util.ArrayList"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.QuizBase"%>
<%@page import="java.util.List"%>
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
	<title>Guest</title>
	</head>
	<body>
		<%
	    	Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	    	Connection connection = Factory_Database.getConnection();
	    	Factory_Database.cleanSession(request.getSession(false));
	    	
	    	if (db == null || connection == null) {
	    		out.println("<h1>Redirecting to Login page...</h1>");
	    		out.println("<script> setTimeout(function() { document.location = \"login.html\";}, 3000);	</script>");
	    		return;
	    	}
    	%>
    	
    	<div class="main-container"> 

			<!-- HEADER -->
            <header class="block">
            <div class="profile-menu">
                	<p>Log Out <a href="LogoutServlet"><span class="entypo-logout scnd-font-color"></span></a></p>
            </div>
            </header>
            
            <div class="left-container container">
                <div class="menu-box block">
                    <h2 class="titular">Popular quizzes</h2>
                    <ul class="menu-box-menu">
                    	<div id="popular_quizzes"><%
                        	List<QuizBase> list2 = db.getPopularQuizzes(ServletConstants.LISTS_LIMIT, connection);
                        	if (list2 != null && list2.size() > 0) {
	                        	ArrayList<QuizBase> topQuizzes = new ArrayList<QuizBase>(list2);
		                       	for (int i = 0; i < topQuizzes.size(); i++) {
		                       		QuizBase currQuiz = topQuizzes.get(i);
		                       		out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "="
		                       			+ currQuiz.getName() +"\"><span class=\"icon entypo-paper-plane scnd-font-color\"></span>" + currQuiz.getName() + "</a></li>");
		                       	}
                        	}
                    	%></div>
                    </ul>
                </div>
			</div>
			
			<div class="middle-container container">
				<div class="profile block">
                    <div class="profile-picture big-profile-picture clear">
                        <img width="150px" alt="picture" src=<%= "http://lh4.googleusercontent.com/-e1IezqyHQ7M/AAAAAAAAAAI/AAAAAAAAAI0/-IMyZ0GGqB8/photo.jpg?sz=300" %> >
                    </div><br>
                    <h1 class="user-name"><%="Anonymous"%></h1>
				</div>
			</div>
			
			<div class="right-container container">
                <div class="menu-box block">
                    <h2 class="titular">Recently created quizzes</h2>
                    <ul class="menu-box-menu">
                        <div id="recently_created_quizzes"><%
	                        List<QuizBase> list3 = db.recentlyAddedQuizzes(ServletConstants.LISTS_LIMIT, connection);
	                        if (list3 != null && list3.size() > 0) {
		                       	ArrayList<QuizBase> recentlyQuizzes = new ArrayList<QuizBase>(list3);
		                       	for (int i = 0; i < recentlyQuizzes.size(); i++) {
		                       		QuizBase currQuiz = recentlyQuizzes.get(i);
		                       		out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "="
		                       			+ currQuiz.getName() +"\"><span class=\"icon entypo-paper-plane scnd-font-color\"></span>" + currQuiz.getName() + "</a></li>");
		                       	}
	                        }
	                       	Factory_Database.closeConnection(connection);
                   		%></div>                        
                    
                    </ul>
                </div>
               </div>
        </div> <!-- end main-container -->
	</body>

</html>


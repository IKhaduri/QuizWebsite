<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sourcePackage.Factory_Database"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.QuizBase"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.Submission"%>
<%@page import="java.sql.Connection"%>
<%@page import="sourcePackage.User"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.Database"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/homepage_style.css">
<title>homepage</title>
</head>

    <body>
    
    	<%
	    	Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	    	Connection connection = Factory_Database.getConnection();
	    	User user = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
	    	
	    	if (db == null || connection == null || user == null) {
	    		out.println("<h1>Redirecting to Login page...</h1>");
	    		out.println("<script> setTimeout(function() { document.location = \"login.html\";}, 3000);	</script>");
	    		return;
	    	}
    	%>

        <div class="main-container"> 

			<!-- HEADER -->
            <header class="block">
                <ul class="header-menu horizontal-list">
                    <li>
                        <a class="header-menu-tab" href="Settings.jsp"><span class="icon entypo-cog scnd-font-color"></span>Settings</a>
                    </li>
                    <li>
                        <a class="header-menu-tab" href="Inox.jsp"><span class="icon fontawesome-envelope scnd-font-color"></span>Messages</a>
                        <a class="header-menu-number" href="Inbox.jsp"><%= user.getNumOfUnreadMessages(connection, db) %></a>
                    </li>
                </ul>
                <div class="profile-menu">
                    <p>Me <a href="homepage.jsp"><span class="entypo-to-end scnd-font-color"></span></a></p>
                    <div class="profile-picture small-profile-picture">
                        <img width="40px" alt="user picture" src="http://upload.wikimedia.org/wikipedia/commons/e/e1/Anne_Hathaway_Face.jpg">
                    </div>
                </div>
            </header>
		

            <!-- LEFT-CONTAINER -->
            <div class="left-container container">
                <div class="menu-box block">
                    <h2 class="titular">MENU</h2>
                    <ul class="menu-box-menu">
                        <li>
                        <a class="menu-box-tab"><span class="icon entypo-chart-line scnd-font-color"></span>Total score
                        <div class="menu-box-number"><%= user.getTotalScore(db, connection) %></div></a>	                            
                    </li>
                    <li>
                        <a class="menu-box-tab"><span class="icon entypo-chart-line scnd-font-color"></span>Max score
                        <div class="menu-box-number"><%= user.getMaxScorePercentage(db, connection) %></div></a>                     
                    </li>
                    <li>
                        <a class="menu-box-tab"><span class="icon entypo-calendar scnd-font-color"></span>Submissions</a>                     
                    </li>
                                                 
                    <div id="submissions"><%
                    	List<Submission> f_list = user.getSubmissions(db, connection, ServletConstants.LISTS_LIMIT);
                   		if (f_list != null && f_list.size() > 0) {
	                    	ArrayList<Submission> submissions = new ArrayList<Submission>(f_list);
	                       	for (int i = 0; i < submissions.size(); i++) {
	                       		Submission currSub = submissions.get(i);
	                       		out.println("<li><a class=\"menu-box-tab\"><span class=\"icon entypo-paper-plane scnd-font-color\"></span>"
	                       				+ currSub.getQuiz().getName());
	                       		out.println("<div class=\"menu-box-number\">" + user.getMaxScorePercentage(db, connection) + "</div></a></li>");
	                       	}
                   		}
                    %></div>
                        
                    <li>
                        <a class="menu-box-tab"><span class="icon entypo-calendar scnd-font-color"></span>Recently created quizzes</a>                     
                    </li>
                        
                    <div id="created_quizzes"><%
                    	List<QuizBase> list = user.getCreatedQuizzes(db, connection, ServletConstants.LISTS_LIMIT);
                    	if (list != null && list.size() > 0) {
	                       	ArrayList<QuizBase> quizzes = new ArrayList<QuizBase>(list);
	                       	for (int i = 0; i < quizzes.size(); i++) {
	                       		QuizBase currQuiz = quizzes.get(i);
	                       		out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "="
	                       			+ currQuiz.getName() +"\"><span class=\"icon entypo-paper-plane scnd-font-color\"></span>" + currQuiz.getName() + "</a></li>");
	                       	}
                    	}
                    %></div>                       
                    </ul>
                </div>
               </div>

            <!-- MIDDLE-CONTAINER -->
            <div class="middle-container container">
                <div class="profile block">
                    <div class="profile-picture big-profile-picture clear">
                        <img width="150px" alt="picture" src="https://upload.wikimedia.org/wikipedia/commons/0/0b/Jennifer_Lawrence_SDCC_2015_X-Men.jpg" >
                    </div>
                    <h1 class="user-name"><%= user.getName() %></h1>
                    <div class="profile-description">
                        <p class="scnd-font-color"><%= user.getStatus(db, connection) %></p>
                    </div>
                    <form action="UserStatusServlet" method="get">
	                	<input type="text" name="update_description_field">
	                    <input class="button" type="submit" name="update_description_update" value="UPDATE">
                    </form>
                </div>
            </div>

            <!-- RIGHT-CONTAINER -->
            
            <div class="right-container container">
                <div class="menu-box block">
                    <h2 class="titular">MENU</h2>
                    <ul class="menu-box-menu">
                    <li>
                        <a class="menu-box-tab"><span class="icon entypo-calendar scnd-font-color"></span>Popular quizzes</a>                     
                    </li>
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
                    
                    <li>
                        <a class="menu-box-tab"><span class="icon entypo-calendar scnd-font-color"></span>Recently created quizzes</a>                     
                    </li>
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

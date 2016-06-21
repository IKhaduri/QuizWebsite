<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.Factory_Database"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="sourcePackage.QuizBase"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.Submission"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.Database"%>
<%@page import="sourcePackage.User"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/userpage_style.css">
<title>user</title>
</head>

    <body>
    
    <%
    	Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
    	Connection connection = Factory_Database.getConnection();
    	User me = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
    	
    	// not main user, but someone we are guested
    	User user = db.getUser(request.getParameter("username"), request.getParameter("password_hash"), connection);
    	
    	if (db == null || connection == null || user == null) {
    		out.println("<h1>Redirecting to Home page...</h1>");
    		out.println("<script> setTimeout(function() { document.location = \"homepage.jsp\";}, 3000);	</script>");
    		return;
    	}
    %>

        <div class="main-container"> 
        
       		<!-- HEADER -->
            <header class="block">
                    
                <div class="profile-menu">
                	<p>Log Out <a href="LogoutServlet"><span class="entypo-logout scnd-font-color"></span></a></p>
                    <p>Me <a href="homepage.jsp"><span class="entypo-to-end scnd-font-color"></span></a></p>
                    <div class="profile-picture small-profile-picture">
                        <img width="40px" alt="user picture" src=<%= me.profilePictureLink() %> >
                    </div>
                </div>
            </header> 

            <!-- LEFT-CONTAINER -->
            <div class="left-container container">
                <div class="menu-box block">
                    <h2 class="titular">MENU</h2>
                    <ul class="menu-box-menu">
                        <li>
                            <a class="menu-box-tab"><span class="icon fontawesome-envelope scnd-font-color"></span>Total score
                            <div class="menu-box-number"><%= user.getTotalScore(db, connection) %></div></a>	                            
                        </li>
                        <li>
                            <a class="menu-box-tab"><span class="icon entypo-paper-plane scnd-font-color"></span>Max score
                            <div class="menu-box-number"><%= user.getMaxScorePercentage(db, connection) %></div></a>                     
                        </li>
                        <li>
                            <a class="menu-box-tab"><span class="icon entypo-paper-plane scnd-font-color"></span>Submissions</a>                     
                        </li>
                                                 
                        <div id="submissions"><%
                        	Submission[] subs = new Submission[ServletConstants.LISTS_LIMIT];
                        	user.getSubmissions(db, connection, ServletConstants.LISTS_LIMIT).toArray(subs);
                        	for (int i = 0; i < ServletConstants.LISTS_LIMIT; i++) {
                        		Submission currSub = subs[i];
                        		out.println("<li><a class=\"menu-box-tab\"><span class=\"icon entypo-calendar scnd-font-color\"></span>"
                        				+ currSub.getQuiz().getName());
                        		out.println("<div class=\"menu-box-number\">" + user.getMaxScorePercentage(db, connection) + "</div></a></li>");
                        	}
                        %></div>
                                              
                    </ul>
                </div>
               </div>

            <!-- MIDDLE-CONTAINER -->
            <div class="middle-container container">
                <div class="profile block">
                    <div class="profile-picture big-profile-picture clear">
                        <img width="150px" alt="picture" src=<%= user.profilePictureLink() %> >
                    </div>
                    <h1 class="user-name"><%= out.print(user.getName()) %></h1>
                    <div class="profile-description">
                        <p class="scnd-font-color"><%= out.print(user.getStatus(db, connection)) %></p>
                    </div>
                </div>
            </div>

            <!-- RIGHT-CONTAINER -->
            
            <div class="right-container container">
                <div class="menu-box block">
                    <h2 class="titular">MENU</h2>
                    <ul class="menu-box-menu">
                        <li>
                            <a class="menu-box-tab"><span class="icon entypo-paper-plane scnd-font-color"></span>Recently created quizzes</a>                     
                        </li>
                        
                        <div id="created_quizzes"><%
                        	QuizBase[] quizzes = new QuizBase[ServletConstants.LISTS_LIMIT];
                        	user.getCreatedQuizzes(db, connection, ServletConstants.LISTS_LIMIT).toArray(quizzes);
                        	for (int i = 0; i < ServletConstants.LISTS_LIMIT; i++) {
                        		QuizBase currQuiz = quizzes[i];
                        		out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "="
                        			+ currQuiz.getName() +"\"><span class=\"icon entypo-calendar scnd-font-color\"></span>" + currQuiz.getName() + "</a></li>");
                        	}
                        	Factory_Database.closeConnection(connection);
                        %></div>                      
                    </ul>
                </div>
               </div>
        </div> <!-- end main-container -->
    </body>
</html>

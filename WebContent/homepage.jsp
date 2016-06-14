<!DOCTYPE html>
<%@page import="sourcePackage.SessionListener"%>
<%@page import="sourcePackage.QuizBase"%>
<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.Submission"%>
<%@page import="sourcePackage.Factory"%>
<%@page import="java.sql.Connection"%>
<%@page import="sourcePackage.User"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.Database"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/homepage_style.css">
<title>homepage</title>
</head>

    <body>
    
    	<%
	    	Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
	    	Connection connection = Factory.getConnection();
	    	User user = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
    	%>

        <div class="main-container"> 

			<!-- HEADER -->
            <header class="block">
                <ul class="header-menu horizontal-list">
                    <li>
                        <a class="header-menu-tab" href="Settings.jsp"><span class="icon entypo-cog scnd-font-color"></span>Settings</a>
                    </li>
                    <li>
                        <a class="header-menu-tab" href="#3"><span class="icon fontawesome-envelope scnd-font-color"></span>Messages</a>
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
                        <a class="menu-box-tab"><span class="icon entypo-paper-plane scnd-font-color"></span>Popular quizzes</a>                     
                    </li>
                        <div id="popular_quizzes"><%
                    	QuizBase[] topQuizzes = new QuizBase[ServletConstants.LISTS_LIMIT];
                       	db.getPopularQuizzes(ServletConstants.LISTS_LIMIT, connection).toArray(topQuizzes);
                       	for (int i = 0; i < ServletConstants.LISTS_LIMIT; i++) {
                       		QuizBase currQuiz = topQuizzes[i];
                       		out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "="
                       			+ currQuiz.getName() +"\"><span class=\"icon entypo-calendar scnd-font-color\"></span>" + currQuiz.getName() + "</a></li>");
                       	}
                    %></div>
                    
                    <li>
                        <a class="menu-box-tab"><span class="icon entypo-paper-plane scnd-font-color"></span>Recently created quizzes</a>                     
                    </li>
                        <div id="recently_created_quizzes"><%
                    	QuizBase[] recentlyQuizzes = new QuizBase[ServletConstants.LISTS_LIMIT];
                       	db.recentlyAddedQuizzes(ServletConstants.LISTS_LIMIT, connection).toArray(recentlyQuizzes);
                       	for (int i = 0; i < ServletConstants.LISTS_LIMIT; i++) {
                       		QuizBase currQuiz = recentlyQuizzes[i];
                       		out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "="
                       			+ currQuiz.getName() +"\"><span class=\"icon entypo-calendar scnd-font-color\"></span>" + currQuiz.getName() + "</a></li>");
                       	}
                       	Factory.closeConnection(connection);
                    %></div>                        
                    
                    </ul>
                </div>
               </div>
        </div> <!-- end main-container -->
    </body>
</html>
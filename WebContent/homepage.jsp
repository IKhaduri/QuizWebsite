<!DOCTYPE html>
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
	    	User user = db.getUser(request.getParameter("username"), request.getParameter("password_hash"), connection);
    	%>

        <div class="main-container"> 

			<!-- HEADER -->
            <header class="block">
                <ul class="header-menu horizontal-list">
                    <li>
                        <a class="header-menu-tab" href="#1"><span class="icon entypo-cog scnd-font-color"></span>Settings</a>
                    </li>
                    <li>
                        <a class="header-menu-tab" href="#2"><span class="icon fontawesome-user scnd-font-color"></span>Account</a>
                    </li>
                    <li>
                        <a class="header-menu-tab" href="#3"><span class="icon fontawesome-envelope scnd-font-color"></span>Messages</a>
                        <a class="header-menu-number" href="#4">5</a>
                    </li>
                    <li>
                        <a class="header-menu-tab" href="#5"><span class="icon fontawesome-star-empty scnd-font-color"></span>Favorites</a>
                    </li>
                </ul>
                <div class="profile-menu">
                    <p>Me <a href="#26"><span class="entypo-down-open scnd-font-color"></span></a></p>
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
                        <div class="menu-box-number"><% user.getTotalScore(db, connection); %></div></a>	                            
                    </li>
                    <li>
                        <a class="menu-box-tab"><span class="icon entypo-paper-plane scnd-font-color"></span>Max score
                        <div class="menu-box-number"><% user.getMaxScorePercentage(db, connection); %></div></a>                     
                    </li>
                    <li>
                        <a class="menu-box-tab"><span class="icon entypo-paper-plane scnd-font-color"></span>Submissions</a>                     
                    </li>
                                                 
                    <div id="submissions"><%
                    	Submission[] subs = new Submission[user.getNumOfSubmissions(db, connection)];
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
                    	QuizBase[] quizzes = new QuizBase[user.getNumOfCreatedQuizzes(db, connection)];
                       	user.getCreatedQuizzes(db, connection, ServletConstants.LISTS_LIMIT).toArray(quizzes);
                       	for (int i = 0; i < ServletConstants.LISTS_LIMIT; i++) {
                       		QuizBase currQuiz = quizzes[i];
                       		out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "="
                       			+ currQuiz.getName() +"\"><span class=\"icon entypo-calendar scnd-font-color\"></span>" + currQuiz.getName() + "</a></li>");
                       	}
                       	Factory.closeConnection(connection);
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
                    <h1 class="user-name">Jennifer Lawrence</h1>
                    <div class="profile-description">
                        <p class="scnd-font-color">Status or Description</p>
                    </div>
                </div>
            </div>

            <!-- RIGHT-CONTAINER -->
            
            <div class="right-container container">
                <div class="menu-box block">
                    <h2 class="titular">MENU</h2>
                    <ul class="menu-box-menu">
                        <li>
                            <a class="menu-box-tab" href="#6"><span class="icon fontawesome-envelope scnd-font-color"></span>Messages<div class="menu-box-number">24</div></a>                            
                        </li>
                        <li>
                            <a class="menu-box-tab" href="#8"><span class="icon entypo-paper-plane scnd-font-color"></span>Invites<div class="menu-box-number">3</div></a>                            
                        </li>
                        <li>
                            <a class="menu-box-tab" href="#10"><span class="icon entypo-calendar scnd-font-color"></span>Events<div class="menu-box-number">5</div></a>                            
                        </li>
                        <li>
                            <a class="menu-box-tab" href="#12"><span class="icon entypo-cog scnd-font-color"></span>Account Settings</a>
                        </li>
                        <li>
                            <a class="menu-box-tab" href="#13"><sapn class="icon entypo-chart-line scnd-font-color"></sapn>Statistics</a>
                        </li>                        
                    </ul>
                </div>
               </div>
        </div> <!-- end main-container -->
    </body>
</html>

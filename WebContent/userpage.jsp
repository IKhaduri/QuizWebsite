<!DOCTYPE html>
<%@page import="sourcePackage.ServletConstants"%>
<%@page import="sourcePackage.Submission"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%@page import="sourcePackage.Factory"%>
<%@page import="sourcePackage.ContextInitializer"%>
<%@page import="sourcePackage.Database"%>
<%@page import="sourcePackage.User"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/userpage_style.css">
<title>user</title>
</head>

    <body>
    
    <%
    	Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
    	User user = db.getUser(request.getParameter("username"), request.getParameter("password_hash"), Factory.getConnection());
   		Connection connection = Factory.getConnection();
    %>

        <div class="main-container">  

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
                                                 
                        <div id="submissions"><%
                        		Submission[] subs = new Submission[user.getNumOfSubmissions(db, connection)];
                        		user.getSubmissions(db, connection, ServletConstants.LISTS_LIMIT).toArray(subs);
                        		for (int i = 0; i < ServletConstants.LISTS_LIMIT; i++) {
                        			Submission currSub = subs[i];
                        			out.println("<li><a class=\"menu-box-tab\"><span class=\"icon entypo-calendar scnd-font-color\"></span>" + currSub.getQuiz().getName());
                        			out.println("<div class=\"menu-box-number\">" + user.getMaxScorePercentage(db, connection) + "</div></a></li>");
                        		}
                        %></div>
                        
                        <div id="created_quizzes"><%
                        		Submission[] subs = new Submission[user.getNumOfSubmissions(db, connection)];
                        		user.getSubmissions(db, connection, ServletConstants.LISTS_LIMIT).toArray(subs);
                        		for (int i = 0; i < ServletConstants.LISTS_LIMIT; i++) {
                        			Submission currSub = subs[i];
                        			out.println("<li><a class=\"menu-box-tab\"><span class=\"icon entypo-calendar scnd-font-color\"></span>" + currSub.getQuiz().getName());
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
                        <img width="150px" alt="picture" src="http://www.rmi.ge/~meskhi/meskhi.jpg" >
                    </div>
                    <h1 class="user-name">Name</h1>
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

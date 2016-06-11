<!DOCTYPE html>
<%@page import="sourcePackage.QuizBase"%>
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
    	Connection connection = Factory.getConnection();
    	User user = db.getUser(request.getParameter("username"), request.getParameter("password_hash"), connection);
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
                        <img width="150px" alt="picture" src="http://www.rmi.ge/~meskhi/meskhi.jpg" >
                    </div>
                    <h1 class="user-name"><% out.print(user.getName()); %></h1>
                    <div class="profile-description">
                        <p class="scnd-font-color"><% out.print(user.getStatus(db, connection)); %></p>
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
                        	Factory.closeConnection(connection);
                        %></div>                      
                    </ul>
                </div>
               </div>
        </div> <!-- end main-container -->
    </body>
</html>

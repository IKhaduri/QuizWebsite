<%@page import="java.util.List"%>
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
<title>User</title>
<style>
.menu-box {
	height: <%="" + ServletConstants.LISTS_LIMIT * 72 + "px;"%>
}
</style>
</head>

    <body>
    
    <%
    	Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
    	Connection connection = Factory_Database.getConnection();
    	User me = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
    	
    	// not main user, but someone we are guested
    	User user = db.getUser(request.getParameter("username"), connection);
    	
    	if (db == null || connection == null || user == null || me == null) {
    		out.println("<h1>Redirecting to Home page...</h1>");
    		Factory_Database.closeConnection(connection);
    		out.println("<script> setTimeout(function() { document.location = \"homepage.jsp\";}, 3000);	</script>");
    		return;
    	} else if (user.equals(me)) {
    		Factory_Database.closeConnection(connection);
    		out.println("<script> function Redirect() { window.location=\"homepage.jsp\";} Redirect();	</script>");
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
                        <img width="40px" height="40px" alt="user picture" src=<%= me.getProfilePictureLink(connection, db) %> >
                    </div>
                </div>
            </header> 

            <!-- LEFT-CONTAINER -->
            <div class="left-container container">
            	<div class="hidden"></div><br>
                <div class="menu-box block">
                    <h2 class="titular">Submissions</h2>
                    <ul class="menu-box-menu">                                                 
                        <div id="submissions"><%
                        	List<Submission> f_list = user.getSubmissions(db, connection, ServletConstants.LISTS_LIMIT);
	                   		if (f_list != null && f_list.size() > 0) {
		                    	ArrayList<Submission> submissions = new ArrayList<Submission>(f_list);
		                       	for (int i = 0; i < submissions.size(); i++) {
		                       		Submission currSub = submissions.get(i);
		                       		out.println("<li><a class=\"menu-box-tab\"><span class=\"icon entypo-paper-plane scnd-font-color\"></span>"
		                       				+ currSub.getQuiz().getName());
		                       		out.println("<div class=\"menu-box-number\">" + Database.percentage(currSub.getQuiz().getQuizScore(), currSub.getScore()) + "</div></a></li>");
		                       	}
	                   		}
                        %></div>              
                    </ul>
                </div>
                <div class="menu-box block">
                    <h2 class="titular">Statistics</h2>
                    <ul class="menu-box-menu">
		                <li>
			                <a class="menu-box-tab"><span class="icon entypo-chart-line scnd-font-color"></span>Total score
	                        <div class="menu-box-number"><%= user.getTotalScore(db, connection) %></div></a>	                            
                        </li>
                        <li>
                            <a class="menu-box-tab"><span class="icon entypo-chart-line scnd-font-color"></span>Max score
                            <div class="menu-box-number"><%= user.getMaxScorePercentage(db, connection) %></div></a>                     
                        </li>
                    </ul>
               </div>
           </div>

            <!-- MIDDLE-CONTAINER -->
            <div class="middle-container container">
                <div class="profile block">
                    <div class="profile-picture big-profile-picture clear">
                        <img width="150px" height="150px" alt="picture" src=<%= user.getProfilePictureLink(connection, db) %> >
                    </div>
                    <h1 class="user-name"><%= user.getName() %></h1>
                    <div class="profile-description">
                        <p class="scnd-font-color"><%= user.getStatus(db, connection) %></p>
                    </div>
                </div>
            </div>

            <!-- RIGHT-CONTAINER -->
            
            <div class="right-container container">
            	<%
            		boolean received = false;	// friend request received from this user
            		String label = "Unfriend";
	            	if (!db.areFriends(me.getName(), user.getName(), true, connection)) {
	            		if (!db.areFriends(me.getName(), user.getName(), false, connection)) {
				           	label = "Add Friend";
	            		} else {
	            			if (db.hasFriendRequestSentOrReceived(me.getName(), user.getName(), true, connection)) {
				           		label = "Cancel Request";
	            			} else {
	            				label = "Confirm";
	            				received = true;
	            			}
	            		}
	            	}
	            	out.println("<form action=\"FriendRequest\" method=\"post\">");
		           	out.println("<input type=\"hidden\" name=\"username\" value=\"" + user.getName() + "\">");
		           	out.println("<input type=\"hidden\" name=\"action\" value=\"" + label + "\">");
		           	out.println("<input type=\"submit\" name = \"first_button\" value=\"" +  label + "\" class=\"friend\">");
		           	if (received) out.println("<input type=\"submit\" name = \"decline\" value=\" Decline \" class=\"friend\">");
		           	out.println("</form><br>");
            	%>
            	
                <div class="menu-box block">
                    <h2 class="titular">Own Quizzes</h2>
                    <ul class="menu-box-menu">                        
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
                        	Factory_Database.closeConnection(connection);
                        %></div>                      
                    </ul>
                </div>
               </div>
        </div> <!-- end main-container -->
    </body>
</html>

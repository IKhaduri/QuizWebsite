<%@page import="sourcePackage.Touple"%>
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
<script src="http://code.jquery.com/jquery-1.7.js"
    type="text/javascript"></script>
<script
    src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"
    type="text/javascript"></script>
<link
    href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
    rel="stylesheet" type="text/css" />
<STYLE TYPE="text/css" media="all">
.ui-autocomplete { 
    position: absolute; 
    cursor: default; 
    height: 200px; 
    overflow-y: scroll; 
    overflow-x: hidden;}
</STYLE>




<title>Homepage</title>
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
	    	User user = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
	    	
	    	if (db == null || connection == null || user == null) {
	    		out.println("<h1>Redirecting to Login page...</h1>");
	    		Factory_Database.closeConnection(connection);
	    		out.println("<script> setTimeout(function() { document.location = \"login.html\";}, 3000);	</script>");
	    		return;
	    	}
	    	
	    	List<Touple<String, String, Integer>> friend_submissions = db.getFriendSubmissions(user.getName(), 3 * ServletConstants.ACTIVITY_LISTS_LIMIT, connection);
    	%>
    	<script>
	    	function checkURL(url) {
	    	    return(url.match(/\.(jpeg|jpg|gif|png)$/) != null);
	    	}
    		function changePicture(){
    			var pictureUrl = prompt("Please enter url for you new picture", "http://blog.divineaspect.com/wp-content/uploads/2015/10/I-AM-Magnificent-2.jpeg");
    			while(checkURL(pictureUrl)!=true)
    				pictureUrl = prompt("Please enter  a correct url for you new picture", "http://blog.divineaspect.com/wp-content/uploads/2015/10/I-AM-Magnificent-2.jpeg");
    			var xhttp = new XMLHttpRequest();
    			xhttp.open("POST", "PictureChangeServlet", true);
    			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    			xhttp.send("url="+pictureUrl);
    			
    			setTimeout(function() { document.location = "homepage.jsp";}, 2000);
    		}
    	
    	
    	</script>
        <div class="main-container"> 

			<!-- HEADER -->
            <header class="block">
                <ul class="header-menu horizontal-list">
                	<li>
                        <a class="header-menu-tab" href="QuizCreationHead.html"><span class="icon entypo-plus scnd-font-color"></span>New Quiz</a>
                    </li>
                    <li>
                        <a class="header-menu-tab" href="Settings.jsp"><span class="icon entypo-cog scnd-font-color"></span>Settings</a>
                    </li>
                    <li>
                        <a class="header-menu-tab" href="MyFriends.jsp"><span class="icon entypo-users scnd-font-color"></span>Friends</a>
                    </li>
                    <li>
                        <a class="header-menu-tab" href="Inbox.jsp"><span class="icon fontawesome-envelope scnd-font-color"></span>Messages</a>
                        <a class="header-menu-number" href="Inbox.jsp"><%= user.getNumOfUnreadMessages(connection, db) %></a>
                    </li>
                </ul>
                <div class="profile-menu">
                	<p>Log Out <a href="LogoutServlet"><span class="entypo-logout scnd-font-color"></span></a></p>
                	
                    <p>Me <a href="homepage.jsp"><span class="entypo-home scnd-font-color"></span></a></p>
                    
                    <div class="profile-picture small-profile-picture">
                        <img width="40px" height="40px" alt="user picture" src=<%= user.getProfilePictureLink(connection, db) %> >
                    </div>
                </div>
            </header>
			
			<header class="block" style="height:181px;">
				<div class="left-container container">
					<ul class="menu-box-menu">
					<%
						if (friend_submissions == null || 0 >= friend_submissions.size()) {
	                    	out.println("<li>");
	                        out.println("<a class=\"menu-box-tab\"><span class=\"icon entypo-check scnd-font-color\"></span>no new activities</a>");                            
	                        out.println("</li>");
	                    } else {
							for (int i = 0; i < Math.min(ServletConstants.ACTIVITY_LISTS_LIMIT, friend_submissions.size()); i++) {
								Touple<String, String, Integer> trio = friend_submissions.get(i);
								String label = trio.getFirst() + " - " + trio.getSecond() + " - " + trio.getThird();
		                        out.println("<li>");
		                        out.println("<a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "=" + trio.getSecond() +
		                       		"\"><span class=\"icon entypo-clock scnd-font-color\"></span>" + label + "</a>");                            
		                        out.println("</li>");
							}
	                    }
                    %>
					</ul>
				</div>
           
				<div class="middle-container container">
					<ul class="menu-box-menu">
                    <%
	                    if (friend_submissions == null || ServletConstants.ACTIVITY_LISTS_LIMIT >= friend_submissions.size()) {
	                    	out.println("<li>");
	                        out.println("<a class=\"menu-box-tab\"><span class=\"icon entypo-check scnd-font-color\"></span>no new activities</a>");                            
	                        out.println("</li>");
	                    } else {
							for (int i = ServletConstants.ACTIVITY_LISTS_LIMIT; i < Math.min(2 * ServletConstants.ACTIVITY_LISTS_LIMIT, friend_submissions.size()); i++) {
								Touple<String, String, Integer> trio = friend_submissions.get(i);
								String label = trio.getFirst() + " - " + trio.getSecond() + " - " + trio.getThird();
		                        out.println("<li>");
		                        out.println("<a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "=" + trio.getSecond() +
		                       		"\"><span class=\"icon entypo-clock scnd-font-color\"></span>" + label + "</a>");                            
		                        out.println("</li>");
							}
	                    }
                    %>
					</ul>
				</div>
           
				<div class="right-container container">
					<ul class="menu-box-menu">
                        <%
                        if (friend_submissions == null || 2 * ServletConstants.ACTIVITY_LISTS_LIMIT >= friend_submissions.size()) {
                        	out.println("<li>");
	                        out.println("<a class=\"menu-box-tab\"><span class=\"icon entypo-check scnd-font-color\"></span>no new activities</a>");                            
	                        out.println("</li>");
                        } else {
							for (int i = 2 * ServletConstants.ACTIVITY_LISTS_LIMIT; i < Math.min(3 * ServletConstants.ACTIVITY_LISTS_LIMIT, friend_submissions.size()); i++) {
								Touple<String, String, Integer> trio = friend_submissions.get(i);
								String label = trio.getFirst() + " - " + trio.getSecond() + " - " + trio.getThird();
		                        out.println("<li>");
		                        out.println("<a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "=" + trio.getSecond() +
		                       		"\"><span class=\"icon entypo-clock scnd-font-color\"></span>" + label + "</a>");                            
		                        out.println("</li>");
							}
                        }
                    %>
					</ul>
				</div>
           
           </header>

			
			 <script>
			 <%List<String> users = db.getAllUserNames(connection);%>
		     var all_users = [<% for (int i = 0; i < users.size(); i++) { %> 
		     			"<%= users.get(i) %>" <%= i + 1 < users.size() ? ",":"" %><% } %>];	 
			 $(document).ready(function() {
				    $("input#user_search").autocomplete({
				        width: 300,
				        max: 10,
				        delay: 100,
				        minLength: 1,
				        autoFocus: true,
				        cacheLength: 1,
				        scroll: true,
				        highlight: false,
				        source: all_users
				    });
				});
			 </script>

            <!-- LEFT-CONTAINER -->
            <div class="left-container container">
            	<form action="UserSearchServlet" method="get">
            		<input id = "user_search" type="text" name="search" placeholder="User search...">
            		<input type="submit" style="position: absolute; left: -9999px"/>
            	</form>
                <div class="menu-box block">
                    <h2 class="titular">Submissions</h2>
                    <ul class="menu-box-menu">
                                                 
	                    <div id="submissions">
	                    <%
	                    	List<Submission> f_list = user.getSubmissions(db, connection, ServletConstants.LISTS_LIMIT - 1);
	                   		if (f_list != null && f_list.size() > 0) {
		                    	ArrayList<Submission> submissions = new ArrayList<Submission>(f_list);
		                       	for (int i = 0; i < submissions.size(); i++) {
		                       		Submission currSub = submissions.get(i);
		                       		out.println("<li><a class=\"menu-box-tab\"><span class=\"icon entypo-check scnd-font-color\"></span>"
		                       				+ currSub.getQuiz().getName());
		                       		out.println("<div class=\"menu-box-number\">" + Database.percentage(currSub.getQuiz().getQuizScore(), currSub.getScore()) + "</div></a></li>");
		                       	}
	                   		}
	                    %>
	                    <li>
	                    	<a class="menu-box-tab" href="MySubmissions.jsp"><span class="icon entypo-dot-3 scnd-font-color"></span>See More</a>
	                    </li>
	                    </div>
                    </ul>
                </div><br>
                 <div class="menu-box block">
                    <h2 class="titular">My Quizzes</h2>
                    <ul class="menu-box-menu">                        
	                    <div id="created_quizzes">
	                    <%
	                    	List<QuizBase> list = user.getCreatedQuizzes(db, connection, ServletConstants.LISTS_LIMIT - 1);
	                    	if (list != null && list.size() > 0) {
		                       	ArrayList<QuizBase> quizzes = new ArrayList<QuizBase>(list);
		                       	for (int i = 0; i < quizzes.size(); i++) {
		                       		QuizBase currQuiz = quizzes.get(i);
		                       		out.println("<li><a class=\"menu-box-tab\" href=\"QuizSummary.jsp?" + ServletConstants.QUIZ_PARAMETER_NAME + "="
		                       			+ currQuiz.getName() +"\"><span class=\"icon entypo-paper-plane scnd-font-color\"></span>" + currQuiz.getName() + "</a></li>");
		                       	}
	                    	}
	                    %>
	                    <li>
	                    	<a class="menu-box-tab" href="MyQuizzes.jsp"><span class="icon entypo-dot-3 scnd-font-color"></span>See More</a>
	                    </li>
	                    </div>                  
                    </ul>
                </div>
               </div>

            <!-- MIDDLE-CONTAINER -->
            <div class="middle-container container">
                <div class="profile block">
                	<button value="NEW" onclick="changePicture()">change</button>
                    <div class="profile-picture big-profile-picture clear">
                        <img width="150px" height="150px" alt="picture" src=<%= user.getProfilePictureLink(connection, db) %> >
                    </div>
                    <h1 class="user-name"><%= user.getName() %></h1>
                    <div class="profile-description">
                        <p class="scnd-font-color"><%= user.getStatus(db, connection) %></p>
                    </div>
                    <form action="UserStatusServlet" method="get">
	                	<input type="text" name="update_description_field">
	                    <input class="button" type="submit" name="update_description_update" value="UPDATE">
                    </form>
                </div><br>
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

            <!-- RIGHT-CONTAINER -->
            <script>
            <%List<String> quizzes = db.getAllQuizNames(connection);%>
		     var all_quizzes = [<% for (int i = 0; i < quizzes.size(); i++) { %> 
		     			"<%= quizzes.get(i) %>" <%= i + 1 < quizzes.size() ? ",":"" %><% } %>];	 
			 $(document).ready(function() {
				    $("input#quiz_search").autocomplete({
				        width: 300,
				        max: 10,
				        delay: 100,
				        minLength: 1,
				        autoFocus: true,
				        cacheLength: 1,
				        scroll: true,
				        highlight: false,
				        source: all_quizzes
				    });
				});
            </script>
            <div class="right-container container">
            	<form action="QuizSearchServlet" method="get">
            		<input id = "quiz_search" type="text" name="search" placeholder="Quiz search...">
            		<input type="submit" style="position: absolute; left: -9999px"/>
            	</form>
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
                </div><br>
                    
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

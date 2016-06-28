package sourcePackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChallengeServlet
 */
@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String friend_name = request.getParameter("friend_name");
	
		Database base = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		Connection con = Factory_Database.getConnection();
		String quizName = request.getParameter("quiz_name");
		User curUser = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);	
		//friend not found in database
		if (base.getUser(friend_name, con)==null){
			request.setAttribute(ServletConstants.ERROR_MESSAGE, "the user named "+friend_name+" was not found");	
			request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
			return;
		}
		//not a friend
		if (!base.getFriendList(friend_name, con).contains(curUser.getName())){
			request.setAttribute(ServletConstants.ERROR_MESSAGE, "the user named "+friend_name+" is not your friend");	
			request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
			return;
		}
		String challenge = generateChallenge(friend_name, quizName, curUser.getName());
		
		Message challengeMessage = Factory_User.getNewMassege(friend_name, curUser.getName(), challenge);
		base.addMessage(challengeMessage, con);
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/QuizSummary.jsp?quizName="+quizName).forward(request, response);
		
		
	}
	
	//generates challenge string
	//which hot links to the quiz
	private String generateChallenge(String friendName,String quizName, String userName){
		String res = "";
		res+="Dear "+friendName+",\n"
			+"Tis I, the super ultimate friend of yours - " + userName+"\n"
			+"I have come upon an indeed marvelous quiz to participate in\n"
			+"<a href = '/QuizWebsite/QuizSummary.jsp?quizName="  +quizName+"'>"+quizName+"</a>";
		return res;
	}
	
	
	
	

}

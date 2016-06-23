package sourcePackage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class StartQuestionCreation
 */
@WebServlet("/StartQuestionCreation")
public class StartQuestionCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartQuestionCreation() { super(); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String quizname = request.getParameter("quiz_name"), description = request.getParameter("description"),
				timelimit = request.getParameter("time_limit");
		
		if (quizname == null || quizname.length() <= 0 ||
				!((Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME)).quizNameAvailable(quizname, Factory_Database.getConnection())
				|| description == null || description.length() <= 0 || timelimit == null || timelimit.length() <= 0) {
			request.getRequestDispatcher("QuizCreationHead.html").forward(request, response);
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute(ServletConstants.CREATING_QUIZ_NAME, quizname);
		session.setAttribute(ServletConstants.CREATING_QUIZ_DESCRIPTION, description);
		session.setAttribute(ServletConstants.CREATING_QUIZ_TIME_LIMIT, timelimit);
		session.setAttribute(ServletConstants.CREATING_QUIZ_SHUFFLE_OPTION, (request.getParameter("shuffle_check") == null) ? false : true);
		session.setAttribute(ServletConstants.CREATING_QUIZ_SINGLEPAGE_OPTION, (request.getParameter("singlepage_check") == null) ? false : true);
		
		if (session.getAttribute(SessionListener.USER_IN_SESSION) == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			try {
	    		out.println("<style>body {background: #1f253d;} h1{color:#fff;} </style><h1>Redirecting to Login page...</h1>");
	    		out.println("<script> setTimeout(function() { document.location = \"login.html\";}, 3000);	</script>");
			} finally {
				out.close();
			}
    	} else {
    		request.getRequestDispatcher("QuestionCreation.html").forward(request, response);
    	}
	}

}

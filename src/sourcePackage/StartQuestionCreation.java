package sourcePackage;

import java.io.IOException;
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
		HttpSession session = request.getSession();
		session.setAttribute(ServletConstants.CREATING_QUIZ_NAME, request.getParameter("quiz_name"));
		session.setAttribute(ServletConstants.CREATING_QUIZ_DESCRIPTION, request.getParameter("description"));
		session.setAttribute(ServletConstants.CREATING_QUIZ_TIME_LIMIT, request.getParameter("time_limit"));
		session.setAttribute(ServletConstants.CREATING_QUIZ_SHUFFLE_OPTION, (request.getParameter("shuffle_check") == null) ? true : false);
		session.setAttribute(ServletConstants.CREATING_QUIZ_SINGLEPAGE_OPTION, (request.getParameter("singlepage_check") == null) ? true : false);
		
		request.getRequestDispatcher("QuestionCreation.html").forward(request, response);
	}

}

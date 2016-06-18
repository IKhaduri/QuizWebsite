package sourcePackage;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuestionCreationServlet
 */
@WebServlet("/QuestionCreationServlet")
public class QuestionCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionCreationServlet() { super(); }

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
		User user = (User) session.getAttribute(SessionListener.USER_IN_SESSION);
		
		@SuppressWarnings("unchecked")
		ArrayList<QuestionAbstract> questions = (ArrayList<QuestionAbstract>) session.getAttribute(ServletConstants.CREATED_QUESTIONS);
		if (questions == null)
			questions = new ArrayList<QuestionAbstract>();
		
		String question_text = request.getParameter("question_area");
		String[] question_answers = request.getParameterValues("answer(s)");
		String question_type = request.getParameter("radio");
		
		if (question_type.equals("Multiple-choice")) {
			
		} else if (question_type.equals("Picture-response")) {
			
		} else {
			
		}
	}

}

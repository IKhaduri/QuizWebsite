package sourcePackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
		
		@SuppressWarnings("unchecked")
		ArrayList<QuestionAbstract> questions = (ArrayList<QuestionAbstract>) session.getAttribute(ServletConstants.CREATED_QUESTIONS);
		if (questions == null) {
			questions = new ArrayList<QuestionAbstract>();
			session.setAttribute(ServletConstants.CREATED_QUESTIONS, questions);
		}
		
		String question_text = request.getParameter("question_area");
		ArrayList<String> question_answers = new ArrayList<>(Arrays.asList(request.getParameterValues("answer(s)")));
		String question_type = request.getParameter("radio");
		QuestionAbstract result;
		
		if (question_type.equals("Multiple-choice")) {
			result = new MultipleChoiceQuestion(question_text, question_answers);
		} else if (question_type.equals("Picture-response")) {
			String picture_link = request.getParameter("picture_link");
			result = new PictureResponseQuestion(question_text, picture_link, question_answers);
		} else {
			result = new TextResponseQuestion(question_text, question_answers);
		}
		
		questions.add(result);
		
		checkForFinish(request, response);
	}

	private void checkForFinish(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("finish") == null) {
			request.getRequestDispatcher("QuestionCreation.html");
		} else {
			HttpSession session = request.getSession();
			
			/** parameters of the quiz we are to add in database */
			String name = (String) session.getAttribute(ServletConstants.CREATING_QUIZ_NAME);
			String author = ((User) session.getAttribute(SessionListener.USER_IN_SESSION)).getName();
			String description = (String) session.getAttribute(ServletConstants.CREATING_QUIZ_DESCRIPTION);
			boolean shuffle = (boolean) session.getAttribute(ServletConstants.CREATING_QUIZ_SHUFFLE_OPTION);
			// TODO
			boolean isSinglePage = false;
			int timeLimit = (int) session.getAttribute(ServletConstants.CREATING_QUIZ_TIME_LIMIT);
			@SuppressWarnings("unchecked")
			ArrayList<QuestionAbstract> questions = (ArrayList<QuestionAbstract>) session.getAttribute(ServletConstants.CREATED_QUESTIONS);
			
			Quiz newQuiz = Factory_Quiz.getNewQuiz(name, author, description, shuffle, timeLimit, isSinglePage, questions);
		
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
		}
	}

}

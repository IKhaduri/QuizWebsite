package sourcePackage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StartQuiz
 */
@WebServlet("/StartQuiz")
public class StartQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartQuiz() {
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
		String quizName = (String)request.getAttribute(ServletConstants.QUIZ_PARAMETER_NAME);
		Database base =(Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		Quiz quiz = base.getQuiz(quizName, Factory_Database.getConnection());
		request.setAttribute(ServletConstants.QUIZ_QUESTION_LIST, quiz.getQuestions());
		request.setAttribute(ServletConstants.QUIZ_QUESTION_INDEX, 0);
		RequestDispatcher dispatcher = request.getRequestDispatcher("QuizPage.jsp");
		dispatcher.forward(request, response);
	}

}

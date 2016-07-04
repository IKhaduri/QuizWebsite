package sourcePackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String quizName = (String)request.getAttribute(ServletConstants.QUIZ_PARAMETER_NAME);
		Database base =(Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		Connection connection = Factory_Database.getConnection();
		Quiz quiz = base.getQuiz(quizName, connection);
		Factory_Database.closeConnection(connection);
		
		request.setAttribute(ServletConstants.QUIZ_QUESTION_LIST, quiz.getQuestions());
		request.setAttribute(ServletConstants.QUIZ_QUESTION_INDEX, 0);
		request.getSession().setAttribute(ServletConstants.QUIZ_START_TIME, new Timestamp(new Date().getTime()));
		RequestDispatcher dispatcher = request.getRequestDispatcher("QuizPage.jsp");
		dispatcher.forward(request, response);
	}

}

package sourcePackage;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NextQuestion
 */
@WebServlet("/NextQuestion")
public class NextQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NextQuestion() {
        super();
        
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
		
		int curQuestionNum = Integer.parseInt(request.getParameter(ServletConstants.QUIZ_QUESTION_NUMBER));
		//TODO add a score, correct one
		int curScore = (Integer)request.getAttribute(ServletConstants.CURRENT_SCORE);
		ArrayList<Question> questions = (ArrayList<Question>) request.getAttribute(ServletConstants.QUIZ_QUESTION_LIST);
		Question curQuestion = questions.get(curQuestionNum);
		switch(curQuestion.getQuestionType()){
			case FILL_BLANK: case TEXT_RESPONSE: case PICTURE_RESPONSE:{
				String userResponse = request.getParameter("answer");
				if (curQuestion.getAnswers().contains(userResponse)){
					curScore++;
					break;
				}
				break;	
			}
			case MULTIPLE_CHOICE:{
				String correctAnswer = request.getParameter(ServletConstants.HIDDEN_CORRECT_ANSWER);
				String userAnswer = request.getParameter("answer");
				if (correctAnswer.equals(userAnswer))
					curScore++;
				break;
			}
		}
		curQuestionNum++;
		request.setAttribute(ServletConstants.QUIZ_QUESTION_NUMBER, curQuestionNum);
		request.setAttribute(ServletConstants.CURRENT_SCORE, curScore);
		RequestDispatcher dispatch = request.getRequestDispatcher("quizPage.jsp");
		dispatch.forward(request, response);
	}

}

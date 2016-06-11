package sourcePackage;

import java.io.IOException;
import java.util.ArrayList;

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
		int curScore = 0;
		ArrayList<Question> questions = (ArrayList<Question>) request.getAttribute(ServletConstants.QUIZ_QUESTION_LIST);
		Question curQuestion = questions.get(curQuestionNum);
		curQuestionNum++;
		request.setAttribute(ServletConstants.QUIZ_QUESTION_NUMBER, curQuestionNum);
		switch(curQuestion.getQuestionType()){
			case FILL_BLANK: case TEXT_RESPONSE:{
				String userResponse = request.getParameter("answer");
				if (curQuestion.getAnswers().contains(userResponse)){
					curScore++;
					break;
				}
				break;	
			}
			case MULTIPLE_CHOICE:{
				
			}
			case PICTURE_RESPONSE:{
				
			}
		
		}
		doGet(request, response);
	}

}

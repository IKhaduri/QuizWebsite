package sourcePackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
		doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int curQuestionNum = Integer.parseInt((String)request.getSession().getAttribute(ServletConstants.QUIZ_QUESTION_NUMBER));
		int curScore = (Integer.parseInt((String)request.getSession().getAttribute(ServletConstants.CURRENT_SCORE)));
		ArrayList<QuestionAbstract> questions = (ArrayList<QuestionAbstract>) request.getSession().getAttribute(ServletConstants.QUIZ_QUESTION_LIST);
		QuestionAbstract curQuestion = questions.get(curQuestionNum);
		String userResponse = request.getParameter("answer");
		if (curQuestion.isCorrectAnswer(userResponse))
			curScore++;
		curQuestionNum++;
		Connection con =null;
		int minutesLeft = Integer.parseInt(request.getParameter("minutesLeft"));
		int secondsLeft = Integer.parseInt(request.getParameter("secondsLeft"));
		request.setAttribute("minutesLeft", minutesLeft);
		request.setAttribute("secondsLeft", secondsLeft);	
		if (questions.size() == curQuestionNum||minutesLeft+secondsLeft<=0){
			PrintWriter out = null;
			try{
				String userName =((User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION)).getName();
				out = response.getWriter();
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Results</title>");
				out.println("<link rel=\"stylesheet\" href=\"css/quiz_finished_style.css\">");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Congratulations! You Have Successfully Finished The Quiz!</h1>");
				out.println("<h2>Your Score Is :"+ curScore+ "</h2>");
				out.println("<h3>Maximum Score On this Quiz Was: "+questions.size()+"</h3>");
				if (questions.size()*5/6<=curScore)
					out.println("<h1>So, YOU DID AN AWESOME JOB! KEEP THAT UP!</h1>");
				out.println("<a href = 'homepage.jsp' value = 'Back To Home'>Back to Home</a>");
				out.println("</body>");
				out.println("</html>");
				Database base = (Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
				String quizName = (String)request.getSession().getAttribute(ServletConstants.QUIZ_PARAMETER_NAME);
				con = Factory_Database.getConnection();
				base.logSubmission(quizName, userName, curScore, questions.size(), con);
				request.getSession().setAttribute(ServletConstants.QUIZ_STARTED, false);
			}catch(Throwable e){
				e.printStackTrace();
			}
			finally{
				out.close();
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return;
		}
		request.getSession().setAttribute(ServletConstants.QUIZ_QUESTION_NUMBER, curQuestionNum+"");
		request.getSession().setAttribute(ServletConstants.CURRENT_SCORE, curScore+"");
		RequestDispatcher dispatch = request.getRequestDispatcher("QuizPage.jsp");
		dispatch.forward(request, response);
	}

}

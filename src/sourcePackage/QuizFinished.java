package sourcePackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizFinished
 */
@WebServlet("/QuizFinished")
public class QuizFinished extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizFinished() {
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
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<QuestionAbstract> questionList = (ArrayList<QuestionAbstract>)request.getSession().getAttribute(ServletConstants.QUIZ_QUESTION_SHUFFLED_LIST); 
		int curScore = 0;
		for (int i=0; i<questionList.size();i++){
			String answer = request.getParameter("answer"+i);
			QuestionAbstract question = questionList.get(i);
			if (question.isCorrectAnswer(answer))
				curScore++;
		}
		Connection con =null;
			PrintWriter out = null;
			try{
				String userName =((User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION)).getName();
				out = response.getWriter();
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Congratulations! You Have Successfully Finished The Quiz!</h1>");
				out.println("<h2>Your Score Is :"+ curScore+ "</h2>");
				out.println("<h3>Maximum Score On this Quiz Was:"+questionList.size()+"</h3>");
				if (questionList.size()*5/6<=curScore)
					out.println("<h1>So, YOU DID AN AWESOME JOB! KEEP THAT UP!</h1>");
				out.println("<a href = 'userpage.jsp?"+userName+"' value = 'Back To Home'>Back to Home</a>");
				out.println("</body>");
				out.println("</html>");
				Database base = (Database) getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
				String quizName = (String)request.getSession().getAttribute(ServletConstants.QUIZ_PARAMETER_NAME);
				con = Factory_Database.getConnection();
				base.logSubmission(quizName, userName, curScore, con);
				request.getSession().setAttribute(ServletConstants.QUIZ_STARTED, false);
			}catch(Throwable e){
				e.printStackTrace();
			}
			finally{
				out.close();
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		
	}

}

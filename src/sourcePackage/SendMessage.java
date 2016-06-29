package sourcePackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/SendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
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
		Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		User user = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
		String receiver = request.getParameter("dest");
		String msg_text = request.getParameter("message_text");
		
		Message msg = Factory_User.getMessage(receiver, user.getName(), msg_text, new Timestamp(new Date().getTime()), false);
		Connection connection = Factory_Database.getConnection();
		db.addMessage(msg, connection);
		
		Factory_Database.closeConnection(connection);
		request.getRequestDispatcher("Inbox.jsp").forward(request, response);
	}

}

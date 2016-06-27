package sourcePackage;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetFriend
 */
@WebServlet("/FriendRequest")
public class FriendRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequest() {
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
		String me = ((User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION)).getName();
		Connection connection = Factory_Database.getConnection();
		String second_user = request.getParameter("username");
		String action = request.getParameter("action");
		
		if (request.getParameter("first_button") == null || action.equals("Unfriend") || action.equals("Cancel Request")) {
			db.unfriend(me, second_user, connection);
		} else if (action.equals("Add Friend")) {
			db.addFriends(me, second_user, false, connection);
		} else {
			db.addFriends(me, second_user, true, connection);
		}
		
		request.getRequestDispatcher("userpage.jsp?username=" + second_user).forward(request, response);
	}

}

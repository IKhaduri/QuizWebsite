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
		User me = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
		Connection connection = Factory_Database.getConnection();
		String second_user = request.getParameter("username");
		
		if (db.areFriends(me.getName(), second_user, true, connection) || db.areFriends(me.getName(), second_user, false, connection)) {
			db.unfriend(me.getName(), second_user, connection);
		} else {
    		db.addFriends(me.getName(), second_user, false, connection);
		}
		
		request.getRequestDispatcher("userpage.jsp?username=" + second_user).forward(request, response);
	}

}

package sourcePackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class MyFriendsServlet
 */
@WebServlet("/MyFriendsServlet")
public class MyFriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyFriendsServlet() {
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
		Database base = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		Connection con = Factory_Database.getConnection();
		JSONObject obj = new JSONObject();
		String userName = ((User)request.getSession().getAttribute(SessionListener.USER_IN_SESSION)).getName();
		int from = Integer.parseInt(request.getParameter("from"));
		int to = Integer.parseInt(request.getParameter("to"));
		List<String> friends = base.getFriendList(userName, from, to, con);
		for (int i=0;i<friends.size();i++){
			String friend = friends.get(i);
			obj.put("friend_name"+i, friend);
		}
		obj.put("size", friends.size());
		Factory_Database.closeConnection(con);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.flush();
	}
}

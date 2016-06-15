package sourcePackage;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SettingsServlet
 */
@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SettingsServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database base = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		User currentUser = (User) request.getSession().getAttribute(SessionListener.USER_IN_SESSION);
		Connection connection = Factory.getConnection();
		String newPasswordHash = request.getParameter("new_password");
		String repeated = request.getParameter("repeat_password");
		
		if (newPasswordHash.equals(repeated)) {
			currentUser.setPasswordHash(newPasswordHash, base, connection);
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("Settings.jsp").forward(request, response);
		}
		
		Factory.closeConnection(connection);
	}

}

package sourcePackage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	// we could just ignore this method for this assign.
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = db.getUser(username, Hasher.hash(username, password), Factory_Database.getConnection());
		if (user == null) {
			request.getRequestDispatcher("login.html").forward(request, response);
		} else {
			
			request.getSession().setAttribute(SessionListener.USER_IN_SESSION, user);
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
		}
			
	}

}

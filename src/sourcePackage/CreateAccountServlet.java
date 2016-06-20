package sourcePackage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public CreateAccountServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!request.getParameter("new_password").equals(request.getParameter("repeat_password")))	// passwords don't match
			request.getRequestDispatcher("login.html");
		
		String username = request.getParameter("new_username");
		String password = request.getParameter("new_password");
		User user = Factory_User.getUser(username, Hasher.hash(username, password));
		Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		request.getSession().setAttribute(SessionListener.USER_IN_SESSION, user);
		
		if (db.addUser(user, Factory_Database.getConnection()))
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
		else
			request.getRequestDispatcher("not_registered.html").forward(request, response); 	// just to see it directs
	}

}

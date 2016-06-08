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
			return;
		User user = Factory.getUser(request.getParameter("new_username"), request.getParameter("new_password"));
		Database db = (Database) request.getServletContext().getAttribute(ContextInitializer.DATABASE_ATTRIBUTE_NAME);
		
		if (db.addUser(user, Factory.getConnection()))
			request.getRequestDispatcher("homepage.html").forward(request, response);
		// else password or username already used
		
			
	}

}

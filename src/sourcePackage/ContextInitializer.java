package sourcePackage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextInitializer implements ServletContextListener {
	
	public static final String DATABASE_ATTRIBUTE_NAME = "db_attribute_for_context";

	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute(DATABASE_ATTRIBUTE_NAME, Factory_Database.getDBObject());
	}
	
	
}

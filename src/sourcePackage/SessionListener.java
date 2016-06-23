package sourcePackage;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

	public static final String USER_IN_SESSION = "userinsession";
	
	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
         User currentUser = null;
         arg0.getSession().setAttribute(USER_IN_SESSION, currentUser);
         arg0.getSession().setAttribute(ServletConstants.QUIZ_STARTED, false);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  {}
	
}

package sourcePackage;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {
	
	Database base;
	
	@Before
	public void setUp() {
		base = Factory_Database.getDBObject();
	}
	
	@Test
	public void testAddUser() {
		User user = Factory_User.getUser("Some dude", "PASSWORD_HASH_THING");
		Connection connection = Factory_Database.getConnection();
		assertTrue(connection != null);
		assertTrue(base.addUser(user, connection));
		assertFalse(base.addUser(user, connection));
		Factory_Database.closeConnection(connection);
	}
	
	@Test
	public void popularQuizzesTest() {
		Connection connection = Factory_Database.getConnection();
		assertEquals(base.getPopularQuizzes(0, connection), null);
		assertEquals(base.getPopularQuizzes(5, null), null);
		assertTrue(base.getPopularQuizzes(3, connection) != null);
		Factory_Database.closeConnection(connection);
	}
	
	
}



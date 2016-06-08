package sourcePackage;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {
	
	Database base;
	
	@Before
	public void setUp() {
		base = Factory.getDBObject();
	}
	
	@Test
	public void testAddUser() {
		User user = Factory.getUser("Some dude", "PASSWORD_HASH_THING");
		Connection connection = Factory.getConnection();
		assertTrue(connection != null);
		assertTrue(base.addUser(user, connection));
		assertFalse(base.addUser(user, connection));
		Factory.closeConnection(connection);
	}
	
	@Test
	public void popularQuizzesTest() {
		assertEquals(base.getPopularQuizzes(0, Factory.getConnection()), null);
		assertEquals(base.getPopularQuizzes(5, null), null);
		assertTrue(base.getPopularQuizzes(3, Factory.getConnection()) != null);
	}
	
	
}



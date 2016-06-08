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
}

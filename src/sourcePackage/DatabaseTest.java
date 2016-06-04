package sourcePackage;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

public class DatabaseTest {
	
	@Test
	public void testAddUser() {
		Database base = Factory.getDBObject();
		User user = Factory.getUser("Some dude", "PASSWORD_HASH_THING");
		Connection connection = Factory.getConnection();
		assertTrue(connection != null);
		assertTrue(base.addUser(user, connection));
		assertFalse(base.addUser(user, connection));
		Factory.closeConnection(connection);
	}
}

package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DBConnectionTest {

	DBConnection dbc = new DBConnection();
	
	@Test
	public void testGetIsConnected() {
		dbc.connectToDB();
		assertTrue("Conxiunea e realizata cu succes.", dbc.getIsConnected());
	}

}

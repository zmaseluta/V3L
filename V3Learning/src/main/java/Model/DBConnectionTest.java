package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DBConnectionTest {

	DBConnection dbc = new DBConnection();
	
	@Test
	public void testGetIsConnected() {
		dbc.connectToDB();
		assertTrue("Conxiunea s-a realizat cu succes", dbc.getIsConnected());
	}

}

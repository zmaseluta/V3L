package Model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DBOperationsTest {

	
	@Test
	public void testLogin() {
		DBConnection dbc = new DBConnection();
		dbc.connectToDB();
		DBOperations dbo = new DBOperations(dbc);
		User user = dbo.login("vm@mail.com", "mona");
		assertNotNull("Userul de la logare nu este null", user);
		assertEquals("Userul logat are id-ul dat", 5, user.getId());
	}

	@Test
	public void testSearchUsers() {
		boolean verif = false;
		DBConnection dbc = new DBConnection();
		dbc.connectToDB();
		DBOperations dbo = new DBOperations(dbc);
		User us = dbo.login("vm@mail.com", "mona");
		List<User> searchU = dbo.searchUsers("vi"); 
		for(int i=0; i < searchU.size(); i++) 
			if (searchU.get(i).getId() == us.getId())
				{
				verif = true;
				break;
				}
		assertTrue("Userul de la logare sa fie in lista rezultata la cautare", verif);
		
	}

	@Test
	public void testSearchDomains() {
		boolean verif = false;
		DBConnection dbc = new DBConnection();
		dbc.connectToDB();
		DBOperations dbo = new DBOperations(dbc);
		List<Domain> searchD = dbo.searchDomain("Cooking"); 
		List<Domain> allD = dbo.getAllDomains();
		for(int i=0; i < allD.size(); i++) 
			if (searchD.get(0).getId() == allD.get(i).getId())
				{
				verif = true;
				break;
				}
		assertTrue("Domeniul cautat sa fie in lista tuturor domeniilor", verif);
		
	}

}

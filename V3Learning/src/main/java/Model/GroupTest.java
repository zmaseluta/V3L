package Model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class GroupTest {

	@Test
	public void testLoggedUserIsCreatorOfGroupsFromSearchedDomain() { // getCreatorId()
		boolean verif = false;
		DBConnection dbc = new DBConnection();
		dbc.connectToDB();
		DBOperations dbo = new DBOperations(dbc);
		User loggedUser = dbo.login("ana@test.eu", "testana");//("vm@mail.com", "mona");
		List<Domain> searchDom = dbo.searchDomain("Survival"); // ("Cooking");
		List<Group> listGroupsByDomain = dbo.getAllGroupsInDomain(searchDom.get(0));
		for(int i=0; i < listGroupsByDomain.size(); i++) 
			if (listGroupsByDomain.get(i).getCreatorId() == loggedUser.getId())
				{
				verif = true;
				break;
				}
		assertTrue("User-ul logat este creatorul unui grup din domeniul dat", verif);
		//assertNotNull("Lista de grupuri nu e nula", listGroupsByDomain);
	}

}

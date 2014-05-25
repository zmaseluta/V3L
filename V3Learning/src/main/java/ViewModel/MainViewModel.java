package ViewModel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import Model.DBConnection;
import Model.DBOperations;
import Model.User;

public class MainViewModel {
	@Init
	public void init() {
		/*
		 * login for developing purpose
		 */
		// TODO delete this section when done with tests
		System.out.println("login...");
		DBConnection dbc = new DBConnection();
		dbc.connectToDB();
		System.out.println(dbc.getIsConnected());

		DBOperations dbo = new DBOperations(dbc);

		// admin -> right to create group
		// User user = dbo.login("ana@test.eu", "testana");

		User user = dbo.login("vm@mail.com", "mona");

		// System.out.println(dbc.closeConnection());

		if (user != null) {
			Sessions.getCurrent().setAttribute("user", user);
			Executions.sendRedirect("home.zul");
		}

		
//		 User user = (User) Sessions.getCurrent().getAttribute("user"); if
//		 (user != null) { Executions.sendRedirect("home.zul"); }
		 
	}
}

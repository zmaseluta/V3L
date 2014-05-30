package ViewModel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import Model.DBConnection;
import Model.DBOperations;
import Model.User;

public class OthersVM {

	private User visitedUser;
	@Init
	public void init(@QueryParam("us") int usersIds) {
		// -------------------------------------------
		// TODO Diana -> this is not safe!, only for demo purpose
		
		
		DBOperations dbo = (DBOperations) Sessions.getCurrent().getAttribute(
				"dbOperations");
		if(dbo ==null){
			DBConnection dbc = new DBConnection();
			dbc.connectToDB();
			dbo = new DBOperations(dbc);
		}
		for (User us : dbo.getAllUsers()) {
			if(us.getId()==usersIds)
			{
				visitedUser = us;
				visitedUser.computeUserLists();
				return;
			}
		}
		// -------------------------------------------
	}
	public User getVisitedUser() {
		return visitedUser;
	}
	public void setVisitedUser(User visitedUser) {
		this.visitedUser = visitedUser;
	}

	
	@Command
	public void Logout(){
		Sessions.getCurrent().getAttributes().clear();
		Executions.sendRedirect("~/V3L/login.zul");
	}
}

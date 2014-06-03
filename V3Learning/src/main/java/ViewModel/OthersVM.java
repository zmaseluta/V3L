package ViewModel;

import org.zkoss.bind.annotation.BindingParam;
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
	private User currentUser;
	@Init
	public void init(@QueryParam("us") int usersIds) {
		// -------------------------------------------
		// TODO Diana -> this is not safe!, only for demo purpose
		
		currentUser = (User) Sessions.getCurrent().getAttribute("user");
		currentUser.computeUserLists();
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
	public void goToUser(@BindingParam("visitUser")User user){
		Executions.sendRedirect("otherprofile.zul?us="
				+ user.getId());
	}
	
	@Command
	public void addFriend(@BindingParam("visitUser")User user){
		currentUser.addFriend(user);
		Executions.sendRedirect("otherprofile.zul?us="
				+ user.getId());
	}
	public User getCurrentUser() {
		return currentUser;
	}
	
}

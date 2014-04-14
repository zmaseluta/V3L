package ViewModel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Sessions;

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

}

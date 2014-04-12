package ViewModel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import Model.User;

public class HomeVM {
	private User user;

	@Init
	public void init() {
		user = (User) Sessions.getCurrent().getAttribute("user");
		if (user == null) {
			Executions.sendRedirect("index.zul");
		} else {
			user.computeUserLists();
		}

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

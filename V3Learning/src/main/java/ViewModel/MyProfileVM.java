package ViewModel;

import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;

import Model.User;

public class MyProfileVM {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		setUser((User) Sessions.getCurrent().getAttribute("user"));
	}
}

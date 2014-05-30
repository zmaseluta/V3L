package ViewModel;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import Model.Group;
import Model.User;

public class HomeVM {
	private User user;

	@Init
	public void init() {
		user = (User) Sessions.getCurrent().getAttribute("user");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Command
	public void goToUser(@BindingParam("visitUser")User user){
		Executions.sendRedirect("otherprofile.zul?us="
				+ user.getId());
	}
	
	@Command
	public void openGroupPage(@BindingParam("group") Group group) {
		Sessions.getCurrent().setAttribute("currentGroup", group);
		Executions.sendRedirect("group/home.zul");
	}
	@Command
	public void Logout(){
		Sessions.getCurrent().getAttributes().clear();
		Executions.sendRedirect("~/V3L/login.zul");
	}
}

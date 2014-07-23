package ViewModel.Groups;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import Model.Event;
import Model.Group;
import Model.User;

public class GroupPageVM {
	private Group group;
	private User user;

	@Init
	public void init(@QueryParam("gr") int groupId) {
		user = (User) Sessions.getCurrent().getAttribute("user");
		group = (Group) Sessions.getCurrent().getAttribute("currentGroup");

		if (group == null) {
			Executions.sendRedirect("../home.zul");
		} 
	}

	
	@Command
	public void goToUser(@BindingParam("visitUser")User user){
		Executions.sendRedirect("otherprofile.zul?us="
				+ user.getId());
	}
	
	@Command
	public void openGroupPage(@BindingParam("group") Group group) {
		Sessions.getCurrent().setAttribute("currentGroup", group);
		Executions.sendRedirect("group/home.zul?gr="+group.getId());
	}
	
	
	@Command
	public void openEventPage(@BindingParam("event") Event event) {
		Executions.sendRedirect("event.zul");
	}
	
	@Command
	@NotifyChange("user")
	public void addFriend(@BindingParam("visitUser")User usr){
		user.addFriend(usr);
		Executions.sendRedirect("home.zul");
	}
	
	@Command
	@NotifyChange("user")
	public void joinGroup(@BindingParam("group")Group group){
		user.addGroup(group);
		Executions.sendRedirect("home.zul?us="
				+ user.getId());
	}
	
	@Command
	public void Logout(){
		Sessions.getCurrent().getAttributes().clear();
		Executions.sendRedirect("index.zul");
	}


	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
}

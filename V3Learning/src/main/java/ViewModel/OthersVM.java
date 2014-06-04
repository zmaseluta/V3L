package ViewModel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import Model.DBConnection;
import Model.DBOperations;
import Model.Skill;
import Model.User;

public class OthersVM {

	private User visitedUser;
	private User currentUser;
	private Map<String,Double> skills;
	private Map<String, Map<String, Double>> domains;
	
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
		domains = new LinkedHashMap<String, Map<String, Double>>();
		
		for(Skill skill : visitedUser.getSkills()){
			domains.put(skill.getDomainName(), new LinkedHashMap<String, Double>());
		}
		
		for(Skill skill : visitedUser.getSkills()){
			skills = domains.get(skill.getDomainName());
			skills.put(skill.getName(), 1.0/visitedUser.getSkills().size());
			domains.put(skill.getDomainName(), skills);
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
	
	
	@Command
	public void Logout(){
		Sessions.getCurrent().getAttributes().clear();
		Executions.sendRedirect("~/V3L/login.zul");
	}
}

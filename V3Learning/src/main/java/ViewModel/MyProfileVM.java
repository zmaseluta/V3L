package ViewModel;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import Model.DBConnection;
import Model.DBOperations;
import Model.Domain;
import Model.Skill;
import Model.User;

public class MyProfileVM {
	private User user;
	private List<Skill> skills;
	private List<Domain> domains;
	private Domain selectedDomain;
	private Skill selectedSkill;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public List<Skill> getFilteredSkills() {
		if (selectedDomain == null)
			return getSkills();
		List<Skill> aux = new ArrayList<Skill>();
		for (Skill skill : skills) {
			if (skill.getDomainId() == selectedDomain.getId())
				aux.add(skill);
		}
		return aux;
	}

	public Domain getSelectedDomain() {
		return selectedDomain;
	}

	@NotifyChange("filteredSkills")
	public void setSelectedDomain(Domain selectedDomain) {
		this.selectedDomain = selectedDomain;
	}

	public Skill getSelectedSkill() {
		return selectedSkill;
	}

	public void setSelectedSkill(Skill selectedSkill) {
		this.selectedSkill = selectedSkill;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		User usera = (User) Sessions.getCurrent().getAttribute("user");
		if (usera == null) {
			Executions.sendRedirect("index.zul");
		} else {
			setUser(usera);
			skills = user.getSkills();
			user.computeUserLists();
			
			System.out.println("register...");
			DBConnection dbc = new DBConnection();
			dbc.connectToDB();
			System.out.println(dbc.getIsConnected());

			DBOperations dbo = new DBOperations(dbc);
			domains = dbo.getAllDomains();
			skills = dbo.getAllSkills();
			
		}
	}

	@Command
	@NotifyChange("user")
	public void saveChanges() {
		user.update();
	}

	@Command
	@NotifyChange("user")
	public void addSkill() {
		user.addSkill(selectedSkill);
		user.update();
	}

	@Command
	public void openWindow() {
		Window window = (Window) Executions.createComponents(
				"/components/new_group.zul", null, null);
		window.doModal();
	}
}

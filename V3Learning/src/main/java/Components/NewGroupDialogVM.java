package Components;

import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import Model.User;
import Model.Domain;

public class NewGroupDialogVM {

	private User currentUser;
	private List<Domain> domains;

	private Domain domain=null;
	private String name="";
	private String description="";
	private String message="";
	@Wire("#modalDialog")
	Window modalDialog;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		User user = (User) Sessions.getCurrent().getAttribute("user");
		if (user == null) {
			Executions.sendRedirect("index.zul");
		} else {
			currentUser = user;
			setDomains((List<Domain>) Sessions.getCurrent().getAttribute(
					"domainList"));
			System.out.println(domains);
		}
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripstion() {
		return description;
	}

	public void setDescripstion(String descripstion) {
		this.description = descripstion;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Command
	public void closeThis() {
		modalDialog.detach();
	}

	@Command
	@NotifyChange({"message","currentUser"})
	public void save() {
		if (name.length() < 3 || domain == null || description.length() < 3) {
			setMessage("Please fill all the fields!");
			return;
		}
		if (currentUser.createGroup(name, domain.getId(), description) == 1) {
			modalDialog.detach();
		} else {
			setMessage("Please try a different name!");
			return;
		}

	}
}
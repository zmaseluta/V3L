package Components;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import Model.User;

public class NewGroupDialogController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	User currentUser;
	
	@Init
	public void init() {
		User user = (User) Sessions.getCurrent().getAttribute("user");
		if (user != null) {
			Executions.sendRedirect("home.zul");
		}
		else {
			currentUser = user;
		}
	}
	
	@Wire
	Window modalDialog;

	@Listen("onClick = #closeBtn")
	public void showModal(Event e) {
		modalDialog.detach();
	}
}
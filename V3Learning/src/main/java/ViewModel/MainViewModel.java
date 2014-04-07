package ViewModel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import Model.User;

public class MainViewModel {
	@Init
	public void init() {
		User user = (User) Sessions.getCurrent().getAttribute("user");
		if (user != null) {
			Executions.sendRedirect("home.zul");
		}
	}
}

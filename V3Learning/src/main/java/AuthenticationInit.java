import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import Model.User;

public class AuthenticationInit implements Initiator {

	public void doInit(Page page, Map<String, Object> args) throws Exception {

		User usera = (User) Sessions.getCurrent().getAttribute("user");
		if (usera == null) {
			Executions.sendRedirect("~/V3Learning/login.zul");
		}
	}
}
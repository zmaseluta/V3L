package ViewModel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import Model.DBConnection;
import Model.DBOperations;
import Model.User;

public class LogInVM {

	private String emailadress;
	private String password;
	
	public String getEmailadress() {
		return emailadress;
	}
	public void setEmailadress(String emailadress) {
		this.emailadress = emailadress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	@Command("logIn")
	public void logIn(){
		System.out.println("login...");
		DBConnection dbc = new DBConnection();
		dbc.connectToDB();
		System.out.println(dbc.getIsConnected());

		DBOperations dbo = new DBOperations(dbc);
		User user;
		user = dbo.login(emailadress, password);
		System.out.println(dbc.closeConnection());
		if(user!=null){
		Sessions.getCurrent().setAttribute("user", user);
		Executions.sendRedirect("myprofile.zul");}
	}
}

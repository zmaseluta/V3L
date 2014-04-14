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
		//System.out.println(dbc.closeConnection());
		if(user!=null){
		Sessions.getCurrent().setAttribute("user", user);
		
		//-------------------------------------------
		//TODO Diana -> this is not safe!, only for demo purpose
		Sessions.getCurrent().setAttribute("dbConnection", dbc);
		Sessions.getCurrent().setAttribute("dbOperations", dbo);
		//-------------------------------------------
		
		Executions.sendRedirect("myprofile.zul");
		}
	}

	/*public void signinCallback(authResult) {
		  if (authResult['status']['signed_in']) {
		    // Update the app to reflect a signed in user
		    // Hide the sign-in button now that the user is authorized, for example:
		    document.getElementById('signinButton').setAttribute('style', 'display: none');
		  } else {
		    // Update the app to reflect a signed out user
		    // Possible error values:
		    //   "user_signed_out" - User is signed-out
		    //   "access_denied" - User denied access to your app
		    //   "immediate_failed" - Could not automatically log in the user
		    console.log('Sign-in state: ' + authResult['error']);
		  }
		}
*/
}

package ViewModel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import Model.DBConnection;
import Model.DBOperations;

public class RegisterVM {

	private String name;
	private String email;
	private String password1;
	private String password2;

	@Init
	public void init() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Command("register")
	public void register() {
		System.out.println("register...");
		DBConnection dbc = new DBConnection();
		dbc.connectToDB();
		System.out.println(dbc.getIsConnected());

		DBOperations dbo = new DBOperations(dbc);
		int val;
		//TODO add firstName lastName to UI
		val = dbo.registerUser(email, password1, name, "");
		System.out.println(val);
		System.out.println(dbc.closeConnection());
	}

}

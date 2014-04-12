package ViewModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import Model.DBConnection;
import Model.DBOperations;

public class RegisterVM {

	private String firstName = "";
	private String lastName = "";

	private String email = "";
	private String password = "";
	private String retypedPassword = "";
	private Date birthDate = new Date(1234);

	private String message = "";

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
		return password;
	}

	public void setPassword1(String password1) {
		this.password = password1;
	}

	public String getPassword2() {
		return retypedPassword;
	}

	public void setPassword2(String password2) {
		this.retypedPassword = password2;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypedPassword() {
		return retypedPassword;
	}

	public void setRetypedPassword(String retypedPassword) {
		this.retypedPassword = retypedPassword;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Command("register")
	@NotifyChange("message")
	public void register() {
		if (firstName.length() < 3) {
			setMessage("Please enter a valid First Name!");
			return;
		}
		if (lastName.length() < 3) {
			setMessage("Please enter a valid Last Name!");
			return;
		}

		if (!password.equals(retypedPassword) || password.length() < 6) {
			setMessage("Your retyped password does not match!");
			return;
		}

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(emailreg);
		Matcher m = p.matcher(email);

		if (!m.find()) {
			setMessage("Invalid email!");
			return;
		}

		System.out.println("register...");
		DBConnection dbc = new DBConnection();
		dbc.connectToDB();
		System.out.println(dbc.getIsConnected());

		DBOperations dbo = new DBOperations(dbc);
		int val;

		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		val = dbo.registerUser(email, password, firstName, lastName,
				formatter.format(birthDate));

		if (val == 0) {
			setMessage("Could not create the user");
			return;
		}
		if (val == -1) {
			setMessage("A User with the same email adress already exists!");
			return;
		}
		if (val == 1) {
			setMessage("Your new account has been created!");
			return;
		}
		System.out.println(dbc.closeConnection());
	}

}

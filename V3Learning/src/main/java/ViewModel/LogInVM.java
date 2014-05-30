package ViewModel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Charsets;
import com.google.common.io.CharStreams;

import Model.DBConnection;
import Model.DBOperations;
import Model.User;

public class LogInVM {

	private static final HttpTransport TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final String CLIENT_ID = "87237935165-ct20udmnvvbvgm6krkv1sk0gi6ll2n05.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "D7UurwYg84ToKMNH4chsrc30";
	private final GoogleAuthHelper helper = new GoogleAuthHelper();
	
	private String emailadress;
	private String password;
	private String loginUri;
	
	@Init
	public void Init(){
		setLoginUri(helper.buildLoginUrl());
	}
	
	
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
		
		
		Executions.sendRedirect("home.zul");
		}
	}
	
	@Command("googleLogIn")
	public void googleLogIn() throws IOException{
		/*Session s= org.zkoss.zk.ui.Sessions.getCurrent();
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		InputStream inputStream = request.getInputStream();
		System.out.println(request+"/n inputstream" + inputStream);
	    String code = request.getRequestURL().toString()+"/plus.login";//new String(getContent(inputStream).toByteArray(), "UTF-8");;
		//System.out.println(request.getRequestURI());
	 	 GoogleTokenResponse tokenResponse =
		          new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
		              CLIENT_ID, CLIENT_SECRET, code, "postmessage").execute();
		      // Create a credential representation of the token data.
		      GoogleCredential credential = new GoogleCredential.Builder()
		          .setJsonFactory(JSON_FACTORY)
		          .setTransport(TRANSPORT)
		          .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
		          .setFromTokenResponse(tokenResponse);*/
		      /*
		      // Build credential from stored token data.
		      GoogleCredential credential = new GoogleCredential.Builder()
		          .setJsonFactory(JSON_FACTORY)
		          .setTransport(TRANSPORT)
		          .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
		          .setFromTokenResponse(JSON_FACTORY.fromString(
		              tokenData, GoogleTokenResponse.class));
		      // Create a new authorized API client.
		      Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
		          .setApplicationName(APPLICATION_NAME)
		          .build();
		      // Get a list of people that this user has shared with this app.
		      PeopleFeed people = service.people().list("me", "visible").execute();*/
		
		final GoogleAuthHelper helper = new GoogleAuthHelper();
		
	}
	
	 
	    static ByteArrayOutputStream getContent(InputStream inputStream)
	        throws IOException {
	      // Read the response into a buffered stream 
	    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    	 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	         int readChar;
	         while ((readChar = reader.read()) != -1) {
	           outputStream.write(readChar);
	         }
	         reader.close();
	      return outputStream;
	    }


		public String getLoginUri() {
			return loginUri;
		}


		public void setLoginUri(String loginUri) {
			this.loginUri = loginUri;
		}
	  
}

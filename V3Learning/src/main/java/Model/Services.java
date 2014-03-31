package Model;

import java.sql.*;

public class Services {
	
	//private static String user3 = "emergent_tzaran";
	//private static String pass3 = "capracu3iezi";
	//static String dbDriver3 = "jdbc:mysql://emergentzone.com:3306/emergent_v3l";
	
	private static String user = "echipab6";
	private static String pass = "echipab6";
	private static String dbDriver = "jdbc:mysql://db4free.net:3306/virtual3l";
	
/*	private static String user2 = "root";
	private static String pass2 = "";
	private static String dbDriver2 = "jdbc:mysql://localhost/v3l";*/
	
	private static String dbClass = "com.mysql.jdbc.Driver";
	private static Connection conn = null;
	
	public static int registerUser (String email, String pass, String firstname, String lastname) {
		int done = 0;
	    //load driver
	    try {
	        Class.forName(dbClass).newInstance();
	        System.out.println("driver loaded"); // THIS IS BEING RETURNED
	    } catch (Exception ex) {
	        System.err.println(ex);
	    }
	    // Connection
	    try {
	        conn = DriverManager.getConnection(dbDriver, user, pass);
	        //conn = DriverManager.getConnection(dbDriver3, user3, pass3);
	        System.out.println("connected");
	        
	        // search if user exists
	        PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT email FROM v3l.user;");
	        ResultSet data = statement.executeQuery();
	        boolean exists = false;
	        while(data.next()){
	        	if (email.equals(data.getObject("email"))) {
	        		System.out.println("user exists");
	        		exists  = true;
	        	}
	        }
	        
	        if(exists){
	        	done = -1;
	        } else {
	        	//user doesn't exist, insert user in database
	        	statement = (PreparedStatement) conn.prepareStatement("INSERT INTO v3l.user (id_user, last_name, first_name, email, password, birthday_date, rank, is_valid, is_public, account_type) VALUES (NULL, ?, ?, ?, ?, NULL, '0', '0', '1', '2');");
		        statement.setString(1 , lastname);
		        statement.setString(2 , firstname);
		        statement.setString(3 , email);
		        statement.setString(4 , pass);
		        statement.executeUpdate();
		        done = 1;
	        }
	        
	        statement.close();
	        conn.close();
	        
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        done = 0;
	    }
	    return done;
	}
	
	public static int sendEmailVerf (String email) {
		// TODO
		return 0;
	}
	
	public static User getUser (String email) {
		
		User u = null;
		
		try {
	        Class.forName(dbClass).newInstance();
	        System.out.println("driver loaded"); // THIS IS BEING RETURNED
	    } catch (Exception ex) {
	        System.err.println(ex);
	    }
	    // Connection
	    try {
	        conn = DriverManager.getConnection(dbDriver, user, pass);
	        //conn = DriverManager.getConnection(dbDriver3, user3, pass3);
	        System.out.println("connected");
	        
	        // search if user exists
	        PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT * FROM v3l.user WHERE email = ?;");
	        statement.setString(1 , email);
	        ResultSet data = statement.executeQuery();
	        
	        if (data.next()){
	        	int id = data.getInt("id_user");
		    	String lastName = data.getString("last_name");
		    	String firstName = data.getString("first_name");
		    	String password = data.getString("password");
		    	//System.out.println(data.getString("birthday_date"));
		    	String birthDate = data.getString("birthday_date");
		    	
		    	int rank = data.getInt("rank");
		    	int isValid = data.getInt("is_valid");
		    	int isPublic = data.getInt("is_public");
		    	String type = data.getString("account_type");
		    	
		    	u = new User(id, lastName, firstName, email, password, birthDate, rank, isValid, isPublic, type);
	        } 
	       
	    	statement.close();
	        conn.close();
	        
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	    }
	    	
		return u;
	}
	
	public static Skill getSkill(String name) {
		Skill s = null;
		
		try {
	        Class.forName(dbClass).newInstance();
	        System.out.println("driver loaded"); // THIS IS BEING RETURNED
	    } catch (Exception ex) {
	        System.err.println(ex);
	    }
	    // Connection
	    try {
	        conn = DriverManager.getConnection(dbDriver, user, pass);
	        //conn = DriverManager.getConnection(dbDriver3, user3, pass3);
	        System.out.println("connected");
	        
	        // search if user exists
	        PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT * FROM v3l.skills WHERE name = ?;");
	        statement.setString(1 , name);
	        ResultSet data = statement.executeQuery();
	        
	        if (data.next()){
		    	s = new Skill(name);
		    	s.id = data.getInt("id_skill");
	        	s.domainId = data.getInt("id_domain");
	        } 
	       
	    	statement.close();
	        conn.close();
	        
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	    }
	    	
		return s;
	}
	
	/*
	public static User[] getAllUsers () {
		// TODO
		return null;
	}
	
	public static Skill[] getAllSkills () {
		// TODO
		return null;
	}
	
	public static Domain[] getAllDomains () {
		// TODO
		Domain[] domainList = null;
	    //load driver
	    try {
	        Class.forName(dbClass).newInstance();
	        System.out.println("driver loaded"); // THIS IS BEING RETURNED
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
	        System.err.println(ex);
	    }
	    // Connection
	    try {
	    	conn = DriverManager.getConnection(dbDriver2, user2, pass2);
	        //conn = DriverManager.getConnection(dbDriver3, user3, pass3);
	        System.out.println("connected");
	        
	        // search if domain exists
	        PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT * FROM v3l.domain;");
	        ResultSet data = statement.executeQuery();
	        
	        Domain domain = new Domain("");
	        int rowcount = 0;
	        
	        if (data.last()) {
	        	  rowcount = data.getRow();
	        	  data.beforeFirst();
	        }
	        
	        domainList = new Domain[rowcount];
	        int i = 0;
	        
	        while(data.next()){
	        	domain.name = data.getString("name");
	        	domain.id = data.getInt("id_domain");
	        	domainList[i].id = data.getInt("id_domain");
	        	domainList[i].name = data.getString("name");
	        	i++;
	        	//System.out.println(domain.id + " " + domain.name);
	        	//System.out.println(data.getInt("id_domain") + " " + data.getString("name"));
	        }
	        
	        statement.close();
	        conn.close();
	        
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	    }
		return domainList;
		return null;
	}*/

	
}

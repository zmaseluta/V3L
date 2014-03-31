package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBOperations {
	private static final String EMAIL_STATEMENT2 = "SELECT email FROM User WHERE email = ?";
	private static final String EMAIL_STATEMENT = "SELECT * FROM User "
			+ "WHERE email = ? AND password = ?;";
	private static final String USER_STATEMENT = "INSERT INTO User "
			+ "(id_user, last_name, first_name, email, password, birthday_date, "
			+ "rank, is_valid, is_public, account_type) "
			+ "VALUES (NULL, ?, ?, ?, ?, NULL, '0', '0', '1', '2');";
	private static final String SKILL_STATEMENT = "SELECT * FROM Skills WHERE name = ?;";
	private static final String DOMAIN_STATEMENT = "SELECT * FROM Domain WHERE name = ?;";
	private static final String ALL_SKILLS_STATEMENT = "SELECT * FROM Skills;";
	private static final String ALL_DOMAINS_STATEMENT = "SELECT * FROM Domain;";
	private static final String SELECT_USER = "SELECT * FROM User;";
	
	private DBConnection dbConnection;
	private Connection connection;

	public DBOperations(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
		connection = dbConnection.getConnection();
	}
	
	/**
	 * if DB not connected or other error returns 0
	 * if user (user email) already exists in DB returns -1
	 * if user added successfully in DB returns 1
	*/
	public int registerUser(String email, String pass, String firstname, String lastname) {
		int done = 0;
		if (dbConnection.getIsConnected()) {
			try {
				PreparedStatement statement = 
						(PreparedStatement) connection.prepareStatement(EMAIL_STATEMENT2);
				statement.setString(1, email);
				ResultSet data = statement.executeQuery();
		        if (data.next()) {
		        	System.out.println("user exists");
		        	done = -1;
		        } else {
		        	statement = (PreparedStatement) connection.prepareStatement(USER_STATEMENT);
			        statement.setString(1 , lastname);
			        statement.setString(2 , firstname);
			        statement.setString(3 , email);
			        statement.setString(4 , pass);
			        statement.executeUpdate();
			        done = 1;
		        }
		        statement.close();
			} catch (SQLException ex) {
		        System.err.println("SQLException: " + ex.getMessage());
		        done = 0;
		    }
		} else {
			System.err.println("not connected");
		}
		return done;
	}
	
	/**
	 * returns user with matching email and password from DB
	 * returns null if unsuccessful
	*/
	public User login(String email, String password) {		
		User u = null;	
	    try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(EMAIL_STATEMENT);
	        statement.setString(1 , email);
	        statement.setString(2 , password);
	        ResultSet data = statement.executeQuery();     
	        if (data.next()){
	        	int id = data.getInt("id_user");
		    	String lastName = data.getString("last_name");
		    	String firstName = data.getString("first_name");
		    	String birthDate = data.getString("birthday_date");
		    	int rank = data.getInt("rank");
		    	int isValid = data.getInt("is_valid");
		    	int isPublic = data.getInt("is_public");
		    	String type = data.getString("account_type");
		    	if (isValid != 0){
		    		u = new User(dbConnection, id, lastName, firstName, email, password, 
		    				birthDate, rank, isValid, isPublic, type);
		    	} else {
		    		System.out.println("user not validated");
		    	}
	        } else {
	        	System.out.println("user and password don't match");
	        }
	    	statement.close();	        
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	    }
		return u;
	}
	
	/**
	 * returns skill with matching name from DB
	 * returns null if unsuccessful
	*/
	public Skill getSkill(String name) {
		Skill s = null;
		try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(SKILL_STATEMENT);
	        statement.setString(1, name);
	        ResultSet data = statement.executeQuery(); 
	        if (data.next()) {
		    	s = new Skill(dbConnection, name);
		    	s.setDomainId(data.getInt("id_domain"));
	        	s.setId(data.getInt("id_skill"));
	        } else {
	        	System.out.println("skill doesn't exist");
	        }
	    	statement.close();	        
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	    }    	
		return s;
	}
	
	/**
	 * returns domain with matching name from DB
	 * returns null if unsuccessful
	*/
	public Domain getDomain(String name) {
		Domain d = null;
		try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(DOMAIN_STATEMENT);
	        statement.setString(1, name);
	        ResultSet data = statement.executeQuery(); 
	        if (data.next()) {
		    	d = new Domain(dbConnection, name);
	        	d.setId(data.getInt("id_domain"));
	        } else {
	        	System.out.println("domain doesn't exist");
	        }
	    	statement.close();	        
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	    }    	
		return d;
	}
	
	/**
	 * returns list with all users in DB
	 * returns null if unsuccessful
	*/
	public List<User> getAllUsers () {
		List<User> userList = new ArrayList<User>();
	    try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(SELECT_USER);
	        ResultSet data = statement.executeQuery();
	        
	        while(data.next()){
	        	int id = data.getInt("id_user");
		    	String lastName = data.getString("last_name");
		    	String firstName = data.getString("first_name");
		    	String email = data.getString("email");
		    	String password = data.getString("password");
		    	String birthDate = data.getString("birthday_date");	
		    	int rank = data.getInt("rank");
		    	int isValid = data.getInt("is_valid");
		    	int isPublic = data.getInt("is_public");
		    	String type = data.getString("account_type");
		    	
		    	userList.add(new User(dbConnection, id, lastName, firstName, email, 
		    			password, birthDate, rank, isValid, isPublic, type));
	        }    
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return userList;
	}
	
	/**
	 * returns list with all skills in DB
	 * returns null if unsuccessful
	*/
	public List<Skill> getAllSkills () {
        List<Skill> skillList = new ArrayList<Skill>();
	    try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(ALL_SKILLS_STATEMENT);
	        ResultSet data = statement.executeQuery();
	        
	        while(data.next()) {
	        	Skill skill = new Skill(dbConnection, data.getString("name"));
	        	skill.setDomainId(data.getInt("id_domain"));
	        	skill.setId(data.getInt("id_skill"));
	        	skillList.add(skill);
	        }	        
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return skillList;
	}
	
	/**
	 * returns list with all domains in DB
	 * returns null if unsuccessful
	*/
	public List<Domain> getAllDomains() {
		List<Domain> domainList = new ArrayList<Domain>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(ALL_DOMAINS_STATEMENT);
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	Domain domain = new Domain(dbConnection, data.getString("name"));
	        	domain.setId(data.getInt("id_domain"));
	        	domainList.add(domain);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return domainList;
	}
}

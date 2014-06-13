package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DBOperations {
	private static final String EMAIL_STATEMENT2 = "SELECT email FROM User WHERE email = ?";
	private static final String EMAIL_STATEMENT = "SELECT * FROM User "
			+ "WHERE email = ? AND password = ?;";
	private static final String USER_STATEMENT = "INSERT INTO User "
			+ "(id_user, last_name, first_name, email, password, birthday_date, "
			+ "rank, is_valid, is_public, account_type) "
			+ "VALUES (NULL, ?, ?, ?, ?, ?, '0', '1', '1', '2');";
	private static final String SKILL_STATEMENT = "SELECT * FROM Skills WHERE name = ?;";
	private static final String DOMAIN_STATEMENT = "SELECT * FROM Domain WHERE name = ?;";
	private static final String ALL_SKILLS_STATEMENT = "SELECT * FROM Skills;";
	private static final String ALL_DOMAINS_STATEMENT = "SELECT * FROM Domain;";
	private static final String SELECT_USER = "SELECT * FROM User;";
	private static final String ALL_GROUPS_STATEMENT = "SELECT * FROM Groups WHERE id_domain = ?;";
	private static final String ALL_EVENTS_IN_GROUP = "SELECT * FROM Events WHERE id_group = ? ORDER BY date DESC;";
	private static final String ALL_FILES_IN_GROUP = "SELECT * FROM Files WHERE id_group = ?;";
	private static final String ALL_VIDEOS_IN_GROUP = "SELECT * FROM Videos WHERE id_group = ?;";
	private static final String SELECT_USER_IN_GROUP = "SELECT * FROM User WHERE id_user IN "
			+ "(SELECT id_user FROM UsGroups WHERE id_group = ?);";
	private static final String SELECT_USER_WITH_SKILL = "SELECT * FROM User WHERE id_user IN "
			+ "(SELECT id_user FROM UsSkills WHERE id_skill = ?);";
	private static final String USER_ID_STATEMENT = "SELECT * FROM User WHERE id_user = ?;";
	
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
	public int registerUser(String email, String pass, String firstname, String lastname, String bdate) {
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
			        statement.setString(5 , bdate);
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

	/**
	 * returns list with all groups in DB
	 * returns null if unsuccessful
	*/
	public List<Group> getAllGroupsInDomain(Domain d) {
		List<Group> groupList = new ArrayList<Group>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(ALL_GROUPS_STATEMENT);
	    	statement.setString(1, Integer.toString(d.getId()));
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	Group group = new Group(dbConnection, data.getInt("id_group"), 
	        			data.getString("name"), data.getInt("id_domain"), 
	        			data.getInt("id_creator"), data.getString("description"));
	        	groupList.add(group);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return groupList;
	}
	
	/**
	 * returns list with all events in group from DB
	 * returns null if unsuccessful
	 */
	public List<Event> getAllEventsInGroup(Group g) {
		List<Event> eventList = new ArrayList<Event>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(ALL_EVENTS_IN_GROUP);
	    	statement.setString(1, Integer.toString(g.getId()));
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	Event event = new Event(dbConnection, data.getInt("id_event"), 
	        			data.getString("name"), data.getInt("id_creator"), 
	        			data.getInt("id_group"), data.getString("date"), 
	        			data.getString("description"));
	        	eventList.add(event);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return eventList;
	}
	
	/**
	 * returns list with all files in group from DB
	 * returns null if unsuccessful
	 */
	public List<File> getAllFilesInGroup(Group g) {
		List<File> fileList = new ArrayList<File>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(ALL_FILES_IN_GROUP);
	    	statement.setString(1, Integer.toString(g.getId()));
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	File file = new File(data.getString("url"), data.getString("name"), 
	        			data.getInt("id_user"), data.getInt("id_group"), 
	        			data.getString("description"));
	        	file.setId(data.getInt("id_file"));
	        	fileList.add(file);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return fileList;
	}
	
	/**
	 * returns list with all videos in group from DB
	 * returns null if unsuccessful
	 */
	public List<File> getAllVideosInGroup(Group g) {
		List<File> videoList = new ArrayList<File>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(ALL_VIDEOS_IN_GROUP);
	    	statement.setString(1, Integer.toString(g.getId()));
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	File video = new File(data.getString("url"), data.getString("name"), 
	        			data.getInt("id_user"), data.getInt("id_group"), 
	        			data.getString("description"));
	        	video.setId(data.getInt("id_video"));
	        	videoList.add(video);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return videoList;
	}
	
	/**
	 * returns list with all users with string words in first name, last name or email from DB
	 * returns null if unsuccessful
	 */
	public List<User> searchUsers (String words) {
		List<User> userList = new ArrayList<User>();
		StringTokenizer tokenizer = new StringTokenizer(words);
		String STATEMENT = "SELECT * FROM User WHERE (";
		String st = getStatementPartUsers(tokenizer.nextToken());
		STATEMENT += st;
		while (tokenizer.hasMoreTokens()) {
			STATEMENT += " AND ";
			st = getStatementPartUsers(tokenizer.nextToken());
			STATEMENT += st;
		}
		STATEMENT += ");";
		try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(STATEMENT);
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
	 * formats part of users search statement
	 * returns string
	 */
	private String getStatementPartUsers(String word) {
		return "((last_name LIKE '%" + word + "%') OR (first_name LIKE '%" + word + "%') "
				+ "OR (email LIKE '%" + word + "%'))";
	}

	/**
	 * returns list with all domains with string in name from DB
	 * returns null if unsuccessful
	 */
	public List<Domain> searchDomain (String words) {
		List<Domain> domainList = new ArrayList<Domain>();
		StringTokenizer tokenizer = new StringTokenizer(words);
		String STATEMENT = "SELECT * FROM Domain WHERE (";
		String st = getStatementPartDomainSkill(tokenizer.nextToken());
		STATEMENT += st;
		while (tokenizer.hasMoreTokens()) {
			STATEMENT += " AND ";
			st = getStatementPartDomainSkill(tokenizer.nextToken());
			STATEMENT += st;
		}
		STATEMENT += ");";
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(STATEMENT);
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
	
	/**
	 * formats part of domain/skill search statement
	 * returns string
	 */
	private String getStatementPartDomainSkill(String word) {
		return "(name LIKE '%" + word + "%')";
	}

	/**
	 * returns list with all skills with string in name from DB
	 * returns null if unsuccessful
	 */
	public List<Skill> searchSkill (String words) {
		List<Skill> skillList = new ArrayList<Skill>();
		StringTokenizer tokenizer = new StringTokenizer(words);
		String STATEMENT = "SELECT * FROM Skills WHERE (";
		String st = getStatementPartDomainSkill(tokenizer.nextToken());
		STATEMENT += st;
		while (tokenizer.hasMoreTokens()) {
			STATEMENT += " AND ";
			st = getStatementPartDomainSkill(tokenizer.nextToken());
			STATEMENT += st;
		}
		STATEMENT += ");";
		try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(STATEMENT);
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
	 * returns list with all users with skill in skill list from DB
	 * returns null if unsuccessful
	 */
	public List<User> getAllUsersWithSkill (Skill s) {
		List<User> userList = new ArrayList<User>();
	    try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(SELECT_USER_WITH_SKILL);
	        statement.setString(1, Integer.toString(s.getId()));
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
	 * returns list with all groups with string in name or description from DB
	 * returns null if unsuccessful
	 */
	public List<Group> searchGroup (String words) {
		List<Group> groupList = new ArrayList<Group>();
		StringTokenizer tokenizer = new StringTokenizer(words);
		String STATEMENT = "SELECT * FROM Groups WHERE (";
		String st = getStatementPartGrEvFiVi(tokenizer.nextToken());
		STATEMENT += st;
		while (tokenizer.hasMoreTokens()) {
			STATEMENT += " AND ";
			st = getStatementPartGrEvFiVi(tokenizer.nextToken());
			STATEMENT += st;
		}
		STATEMENT += ");";
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(STATEMENT);
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	Group group = new Group(dbConnection, data.getInt("id_group"), 
	        			data.getString("name"), data.getInt("id_domain"), 
	        			data.getInt("id_creator"), data.getString("description"));
	        	groupList.add(group);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return groupList;
	}
	
	/**
	 * formats part of group/event/file/video search statement
	 * returns string
	 */
	private String getStatementPartGrEvFiVi(String word) {
		return "((name LIKE '%" + word + "%') OR (description LIKE '%" + word + "%'))";
	}

	/**
	 * returns list with all users with group in group list from DB
	 * returns null if unsuccessful
	 */
	public List<User> getAllUsersInGroup (Group g) {
		List<User> userList = new ArrayList<User>();
	    try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(SELECT_USER_IN_GROUP);
	        statement.setString(1, Integer.toString(g.getId()));
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
	 * returns list with all events with string in name or description from DB
	 * returns null if unsuccessful
	 */
	public List<Event> searchEvent (String words) {
		List<Event> eventList = new ArrayList<Event>();
		StringTokenizer tokenizer = new StringTokenizer(words);
		String STATEMENT = "SELECT * FROM Events WHERE (";
		String st = getStatementPartGrEvFiVi(tokenizer.nextToken());
		STATEMENT += st;
		while (tokenizer.hasMoreTokens()) {
			STATEMENT += " AND ";
			st = getStatementPartGrEvFiVi(tokenizer.nextToken());
			STATEMENT += st;
		}
		STATEMENT += ");";
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(STATEMENT);
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	Event event = new Event(dbConnection, data.getInt("id_event"), 
	        			data.getString("name"), data.getInt("id_creator"), 
	        			data.getInt("id_group"), data.getString("date"), 
	        			data.getString("description"));
	        	eventList.add(event);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return eventList;
	}
	
	/**
	 * returns list with all files with string in name or description from DB
	 * returns null if unsuccessful
	 */
	public List<File> searchFile (String words) {
		List<File> fileList = new ArrayList<File>();
		StringTokenizer tokenizer = new StringTokenizer(words);
		String STATEMENT = "SELECT * FROM Files WHERE (";
		String st = getStatementPartGrEvFiVi(tokenizer.nextToken());
		STATEMENT += st;
		while (tokenizer.hasMoreTokens()) {
			STATEMENT += " AND ";
			st = getStatementPartGrEvFiVi(tokenizer.nextToken());
			STATEMENT += st;
		}
		STATEMENT += ");";
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(STATEMENT);
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	File file = new File(data.getString("url"), data.getString("name"), 
	        			data.getInt("id_user"), data.getInt("id_group"), 
	        			data.getString("description"));
	        	file.setId(data.getInt("id_file"));
	        	fileList.add(file);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return fileList;
	}
	
	/**
	 * returns list with all videos with string in name or description from DB
	 * returns null if unsuccessful
	 */
	public List<File> searchVideo (String words) {
		List<File> videoList = new ArrayList<File>();
		StringTokenizer tokenizer = new StringTokenizer(words);
		String STATEMENT = "SELECT * FROM Videos WHERE (";
		String st = getStatementPartGrEvFiVi(tokenizer.nextToken());
		STATEMENT += st;
		while (tokenizer.hasMoreTokens()) {
			STATEMENT += " AND ";
			st = getStatementPartGrEvFiVi(tokenizer.nextToken());
			STATEMENT += st;
		}
		STATEMENT += ");";
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(STATEMENT);
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	File file = new File(data.getString("url"), data.getString("name"), 
	        			data.getInt("id_user"), data.getInt("id_group"), 
	        			data.getString("description"));
	        	file.setId(data.getInt("id_video"));
	        	videoList.add(file);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return videoList;
	}
	
	public User getUser(int idU) {		
		User u = null;	
	    try {
	        PreparedStatement statement = 
	        		(PreparedStatement) connection.prepareStatement(USER_ID_STATEMENT);
	        statement.setString(1 , Integer.toString(idU));
	        ResultSet data = statement.executeQuery();     
	        if (data.next()){
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

}

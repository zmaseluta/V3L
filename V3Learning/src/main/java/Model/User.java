package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User {
	private static final String SELECT_RANK_USRANK = "SELECT rank FROM UsRank "
			+ "WHERE id_receiver = ?;";
	private static final String UPDATE_USER = "UPDATE User SET last_name = ?, first_name = ?, "
			+ "email = ?, password = ?, url_img = ?, birthday_date = ?, rank = ?, is_valid = ?, "
			+ "is_public = ?, account_type = ? WHERE User.id_user = ?;";
	private static final String IDRANK_USRANK = "SELECT id_rank FROM UsRank "
			+ "WHERE (id_receiver = ? AND id_giver = ?);";
	private static final String UPDATE_USRANK = "UPDATE UsRank SET rank = ? "
			+ "WHERE UsRank.id_rank = ?;";
	private static final String INSERT_USRANK = "INSERT INTO UsRank "
			+ "(id_rank, id_giver, id_receiver, rank) VALUES (NULL, ?, ?, ?);";
	private static final String IDSKILL_USSKILL = "SELECT id_skill FROM UsSkills "
			+ "WHERE (id_user = ? AND id_skill = ?);";
	private static final String INSERT_USSKILL = "INSERT INTO UsSkills "
			+ "(id_us_sk, id_skill, id_user) VALUES (NULL, ?, ?);";
	private static final String DELETE_USSKILL = "DELETE FROM UsSkills "
			+ "WHERE (id_user = ? AND id_skill = ?);";
	private static final String IDF_USFRIENDS = "SELECT id_friendship FROM UsFriends "
			+ "WHERE (id_user1 = ? AND id_user2 = ?) OR (id_user1 = ? AND id_user2 = ?);";
	private static final String INSERT_USFRIENDS = "INSERT INTO UsFriends "
			+ "(id_friendship, id_user1, id_user2) VALUES (NULL, ?, ?);";
	private static final String DELETE_USFRIENDS = "DELETE FROM UsFriends "
			+ "WHERE id_friendship = ?;";
	private static final String UPDATE_RANK_USER = "UPDATE User SET rank = ? "
			+ "WHERE id_user = ?;";
	private static final String SKILL_USER_USSKILL = "SELECT * FROM Skills "
			+ "WHERE id_skill IN (SELECT id_skill FROM UsSkills WHERE id_user = ?);";
	private static final String USER_FRIEND_USFRIEND = "SELECT * FROM User "
			+ "WHERE id_user IN (SELECT id_user1 FROM UsFriends WHERE id_user2 = ?) "
			+ "OR id_user IN (SELECT id_user2 FROM UsFriends WHERE id_user1 = ?);";
	private static final String SEARCH_USER_GROUP = "SELECT name FROM Groups "
			+ "WHERE (name = ? AND id_domain = ? AND id_creator = ?);";
	private static final String INSERT_GROUP = "INSERT INTO Groups "
			+ "(id_group, name, id_domain, id_creator, description) VALUES (NULL, ?, ?, ?, ?);";
	private static final String SEARCH_USER_GROUP_USGROUPS = "SELECT id_group FROM UsGroups "
			+ "WHERE (id_group = ? AND id_user = ?);";
	private static final String INSERT_GROUP_USGROUPS = "INSERT INTO UsGroups "
			+ "(id_us_gr, id_group, id_user) VALUES (NULL, ?, ?);";
	private static final String USER_GROUPS_STATEMENT = "SELECT * FROM Groups "
			+ "WHERE id_group IN (SELECT id_group FROM UsGroups WHERE id_user = ?);";
	private static final String USER_EVENTS_STATEMENT = "SELECT * FROM Events "
			+ "WHERE id_event IN (SELECT id_event FROM UsEvents WHERE id_user = ?);";
	private static final String USER_COURSE_STATEMENT = "SELECT * FROM Events "
			+ "WHERE id_creator = ?;";
	private static final String UPDATE_GROUP_SEARCH = "SELECT id_group FROM Groups "
			+ "WHERE (id_group = ? AND id_creator = ?);";
	private static final String UPDATE_GROUP = "UPDATE Groups SET name = ?, description = ? "
			+ "WHERE id_group = ?;";
	private static final String DELETE_GROUP_USGROUPS = "DELETE FROM UsGroups "
			+ "WHERE (id_group = ? AND id_user = ?);";
	private static final String SEARCH_USER_EVENT = "SELECT name FROM Events "
			+ "WHERE (name = ? AND id_group = ? AND id_creator = ?);";
	private static final String INSERT_EVENT = "INSERT INTO Events "
			+ "(id_event, name, id_creator, id_group, date, description) VALUES (NULL, ?, ?, ?, ?, ?);";
	private static final String SEARCH_USER_EVENT_USEVENTS = "SELECT id_event FROM UsEvents "
			+ "WHERE (id_user = ? AND id_event = ?);";
	private static final String INSERT_EVENT_USEVENTS = "INSERT INTO UsEvents "
			+ "(id_us_ev, id_user, id_event) VALUES (NULL, ?, ?);";
	private static final String UPDATE_EVENT_SEARCH = "SELECT id_event FROM Events "
			+ "WHERE (id_event = ? AND id_creator = ?);";
	private static final String UPDATE_EVENT = "UPDATE Events "
			+ "SET name = ?, date = ?, description = ? WHERE id_event = ?;";
	private static final String DELETE_EVENT_USEVENTS = "DELETE FROM UsEvents "
			+ "WHERE (id_event = ? AND id_user = ?);";
	private static final String SEARCH_USER_FILE = "SELECT id_file FROM Files "
			+ "WHERE (name = ? AND id_user = ? AND id_group = ?);";
	private static final String INSERT_FILE = "INSERT INTO Files "
			+ "(id_file, url, name, id_user, id_group, description) "
			+ "VALUES (NULL, ?, ?, ?, ?, ?);";
	private static final String DELETE_FILE = "DELETE FROM Files "
			+ "WHERE (id_file = ? AND id_user = ?);";
	private static final String SEARCH_USER_VIDEO = "SELECT id_video FROM Videos "
			+ "WHERE (name = ? AND id_user = ? AND id_group = ?);";
	private static final String INSERT_VIDEO = "INSERT INTO Videos "
			+ "(id_video, url, name, id_user, id_group, description) "
			+ "VALUES (NULL, ?, ?, ?, ?, ?);";
	private static final String DELETE_VIDEO = "DELETE FROM Videos "
			+ "WHERE (id_video = ? AND id_user = ?);";
	private static final String SELECT_ID_GROUP = "SELECT id_group FROM Groups "
			+ "WHERE (name = ? AND id_domain = ? AND id_creator = ?);";
	private static final String SUGGESTED_GROUPS = "SELECT * FROM Groups "
			+ "WHERE (id_domain = ? AND (id_group NOT IN "
			+ "(SELECT id_group FROM UsGroups WHERE id_user = ?)));";
	private static final String SUGGESTED_FRIENDS = "SELECT * FROM User "
			+ "WHERE (((id_user IN (SELECT id_user1 FROM UsFriends WHERE id_user2 = ?)) "
			+ "OR (id_user IN (SELECT id_user2 FROM UsFriends WHERE id_user1 = ?))) "
			+ "AND((id_user NOT IN (SELECT id_user1 FROM UsFriends WHERE id_user2 = ?)) "
			+ "OR (id_user NOT IN (SELECT id_user2 FROM UsFriends WHERE id_user1 = ?))));";
	private static final String INSERT_POST = "INSERT INTO Posts "
			+ "(id_post, id_user, id_event, data_post, content_post) "
			+ "VALUES (NULL, ?, ? , ?, ?);";
	private static final String INSERT_COMMENT = "INSERT INTO Comments "
			+ "(id_comment, id_user, id_post, data_comment, content_comment) "
			+ "VALUES (NULL, ?, ? , ?, ?);";
	private static final String SEARCH_USER_POST = "SELECT id_post FROM Posts"
			+ "WHERE (id_post = ? AND id_user = ?);";
	private static final String DELETE_POST = "DELETE FROM Posts "
			+ "WHERE (id_post = ? AND id_user = ?);";
	private static final String SEARCH_USER_COMMENT = "SELECT id_comment FROM Comments"
			+ "WHERE (id_comment = ? AND id_user = ?);";
	private static final String DELETE_COMMENT = "DELETE FROM Comments "
			+ "WHERE (id_comment = ? AND id_user = ?);";
	private static final String USER_PICTURE = "SELECT * FROM User WHERE id_user = ?;";
	
	private DBConnection dbConnection;;
	private Connection connection;
	
	private int id;
	private String lastName;
	private String firstName;
	private String email;
	private String password;
	private String birthDate;
	private int age;
	private float rank;
	private int rankNoUsers;
	private int isValid;
	private int isPublic;
	private int isActive; 
	private String type;
	private List<Skill> skills;
	private List<User> friends;
	private List<Group> groups;
	private List<Event> events;
	private List<Event> courses;
	private String picURL;
	
	/**
	 * id, email, rank, age, rankNoUsers, isValid, type, skills, friends, groups, events, courses
	 * have no setters, cannot be changed from outside
	 * 
	*/

	public User(DBConnection dbConnection, int id, String lastName, String firstName, 
			String email, String password, String birthDate, float rank, int isValid, 
			int isPublic, String type) {
		
		this.dbConnection = dbConnection;
		connection = dbConnection.getConnection();
		
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.age = computeAge();
		this.rank = rank;
		this.rankNoUsers = computeRankNoUsers();
		this.isValid = isValid;
		this.isPublic = isPublic;
		this.type = type;
	}	

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the birthDate as Date
	 * @throws ParseException 
	 */
	public Date getBirthDate() throws ParseException {
		if(birthDate !=null)
			return	new SimpleDateFormat("yyyy-M-dd").parse(birthDate);
		else
			return new Date(1234);
	}

	/**
	 * @param birthDate the birthDate to set
	 * computes user age
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = new SimpleDateFormat("yyyy-M-dd").format(birthDate).toString();
		age = computeAge();
	}

	/**
	 * @return the isPublic
	 */
	public int getIsPublic() {
		return isPublic;
	}

	/**
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * @return the isActive
	 */
	public int getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return the rank
	 */
	public float getRank() {
		return rank;
	}

	/**
	 * @return the rankNoUsers
	 */
	public int getRankNoUsers() {
		return rankNoUsers;
	}

	/**
	 * @return the isValid
	 */
	public int getIsValid() {
		return isValid;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the skills
	 */
	public List<Skill> getSkills() {
		return skills;
	}

	/**
	 * @return the friends
	 */
	public List<User> getFriends() {
		return friends;
	}

	/**
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * @return the events
	 */
	public List<Event> getEvents() {
		return events;
	}

	/**
	 * @return the courses
	 */
	public List<Event> getCourses() {
		return courses;
	}
	
	/**
	 * @return the picURL
	 */
	public String getPicURL() {
		return picURL;
	}
	
	/**
	 * @param picURL the picURL to set
	 */
	public void setPicURL(String picURL) {
		if (picURL!= null) this.picURL = picURL;
		else this.picURL = "http://www.strangehistory.net/blog/wp-content/uploads/2014/05/black-dog.jpg";
	}

	public String getGender() {
		return firstName.charAt(firstName.length() -1 ) == 'a'? "F" : "B" ;
	}
	/**
	 * computes age from birthDate
	 * returns age
	*/
	private int computeAge() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");
		int age = 0;
		try {
			Date date = formatter.parse(birthDate);
			long ageInMilis = new Date().getTime() - date.getTime() ;
			age = (int) (ageInMilis / (60L * 60 * 24 * 365 * 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return age;
	}

	/**
	 * computes the no. of users that ranked current user
	 * returns no.
	*/
	private int computeRankNoUsers() {
		int count = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SELECT_RANK_USRANK);
			statement.setString(1 , Integer.toString(id));
			ResultSet data = statement.executeQuery();
			while(data.next()){
				count++;
			}
			statement.close();
			return count;		    
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return -1;
		}
	}
	
	/**
	 * computes the rank for RECEIVER from ranking table in DB
	 * updates RECEIVER rank in user table in DB
	 * returns 1 if successful
	 * else returns 0 
	*/
	private int computeRank(int idReceiver) {
		int done = 0;
		float sum = 0;
		int count = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SELECT_RANK_USRANK);
			statement.setString(1 , Integer.toString(idReceiver));
			ResultSet data = statement.executeQuery();
			while(data.next()){
				sum += data.getInt("rank");
				count++;
			}
			sum /= count;
			if (count != 0) {
				statement = (PreparedStatement) connection.prepareStatement(UPDATE_RANK_USER);
				statement.setString(1 , Float.toString(sum));
				statement.setString(2 , Integer.toString(idReceiver));
				statement.executeUpdate();
			}
			statement.close();
			done = 1;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * returns skill list for current user
	 * returns null if unsuccessful
	*/
	private List<Skill> getSkillList() {
		List<Skill> skillList = new ArrayList<Skill>();
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SKILL_USER_USSKILL);
			statement.setString(1 , Integer.toString(id));
			ResultSet data = statement.executeQuery();
			while(data.next()){
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
	 * returns friend list for current user
	 * returns null if unsuccessful
	*/
	private List<User> getFriendList() {
		List<User> friendList = new ArrayList<User>();
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(USER_FRIEND_USFRIEND);
			statement.setString(1 , Integer.toString(id));
			statement.setString(2 , Integer.toString(id));
			ResultSet data = statement.executeQuery();
			while (data.next()) {
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
				friendList.add(new User(dbConnection, id, lastName, firstName, email, password, 
						birthDate, rank, isValid, isPublic, type));
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return null;
		}
		return friendList;
	}
	
	/**
	 * computes user lists & gets picURL from DB 	
	*/
	public void computeUserLists() {
		skills = getSkillList();
		friends = getFriendList();
		groups = getGroupList();
		events = getEventList();
		courses = getCourseList();
		picURL = getPictureURL();
	}
	
	/**
	 * returns picture URL from DB
	 */
	private String getPictureURL() {
		String url = "";
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(USER_PICTURE);
	    	statement.setString(1, Integer.toString(id));
	        ResultSet data = statement.executeQuery();
	        data.next();
	        url = data.getString("url_img");
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return url;
	}

	/**
	 * returns course list for current user
	 * returns null if unsuccessful
	*/
	private List<Event> getCourseList() {
		List<Event> courseList = new ArrayList<Event>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(USER_COURSE_STATEMENT);
	    	statement.setString(1, Integer.toString(id));
	        ResultSet data = statement.executeQuery();
	        while(data.next()){
	        	Event event = new Event(dbConnection, data.getInt("id_event"), 
	        			data.getString("name"), data.getInt("id_creator"), 
	        			data.getInt("id_group"), data.getString("date"), 
	        			data.getString("description"));
	        	courseList.add(event);
	        }      
	        statement.close();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return courseList;
	}

	/**
	 * returns event list for current user
	 * returns null if unsuccessful
	*/
	private List<Event> getEventList() {
		List<Event> eventList = new ArrayList<Event>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(USER_EVENTS_STATEMENT);
	    	statement.setString(1, Integer.toString(id));
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
	 * returns group list for current user
	 * returns null if unsuccessful
	*/
	private List<Group> getGroupList() {
		List<Group> groupList = new ArrayList<Group>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(USER_GROUPS_STATEMENT);
	    	statement.setString(1, Integer.toString(id));
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
	 * verifies if ranking exists
	 * if exists updates ranking with new grade
	 * else inserts ranking in DB
	 * returns result of computeRank(selectedUser.id); if successful
	 * else returns 0 
	*/
	public int rank(User selectedUser, int grade) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(IDRANK_USRANK);
			statement.setString(1 , Integer.toString(selectedUser.id));
			statement.setString(2 , Integer.toString(id));
			ResultSet data = statement.executeQuery();
			int exists = 0;
			if (data.next()){
					exists  = data.getInt("id_rank");
			}
			if (exists != 0) {
				statement = (PreparedStatement) connection.prepareStatement(UPDATE_USRANK);
				statement.setString(1 , Integer.toString(grade));
				statement.setString(2 , Integer.toString(exists));
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_USRANK);
				statement.setString(1 , Integer.toString(id));
				statement.setString(2 , Integer.toString(selectedUser.id));
				statement.setString(3 , Integer.toString(grade));
			}
			statement.executeUpdate();
			statement.close();
			done = computeRank(selectedUser.id);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * updates current user in DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int update() {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(UPDATE_USER);
			statement.setString(1 , lastName);
			statement.setString(2 , firstName);
			statement.setString(3 , email);
			statement.setString(4 , password);
			statement.setString(5 , picURL);
			statement.setString(6 , birthDate.toString());
			statement.setString(7 , Float.toString(rank));
			statement.setString(8 , Integer.toString(isValid));
			statement.setString(9 , Integer.toString(isPublic));
			statement.setString(10 , type);
			statement.setString(11 , Integer.toString(id));
			statement.executeUpdate();
			done = 1;
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}

	/**
	 * verifies if skill entry exists in user-skill table in DB
	 * returns -1 if skill entry exists
	 * else inserts skill entry in DB
	 * updates skills list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int addSkill(Skill s) {
		int done = 0;	
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(IDSKILL_USSKILL);
			statement.setString(1, Integer.toString(id));
			statement.setString(2, Integer.toString(s.getId()));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				System.out.println("user already has skill");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_USSKILL);
				statement.setString(1 , Integer.toString(s.getId()));
				statement.setString(2 , Integer.toString(id));
				statement.executeUpdate();
				skills = getSkillList();
				done = 1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if skill entry exists in user-skill table in DB
	 * returns -1 if skill entry doesn't exist
	 * else deletes skill entry from DB
	 * updates skills list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int removeSkill(Skill s) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(IDSKILL_USSKILL);
			statement.setString(1, Integer.toString(id));
			statement.setString(2, Integer.toString(s.getId()));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				statement = (PreparedStatement) connection.prepareStatement(DELETE_USSKILL);
				statement.setString(1 , Integer.toString(id));
				statement.setString(2 , Integer.toString(s.getId()));
				statement.executeUpdate();
				skills = getSkillList();
				done = 1;
			} else {
				System.out.println("skill doesn't exist");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if friendship exists in DB
	 * returns -1 if friendship exists
	 * else inserts friendship in DB
	 * updates friends list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int addFriend(User u) {
		int done = 0;
		if (this.equals(u)){
			System.out.println("same user");
			return done;
		}
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(IDF_USFRIENDS);
			statement.setString(1, Integer.toString(id));
			statement.setString(2, Integer.toString(u.id));
			statement.setString(3, Integer.toString(u.id));
			statement.setString(4, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				System.out.println("friendship exists");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_USFRIENDS);
				statement.setString(1 , Integer.toString(id));
				statement.setString(2 , Integer.toString(u.id));
				statement.executeUpdate();
				friends = getFriendList();
				done = 1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if friendship exists in DB
	 * returns -1 if friendship doesn't exist
	 * else deletes friendship in DB
	 * updates friends list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int removeFriend(User u) {
		int done = 0;
		if (this.equals(u)){
			System.out.println("same user");
			return done;
		}
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(IDF_USFRIENDS);
			statement.setString(1, Integer.toString(id));
			statement.setString(2, Integer.toString(u.id));
			statement.setString(3, Integer.toString(u.id));
			statement.setString(4, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				int exists  = data.getInt("id_friendship");
				statement = (PreparedStatement) connection.prepareStatement(DELETE_USFRIENDS);
				statement.setString(1 , Integer.toString(exists));
				statement.executeUpdate();
				//this.friends = getFriendList();
				done = 1;
			} else {
				System.out.println("friendship doesn't exist");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if group with user as creator exists in DB
	 * returns -1 if exists
	 * else inserts group in DB and adds group to user group list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int createGroup(String name, int domainId, String description) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_GROUP);
			statement.setString(1, name);
			statement.setString(2, Integer.toString(domainId));
			statement.setString(3, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				System.out.println("group already exists");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_GROUP);
				statement.setString(1, name);
				statement.setString(2, Integer.toString(domainId));
				statement.setString(3, Integer.toString(id));
				statement.setString(4, description);
				statement.executeUpdate();
				statement = (PreparedStatement) connection.prepareStatement(SELECT_ID_GROUP);
				statement.setString(1, name);
				statement.setString(2, Integer.toString(domainId));
				statement.setString(3, Integer.toString(id));
				data = statement.executeQuery();
				if (data.next()) {
					int groupId = data.getInt("id_group");
					statement = (PreparedStatement) 
							connection.prepareStatement(INSERT_GROUP_USGROUPS);
					statement.setString(1, Integer.toString(groupId));
					statement.setString(2, Integer.toString(id));
					statement.executeUpdate();
					groups = getGroupList();
					done = 1;
				} else {
					System.out.println("group not added to user groups");
					done = -1;
				}
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}

	/**
	 * verifies if user is member of group in table UsGroups in DB
	 * returns -1 if yes
	 * else inserts entry in table UsGroups in DB
	 * updates user group list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int addGroup(Group g) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_GROUP_USGROUPS);
			statement.setString(1, Integer.toString(g.getId()));
			statement.setString(2, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				System.out.println("user already exists in group");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_GROUP_USGROUPS);
				statement.setString(1, Integer.toString(g.getId()));
				statement.setString(2, Integer.toString(id));
				statement.executeUpdate();
				groups = getGroupList();
				done = 1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * !!!group can only be updated by creator
	 * verifies if user is creator of group and group exists in DB
	 * returns -1 if not
	 * else updates group in DB
	 * updates user group list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int updateGroup(Group g) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(UPDATE_GROUP_SEARCH);
			statement.setString(1, Integer.toString(g.getId()));
			statement.setString(2, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				statement = (PreparedStatement) connection.prepareStatement(UPDATE_GROUP);
				statement.setString(1, g.getName());
				statement.setString(2, g.getDescription());
				statement.setString(3, Integer.toString(g.getId()));
				statement.executeUpdate();
				groups = getGroupList();
				done = 1;
			} else {
				System.out.println("user not creator of group or invalid group");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if user if member of group in table UsGroups in DB
	 * returns -1 if not
	 * else deletes entry from table UsGroups in DB
	 * updates user group list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int removeGroup(Group g) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_GROUP_USGROUPS);
			statement.setString(1, Integer.toString(g.getId()));
			statement.setString(2, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				statement = (PreparedStatement) connection.prepareStatement(DELETE_GROUP_USGROUPS);
				statement.setString(1, Integer.toString(g.getId()));
				statement.setString(2, Integer.toString(id));
				statement.executeUpdate();
				groups = getGroupList();
				done = 1;
			} else {
				System.out.println("user doesn't exist in group");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if event with user as creator exists in group in DB
	 * returns -1 if exists
	 * else inserts event in DB
	 * updates course list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int createEvent(String name, int groupId, String date, String description) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_EVENT);
			statement.setString(1, name);
			statement.setString(2, Integer.toString(groupId));
			statement.setString(3, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				System.out.println("event already exists");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_EVENT);
				statement.setString(1, name);
				statement.setString(2, Integer.toString(id));
				statement.setString(3, Integer.toString(groupId));
				statement.setString(4, date);
				statement.setString(5, description);
				statement.executeUpdate();
				courses = getCourseList();
				done = 1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if user participates in event in table UsEvents in DB
	 * returns -1 if yes
	 * else inserts entry in table UsEvents in DB
	 * updates user event list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int addEvent(Event e) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_EVENT_USEVENTS);
			statement.setString(1, Integer.toString(id));
			statement.setString(2, Integer.toString(e.getId()));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				System.out.println("user already participates in event");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_EVENT_USEVENTS);
				statement.setString(1, Integer.toString(id));
				statement.setString(2, Integer.toString(e.getId()));
				statement.executeUpdate();
				events = getEventList();
				done = 1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * !!!event can only be updated by creator
	 * verifies if user is creator of event and event exists in DB
	 * returns -1 if not
	 * else updates event in DB
	 * updates user event list
	 * updates user course list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int updateEvent(Event e) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(UPDATE_EVENT_SEARCH);
			statement.setString(1, Integer.toString(e.getId()));
			statement.setString(2, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				statement = (PreparedStatement) connection.prepareStatement(UPDATE_EVENT);
				statement.setString(1, e.getName());
				statement.setString(2, e.getDate());
				statement.setString(3, e.getDescription());
				statement.setString(4, Integer.toString(e.getId()));
				statement.executeUpdate();
				events = getEventList();
				courses = getCourseList();
				done = 1;
			} else {
				System.out.println("user not creator of event or invalid event");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if user participates in event in table UsEvents in DB
	 * returns -1 if not
	 * else deletes entry from table UsEvents in DB
	 * updates user event list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int removeEvent(Event e) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_EVENT_USEVENTS);
			statement.setString(1, Integer.toString(id));
			statement.setString(2, Integer.toString(e.getId()));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				statement = (PreparedStatement) connection.prepareStatement(DELETE_EVENT_USEVENTS);
				statement.setString(1, Integer.toString(e.getId()));
				statement.setString(2, Integer.toString(id));
				statement.executeUpdate();
				events = getEventList();
				done = 1;
			} else {
				System.out.println("user doesn't participate in event");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if user already added file in group in DB
	 * returns -1 if yes
	 * else adds file in DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int addFile(File f) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_FILE);
			statement.setString(1, f.getName());
			statement.setString(2, Integer.toString(id));
			statement.setString(3, Integer.toString(f.getGroupId()));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				System.out.println("file uploaded by user already exists in group");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_FILE);
				statement.setString(1, f.getUrl());
				statement.setString(2, f.getName());
				statement.setString(3, Integer.toString(id));
				statement.setString(4, Integer.toString(f.getGroupId()));
				statement.setString(5, f.getDescription());
				statement.executeUpdate();
				done = 1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if file added by user exists in group in DB
	 * returns -1 if not
	 * else deletes file from DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int removeFile(File f) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_FILE);
			statement.setString(1, f.getName());
			statement.setString(2, Integer.toString(id));
			statement.setString(3, Integer.toString(f.getGroupId()));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				statement = (PreparedStatement) connection.prepareStatement(DELETE_FILE);
				statement.setString(1, Integer.toString(f.getId()));
				statement.setString(2, Integer.toString(id));
				statement.executeUpdate();
				done = 1;
			} else {
				System.out.println("file doesn't exist or deletion dot allowed");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if user already added video in group in DB
	 * returns -1 if yes
	 * else adds video in DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int addVideo(File v) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_VIDEO);
			statement.setString(1, v.getName());
			statement.setString(2, Integer.toString(id));
			statement.setString(3, Integer.toString(v.getGroupId()));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				System.out.println("video uploaded by user already exists in group");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_VIDEO);
				statement.setString(1, v.getUrl());
				statement.setString(2, v.getName());
				statement.setString(3, Integer.toString(id));
				statement.setString(4, Integer.toString(v.getGroupId()));
				statement.setString(5, v.getDescription());
				statement.executeUpdate();
				done = 1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * verifies if video added by user exists in group in DB
	 * returns -1 if not
	 * else deletes video from DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int removeVideo(File v) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SEARCH_USER_VIDEO);
			statement.setString(1, v.getName());
			statement.setString(2, Integer.toString(id));
			statement.setString(3, Integer.toString(v.getGroupId()));
			ResultSet data = statement.executeQuery();
			if (data.next()){
				statement = (PreparedStatement) connection.prepareStatement(DELETE_VIDEO);
				statement.setString(1, Integer.toString(v.getId()));
				statement.setString(2, Integer.toString(id));
				statement.executeUpdate();
				done = 1;
			} else {
				System.out.println("video doesn't exist or deletion dot allowed");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * !!! computeUserList() method must run first, friends must not be NULL
	 * returns list of suggested friends for current user
	 * gets id from random friend in user friends list
	 * gets list of friends of friend from DB
	 * returns null if unsuccessful
	*/
	public List<User> getSuggestedFriendsList() {
		List<User> friendList = new ArrayList<User>();
		try {
			int randomFriendId = 0;
			if(friends != null) {
				randomFriendId = friends.get((int)(Math.random() * friends.size())).getId();
				PreparedStatement statement = 
						(PreparedStatement) connection.prepareStatement(SUGGESTED_FRIENDS);
				statement.setString(1 , Integer.toString(randomFriendId));
				statement.setString(2 , Integer.toString(randomFriendId));
				statement.setString(3 , Integer.toString(id));
				statement.setString(4 , Integer.toString(id));
				ResultSet data = statement.executeQuery();
				while (data.next()) {
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
					friendList.add(new User(dbConnection, id, lastName, firstName, email, password, 
							birthDate, rank, isValid, isPublic, type));
				}
				statement.close();
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return null;
		}
		return friendList;
	}
	
	/**
	 * !!! computeUserList() method must run first, groups must not be NULL
	 * returns list of suggested groups for current user
	 * gets domain from random group in user group list
	 * gets list of groups in domain from DB that user has not joined
	 * returns null if unsuccessful
	*/
	public List<Group> getSuggestedGroupsList() {
		List<Group> groupList = new ArrayList<Group>(); 
		try {
			int randomDomainId = 0;
			if(groups != null) {
				randomDomainId = groups.get((int)(Math.random() * groups.size())).getDomainId();
				PreparedStatement statement = 
		    			(PreparedStatement) connection.prepareStatement(SUGGESTED_GROUPS);
		    	statement.setString(1, Integer.toString(randomDomainId));
		    	statement.setString(2, Integer.toString(id));
		        ResultSet data = statement.executeQuery();
		        while(data.next()){
		        	Group group = new Group(dbConnection, data.getInt("id_group"), 
		        			data.getString("name"), data.getInt("id_domain"), 
		        			data.getInt("id_creator"), data.getString("description"));
		        	groupList.add(group);
		        }      
		        statement.close();
			}
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        return null;
	    }
		return groupList;
	}

	/**
	 * adds post with current date & time to event in DB
	 * updates event post list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int postInEvent(Event e, String message) {
		int done = 0;
		try {
			String today = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(
					Calendar.getInstance().getTime());
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(INSERT_POST);
			statement.setString(1, Integer.toString(id));
			statement.setString(2, Integer.toString(e.getId()));
			statement.setString(3, today);
			statement.setString(4, message);
			statement.executeUpdate();
			e.computeEventLists();
			done = 1;
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * adds comment with current date & time to post in DB
	 * updates post comment list
	 * returns 1 if successful
	 * else returns 0
	*/
	public int commentPost(Post p, String message) {
		int done = 0;
		try {
			String today = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(
					Calendar.getInstance().getTime());
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(INSERT_COMMENT);
			statement.setString(1, Integer.toString(id));
			statement.setString(2, Integer.toString(p.getId()));
			statement.setString(3, today);
			statement.setString(4, message);
			statement.executeUpdate();
			p.computePostLists();
			done = 1;
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * if post created by current user, deletes post from DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int deletePostFromEvent(Post p) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(DELETE_POST);
			statement.setString(1, Integer.toString(p.getId()));
			statement.setString(2, Integer.toString(id));
			statement.executeUpdate();
			done = 1;
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	/**
	 * if comment created by current user, deletes comment from DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int deletePostComment(Comment c) {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(DELETE_COMMENT);
			statement.setString(1, Integer.toString(c.getId()));
			statement.setString(2, Integer.toString(id));
			statement.executeUpdate();
			done = 1;
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
	
	public void removeSkill(String id) {
		for(Skill skill : skills){
		 	if(skill.getId() == Integer.parseInt(id)) {
		 		removeSkill(skill);
		 	}
		}
	}
	
	public void removeGroup(String id) {
		for(Group group : groups){
		 	if(group.getId() == Integer.parseInt(id)) {
		 		removeGroup(group);
		 	}
		}
	}
	
	public boolean isFriendWith(User other){
		for(User friend:this.friends){
			if(other.email.equals(friend.email)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isInGroup(Group newG){
		for(Group listGroups:this.groups){
			if(newG.getId() == listGroups.getId()){
				return true;
			}
		}
		return false;
	}
}

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
			+ "email = ?, password = ?, birthday_date = ?, rank = ?, is_valid = ?, is_public = ?, "
			+ "account_type = ? WHERE User.id_user = ?;";
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
	
	private DBConnection dbConnection;
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
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 * computes user age
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
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
	 * computes age from birthDate
	 * returns age
	*/
	private int computeAge() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");
		int age = -1;
		try {
			Date date = formatter.parse(birthDate);
			long ageInMilis = new Date().getTime() - date.getTime() ;
			age = (int) (ageInMilis / (60L * 60 * 24 * 365 * 1000));
		} catch (ParseException e) {
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
	public List<Skill> getSkillList() {
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
	public List<User> getFriendList() {
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
			statement.setString(5 , birthDate.toString());
			statement.setString(6 , Float.toString(rank));
			statement.setString(7 , Integer.toString(isValid));
			statement.setString(8 , Integer.toString(isPublic));
			statement.setString(9 , type);
			statement.setString(10 , Integer.toString(id));
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
				System.out.println("frindship exists");
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
				System.out.println("frindship doesn't exist");
				done = -1;
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
}

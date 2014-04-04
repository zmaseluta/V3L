package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
	private static final String USER_EVENT_USEVENTS = "SELECT * FROM User "
			+ "WHERE id_user IN (SELECT id_user FROM UsEvents WHERE id_event = ?);";
	
	private DBConnection dbConnection;
	private Connection connection;

	private int id;
	private String name;
	private int creatorId;
	private int groupId;
	private String date;
	private String description;
	private int isExpired;
	private List<User> members;

	public Event(DBConnection dbConnection, String name, int creatorId, int groupId, String date, 
			String description) {
		this.dbConnection = dbConnection;
		connection = dbConnection.getConnection();
		this.name = name;
		this.groupId = groupId;
		this.creatorId = creatorId;
		this.date = date;
		this.description = description;
		isExpired = computeIsExpired();
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the creatorId
	 */
	public int getCreatorId() {
		return creatorId;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @return the isExpired
	 */
	public int getIsExpired() {
		return isExpired;
	}

	/**
	 * @return the members
	 */
	public List<User> getMembers() {
		return members;
	}

	/**
	 * computes the value of isExpired from date
	 * returns value
	 */
	private int computeIsExpired() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");
		int val = 1;
		try {
			Date date = formatter.parse(this.date);
			long diferenceInMilis = new Date().getTime() - date.getTime() ;
			if (diferenceInMilis < 0) {
				val = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public void computeEventLists() {
		members = getMemberList();
	}

	/**
	 * returns member list for current event
	 * returns null if unsuccessful
	 */
	private List<User> getMemberList() {
		List<User> memberList = new ArrayList<User>();
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(USER_EVENT_USEVENTS);
			statement.setString(1 , Integer.toString(id));
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
				memberList.add(new User(dbConnection, id, lastName, firstName, email, password, 
						birthDate, rank, isValid, isPublic, type));
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return null;
		}
		return memberList;
	}
}

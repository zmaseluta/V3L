package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {
	private static final String USER_GROUP_USGROUPS = "SELECT * FROM User "
			+ "WHERE id_user IN (SELECT id_user FROM UsGroups WHERE id_group = ?);";
	private static final String EVENTS_IN_GROUP = "SELECT * FROM Events WHERE id_group = ?;";
	private static final String FILES_IN_GROUP = "SELECT * FROM Files WHERE id_group = ?;";
	private static final String VIDEOS_IN_GROUP = "SELECT * FROM Videos WHERE id_group = ?;";
	
	private DBConnection dbConnection;
	private Connection connection;

	private int id;
	private String name;
	private int domainId;
	private int creatorId;
	private String description;
	private List<User> members;
	private List<Event> events;
	private List<File> files;
	private List<File> videos;

	public Group(DBConnection dbConnection, int id, String name, int domainId, int creatorId, 
			String description) {
		this.dbConnection = dbConnection;
		connection = dbConnection.getConnection();
		this.id = id;
		this.name = name;
		this.domainId = domainId;
		this.creatorId = creatorId;
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
	 * @return the domainId
	 */
	public int getDomainId() {
		return domainId;
	}

	/**
	 * @return the creatorId
	 */
	public int getCreatorId() {
		return creatorId;
	}

	/**
	 * @return the members
	 */
	public List<User> getMembers() {
		return members;
	}

	/**
	 * @return the events
	 */
	public List<Event> getEvents() {
		return events;
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @return the videos
	 */
	public List<File> getVideos() {
		return videos;
	}

	public void computeGroupLists() {
		members = getMemberList();
		events = getEventList();
		files = getFileList();
		videos = getVideoList();
	}

	/**
	 * returns member list for current group
	 * returns null if unsuccessful
	 */
	private List<User> getMemberList() {
		List<User> memberList = new ArrayList<User>();
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(USER_GROUP_USGROUPS);
			statement.setString(1, Integer.toString(id));
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

	/**
	 * returns event list for current group
	 * returns null if unsuccessful
	 */
	private List<Event> getEventList() {
		List<Event> eventList = new ArrayList<Event>(); 
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(EVENTS_IN_GROUP);
			statement.setString(1, Integer.toString(id));
			ResultSet data = statement.executeQuery();
			while (data.next()) {
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
	 * returns file list for current group
	 * returns null if unsuccessful
	 */
	private List<File> getFileList() {
		List<File> fileList = new ArrayList<File>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(FILES_IN_GROUP);
	    	statement.setString(1, Integer.toString(id));
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
	 * returns video list for current group
	 * returns null if unsuccessful
	 */
	private List<File> getVideoList() {
		List<File> videoList = new ArrayList<File>(); 
		try {
	    	PreparedStatement statement = 
	    			(PreparedStatement) connection.prepareStatement(VIDEOS_IN_GROUP);
	    	statement.setString(1, Integer.toString(id));
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
}

package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Post {
	private static final String POST_COMMENTS = "Select * FROM Comments "
			+ "WHERE id_post = ?";
	
	private DBConnection dbConnection;
	private Connection connection;
	
	private int id;
	private int userId;
	private User user;
	private int eventId;
	private String date;
	private String content;
	private List<Comment> comments;
	
	public Post(DBConnection dbConnection, int id, int userId, int eventId, String date, String content) {
		this.dbConnection = dbConnection;
		connection = dbConnection.getConnection();
		this.id = id;
		this.userId = userId;
		this.eventId = eventId;
		this.date = date;
		this.content = content;
		DBOperations dbo = new DBOperations(dbConnection);
		setUser(dbo.getUser(userId));
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @return the eventId
	 */
	public int getEventId() {
		return eventId;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}
	
	public void computePostLists() {
		comments = getCommentList();
	}

	/**
	 * returns comment list for current post
	 * returns null if unsuccessful
	 */
	private List<Comment> getCommentList() {
		List<Comment> commentList = new ArrayList<Comment>();
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(POST_COMMENTS);
			statement.setString(1 , Integer.toString(id));
			ResultSet data = statement.executeQuery();
			while (data.next()) {
				commentList.add(new Comment(data.getInt("id_comment"), data.getInt("id_user"), 
						data.getInt("id_post"), data.getString("data_comment"), 
						data.getString("content_comment")));
			}
			statement.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return null;
		}
		return commentList;
	}

	public User getUser() {
		return user;
	}

	private void setUser(User user) {
		this.user = user;
	}
}

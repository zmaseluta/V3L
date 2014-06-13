package Model;

public class Comment {
	private int id;
	private int userId;
	private int postId;
	private String date;
	private String content;
	
	public Comment(int id, int userId, int postId, String date, String content) {
		this.id = id;
		this.userId = userId;
		this.postId = postId;
		this.date = date;
		this.content = content;
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
	 * @return the postId
	 */
	public int getPostId() {
		return postId;
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
}

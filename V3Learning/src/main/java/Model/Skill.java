package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Skill {
	private static final String SELECT_SKILL = "SELECT name FROM Skills "
			+ "WHERE (id_domain = ? AND name = ?);";
	private static final String INSERT_SKILL = "INSERT INTO Skills "
			+ "(id_skill, name, id_domain) VALUES (NULL, ?, ?);";
	
	private Connection connection;
	
	private int id;
	private String name;
	private int domainId;

	public Skill(DBConnection dbConnection, String name) {
		connection = dbConnection.getConnection();
		this.name = name;
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
	 * @return the domainId
	 */
	public int getDomainId() {
		return domainId;
	}

	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	/**
	 * verifies if skill exists in domain in DB
	 * returns -1 if skill exists in domain
	 * else inserts skill in DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int addToDomain (Domain d) {
		int done = 0;
		this.domainId = d.getId();
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SELECT_SKILL);
			statement.setString(1 , Integer.toString(this.domainId));
			statement.setString(2 , this.name);
			ResultSet data = statement.executeQuery();
			if(data.next()){
					System.out.println("skill exists");
					done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_SKILL);
				statement.setString(1 , this.name);
				statement.setString(2 , Integer.toString(this.domainId));
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
}

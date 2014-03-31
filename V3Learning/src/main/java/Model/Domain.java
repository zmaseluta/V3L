package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Domain {
	private static final String SELECT_DOMAIN = "SELECT name FROM Domain WHERE name = ?;";
	private static final String INSERT_DOMAIN = "INSERT INTO Domain (id_domain, name) "
			+ "VALUES (NULL, ?);";
	
	private Connection connection;
	
	private int id;
	private String name;

	public Domain(DBConnection dbConnection, String name) {
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
	 * verifies if domain exists in DB
	 * returns -1 if domain exists
	 * else inserts domain in DB
	 * returns 1 if successful
	 * else returns 0
	*/
	public int add() {
		int done = 0;
		try {
			PreparedStatement statement = 
					(PreparedStatement) connection.prepareStatement(SELECT_DOMAIN);
			statement.setString(1 , name);
			ResultSet data = statement.executeQuery();
			if(data.next()){
				System.out.println("domain exists");
				done = -1;
			} else {
				statement = (PreparedStatement) connection.prepareStatement(INSERT_DOMAIN);
				statement.setString(1 , name);
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

package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private Connection connection;
	private boolean isConnected;

	public DBConnection() {
		this.connection = null;
	}
	
	/**
	 * @return connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @return whether is connected
	 */
	public boolean getIsConnected() {
		return isConnected;
	}

	/**
	 * opens connection to DB
	 * if successful makes isConnected = true
	 * else makes isConnected = false
	*/
	public void connectToDB(String user, String pass, String dbDriver, String dbClass) {		
		isConnected = false;
		try {
	        Class.forName(dbClass).newInstance();
	        System.err.println("driver loaded"); // THIS IS BEING RETURNED
	    } catch (Exception ex) {
	        System.err.println(ex);
	    }
		
		try {
			connection = DriverManager.getConnection(dbDriver, user, pass);
			System.err.println("connected");
			isConnected = true;
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			isConnected = false;
		}
	}
	
	/**
	 * closes connection to DB
	 * returns true if successful
	 * else returns false
	*/
	public boolean closeConnection() {
		try {
			connection.close();
			isConnected = false;
			return true;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
	        return false;
		}
	}
}

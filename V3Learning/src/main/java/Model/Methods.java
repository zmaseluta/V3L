package Model;

import java.sql.*;

public class Methods {

	private static String dbuser = "echipab6";
	private static String dbpass = "echipab6";
	private static String dbDriver = "jdbc:mysql://db4free.net:3306/virtual3l";

	/*
	 * private static String dbuser = "root"; private static String pass2 = "";
	 * private static String dbDriver = "jdbc:mysql://localhost/v3l";
	 */

	private static String dbClass = "com.mysql.jdbc.Driver";
	private static Connection conn = null;

	public static boolean connectTest() {
		boolean done = false;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded"); // THIS IS BEING RETURNED
		} catch (Exception ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			conn = DriverManager.getConnection(dbDriver, dbuser, dbpass);
			// conn = DriverManager.getConnection(dbDriver, user, pass);
			System.out.println("connected");
			PreparedStatement statement = (PreparedStatement) conn
					.prepareStatement("INSERT INTO v3l.Domain (id_domain, name) VALUES (NULL, ?);");
			// PreparedStatement statement = (PreparedStatement)
			// conn.prepareStatement("INSERT INTO virtual3l.Domain (id_domain, name) VALUES (NULL, ?);");
			statement.setString(1, "Sports");
			statement.executeUpdate();
			statement.close();
			conn.close();
			done = true;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
		return done;
	}

	// updates user in DB
	public static int commitToDB(User user) {

		int done = 0;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded"); // THIS IS BEING RETURNED
		} catch (Exception ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			conn = DriverManager.getConnection(dbDriver, dbuser, dbpass);
			// conn = DriverManager.getConnection(dbDriver, user, pass);
			System.out.println("connected");

			PreparedStatement statement = (PreparedStatement) conn
					.prepareStatement("UPDATE v3l.user SET last_name = ?, first_name = ?, email = ?, password = ?, birthday_date = ?, rank = ?, is_valid = ?, is_public = ?, account_type = ? WHERE user.id_user = ?;");
			statement.setString(1, user.getLastName());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getBirthDate().toString());
			statement.setString(6, Float.toString(user.getRank()));
			statement.setString(7, Integer.toString(user.getIsValid()));
			statement.setString(8, Integer.toString(user.getIsPublic()));
			statement.setString(9, user.getType());
			statement.setString(10, Integer.toString(user.getId()));
			statement.executeUpdate();
			done = 1;
			statement.close();
			conn.close();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}

		return done;
	}

	// adds domain in DB if domain doesn't exist
	public static int commitToDB(Domain domain) {

		int done = 0;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded"); // THIS IS BEING RETURNED
		} catch (Exception ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			conn = DriverManager.getConnection(dbDriver, dbuser, dbpass);
			// conn = DriverManager.getConnection(dbDriver3, user3, pass3);
			System.out.println("connected");

			// search if domain exists
			PreparedStatement statement = (PreparedStatement) conn
					.prepareStatement("SELECT name FROM v3l.domain;");
			ResultSet data = statement.executeQuery();
			boolean exists = false;
			while (data.next()) {
				if (domain.name.equals(data.getObject("name"))) {
					System.out.println("domain exists");
					exists = true;
				}
			}

			if (exists) {
				done = -1;
			} else {
				// domain doesn't exist, insert user in database
				statement = (PreparedStatement) conn
						.prepareStatement("INSERT INTO v3l.domain (id_domain, name) VALUES (NULL, ?);");
				statement.setString(1, domain.name);
				statement.executeUpdate();
				done = 1;
			}

			statement.close();
			conn.close();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}

	// adds skill in DB if skill doesn't exist in domain
	public static int commitToDB(Skill skill) {
		int done = 0;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded"); // THIS IS BEING RETURNED
		} catch (Exception ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			conn = DriverManager.getConnection(dbDriver, dbuser, dbpass);
			// conn = DriverManager.getConnection(dbDriver3, user3, pass3);
			System.out.println("connected");

			// search if skill exists in domain
			PreparedStatement statement = (PreparedStatement) conn
					.prepareStatement("SELECT name FROM v3l.skills WHERE id_domain = ?;");
			statement.setString(1, Integer.toString(skill.domainId));
			ResultSet data = statement.executeQuery();
			boolean exists = false;
			while (data.next()) {
				if (skill.name.equals(data.getObject("name"))) {
					System.out.println("skill exists");
					exists = true;
				}
			}

			if (exists) {
				done = -1;
			} else {
				// skill doesn't exist, insert skill in database
				statement = (PreparedStatement) conn
						.prepareStatement("INSERT INTO v3l.skills (id_skill, name, id_domain) VALUES (NULL, ?, ?);");
				statement.setString(1, skill.name);
				statement.setString(2, Integer.toString(skill.domainId));
				statement.executeUpdate();
				done = 1;
			}

			statement.close();
			conn.close();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}

	public static int rank(int idGiver, int idReceiver, int grade) {
		int done = 0;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded"); // THIS IS BEING RETURNED
		} catch (Exception ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			conn = DriverManager.getConnection(dbDriver, dbuser, dbpass);
			// conn = DriverManager.getConnection(dbDriver3, user3, pass3);
			System.out.println("connected");

			// search if rank exists
			PreparedStatement statement = (PreparedStatement) conn
					.prepareStatement("SELECT id_rank, id_giver FROM v3l.usrank WHERE id_receiver = ?;");
			statement.setString(1, Integer.toString(idReceiver));
			ResultSet data = statement.executeQuery();
			int exists = 0;

			while (data.next()) {
				if (data.getInt("id_giver") == idGiver) {
					exists = data.getInt("id_rank");
				}
			}

			if (exists != 0) {
				statement = (PreparedStatement) conn
						.prepareStatement("UPDATE v3l.usrank SET rank = ? WHERE usrank.id_rank = ?;");
				statement.setString(1, Integer.toString(grade));
				statement.setString(2, Integer.toString(exists));
				statement.executeUpdate();

			} else {
				// rank doesn't exist, insert rank in database
				statement = (PreparedStatement) conn
						.prepareStatement("INSERT INTO v3l.usrank (id_rank, id_giver, id_receiver, rank) VALUES (NULL, ?, ?, ?);");
				statement.setString(1, Integer.toString(idGiver));
				statement.setString(2, Integer.toString(idReceiver));
				statement.setString(3, Integer.toString(grade));
				statement.executeUpdate();

			}
			done = computeRank(idReceiver);
			statement.close();
			conn.close();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}

	private static int computeRank(int idReceiver) {
		int done = 0;
		float sum = 0;
		int count = 0;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded"); // THIS IS BEING RETURNED
		} catch (Exception ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			conn = DriverManager.getConnection(dbDriver, dbuser, dbpass);
			// conn = DriverManager.getConnection(dbDriver3, user3, pass3);
			System.out.println("connected");

			// search if domain exists
			PreparedStatement statement = (PreparedStatement) conn
					.prepareStatement("SELECT rank FROM v3l.usrank WHERE id_receiver = ?;");
			statement.setString(1, Integer.toString(idReceiver));
			ResultSet data = statement.executeQuery();

			while (data.next()) {
				sum += data.getInt("rank");
				count++;
			}
			sum /= count;
			if (count != 0) {
				statement = (PreparedStatement) conn
						.prepareStatement("UPDATE v3l.user SET rank = ? WHERE id_user = ?;");
				statement.setString(1, Float.toString(sum));
				statement.setString(2, Integer.toString(idReceiver));
				statement.executeUpdate();

			}
			statement.close();
			conn.close();
			done = 1;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}

	public static int computeRankNoUsers(int idReceiver) {
		int count = 0;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded"); // THIS IS BEING RETURNED
		} catch (Exception ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			conn = DriverManager.getConnection(dbDriver, dbuser, dbpass);
			// conn = DriverManager.getConnection(dbDriver3, user3, pass3);
			System.out.println("connected");

			// search if domain exists
			PreparedStatement statement = (PreparedStatement) conn
					.prepareStatement("SELECT rank FROM v3l.usrank WHERE id_receiver = ?;");
			statement.setString(1, Integer.toString(idReceiver));
			ResultSet data = statement.executeQuery();

			while (data.next()) {
				count++;
			}
			statement.close();
			conn.close();
			return count;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return -1;
		}

	}

	public static Skill[] getSkillList(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static int addUserSkill(int skillId, int userId) {
		int done = 0;
		// load driver
		try {
			Class.forName(dbClass).newInstance();
			System.out.println("driver loaded"); // THIS IS BEING RETURNED
		} catch (Exception ex) {
			System.err.println(ex);
		}
		// Connection
		try {
			conn = DriverManager.getConnection(dbDriver, dbuser, dbpass);
			// conn = DriverManager.getConnection(dbDriver3, user3, pass3);
			System.out.println("connected");

			// search if domain exists
			PreparedStatement statement = (PreparedStatement) conn
					.prepareStatement("SELECT id_skill FROM v3l.usskills WHERE id_user = ?;");
			statement.setString(1, Integer.toString(userId));
			ResultSet data = statement.executeQuery();
			boolean exists = false;

			while (data.next()) {
				if (data.getInt("id_skill") == skillId) {
					exists = true;
				}
			}

			if (exists) {
				done = -1;
			} else {
				// domain doesn't exist, insert user in database
				statement = (PreparedStatement) conn
						.prepareStatement("INSERT INTO v3l.usskills (id_us_sk, id_skill, id_user) VALUES (NULL, ?, ?);");
				statement.setString(1, Integer.toString(skillId));
				statement.setString(2, Integer.toString(userId));
				statement.executeUpdate();
				done = 1;
			}

			statement.close();
			conn.close();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			done = 0;
		}
		return done;
	}
}

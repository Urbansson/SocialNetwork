package vertx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class DBManager {

	private static DBManager instance = null;
	private static LinkedList<Connection> freeConnections = null;
	private static LinkedList<Connection> busyConnections = null;
	private static final String dbURL = "jdbc:mysql://130.237.84.126:3306/socialnetwork?noAccessToProcedureBodies=true";
	private static final String user = "socialnetwork";
	private static final String pwd = "ninjakick";

	private DBManager() throws ClassNotFoundException, SQLException {
		// Exists only to defeat instantiation.
		Class.forName("com.mysql.jdbc.Driver");
		freeConnections = new LinkedList<>();
		busyConnections = new LinkedList<>();
		
		for (int nrofcon = 0; nrofcon < 5; nrofcon++)
			freeConnections.add(createConnection());
		System.out.println("Db connection is created");
	}

	public Connection getConnection() throws SQLException {
		if (freeConnections.isEmpty()) {
			busyConnections.add(createConnection());
		} else {
			if (freeConnections.getFirst().isClosed()){
				freeConnections.remove();
				busyConnections.add(createConnection());
			}
			else
				busyConnections.add(freeConnections.remove());		
		}
		return busyConnections.getLast();
	}

	public void freeConnection(Connection con) {

		if (busyConnections.remove(con)) {
			freeConnections.add(con);
		}

	}

	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(dbURL, user, pwd);
	}

	public static DBManager getInstance() {

		if (instance == null) {
			try {
				instance = new DBManager();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
}

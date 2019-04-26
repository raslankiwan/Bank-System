/**
 * 
 */
package com.server;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.*;

/**
 * @author Raslan
 *
 */
public class DBConnection {

	private static DBConnection instance;
	private Connection conn;
	private String username = "root";
	private String password = "";
	private String url = "jdbc:mysql://localhost:3306/bank";

	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		return conn;
	}

	public static DBConnection getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBConnection();
		} else if (instance.getConnection().isClosed()) {
			instance = new DBConnection();
		}
		return instance;
	}
}

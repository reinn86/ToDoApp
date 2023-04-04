package domain.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static final String USER = "root";
	private static final String PASSWORD = "kamakirihakase0";
	private static final String URL = "jdbc:mySQL://localhost/todoappdb";
	
	public Connection getConnection() {
		Connection cn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn = DriverManager.getConnection(URL,USER,PASSWORD);
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return cn;
	}
}

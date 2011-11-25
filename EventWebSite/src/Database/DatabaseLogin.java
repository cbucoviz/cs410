package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseLogin 
{

	private static final String PASSWORD = "";
	private static final String PORT = "";
	private static final String DATABASE_NAME = "";
	private static final String LOGIN_NAME = "";
	
	
	
	protected static Connection connect() throws SQLException
	{
		/*
		   Create connection to MySQL:
		      - localhost: "port" <- whatever port a local database is located at
		      - name of database <- the title of a database to be connected to
		   Second parameter is a login name;
		   Third is a password;
		 */
		
		return DriverManager.getConnection("jdbc:mysql://localhost:" + PORT + "/" + DATABASE_NAME,
				LOGIN_NAME, PASSWORD);
	}
	
}

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseLogin 
{

	private static final String PASSWORD = "sn00py123";
	private static final String PORT = "3306";
	private static final String DATABASE_NAME = "eventdb";
	private static final String LOGIN_NAME = "root";
	
	
	
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

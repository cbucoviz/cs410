package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseLogin 
{

	private static String password;
	private static String port;
	private static String databaseName;
	private static String loginName;
	
	
	
	protected static Connection connect() throws SQLException
	{
		/*
		   Create connection to MySQL:
		      - localhost: "port" <- whatever port a local database is located at
		      - name of database <- the title of a database to be connected to
		   Second parameter is a login name;
		   Third is a password;
		 */
		
		password = "";
		port = "";
		databaseName = "";
		loginName = "";
		
		return DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + databaseName,
				password, loginName);
	}
	
}

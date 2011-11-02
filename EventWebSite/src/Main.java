import java.sql.SQLException;

import Database.DatabaseManager;


public class Main {

	private static DatabaseManager manager;
	
	public static void main(String args[])
	{				
		try {
			manager = DatabaseManager.getInstance();
		} catch (ClassNotFoundException e1) {
			System.out.println("Driver for MySQL (Java class) was not found!");
			e1.printStackTrace();
		} catch (SQLException e1) {
			System.out.println("Connection failed! Check login and password");
			e1.printStackTrace();
		}	
		
		try {				
		   manager.testQuery();
		} catch (SQLException e) {
			System.out.println("Query failed! Could be a syntax error.");
			e.printStackTrace();
		}
			
		
	}
}




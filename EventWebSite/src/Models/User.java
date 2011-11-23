package Models;

import java.sql.SQLException;

import Database.DatabaseManager;

public class User 
{
	public static void subscribeToUser(int userID, int subscriberID)
	{
		DatabaseManager dbManager;
		try {
			dbManager = DatabaseManager.getInstance();
			dbManager.subscribeToUser(userID, subscriberID);
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
	}
	
	public static void unsubscribeFromUser(int userID, int subscriberID)
	{
		DatabaseManager dbManager;
		try {
			dbManager = DatabaseManager.getInstance();
			dbManager.unsubscribeFromUser(userID, subscriberID);
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
	}
}

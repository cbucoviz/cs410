package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.mail.internet.ParseException;

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
	
	public static boolean isSubscribed(int userID, int targetUserID)
	{
		DatabaseManager dbManager;
		try {
			dbManager = DatabaseManager.getInstance();
			return dbManager.isSubscribedToUser(userID, targetUserID);
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
		
		return false;
	}
	
	public static HashSet<CalendarEvent> getAttendingEvents(int userID)
	{
		HashSet<CalendarEvent> returnSet = null;
		
		try 
		{
			DatabaseManager dbManager = DatabaseManager.getInstance();
			ResultSet result = dbManager.findMyAttendingEvents(userID);
			returnSet = new HashSet<CalendarEvent>();
			
			while(result.next())
			{
				int eventId = result.getInt("eventID");
				String title = result.getString("title");
				String baseDate = result.getString("eventDate");
				String startDateString = baseDate + " " + result.getString("startTime");
				String endDateString = baseDate + " " + result.getString("endTime");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date startDate = dateFormat.parse(startDateString);
				Date endDate = dateFormat.parse(endDateString);
				
				returnSet.add(new CalendarEvent(eventId, title, startDate, endDate));
			}
			
		} 
		catch (ClassNotFoundException e1) 
		{			
			e1.printStackTrace();
		} 
		catch (SQLException e1) 
		{			
			e1.printStackTrace();
		}
		catch (java.text.ParseException e1)
		{
			e1.printStackTrace();
		}
		
		return returnSet;
	}
	
}

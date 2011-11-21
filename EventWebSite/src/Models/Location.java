package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import Database.DatabaseManager;
import Models.Event.EventInfo;


public class Location 
{
	
	public static LocationAddress getLocationAddress(int id)
	{
		try
		{
			DatabaseManager dbManager = DatabaseManager.getInstance();
			ResultSet result = dbManager.getLocation(id);
			if(result.next())
			{
				return new LocationAddress(	result.getString("city"),
											result.getString("state"),
											result.getString("country"));
			}
		} 
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	public static ArrayList<Map<EventInfo, String>> getEventsAtLocation(int locId)
	{
		ArrayList<Map<EventInfo, String>> returnList = new ArrayList<Map<EventInfo, String>>();
		
		try
		{
			// get the events by location, no filtering
			DatabaseManager dbManager = DatabaseManager.getInstance();
			ResultSet result = dbManager.findEvents(locId, "%");
			
			while(result.next())
			{
				int eventId = result.getInt("eventID");
				returnList.add(Event.getExistingEvent(eventId));
			}
		} 
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		return returnList;
	}
}


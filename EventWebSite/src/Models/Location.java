package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonObject;

import Database.DatabaseManager;
import Models.Event.EventInfo;


public class Location 
{
	
	private static final String LOCATION = "city";
	private static final String LATITUDE = "Latitude";
	private static final String LONGITUDE = "Longitude";
	private static final String ID = "locationID";
	private static final String TITLE = "title";
	private static final String EVENTID = "eventID";
	
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
	
	public static int getLocationId(LocationAddress address)
	{
		try
		{
			// get the events by location, no filtering
			DatabaseManager dbManager = DatabaseManager.getInstance();
			ResultSet result = dbManager.getLocationID(address.city, address.state, address.country);
			
			if(result.next())
			{
				return result.getInt("locationID");
				
			}
		} 
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		return -1;
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
	
	
	public static void subscribeToLocation(int locationID, int subscriberID)
	{
		DatabaseManager dbManager;
		try {
			dbManager = DatabaseManager.getInstance();
			dbManager.subscribeToLocation(locationID, subscriberID);
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
	}
	
	public static void unsubscribeFromLocation(int locationID, int subscriberID)
	{
		DatabaseManager dbManager;
		try {
			dbManager = DatabaseManager.getInstance();
			dbManager.unsubscribeFromLocation(locationID, subscriberID);
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
	}
	
	public static boolean isSubscribed(int userID, int locationID)
	{
		DatabaseManager dbManager;
		try {
			dbManager = DatabaseManager.getInstance();
			return dbManager.isSubscribedToLocale(userID, locationID);
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
		
		return false;
	}
	
	public static JsonObject addLocation(LocationAddress addr, double latitude, double longitude)
	{
		DatabaseManager dbManager;
		JsonObject jObj = null;
		try {
			dbManager = DatabaseManager.getInstance();
			ResultSet rSet = dbManager.getLocationID(addr.city, addr.state, addr.country);
			if(rSet.next())
			{
				ResultSet lSet = dbManager.getLocation(rSet.getInt("L.locationID"));
				if(lSet.next())
				{
			        jObj = 	Models.Search.createLocJson(lSet.getString(LOCATION),
			        						lSet.getDouble(LATITUDE),
			        						lSet.getDouble(LONGITUDE),
			        						rSet.getInt("L.locationID")
			        );
				}
			}
			else
			{
				 dbManager.addLocation(addr.city, addr.state, addr.country, latitude, longitude);
				 ResultSet newSet = dbManager.getLocationID(addr.city, addr.state, addr.country);
					if(newSet.next())
					{
						ResultSet lSet = dbManager.getLocation(newSet.getInt("locationID"));
						if(lSet.next())
						{
					        jObj = 	Models.Search.createLocJson(lSet.getString(LOCATION),
					        						lSet.getDouble(LATITUDE),
					        						lSet.getDouble(LONGITUDE),
					        						newSet.getInt("locationID")
					        );
						}
					}
				 
			}
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
		return jObj;
	}
}


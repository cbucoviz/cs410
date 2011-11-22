package Models;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Database.DatabaseManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Search {
	
	private static final String LOCATION = "city";
	private static final String LATITUDE = "Latitude";
	private static final String LONGITUDE = "Longitude";
	private static final String ID = "locationID";
	
	
	/**
	 * Used to get the name, latitude and longitude of an event city to be displayed
	 * on Google Earth.
	 * @return returns a Json Array with the Event Location information
	 */
	public JsonArray getGoogleEarthLoc()
	{
		JsonArray returnValue = new JsonArray();
		try
		{
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet locations = dbMan.findGoogleEarthLocations();
			while (locations.next()) 
			{
		        returnValue.add(
		        		createLocJson(	locations.getString(LOCATION),
		        						locations.getDouble(LATITUDE),
		        						locations.getDouble(LONGITUDE),
		        						locations.getInt(ID)
		        ));
		    }
		}
		catch(SQLException sExp)
		{
			sExp.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	/**
	 * creates a JsonObject that has the event city details as properties.
	 * 
	 * @param name - name 
	 * @param latitude - latitude
	 * @param longitude - longitude 
	 * @param id - id 
	 * @return JsonObject with the name, latitude and longitude
	 */
	private JsonObject createLocJson(String name, double latitude, double longitude, int id)
	{
		JsonObject json = new JsonObject();
		json.addProperty("name", name);
		json.addProperty("lat", latitude);
		json.addProperty("lng", longitude);
		json.addProperty("id", id);
		return json;
	}
	
	
	/**
	 * This method searches the database for events in a city that are of a certain typw and returns 
	 * their id's in an arraylist.
	 * @param type - the type of the event
	 * @param location - the location of the event
	 * @return - an ArrayList with the id's of matching events.
	 */
	public ArrayList<Integer> getEventsByCityAndType(String type, String location)
	{
		ArrayList<Integer> eventId = new ArrayList<Integer>();
		try
		{
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet events = dbMan.findEventsByTypeLocation(type, location);
			
			while (events.next()) 
			{
				eventId.add(events.getInt("eventID"));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return eventId;
	}
	
	
	/**
	 * This method searches the database for events in a city and returns 
	 * their id's in an ArrayList.
	 * @param location - the location of the event
	 * @return - an ArrayList with the id's of matching events.
	 */
	public ArrayList<Integer> getEventsByLocation(String location)
	{
		ArrayList<Integer> eventId = new ArrayList<Integer>();
		try
		{
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet events = dbMan.findEventsByLocation(location);
			
			while (events.next()) 
			{
				eventId.add(events.getInt("eventID"));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return eventId;
	}
	
	
	/**
	 * This method searches the database for events between certain dates and returns 
	 * their id's in an ArrayList.
	 * @param location - the location of the event
	 * @return - an ArrayList with the id's of matching events.
	 */
	public ArrayList<Integer> getEventsBetweenDates(Date from, Date to)
	{
		ArrayList<Integer> eventId = new ArrayList<Integer>();
		try
		{
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet events = dbMan.findEventsByDates(from, to);
			
			while (events.next()) 
			{
				eventId.add(events.getInt("eventID"));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return eventId;
	}
	
	/**
	 * Gets events that have a rating higher thatn the parameter passed in.
	 * @param rating
	 * @return
	 */
	public ArrayList<Integer> getEventsWithRating(float rating)
	{
		ArrayList<Integer> eventId = new ArrayList<Integer>();
		try
		{
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet events = dbMan.findEventsByRating(rating);
			
			while (events.next()) 
			{
				eventId.add(events.getInt("eventID"));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return eventId;
	}
	
	/**
	 * Gets events that were created by a certain user
	 * @param rating
	 * @return
	 */
	public ArrayList<Integer> getEventsByUser(String user)
	{
		ArrayList<Integer> eventId = new ArrayList<Integer>();
		try
		{
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet events = dbMan.findEventsByUser(user);
			
			while (events.next()) 
			{
				eventId.add(events.getInt("eventID"));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return eventId;
	}
	

}

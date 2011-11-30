package Models;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseManager;
import Models.Review.ReviewInfo;
import Models.Review.SortReviews;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Search {
	
	private static final String LOCATION = "city";
	private static final String LATITUDE = "Latitude";
	private static final String LONGITUDE = "Longitude";
	private static final String ID = "locationID";
	private static final String TITLE = "title";
	private static final String EVENTID = "eventID";
	
	
	
	public enum UserInfoSearch
	{
		USER_ID,
		NAME,
		NUM_NEW_EVENTS;
	}
	
	public enum SubscribedLocaleInfo
	{
		LOCALE_ID,
		CITY,
		NUM_NEW_EVENTS;
	}
	
	public enum EventInfoSearch
	{
		EVENT_ID,
		TITLE,
		VENUE,		
		EVENT_DATE,
		START_TIME,
		END_TIME,		
		CREATOR,
		CREATOR_ID,
		LOCATION_ID,
		CITY,
		IS_EDITED,
		EVENT_TYPE;
	}
	
	public enum ModeratorInfo
	{
		MOD_ID,
		MOD_NAME;
	}
	
	
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
			//ResultSet locations = dbMan.findGoogleEarthLocations();
			ResultSet locations = dbMan.findGoogleLocations(null, null, null, null, null, null);
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
	
	public JsonArray getGoogleEarthLoc(String keyWord, String city, String state, String country, String start,String end) 
	{
		JsonArray returnValue = new JsonArray();
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		DateFormat formater = new SimpleDateFormat("dd/MM/yyyy"); 
		try
		{
			if(start != null && start.trim() != "")
			{
				java.util.Date parsedUtilDate = formater.parse(start);  
				startDate= new java.sql.Date(parsedUtilDate.getTime()); 
			}
			if(end != null && end.trim() != "")
			{
				java.util.Date parsedUtilDate = formater.parse(end);  
				endDate= new java.sql.Date(parsedUtilDate.getTime()); 
			}
			DatabaseManager dbMan = DatabaseManager.getInstance();
			//ResultSet locations = dbMan.findGoogleEarthLocations();
			ResultSet locations = dbMan.findGoogleLocations(keyWord, city, state, country, startDate, endDate);
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
		catch(Exception ex)
		{
			ex.printStackTrace();
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
	
	/**
	 * Used to get the name, latitude, longitude and id of an event to be displayed
	 * on Google Earth.
	 * @return returns a Json Array with the Event Location information
	 * @throws ParseException 
	 */
	public JsonArray getGoogleMapEvents(int cityId,String keyword, String[] types, String start) 
	{
		JsonArray returnValue = new JsonArray();
		java.sql.Date startDate = null;
		DateFormat formater = new SimpleDateFormat("dd/MM/yyyy"); 
		try
		{
			if(start != null && start.trim() != "")
			{
				java.util.Date parsedUtilDate = formater.parse(start);  
				startDate= new java.sql.Date(parsedUtilDate.getTime()); 
			}
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet locations = dbMan.filterEventsByCriteria(cityId, startDate, keyword, types);
			while (locations.next()) 
			{
		        returnValue.add(
		        		createLocJson(	locations.getString(TITLE),
		        						locations.getDouble(LATITUDE),
		        						locations.getDouble(LONGITUDE),
		        						locations.getInt(EVENTID)
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
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<Map<EventInfoSearch,String>> findMySubscribedEvents(int userID)
				throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet events = dbMan.findMySubscribedEvents(userID);	
		List<Map<EventInfoSearch, String>> eventsToReturn = Collections.synchronizedList
						(new ArrayList<Map<EventInfoSearch,String>>());
		
		while(events.next())
		{		
			Map<EventInfoSearch, String> event = Collections.synchronizedMap(new HashMap<EventInfoSearch,String>());
			event.put(EventInfoSearch.EVENT_ID,		events.getString("E.eventID"));
			event.put(EventInfoSearch.TITLE,		events.getString("E.title"));
			event.put(EventInfoSearch.VENUE,		events.getString("E.venue"));
			event.put(EventInfoSearch.EVENT_DATE,	events.getString("E.eventDate"));
			event.put(EventInfoSearch.START_TIME,	events.getString("E.startTime"));
			event.put(EventInfoSearch.END_TIME,		events.getString("E.endTime"));
			event.put(EventInfoSearch.CREATOR,		events.getString("creator"));
			event.put(EventInfoSearch.CREATOR_ID,	events.getString("U.userID"));
			event.put(EventInfoSearch.LOCATION_ID,	events.getString("L.locationID"));
			event.put(EventInfoSearch.CITY,			events.getString("L.city"));
			event.put(EventInfoSearch.IS_EDITED,	events.getString("Updates"));
			
			eventsToReturn.add(event);
		}
	
		return eventsToReturn;
	}

	public static List<Map<EventInfoSearch,String>> findMyEvents(int userID)
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet events = dbMan.findMyEvents(userID);	
		List<Map<EventInfoSearch, String>> eventsToReturn = Collections.synchronizedList
						(new ArrayList<Map<EventInfoSearch,String>>());
		
		while(events.next())
		{		
			Map<EventInfoSearch, String> event = Collections.synchronizedMap(new HashMap<EventInfoSearch,String>());
			event.put(EventInfoSearch.EVENT_ID,		events.getString("E.eventID"));
			event.put(EventInfoSearch.TITLE,		events.getString("E.title"));
			event.put(EventInfoSearch.VENUE,		events.getString("E.venue"));
			event.put(EventInfoSearch.EVENT_DATE,	events.getString("E.eventDate"));
			event.put(EventInfoSearch.START_TIME,	events.getString("E.startTime"));
			event.put(EventInfoSearch.END_TIME,		events.getString("E.endTime"));		
			event.put(EventInfoSearch.LOCATION_ID,	events.getString("L.locationID"));
			event.put(EventInfoSearch.CITY,			events.getString("L.city"));
			event.put(EventInfoSearch.IS_EDITED,	events.getString("Updates"));
			
			eventsToReturn.add(event);
		}
	
		return eventsToReturn;
	}


	public static List<Map<EventInfoSearch,String>> findNewEventsByLoc(int locationID, int userID)
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet events = dbMan.getNewEvents(locationID, userID);	
		List<Map<EventInfoSearch, String>> eventsToReturn = Collections.synchronizedList
						(new ArrayList<Map<EventInfoSearch,String>>());
		
		while(events.next())
		{		
			Map<EventInfoSearch, String> event = Collections.synchronizedMap(new HashMap<EventInfoSearch,String>());
			event.put(EventInfoSearch.EVENT_ID,		events.getString("E.eventID"));
			event.put(EventInfoSearch.TITLE,		events.getString("E.title"));
			event.put(EventInfoSearch.VENUE,		events.getString("E.venue"));
			event.put(EventInfoSearch.EVENT_DATE,	events.getString("E.eventDate"));
			event.put(EventInfoSearch.START_TIME,	events.getString("E.startTime"));
			event.put(EventInfoSearch.END_TIME,		events.getString("E.endTime"));	
			event.put(EventInfoSearch.CREATOR,		events.getString("U.name"));
			event.put(EventInfoSearch.CREATOR_ID,	events.getString("U.userID"));
			event.put(EventInfoSearch.LOCATION_ID,	events.getString("L.locationID"));
			event.put(EventInfoSearch.CITY,			events.getString("L.city"));
			
			eventsToReturn.add(event);
		}
	
		return eventsToReturn;
	}

	public static List<Map<EventInfoSearch,String>> findNewEventsByUser(int creatorID, int userID)
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet events = dbMan.findAllNewEvents(creatorID, userID);
		List<Map<EventInfoSearch, String>> eventsToReturn = Collections.synchronizedList
						(new ArrayList<Map<EventInfoSearch,String>>());
		
		while(events.next())
		{		
			Map<EventInfoSearch, String> event = Collections.synchronizedMap(new HashMap<EventInfoSearch,String>());
			event.put(EventInfoSearch.EVENT_ID,		events.getString("E.eventID"));
			event.put(EventInfoSearch.TITLE,		events.getString("E.title"));
			event.put(EventInfoSearch.VENUE,		events.getString("E.venue"));
			event.put(EventInfoSearch.EVENT_DATE,	events.getString("E.eventDate"));
			event.put(EventInfoSearch.START_TIME,	events.getString("E.startTime"));
			event.put(EventInfoSearch.END_TIME,		events.getString("E.endTime"));		
			event.put(EventInfoSearch.LOCATION_ID,	events.getString("L.locationID"));
			event.put(EventInfoSearch.CITY,			events.getString("L.city"));
			
			eventsToReturn.add(event);
		}
	
		return eventsToReturn;
	}
	
	public static List<Map<UserInfoSearch,String>> findSubscribedUsers(int subscriberID)
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet subscribedUsers = dbMan.findSubscribedUsers(subscriberID);
		List<Map<UserInfoSearch, String>> usersToReturn = Collections.synchronizedList
						(new ArrayList<Map<UserInfoSearch,String>>());
		
		while(subscribedUsers.next())
		{		
			Map<UserInfoSearch, String> subscribedUser = Collections.synchronizedMap(new HashMap<UserInfoSearch,String>());
			subscribedUser.put(UserInfoSearch.USER_ID,	subscribedUsers.getString("Temp.userID"));
			subscribedUser.put(UserInfoSearch.NAME,		subscribedUsers.getString("Temp.name"));
			subscribedUser.put(UserInfoSearch.NUM_NEW_EVENTS,subscribedUsers.getString("New Events"));			
			
			usersToReturn.add(subscribedUser);
		}
	
		return usersToReturn;
	}

	
	public static List<Map<SubscribedLocaleInfo,String>> findSubscribedLocales(int userID)
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet subscribedLocales = dbMan.findSubscribedLocales(userID);
		List<Map<SubscribedLocaleInfo, String>> localesToReturn = Collections.synchronizedList
						(new ArrayList<Map<SubscribedLocaleInfo,String>>());
		
		while(subscribedLocales.next())
		{		
			Map<SubscribedLocaleInfo, String> subscribedLocale = Collections.synchronizedMap(new HashMap<SubscribedLocaleInfo,String>());
			subscribedLocale.put(SubscribedLocaleInfo.LOCALE_ID,	subscribedLocales.getString("Temp.locationID"));
			subscribedLocale.put(SubscribedLocaleInfo.CITY,		subscribedLocales.getString("Temp.city"));
			subscribedLocale.put(SubscribedLocaleInfo.NUM_NEW_EVENTS,subscribedLocales.getString("New Events"));			
			
			localesToReturn.add(subscribedLocale);
		}
	
		return localesToReturn;
	}
	
	
	public static List<Map<EventInfoSearch,String>> topEvents(int locationID)
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet events = dbMan.topTenEvents(locationID);
		List<Map<EventInfoSearch, String>> eventsToReturn = Collections.synchronizedList
						(new ArrayList<Map<EventInfoSearch,String>>());
		
		while(events.next())
		{		
			Map<EventInfoSearch, String> event = Collections.synchronizedMap(new HashMap<EventInfoSearch,String>());
			event.put(EventInfoSearch.EVENT_ID,		events.getString("E.eventID"));
			event.put(EventInfoSearch.TITLE,		events.getString("E.title"));
			event.put(EventInfoSearch.VENUE,		events.getString("E.venue"));
			event.put(EventInfoSearch.EVENT_DATE,	events.getString("E.eventDate"));
			event.put(EventInfoSearch.START_TIME,	events.getString("E.startTime"));
			event.put(EventInfoSearch.END_TIME,		events.getString("E.endTime"));	
			event.put(EventInfoSearch.CREATOR,		events.getString("U.name"));
			event.put(EventInfoSearch.CREATOR_ID,	events.getString("U.userID"));
			event.put(EventInfoSearch.LOCATION_ID,	Integer.toString(locationID));
			event.put(EventInfoSearch.CITY,			events.getString("L.city"));
			
			eventsToReturn.add(event);
		}
	
		return eventsToReturn;
	}
	
	public static List<Map<UserInfoSearch,String>> topUsers(int locationID)
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet subscribedUsers = dbMan.topTenUsers(locationID);
		List<Map<UserInfoSearch, String>> usersToReturn = Collections.synchronizedList
						(new ArrayList<Map<UserInfoSearch,String>>());
		
		while(subscribedUsers.next())
		{		
			Map<UserInfoSearch, String> subscribedUser = Collections.synchronizedMap(new HashMap<UserInfoSearch,String>());
			subscribedUser.put(UserInfoSearch.USER_ID,	subscribedUsers.getString("Temp.userID"));
			subscribedUser.put(UserInfoSearch.NAME,		subscribedUsers.getString("Temp.name"));					
			
			usersToReturn.add(subscribedUser);
		}
	
		return usersToReturn;
	}
	
	public static  List<Map<ModeratorInfo,String>> getMods() throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet moderators = dbMan.getAllModerators();
		List<Map<ModeratorInfo, String>> modsToReturn = Collections.synchronizedList
						(new ArrayList<Map<ModeratorInfo,String>>());
		while(moderators.next())
		{	
			Map<ModeratorInfo, String> subscribedUser = Collections.synchronizedMap(new HashMap<ModeratorInfo,String>());
			subscribedUser.put(ModeratorInfo.MOD_ID,	moderators.getString("userID"));
			subscribedUser.put(ModeratorInfo.MOD_NAME,		moderators.getString("name"));					
			
			modsToReturn.add(subscribedUser);
		}
		return modsToReturn;
	}
	
	public static List<Map<EventInfoSearch,String>> filterEvents(int locationID, String keyword, String[] types, Date start )
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet events = dbMan.filterEventsByCriteria(locationID, start, keyword, types);
		List<Map<EventInfoSearch, String>> eventsToReturn = Collections.synchronizedList
						(new ArrayList<Map<EventInfoSearch,String>>());
		
		while(events.next())
		{		
			Map<EventInfoSearch, String> event = Collections.synchronizedMap(new HashMap<EventInfoSearch,String>());
			event.put(EventInfoSearch.EVENT_ID,		events.getString("E.eventID"));
			event.put(EventInfoSearch.TITLE,		events.getString("E.title"));
			event.put(EventInfoSearch.VENUE,		events.getString("E.venue"));
			event.put(EventInfoSearch.EVENT_DATE,	events.getString("E.eventDate"));
			event.put(EventInfoSearch.START_TIME,	events.getString("E.startTime"));
			event.put(EventInfoSearch.END_TIME,		events.getString("E.endTime"));	
			event.put(EventInfoSearch.CREATOR,		events.getString("U.name"));
			event.put(EventInfoSearch.CREATOR_ID,	events.getString("U.userID"));
			event.put(EventInfoSearch.LOCATION_ID,	Integer.toString(locationID));
			event.put(EventInfoSearch.CITY,			events.getString("L.city"));
			event.put(EventInfoSearch.EVENT_TYPE, 	events.getString("ETY.eventType"));
			eventsToReturn.add(event);
		}
	
		return eventsToReturn;
	}
}
						

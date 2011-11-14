package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Database.DatabaseManager;
import Database.DatabaseManager.SortPosts;
import Database.DatabaseManager.SortReviews;

public class Event 
{
	public enum EventContent {
	    GEN_INFO,
	    VENUE_INFO,
	    PRICE_INFO,
	    TRANSPORT_INFO,
	    AWARENESS_INFO,
	    VIDEOS,
	    LINKS,
	    OTHER_INFO; 
	}
	
	public enum StatType {
	    AGE_STAT,
	    CITY_STAT; 
	}
	
	private static boolean exists(int evID) 
	{
		try {
			return DatabaseManager.getInstance().eventExists(evID);
		} catch (SQLException e) {			
			e.printStackTrace();			
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();			
		}
		return false;		
	}
	
	public static boolean deleteEvent(int evID)
	{
		return false;
	}
	
	public static Map<String, String> getExistingEvent(int evID) 
	{ 
		if(exists(evID))  
		{ 	
			Map<String, String> event;
			try {
				event = getEventMap(evID);
				return event;
			} catch (ClassNotFoundException e) {				
				e.printStackTrace();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			return null;
		}			
		else
		{
			return null;
		}
	}
	
	private static Map<String, String> getEventMap(int evID) throws ClassNotFoundException, SQLException
	{
		Map<String, String> event = Collections.synchronizedMap(new HashMap<String,String>());
		
		DatabaseManager dbMan = DatabaseManager.getInstance();
		
		ResultSet eventInfo = dbMan.findEvent(evID);
		ResultSet eventTypes = dbMan.findEventTypes(evID);
		
		while(eventInfo.next())
		{				
			event.put("eventID", eventInfo.getString(1));
			event.put("title", eventInfo.getString(2));
			event.put("venue", eventInfo.getString(3));
			event.put("address", eventInfo.getString(4));
			event.put("eventDate", eventInfo.getString(5));							
			event.put("startTime", eventInfo.getString(6));			
			event.put("endTime", eventInfo.getString(7));		
			event.put("rating", eventInfo.getString(8));		
			event.put("numAttendees", eventInfo.getString(9));			
			event.put("isLocked", eventInfo.getString(10));
			
			event.put("genDesc", eventInfo.getString(11));
			event.put("venueDesc", eventInfo.getString(12));
			event.put("priceDesc", eventInfo.getString(13));
			event.put("transportDesc", eventInfo.getString(14));
			event.put("awareDesc", eventInfo.getString(15));
			event.put("videos", eventInfo.getString(16));
			event.put("links", eventInfo.getString(17));
			event.put("otherInfo", eventInfo.getString(18));
			
			event.put("creator", eventInfo.getString(19));
			event.put("creatorID", eventInfo.getString(20));
			event.put("locationID", eventInfo.getString(21));
			event.put("city", eventInfo.getString(22));
			event.put("state", eventInfo.getString(23));
			event.put("country", eventInfo.getString(24));			
		}
		
		String types = "";
		while(eventTypes.next())
		{			
			types = types + eventTypes.getString(1) + ",";
		}
		event.put("eventTypes", types);
	    return event;
	}
	
	public static int createNewEvent(String title, int creatorID, int locationID, 
			 String address, String venue, Date startDateTime, 
			 Date endTime, String[] types)  
	{  	   
		   try {			   
			  return createEvent(title,creatorID,locationID, 
							 address, venue, startDateTime, 
							 endTime, types);			  
			 
			} catch (SQLException e) {			
				e.printStackTrace();				
			} catch (ClassNotFoundException e) {			
				e.printStackTrace();				
			}	
		   return 0;
	}
	
	private static int createEvent(String title, int creatorID, int locationID, 
			 String address, String venue, Date startDateTime, 
			 Date endTime, String[] types) throws SQLException, ClassNotFoundException 
	{		
		    DatabaseManager dbMan = DatabaseManager.getInstance();
		    
			java.sql.Date startDateForSQL = new java.sql.Date(startDateTime.getTime());
			
			SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH:mm:ss");
	        String startTimeForSQL =  dateFormatGmt.format(startDateTime);
	        String endTimeForSQL =  dateFormatGmt.format(endTime);
				        	        
			int eventID = dbMan.newEvent(title, creatorID, locationID, address, venue,
					startDateForSQL,startTimeForSQL, endTimeForSQL, types); 
			
			return eventID;
	}
	
	
	public static boolean setContent(int eventID, String genDesc, String venueDesc, String priceInfo,
			String transport, String awareness, String videos, String links, String otherInfo)
	{
		
		String[] content = new String[8];
		content[0] = genDesc;
		content[1] = venueDesc;
		content[2] = priceInfo;
		content[3] = transport;
		content[4] = awareness;
		content[5] = videos;
		content[6] = links;
		content[7] = otherInfo;
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();
			dbMan.setEventContent(eventID,content);
			return true;
		} catch (Exception e) {			
			e.printStackTrace();
			
		}
		return false;	
	}
	
		
	public HashMap<String,Integer> showStatistics(int eventID, StatType type)
	{		
		HashMap<String, Integer> m = (HashMap<String, Integer>) Collections.synchronizedMap(new HashMap<String,Integer>());
		
		return null;		
	}
	
	
	public static boolean editEventContent(int eventID, String content, EventContent type)
	{
		String contentType = "";		
		switch (type)
		{
			case GEN_INFO:
			{contentType = "generalDesc";}
			break;
			
			case VENUE_INFO:
			{contentType = "venueDesc";}
			break;
			
			case PRICE_INFO:
			{contentType = "priceDesc";}
			break;	
			
			case TRANSPORT_INFO:
			{contentType = "transportDesc";}
			break;
			
			case AWARENESS_INFO:
			{contentType = "awareInfo";}
			break;
			
			case VIDEOS:
			{contentType = "videos";}
			break;
			
			case LINKS:
			{contentType = "links";}
			break;
			
			case OTHER_INFO:
			{contentType = "otherInfo";}
			break;
		}
		
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();
			dbMan.editEventContent(eventID, content, contentType);	
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return false;		
	}	
	
	public boolean editEventInfo(int eventID, String nTitle, String nVenue, Date startDateTime, Date nEndTime, String nAddress)
	{
		java.sql.Date startDateForSQL = new java.sql.Date(startDateTime.getTime());
		
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH:mm:ss");
        String startTimeForSQL =  dateFormatGmt.format(startDateTime);
        String endTimeForSQL =  dateFormatGmt.format(nEndTime);
        
        try {
        	DatabaseManager dbMan = DatabaseManager.getInstance();
			dbMan.editEventInfo(eventID, nTitle, nAddress, nVenue, 
					startDateForSQL, startTimeForSQL, endTimeForSQL);
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
        return false;		
	}
	
	public boolean addReview(int eventID)
	{
		return false;
		
	}
	
	public boolean addPost(int eventID)
	{
		return false;
		
	}
	
	public boolean removeReview(int eventID, int reviewID)
	{
		return false;
		
	}
	
	public boolean removePost(int eventID, int postID)
	{
		return false;
		
	}
	
	public ArrayList<HashMap<String,String>> displayReviews(int eventID, SortReviews type)
	{
		return null;
		
	}
	
	public ArrayList<HashMap<String,String>> displayPosts(int eventID, SortPosts type)
	{
		return null;
		
	}
	
	public boolean lockEvent(int eventID)
	{
		return false;
		
	}
	
	public boolean unlockEvent(int eventID)
	{
		return false;
		
	}
	
	
	
	
	
}

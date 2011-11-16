package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseManager;

import Models.DiscussionPost.PostInfo;
import Models.DiscussionPost.SortPosts;
import Models.Review.ReviewInfo;
import Models.Review.SortReviews;

public class Event 
{
	
	
	public enum EventInfo
	{
		EVENT_ID,
		TITLE,
		VENUE,
		ADDRESS,
		EVENT_DATE,
		START_TIME,
		END_TIME,
		RATING,
		NUM_ATTENDEES,
		IS_LOCKED,
		CREATOR,
		CREATOR_ID,
		LOCATION_ID,
		CITY,
		STATE,
		COUNTRY,
		EVENT_TYPES,
	
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
	
	public static final String 
		ONE_TO_NINE = "1 to 9",
		TEN_TO_NINETEEN = "10 to 19",
		TWENTY_TO_TWENTYNINE = "20 to 29",
		THIRTY_TO_THIRTYNINE = "30 to 39",
		FORTY_TO_FORTYNINE = "40 to 49",
		FIFTY_TO_FIFTYNINE = "50 to 59",
		SIXTY_PLUS = "60+";	
	
	
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
	
	public static Map<EventInfo, String> getExistingEvent(int evID) 
	{ 
		if(exists(evID))  
		{ 	
			Map<EventInfo, String> event;
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
	
	private static Map<EventInfo, String> getEventMap(int evID) throws ClassNotFoundException, SQLException
	{
		Map<EventInfo, String> event = Collections.synchronizedMap(new HashMap<EventInfo,String>());
		
		DatabaseManager dbMan = DatabaseManager.getInstance();
		
		ResultSet eventInfo = dbMan.findEvent(evID);
		ResultSet eventTypes = dbMan.findEventTypes(evID);
		
		while(eventInfo.next())
		{				
			event.put(EventInfo.EVENT_ID, eventInfo.getString("E.eventID"));
			event.put(EventInfo.TITLE, eventInfo.getString("E.title"));
			event.put(EventInfo.VENUE, eventInfo.getString("E.venue"));
			event.put(EventInfo.ADDRESS, eventInfo.getString("E.address"));
			event.put(EventInfo.EVENT_DATE, eventInfo.getString("E.eventDate"));							
			event.put(EventInfo.START_TIME, eventInfo.getString("E.startTime"));			
			event.put(EventInfo.END_TIME, eventInfo.getString("E.endTime"));		
			event.put(EventInfo.RATING, eventInfo.getString("E.rating"));		
			event.put(EventInfo.NUM_ATTENDEES, eventInfo.getString("E.numAttendees"));			
			event.put(EventInfo.IS_LOCKED, eventInfo.getString("E.isLocked"));
			
			event.put(EventInfo.GEN_INFO, eventInfo.getString("C.generalDesc"));
			event.put(EventInfo.VENUE_INFO, eventInfo.getString("C.venueDesc"));
			event.put(EventInfo.PRICE_INFO, eventInfo.getString("C.priceDesc"));
			event.put(EventInfo.TRANSPORT_INFO, eventInfo.getString("C.transportDesc"));
			event.put(EventInfo.AWARENESS_INFO, eventInfo.getString("C.awareInfo"));
			event.put(EventInfo.VIDEOS, eventInfo.getString("C.videos"));
			event.put(EventInfo.LINKS, eventInfo.getString("C.links"));
			event.put(EventInfo.OTHER_INFO, eventInfo.getString("C.otherInfo"));
			
			event.put(EventInfo.CREATOR, eventInfo.getString("U.name"));
			event.put(EventInfo.CREATOR_ID, eventInfo.getString("U.userID"));
			event.put(EventInfo.LOCATION_ID, eventInfo.getString("L.locationID"));
			event.put(EventInfo.CITY, eventInfo.getString("L.city"));
			event.put(EventInfo.STATE, eventInfo.getString("L.state"));
			event.put(EventInfo.COUNTRY, eventInfo.getString("L.country"));			
		}
		
		String types = "";
		while(eventTypes.next())
		{			
			types = types + eventTypes.getString("T.eventType") + ",";
		}
		event.put(EventInfo.EVENT_TYPES, types);
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
	
		
	public static List<String[]> showStatistics(int eventID, StatType type)
	{		
		List<String[]> statistics = Collections.synchronizedList(new ArrayList<String[]>());
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();			
		
			switch(type)
			{
				case AGE_STAT:
				{
					ResultSet resultInfo = dbMan.showAgeStatistics(eventID);				
					while(resultInfo.next())
					{	
						String[] stats = new String[2];
						stats[0] = resultInfo.getString("ages");
						stats[1] = resultInfo.getString("attendees");
						statistics.add(stats);
					}	
					break;
				}
				case CITY_STAT:
				{
					ResultSet resultInfo = dbMan.showCityStatistics(eventID);				
					while(resultInfo.next())
					{	
						String[] stats = new String[2];
						stats[0] = resultInfo.getString("L.city") + "," +
								   resultInfo.getString("L.state")+ "," + 
								   resultInfo.getString("L.country");
						stats[1] = resultInfo.getString("attendees");
						statistics.add(stats);
					}				
					break;
				}
			}	
			
			return statistics;
				
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	
	public static boolean editEventContent(int eventID, String content, EventInfo type)
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
	
	public static boolean editEventInfo(int eventID, String nTitle, String nVenue, Date startDateTime, Date nEndTime, String nAddress)
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
	
	public static float addReview(int eventID, int userID, String content, int rating)
	{
		return Review.createReview(eventID, userID, content, rating);
	}
	
	public static boolean addPost(int eventID, int userID, String content)
	{
		return DiscussionPost.createPost(eventID, userID, content);		
	}
	
	public static float removeReview(int reviewID, int eventID)
	{
		return Review.delete(reviewID, eventID);		
	}
	
	public static boolean removePost(int postID, int eventID)
	{
		return DiscussionPost.delete(postID, eventID);		
	}
	
	public static List<Map<ReviewInfo,String>> displayReviews(int eventID, SortReviews type)
	{
		List<Map<ReviewInfo, String>> reviews;
		try {
			reviews = Review.getReviews(eventID, type);
			return reviews;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
		
		
	}
	
	public static List<Map<PostInfo, String>> displayPosts(int eventID, SortPosts type)
	{
		List<Map<PostInfo, String>> posts;
		try {
			posts = DiscussionPost.getPosts(eventID, type);
			return posts;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
		
	}
	
	public static boolean lockEvent(int eventID)
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();
			dbMan.lockEvent(eventID);	
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return false;		
	}
	
	public static boolean unlockEvent(int eventID)
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();
			dbMan.unlockEvent(eventID);	
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return false;		
	}
	
	
	
	
	
}

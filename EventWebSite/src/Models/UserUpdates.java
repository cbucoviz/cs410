package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;


import Database.DatabaseManager;
import Servlets.SessionVariables;

public class UserUpdates 
{
	public enum UpdateType
	{
		NEW_EV_BY_USER,
		NEW_EV_BY_LOCATION,
		NEW_REVIEW,
		NEW_DISCUSSION,
		EVENT_EDITTED;		
	}
	
	
    private static final Map<String, HttpSession> sessions = Collections.synchronizedMap
    											(new HashMap<String,HttpSession>());
    
    /**
     * Whenever a user logs in, this function adds this user's session as a future 
     * reference, until this user logs off.
     * @param key - name of the user.
     * @param value - session of this user
     */
    public static void addToSessions(String key, HttpSession value)
    {
    	sessions.put(key,value);    	
    }
    
    /**
     * Removes a session of the given user from the global map once this user logs off
     * @param key - name of the user
     */
    public static void removeFromSessions(String key)
    {
    	sessions.remove(key);
    }
    
    
    /**
     * The main function for updating live updates. It provides all necessary information
     * to create an html paragraph, which lists what event was updates/created, who did
     * it, where the event is and, when applicable, what section of the event was edited.
     * It updates sessions of every affected user (who are also online) with a newly
     * created update.
     * @param type - type of an update (new review, new event, etc.)
     * @param eventTitle - title of the event
     * @param eventID - id of the event
     * @param updator - name of a user who updates the event/creates a new one
     * @param updatorID - id of this user
     * @param location - city for the given event
     * @param locationID -  id of that location
     * @param editType - applicable only when a content of the event is changed (like 
     * Event Description, Venue Description, etc.)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void addUpdate(UpdateType type, String eventTitle, int eventID, 
    							String updator, int updatorID, String location, 
    							int locationID, String editType)
    							throws SQLException, ClassNotFoundException
    {   
    	String update ="";    	
	   	switch(type)
	   	{
		   	case EVENT_EDITTED:
		   	{
		   		DatabaseManager dbMan = DatabaseManager.getInstance();
		   		ResultSet relevantUsers = dbMan.findEventSubsribers(eventID);		   		
		   		
		   		ArrayList<String> onlineUsers = filterUsers(relevantUsers);		
		   		
		   		for(int h=0; h<onlineUsers.size(); h++)
		   		{
		   			String username = onlineUsers.get(h);
			   		if(editType!=null)
			   		{
			   			update = "<p>User "+updator+" (" +
						   		"<a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"sub="+username+"&usid="+updatorID+"\">Subscribe</a>) " +
						   		"has editted <b>" +editType+"</b> section of the event " +
						   		"<a href=\"http://localhost/EventWebSite/eventPage.jsp?id="+eventID+"\">" +
								""+eventTitle+"</a> (in <a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"id="+locationID+"\">"+location+"</a>)</p>";			   			
			   		}
			   		else
			   		{
			   			update = "<p>User "+updator+" (" +
						   		"<a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"sub="+username+"&usid="+updatorID+"\">Subscribe</a>) " +
						   		"has editted the main section of the event " +
						   		"<a href=\"http://localhost/EventWebSite/eventPage.jsp?id="+eventID+"\">" +
								""+eventTitle+"</a> (in <a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"id="+locationID+"\">"+location+"</a>)</p>";
			   		
			   		}
		   			
		   			updateSession(username, update);
		   			update = "";
		   		}		   		
		   	}
		   	break;
		   	
		   	case NEW_REVIEW:
		   	{
		   		DatabaseManager dbMan = DatabaseManager.getInstance();
		   		ResultSet relevantUsers = dbMan.findEventSubsribers(eventID); 
		   		ArrayList<String> onlineUsers = filterUsers(relevantUsers);		
		   		
		   		for(int h=0; h<onlineUsers.size(); h++)
		   		{
		   			String username = onlineUsers.get(h);			   		
			   		update = "<p>User "+updator+" (" +
						   		"<a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"sub="+username+"&usid="+updatorID+"\">Subscribe</a>) " +
						   		"has just written a new review in the event " +
						   		"<a href=\"http://localhost/EventWebSite/eventPage.jsp?id="+eventID+"\">" +
								""+eventTitle+"</a> (in <a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"id="+locationID+"\">"+location+"</a>)</p>";
		   			updateSession(username, update);
		   			update = "";
		   		}		   	
		   	}
		   	break;
		   	
		   	case NEW_DISCUSSION:
		   	{
		   		DatabaseManager dbMan = DatabaseManager.getInstance();
		   		ResultSet relevantUsers = dbMan.findEventSubsribers(eventID); 
		   		ArrayList<String> onlineUsers = filterUsers(relevantUsers);		
		   		
		   		for(int h=0; h<onlineUsers.size(); h++)
		   		{
		   			String username = onlineUsers.get(h);			   		
			   		update = "<p>User "+updator+" (" +
						   		"<a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"sub="+username+"&usid="+updatorID+"\">Subscribe</a>) " +
						   		"has just created a new discussion post in the event " +
						   		"<a href=\"http://localhost/EventWebSite/eventPage.jsp?id="+eventID+"\">" +
								""+eventTitle+"</a> (in <a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"id="+locationID+"\">"+location+"</a>)</p>";
		   			updateSession(username, update);
		   			update = "";
		   		}		   	
		   	}
		   	break;
		   	
		   	case NEW_EV_BY_USER:
		   	{
		   		DatabaseManager dbMan = DatabaseManager.getInstance();
		   		ResultSet relevantUsers = dbMan.findUserSubsribers(updatorID); 
		   		ArrayList<String> onlineUsers = filterUsers(relevantUsers);		
		   		
		   		for(int h=0; h<onlineUsers.size(); h++)
		   		{
		   			String username = onlineUsers.get(h);			   		
			   		update = "<p>User "+updator+" (" +
						   		"<a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"sub="+username+"&usid="+updatorID+"\">Subscribe</a>) " +
						   		"has just created a new event " +
						   		"<a href=\"http://localhost/EventWebSite/eventPage.jsp?id="+eventID+"\">" +
								""+eventTitle+"</a> (in <a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"id="+locationID+"\">"+location+"</a>)</p>";
		   			updateSession(username, update);
		   			update = "";
		   		}		   	
		   	}
		   	break;
		   	
		   	case NEW_EV_BY_LOCATION:
		   	{
		   		DatabaseManager dbMan = DatabaseManager.getInstance();
		   		ResultSet relevantUsers = dbMan.findLocaleSubsribers(locationID); 
		   		ArrayList<String> onlineUsers = filterUsers(relevantUsers);		
		   		
		   		for(int h=0; h<onlineUsers.size(); h++)
		   		{
		   			String username = onlineUsers.get(h);			   		
			   		update = "<p>User "+updator+" (" +
						   		"<a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"sub="+username+"&usid="+updatorID+"\">Subscribe</a>) " +
						   		"has just created a new event " +
						   		"<a href=\"http://localhost/EventWebSite/eventPage.jsp?id="+eventID+"\">" +
								""+eventTitle+"</a> for <a href=\"http://localhost/EventWebSite/subscribeToUser.jsp?" +
						   		"id="+locationID+"\">"+location+"</a></p>";
		   			updateSession(username, update);
		   			update = "";
		   		}		   	
		   	}
		   	break;
	   	} 
	   
    }
    
    /**
     * Filter users. Given users who may require to receive an update, it selects only
     * the ones who are online (since they are the only ones who actually need 
     * live updates)
     * @param users - users who potentially need to receive a given update
     * @return - ArrayList of users who need the update AND who are online
     * @throws SQLException
     */
    private static ArrayList<String> filterUsers(ResultSet users) throws SQLException
    {
    	ArrayList<String> listOfUsers = new ArrayList<String>();
    	while(users.next())
    	{
    		String username = users.getString("U.name");
    		if(sessions.containsKey(username))
    		{
    			listOfUsers.add(username);
    		}
    	}
    	return listOfUsers;
    }
    
    /**
     * Updates a session of users, who need to receive a live update. Each session has
     * an Array List for updates. This function adds an additional element for that list.
     * @param username - name of a user to receive this update
     * @param update - an actual update string
     */
    private static void updateSession(String username, String update)
    {
    	HttpSession session = sessions.get(username);
	    	
		@SuppressWarnings("unchecked")
		ArrayList<String> prevUpdates = (ArrayList<String>) 
	    						session.getAttribute(SessionVariables.UPDATES);
	    	
		prevUpdates.add(update);
	    session.setAttribute(SessionVariables.UPDATES, prevUpdates);
    }    
    
    /**
     * Once a user has requested live updates and receives them, this user's session
     * needs to remove all these updates.
     * @param username - name of a user receiving updates
     */
    public static void removeUpdates(String username)
    {
    	HttpSession session = sessions.get(username);
    	@SuppressWarnings("unchecked")
		ArrayList<String> prevUpdates = (ArrayList<String>) 
	    						session.getAttribute(SessionVariables.UPDATES);    	
    	prevUpdates.clear();
    	session.setAttribute(SessionVariables.UPDATES, prevUpdates);    	
    }
}



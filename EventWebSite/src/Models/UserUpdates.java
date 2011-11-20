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
    
    
    public static void addToSessions(String key, HttpSession value)
    {
    	sessions.put(key,value);
    	System.out.println("Session size is : "+sessions.size());
    }
    
    public static void removeFromSessions(String key)
    {
    	sessions.remove(key);
    }
    
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
    
    
    private static void updateSession(String username, String update)
    {
    	HttpSession session = sessions.get(username);
	    	
		@SuppressWarnings("unchecked")
		ArrayList<String> prevUpdates = (ArrayList<String>) 
	    						session.getAttribute(SessionVariables.UPDATES);
	    	
		prevUpdates.add(update);
	    session.setAttribute(SessionVariables.UPDATES, prevUpdates);
    }    
    
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



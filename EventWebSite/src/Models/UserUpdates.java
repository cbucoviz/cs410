package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import biz.source_code.base64Coder.Base64Coder;


import Database.DatabaseManager;
import Models.Event.EventInfo;
import Models.Search.EventInfoSearch;
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
    
    public static boolean exists(String key)
    {
    	return sessions.containsKey(key);
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
    							int locationID)
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
			   		
			   			update = "User "+updator+" has just editted the event " +
						   		"<a href=EventPage?eventID="+eventID+">" +
								""+eventTitle+"</a> (in "+location+")";
		   			
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
			   		update = "User "+updator+" has just written a review for " +
					   		"<a href=EventPage?eventID="+eventID+">" +
							""+eventTitle+"</a> (in "+location+")";
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
			   		update = "User "+updator+" has just started new discussion for " +
					   		"<a href=EventPage?eventID="+eventID+">" +
							""+eventTitle+"</a> (in "+location+")";
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
			   		update = "User "+updator+" has just created the event " +
						   		"<a href=EventPage?eventID="+eventID+">" +
								""+eventTitle+"</a> (in "+location+")";
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
		   		ArrayList<Integer> onlineUsersIDs = getUsersIDs(relevantUsers);	
		   		
		   		for(int h=0; h<onlineUsers.size(); h++)
		   		{		   			
		   			if(!isSubscribedToBoth(onlineUsersIDs.get(h),locationID,updatorID))
		   			{		   				
			   			String username = onlineUsers.get(h);			   		
				   		update = "User "+updator+" has just created the event " +
						   		"<a href=EventPage?eventID="+eventID+">" +
								""+eventTitle+"</a> (in "+location+")";
			   			updateSession(username, update);
			   			update = "";
		   			}
		   		}		   	
		   	}
		   	break;
	   	} 
	   
    }
    
    private static boolean isSubscribedToBoth(int userID, int subLocID, int subUserID)
    {
    	if(User.isSubscribed(userID, subUserID) && Location.isSubscribed(userID, subLocID))
    	{
    		return true;
    	}
    	return false;
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
    	users.first();
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
    
    private static ArrayList<Integer> getUsersIDs(ResultSet users) throws SQLException
    {
    	ArrayList<Integer> listOfUsers = new ArrayList<Integer>();
    	users.first();
    	while(users.next())
    	{
    		int userID = Integer.parseInt(users.getString("U.userID"));
    		String user = users.getString("U.name");
    		if(sessions.containsKey(user))
    		{
    			listOfUsers.add(userID);
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
    
    
    public static void mailAttendeesOfUpcomingEvents()
    {
    	DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet relevantUsers = dbMan.findAttendeesToMailTo();
						
			Map<Integer, HashMap<Integer,ArrayList<String>>> attendeeMap = Collections.synchronizedMap
					(new HashMap<Integer,HashMap<Integer,ArrayList<String>>>());
			
			Map<Integer, ArrayList<String>> userInfo = Collections.synchronizedMap
					(new HashMap<Integer, ArrayList<String>>());
			
			String prev ="";
			
			HashMap<Integer,ArrayList<String>> eventMap = new HashMap<Integer,ArrayList<String>>();
			
			while(relevantUsers.next())
			{
				String email = relevantUsers.getString("U.email");
				String userName = relevantUsers.getString("U.name");
				int userID = Integer.parseInt(relevantUsers.getString("U.userID"));
				String title = relevantUsers.getString("E.title");
				String eventDate = relevantUsers.getString("E.eventDate");				
				String numOfDays = relevantUsers.getString("NumberOfDays");
				
				int eventID = Integer.parseInt(relevantUsers.getString("E.eventID"));
				
				ArrayList<String> eventInfo = new ArrayList<String>();				
				eventInfo.add(title);
				eventInfo.add(eventDate);
				eventInfo.add(numOfDays);
				
				//if the first row is checked or a new user is to be e-mailed
				if(prev.isEmpty() || !prev.equals(userName))
				{
					ArrayList<String> user = new ArrayList<String>();
					
					user.add(userName);
					user.add(email);
					userInfo.put(userID, user);
					
					eventMap.clear();
					eventMap.put(eventID, eventInfo);
					attendeeMap.put(userID, eventMap);
					prev = userName;
				}
				else 
				{
					attendeeMap.get(userID).put(eventID, eventInfo);
				}
			}
			
			synchronized(attendeeMap)
			{
				Iterator<Entry<Integer, HashMap<Integer, ArrayList<String>>>> iter = 
										attendeeMap.entrySet().iterator();
			    while (iter.hasNext()) {			    	
			        Entry<Integer, HashMap<Integer, ArrayList<String>>> pairs = iter.next();			        
			       	ArrayList<String> userInfoToSend = userInfo.get(pairs.getKey());
			        emailUser(userInfoToSend,pairs.getValue());							       
			    }
			}
		
		} catch (Exception e1) {			
			e1.printStackTrace();
		} 
    }
   
    private static void emailUser(ArrayList<String> userInfo, HashMap<Integer, 
    							  ArrayList<String>> eventInfo) 
    							  throws ParseException, NamingException, MessagingException
    {
    	String userName = userInfo.get(0);
    	String email = userInfo.get(1);
    	String messageContent = "Greetings, "+userName+"!\n\n " +
    			"This is just a friendly reminder that you have the following event(s) " +
    			"happening soon:\n\n";
    	synchronized(eventInfo)
		{
			Iterator<Entry<Integer, ArrayList<String>>> iter = 
									eventInfo.entrySet().iterator();
		    while (iter.hasNext()) {			    	
		        Entry<Integer, ArrayList<String>> pairs = iter.next();			        
		        String eventPage = "http://localhost/EventWebSite/EventPage?eventID="+pairs.getKey()+"";	       
		        String title = pairs.getValue().get(0);
		        Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(pairs.getValue().get(1));
		        int numOfDays = Integer.parseInt(pairs.getValue().get(2));
		        
		        SimpleDateFormat df = new SimpleDateFormat("EEEEEEEEE, d/MMM/yyyy");		        
		        
		        String aboutTheEvent = " "+title+" is happening on "+df.format(eventDate)+", exactly " +
		        		""+numOfDays+" day(s) from the day this email was sent. Please follow the link " +
		        				"to access this event's page: "+eventPage+"\n\n";
		        messageContent = messageContent + aboutTheEvent;
		    }
		}    	
    	messageContent = messageContent + "Thank you,\n\nThe Global Events Team";
    	email = email.trim();		
	 	Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        Session session = (Session) envCtx.lookup("mail/Session");
        InternetAddress toAddress = new InternetAddress(email);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("globalevents410@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, toAddress);
        message.setSubject("Upcoming events information");
        message.setText(messageContent);
        Transport.send(message);
    }
}



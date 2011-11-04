package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.util.Date;


public class DatabaseManager 
{	
	private static DatabaseManager instance;
	private Connection connection;	
	
	public enum SortReviews {
	    EVENT_RATING,
	    RATING,
	    UPLOAD_TIME; 
	}
 
	public enum SortPosts {
	    RATING, 
	    RECENT_FIRST,
	    OLDEST_FIRST, 
	    NUMBER_OF_COMMENTS; 
	}
	
	private DatabaseManager() throws ClassNotFoundException, SQLException
	{	
		//get a driver for the MySQL database
		Class.forName("com.mysql.jdbc.Driver");
		connection = createConnection();
	}
	
	public static DatabaseManager getInstance() throws ClassNotFoundException, SQLException
	{
		if(instance == null) 
	    {
	         instance = new DatabaseManager();	         
	         return instance;
	    }	      
	    else
	      return instance;
	}
	
	/**
	 * This is the only function in the entire application that is allowed to connect to the 
	 * database.
	 * @return - Connection to MySQL database, which is then used to execute queries	 
	 * @throws SQLException - if a database access error occurs 
	 */
	private Connection createConnection() throws SQLException
	{				
		return DatabaseLogin.connect();
	}
	
	/**
	 * This is the main function for loading an Event page. It executes a query for getting
	 * all the information about the given event to be displayed on a web-page. 
	 * @param eventID - id of an event. 
	 * @return - result set to be passed to the Controller. 
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findEvent (int eventID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
				("SELECT E.eventID, E.title, E.venue, E.address, E.eventDate, E.startTime, " + 
						"E.duration, E.rating, C.generalDesc, C.venueDesc, C.priceDesc, " +
						"C.transportDesc, C.videos, C.links, U.name, U.userID, L.city,L.country, " +
						"T.eventType " +
				 "FROM events AS E, eventcontent AS C, users AS U, locations AS L, eventtypes AS T, eventsandtypes AS ET "+
	             "WHERE E.eventID = " + eventID + "" +
	             	  " AND E.creatorID = U.userID " +
	             	  " AND E.locationID = L.locationID " +
	             	  " AND C.eventID = E.eventID " +
	             	  " AND ET.eventID = E.eventID " +
	             	  " AND T.typeID = ET.eventTypeID");
		ResultSet result = statement.executeQuery();
		return result;
	}

	/**
	 * This is the function for sorting reviews. There are three ways to do so:<br/>
	 * 
	 *  - By event rating: first the sorting is done by ratings these reviews gave to
	 *  the event. Then, for each group having the same event rating, the sorting is done
	 *  by good rating - that is, the most useful reviews for a given event rating are 
	 *  the top ones.<br/>
	 *  
	 *  - By rating: first the sorting is done by the difference between a good rating
	 *  and a bad one. The higher the difference, the more useful such a review is. Then, 
	 *  if the difference is the same, the sorting is done by a good rating. So, the ones
	 *  that more people found useful go on top.<br/>
	 *  
	 *  - By upload time: the latest reviews go on top. 
	 * 
	 * @param type - type of a sort: <br/>
	 *                EventRating - by event rating;<br/>
	 *                Rating - by rating; <br/>
	 *                UploadTime - by upload time;
	 * @param eventID - id of the event
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet sortReviews(SortReviews type, int eventID) throws SQLException
	{		
		ResultSet result = null;
		PreparedStatement statement = null;
		
		switch(type)
		{
		    case EVENT_RATING: 		  
		    {	
				statement = connection.prepareStatement			
					("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
							"R.dateTime, R.eventRating " +
					 "FROM users AS U, reviews AS R " +
					 "WHERE R.eventID = "+eventID +" " +
					 	   "AND R.userID = U.userID " +
					 "ORDER BY R.eventRating DESC, R.goodRating DESC");
				result = statement.executeQuery();
		    }	
			break;
		
		
		    case RATING:
		    {	
				statement = connection.prepareStatement
					("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
							"R.dateTime, R.eventRating, R.goodRating - R.badRating AS goodVSbad " +
					 "FROM users AS U, reviews AS R " +
					 "WHERE R.eventID = "+eventID+" " +
					 	   "AND R.userID = U.userID" +
					 "ORDER BY goodVSbad DESC, R.goodRating DESC");
				result = statement.executeQuery();
		    }	
			break;
		
		    case UPLOAD_TIME:
		    {  
		    	statement = connection.prepareStatement
					("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
							"R.dateTime, R.eventRating " +
					 "FROM users AS U, reviews AS R " +
					 "WHERE R.eventID = "+ eventID +" " +
					 	   "AND R.userID = U.userID " +
					 "ORDER BY R.dateTime DESC");
				result = statement.executeQuery();
		    }	
			break;	
			
		}
		return result;
	}
	
	/**
	 * This is the function for sorting posts. There are four ways to do so:<br/>
	 * 
	 *  - By post rating: the sorting is done by difference between good rating and bad rating.
	 *  Highly liked posts go on top. Then, for each group having the same difference, the sorting 
	 *  is done by good rating. So, in general, the most liked and, at the same time, the least
	 *  disliked posts go on top. <br/>
	 *  
	 *  - By upload time: two choices - the sorting can be done starting from the most recent 
	 *  posts or starting from the oldest ones<br/>
	 *  
	 *  - By number of comments: the most commented posts go on top.
	 * 
	 * @param type - type of a sort: <br/>
	 *                Rating - by rating;<br/>
	 *                RecentFirst - by upload time (the most recent go first); <br/>
	 *                OldestFirst - by upload time (the oldest ones go first); <br/>
	 *                NumberOfComments - by number of comments; 
	 *                	             
	 * @param eventID - id of the event
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	
	public ResultSet sortComments(SortPosts type, int eventID) throws SQLException
	{
		ResultSet result = null;
		PreparedStatement statement = null;
		
		switch(type)
		{
			case NUMBER_OF_COMMENTS:	
			{	
				statement = connection.prepareStatement
					("SELECT P.postID, P.userID, U.name, P.postContent, P.dateTime, " +
							"P.goodRating, P.goodRating - P.ratingDifference AS 'badRating', " +
							"P.numComments " +
					 "FROM discussionpost AS P, users AS U " +
					 "WHERE P.eventID = " + eventID + " " +
					 	   "AND P.userID = U.userID " +
					 "ORDER BY P.numComments DESC");
				result = statement.executeQuery();	
			}	
			break;
		
		
			case RECENT_FIRST:
			{	
				statement = connection.prepareStatement
					("SELECT P.postID, P.userID, U.name, P.postContent, P.dateTime, " +
							"P.goodRating, P.goodRating - P.ratingDifference AS 'badRating'," +
							"P.numComments" +
					"FROM discussionpost AS P, users AS U " +
					"WHERE P.eventID = " + eventID + " " +
						  "AND P.userID = U.userID" +
					"ORDER BY P.dateTime DESC");
				result = statement.executeQuery();
			}	
			break;
		
			case OLDEST_FIRST:
			{	
				statement = connection.prepareStatement				
					("SELECT P.postID, P.userID, U.name, P.postContent, P.dateTime, " +
							"P.goodRating, P.goodRating - P.ratingDifference AS 'badRating', " +
							"P.numComments " +
					"FROM discussionpost AS P, users AS U " +
					"WHERE P.eventID = " + eventID + " " +
					      "AND P.userID = U.userID " +
					"ORDER BY P.dateTime ASC");
				result = statement.executeQuery();
			}	
			break;
		
			case RATING:
			{	
				statement = connection.prepareStatement
					("SELECT P.postID, P.userID, U.name, P.postContent, P.dateTime, " +
							"P.goodRating, P.goodRating - P.ratingDifference AS 'badRating', " +
							"P.numComments " +
					"FROM discussionpost AS P, users AS U " +
					"WHERE P.eventID = " + eventID + " " +
						  "AND P.userID = U.userID " +
					"ORDER BY P.ratingDifference DESC, P.goodRating DESC");
				result = statement.executeQuery();
			}	
			break;
		}
		
		return result;
	}

	/**
	 * Shows all comments associated with a given post, sorted by date
	 * (most recent are on top)
	 * @param postID - id of a post
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet revealComments(int postID) throws SQLException
	{		
		PreparedStatement statement = connection.prepareStatement
				("SELECT C.commentBody, C.dateTime, C.userID, U.name " +
				"FROM comments AS C, users AS U, discussionpost AS P " +
				"WHERE C.postID = " + postID + " " +
					  "AND C.postID = P.postID " +
					  "AND C.userID = U.userID " +
				"ORDER BY C.dateTime DESC");
		ResultSet result = statement.executeQuery();
		
		return result;
	}

	/**
	 * This function serves as a way to get statistics on age data for a particular event.
	 * The data forms a table with two columns: the first one containing rows each representing
	 * an age group (such as 20 to 29), and the second column lists how many people of that age
	 * actually attended the event (or at least indicated they will attend it)
	 * @param eventID - an id of the event
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet showAgeStatistics(int eventID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT " +
			   "CASE WHEN U.age BETWEEN 1 AND 9 THEN '1 to 9' " +
			        "WHEN U.age BETWEEN 10 and 19 THEN '10 to 19' " +
			        "WHEN U.age BETWEEN 20 and 29 THEN '20 to 29' " +
			        "WHEN U.age BETWEEN 30 and 39 THEN '30 to 39' " +
			        "WHEN U.age BETWEEN 40 and 49 THEN '40 to 49' " +
			        "WHEN U.age BETWEEN 50 and 59 THEN '50 to 59' " +
			        "WHEN U.age >= 60 THEN '60 +' END AS ages, " +
			        				"COUNT(U.age) AS attendees " +
			"FROM users AS U, eventattendees A " +
			"WHERE A.attendeeID = U.userID " +
				  "AND A.eventID = " + eventID + " " +
			"GROUP BY ages");
		ResultSet result = statement.executeQuery();
		
		return result;
	}

	/**
	 * This function serves as a way to get statistics on city data for a particular event.
	 * The data forms a table with three columns: the first one contains rows with names 
	 * of the cities from where attendees of the event came from; the second one
	 * lists countries where a particular city is located in and the third column lists 
	 * how many people from each of these listed cities actually attended the event (or at least 
	 * indicated they will attend it)
	 * @param eventID - an id of the event
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet showCityStatistics(int eventID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT L.city, L.country, COUNT(U.userID) AS 'attendees' " +
			"FROM  users AS U, eventattendees AS A, locations AS L " +
			"WHERE A.eventID = " + eventID + " " +
				  "AND A.attendeeID = U.userID " +
				  "AND L.locationID = U.locationID " +				  
		    "GROUP BY L.city");
		ResultSet result = statement.executeQuery();
		
		return result;
	}
    
	/**
	 * Lists all the cities where there are events which titles contain a given keyword.
	 * Lists also how many matching events happen in each of the listed cities.<br/>
	 * <i><b>Important constraint:</b> events must be future events or at least happen on 
	 * the same date as the date when such a request was given.</i>
	 * 
	 * @param keyword - a keyword that should be part of a title of matching events
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findEventsLocations(String keyword) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT L.locationID, L.city, L.country, COUNT(E.eventID) AS 'Number of Events' " +
			"FROM locations AS L, events AS E " +
			"WHERE E.locationID = L.locationID " +
				  "AND E.title LIKE '%"+ keyword + "%' " +
				  "AND E.eventDate >= CURDATE() " +
			"GROUP BY L.city");
        ResultSet result = statement.executeQuery();
		
		return result;
	}

	/**
	 * Lists all the cities where there are events with a given type (festival, concert, etc.).
	 * Lists also how many matching events happen in each of the listed cities.<br/>
	 * <i><b>Important constraint:</b> events must be future events or at least happen on 
	 * the same date as the date when such a request was given.</i>
	 * 
	 * @param type - type of events (festival, concert, etc.).
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findEventsLocationsByType(String type) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT L.locationID, L.city, L.country, T.typeID, " +
					"COUNT(EaT.eventID) AS 'Number of Events' " +
			"FROM locations AS L, eventsandtypes AS EaT, eventTypes AS T, events AS E " +
			"WHERE E.locationID = L.locationID " +
			      "AND EaT.eventTypeID = T.typeID " +
			      "AND T.eventType = "+ type +" " +
			      "AND E.eventID = EaT.eventID " +
			      "AND E.eventDate >= CURDATE() " +			      
			"GROUP BY L.city");
		ResultSet result = statement.executeQuery();
		
		return result;
	}

	/**
	 * Lists all events and their creators given a specific location and a keyword
	 * these events should contain in their titles.<br/>
	 * <i><b>Important constraint:</b> events must be future events or at least happen on 
	 * the same date as the date when such a request was given.</i>
	 * @param locationID - an id of the location
	 * @param keyword - a keyword that should be part of a title of matching events
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findEvents (int locationID, String keyword) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
	      ("SELECT E.eventID, E.title, E.venue, E.eventDate, E.startTime, E.duration, U.name " +
	      "FROM events AS E, users AS U, locations AS L " +
	      "WHERE L.locationID = "+locationID+" " +
	      		"AND E.locationID = L.locationID " +
	      		"AND U.userID = E.creatorID " +
	      		"AND E.title LIKE '%"+keyword+"%' " +
	      		"AND E.eventDate >= CURDATE()");
	    ResultSet result = statement.executeQuery();
		
		return result;
	}

	/**
	 * Lists all events and their creators given a specific location and a type
	 * these events should be of.<br/>
	 * <i><b>Important constraint:</b> events must be future events or at least happen on 
	 * the same date as the date when such a request was given.</i>
	 * @param locationID - an id of the location
	 * @param type - type of events (festival, concert, etc.).
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findEventsByType (int locationID, int typeID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
	      ("SELECT E.eventID, E.title, E.venue, E.eventDate, E.startTime, E.duration, U.name " +
	      "FROM events AS E, users AS U, locations AS L, eventsandtypes AS T " +
	      "WHERE L.locationID = "+locationID+" " +
	      		"AND E.locationID = L.locationID " +
	      		"AND U.userID = E.creatorID " +
	      		"AND E.eventID = T.eventID " +
	      		"AND T.eventTypeID = "+typeID+" " +
	    		"AND E.eventDate >= CURDATE()");
	    ResultSet result = statement.executeQuery();
			
		return result;
	}

	/**
	 * An advanced version of find events functions. It takes a location, a range of dates,
	 * a keyword (a word that should appear in a title of any matching event) and a list of types
	 * (each matching event should at least have one listed type to belong to), and then finds
	 * all the events that match those requirements.  	 	 
	 * @param locationID -  an id of a location
	 * @param from - any matching events must be on that date or later
	 * @param to - any matching events must be on that date or earlier
	 * @param keyword - a keyword that should be part of a title of matching events
	 * @param types - a list of types of events (festival, concert, etc.).
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet filterEvents (int locationID, Date from, Date to, String keyword, String[] types) throws SQLException
	{
		java.sql.Date sqlFrom = new java.sql.Date(from.getTime());
		java.sql.Date sqlTo = new java.sql.Date(to.getTime());
		 
		String typesSQL;
	    
		if(types.length == 0)
	          typesSQL = "";
	    else
	    {
	         typesSQL = " (";
		     for (int i = 0; i < types.length; i++)
		     {
		        if (i!=0)
		           typesSQL = typesSQL + " OR T.eventTypeID = " + types[i];
		        else
		           typesSQL = typesSQL + "T.eventTypeID = " + types[i];	
		     }
		     typesSQL = typesSQL + ")";
	    }
		
		PreparedStatement statement = connection.prepareStatement
			("SELECT E.eventID, E.title, E.venue, E.eventDate, E.startTime, E.duration, U.name " +
		    "FROM events AS E, users AS U, locations AS L, eventsandtypes AS EaT, eventtypes as T " +
		    "WHERE L.locationID = locationID " +
		    	  "AND E.locationID = L.locationID " +
		    	  "AND U.userID = E.creatorID " +
		    	  "AND E.eventID = EaT.eventID " +
		    	  "AND EaT.eventTypeID = T.typeID " +
		    	  "AND "+typesSQL+" " +
		    	  "AND E.eventDate BETWEEN "+sqlFrom+" AND "+sqlTo+" " +
		    	  "AND E.title LIKE '%"+keyword+"%'");
		ResultSet result = statement.executeQuery();
		
		return result;
	}

	/**
	 * Finds all the events a given user is subscribed to and sorts them by starting date
	 * (so the events that will happen in the future go on top). It also shows for each event
	 * whether this event was edited while the user was away.
	 * @param myUserID - an id of a user
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findMySubscribedEvents(int myUserID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT " +
				  "CASE WHEN E.lastMod > Me.lastVisited THEN 'isEdited' END AS 'Updates', " +
				         "E.eventID, E.title, E.venue, E.eventDate, E.startTime, E.duration, " +
				         "U.userID, U.name AS creator, L.locationID, L.city, L.country " +
		   "FROM events AS E, eventSubscribers AS S, users AS U, users AS Me,locations AS L " +
		   "WHERE E.eventID = S.eventID " +
		         "AND S.subscriberID = "+myUserID+" " +
		         "AND Me.userID = S.subscriberID " +
		         "AND U.userID = E.creatorID " +
		         "AND L.locationID = E.locationID " +
		   "ORDER BY E.eventDate DESC");
		ResultSet result = statement.executeQuery();
		    		
		return result;
	}
	
	/**
	 * Finds all the events a given user has created and sorts them by starting date
	 * (so the events that will happen in the future go on top). It also shows for each event
	 * whether this event was edited while the user was away.
	 * @param myUserID - an id of a user
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findMyEvents(int myUserID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT " +
				"CASE WHEN E.lastMod > U.lastVisited THEN 'isEdited' END AS 'Updates', " +
				     "E.eventID, E.title, E.venue, E.eventDate, E.startTime, E.duration, " +
				     "L.locationID, L.city, L.country " +
		    "FROM events AS E, users AS U, locations AS L " +
		    "WHERE E.creatorID = "+myUserID+" " +
		    	  "AND U.userID = E.creatorID " +
		    	  "AND L.locationID = E.locationID " +
		    "ORDER BY E.eventDate DESC");
		ResultSet result = statement.executeQuery();
		    		
		return result;
	}

	/**
	 * Finds all locations a given user is subscribed to and counts how many new events
	 * were created for each locale while the user was away (if any). 
	 * @param userID - an id of a user
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findSubscribedLocales(int userID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT COUNT(Temp.newEvents) AS 'New Events', Temp.city, Temp.locationID " +
			"FROM " +
					"(SELECT CASE WHEN E.creationDate > U.lastVisited THEN 'isNew' END AS 'newEvents', " +
								  "L.city, L.locationID " +
					"FROM events AS E, subscribedLocales AS SL, users AS U, locations AS L " +
				    "WHERE SL.userID = "+userID+" " +
				          "AND U.userID = SL.userID " +
				          "AND L.locationID = SL.locationID " +
				          "AND E.locationID = SL.locationID) " +				          
				    "AS Temp");
		ResultSet result = statement.executeQuery();
		
		return result;
	}
	
	/**
	 * This function returns information on all events created in a specific location
	 * when a given user was off-line. The function is used, when a user clicks on one of the 
	 * subscribed locations in a profile and all the new events for this location need to be listed.
	 * @param locationID - an id of a location of matching events
	 * @param userID - an id of a user
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet getNewEvents(int locationID, int userID) throws SQLException
	{
		 PreparedStatement statement = connection.prepareStatement
			 ("SELECT " +
			       "E.eventID, E.title, E.venue, E.eventDate, " +
			       "E.startTime, E.duration, L.locationID, L.city, L.country, " +
			       "U.userID, U.name " +
			"FROM events AS E, users AS U, users AS Me, locations AS L " +
			"WHERE L.locationID = "+locationID+" " +
			      "AND E.creationDate > Me.lastVisited " +
			      "AND E.locationID = L.locationID " +
			      "AND Me.userID = "+userID+" " +
			      "AND U.userID = E.creatorID " +
			"ORDER BY E.eventDate DESC");
		ResultSet result = statement.executeQuery();
					
		return result;
	}
    
	/**
	 * This function lists all the users a given user is subscribed to, together with
	 * a number of events for each subscribed user indicating how many events this user 
	 * has created while the subscribing user was off-line.
	 * @param subscriberID - an id of a subscriber
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findSubscribedUsers(int subscriberID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT COUNT(Temp.newEvents) AS 'New Events', Temp.name, Temp.userID " +
			"FROM " +
			      "(SELECT " +
			          "CASE WHEN E.creationDate > Me.lastVisited THEN 'isNew' END AS 'newEvents', " +
			               "U.name, U.userID " +
			      "FROM events AS E, usersubscribers AS US, users AS U, users AS Me " +
			      "WHERE US.subscriberID = "+subscriberID+"  " +
			            "AND U.userID = US.userID  " +
			            "AND Me.userID = US.subscriberID " +
			            "AND E.creatorID = U.userID  " +
			 
			 		"UNION " +
			 
			 		"SELECT " +
			 		   "CASE WHEN U.createdEvents <=> 0 THEN null END AS 'newEvents', " +
			 			    "U.name, U.userID  " +
			 	    "FROM usersubscribers AS US, users AS U " +
			 	    "WHERE US.subscriberID = "+subscriberID+"  " +
			 	          "AND U.userID = US.userID " +
			 	          "AND U.createdEvents <=> 0) " +
			 	  "AS Temp " +
			"GROUP BY Temp.name " +
			"ORDER BY Temp.name");		
		ResultSet result = statement.executeQuery();
		
		return result;
	}

	/**
	 * This function returns information on all events created by a specific user (creator)
	 * when a current user (subscriber) was off-line. The function is used, when a current user
	 * places a cursor over one of the subscribed users in a profile and a pop-up window shows up
	 * with the information. 
	 * @param creatorID - an id of a creator of the new events
	 * @param userID - an id of a user
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findAllNewEvents(int creatorID, int userID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT E.eventID, E.title, E.venue, E.eventDate, E.startTime, E.duration, " +
					"L.locationID, L.city, L.country " +
			"FROM events AS E, locations AS L, users AS Me " +
			"WHERE E.creatorID = "+creatorID+" "+
			     "AND Me.userID = "+userID+" " +
			     "AND E.creationDate > Me.lastVisited " +
			     "AND L.locationID = E.locationID " +
			"ORDER BY E.eventDate DESC");
		ResultSet result = statement.executeQuery();
					
		return result;
	}
    
	/**
	 * This function returns information on all events created by a specific user. The function 
	 * is used, when a current user clicks on one of the subscribed users in a profile and 
	 * all the events created by this user need to be listed.
	 * @param locationID - an id of a location of matching events
	 * @param userID - an id of a user
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet findEvents(int userID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT E.eventID, E.title, E.venue, E.eventDate, E.startTime, E.duration, " +
					"L.locationID, L.city, L.country " +
		    "FROM events AS E, locations AS L, users AS U " +
		    "WHERE U.userID = "+userID+" " +
		         "AND E.creatorID = U.userID " +
		         "AND E.locationID = L.locationID " +
		    "ORDER BY E.eventDate DESC");
	    ResultSet result = statement.executeQuery();
		
		return result;
	}

	/**
	 * This function returns top 10 events for a given location.
	 * Any given event is rated based on how many subscribers it has and, if
	 * any events have equal number of subscribers, these events are then rated based on
	 * how many of these subscribers actually decided to attend the event.
	 * @param locationID - an id of a location
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet topTenEvents(int locationID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT E.eventID, E.title, E.venue, E.eventDate, E.startTime, E.duration, " +
					"U.userID, U.name " +
			"FROM events AS E, users AS U, locations AS L " +
			"WHERE L.locationID = "+locationID+" " +
			     "AND E.locationID = L.locationID " +
			     "AND E.creatorID = U.userID " +
			"ORDER BY E.numSubscribers DESC, E.numAttendees DESC " +
			"LIMIT 10");
		ResultSet result = statement.executeQuery();
		
		return result;
	}
	
	/**
	 * This function returns top 10 users for a given location. User rating is calculating by 
	 * multiplying a rating of an event created by that user by a multiplier (which depends
	 * on number of reviews for that event:<br/>
	 *				  If between 1 and 4 -> 1 (multiplier)<br/>
	 *				  Between 5 and 9 -> 1.5<br/>
	 *			      10 and 24 -> 2<br/>
	 *				  25 and 49 -> 2.5<br/>
	 *				  50+ -> 3)<br/>
	 * This calculation is done for every event created by this user and for all the events an average
	 * if taken. This is the final rating of a user.
	 * @param locationID - an id of a location
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet topTenUsers(int locationID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("SELECT AVG(Temp.Multiplier * Temp.rating) AS 'User Rating', Temp.name, Temp.userID " +
			"FROM " +
				"(SELECT " +
					"CASE WHEN E.numReviews BETWEEN 1 AND 4 THEN 1 " +
			             "WHEN E.numReviews BETWEEN 5 AND 9 THEN 1.5 " +
			             "WHEN E.numReviews BETWEEN 10 AND 24 THEN 2 " +
			             "WHEN E.numReviews BETWEEN 25 AND 49 THEN 2.5 " +
			             "WHEN E.numReviews >= 50 THEN 3 " +
			             "END AS 'Multiplier',E.rating, U.name, U.userID " +
			     "FROM events AS E, users AS U, locations AS L " +
			     "WHERE  E.creatorID = U.userID " +
			            "AND L.locationID = "+locationID+" " +
			            "AND U.locationID = L.locationID) AS Temp " +
			"ORDER BY 'User rating' " +
			"LIMIT 10");
		ResultSet result = statement.executeQuery();
		
		return result;
	}
    
	/**
	 * New user is inserted into the database. Initially, activation status is 0,
	 * and lastVisited field is set to the current server time. However, since the user has
	 * to activate his/her account to be able to use privileges of a registered user,
	 * lastVisited set in this function does not affect anything. Once the user activates
	 * the account, lastVisited field is updated.
	 * @param name - name of a user.Must be unique
	 * @param password - password -> must have a certain length
	 * @param type - 2 -> general user; 1 -> moderator; 0 -> administrator
	 * @param locationID -> a location of this user's residence (or whatever location
	 * this user has decided to set as his/her "home" location)
	 * @param email - email of this user. Must be unique and actually exist
	 * @param age - must be > 0, obviously.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public void newUser(String name, String password, int type, 
							 int locationID, String email, int age) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
				("INSERT INTO users (name,password,type,locationID,email,age, lastVisited) " +
						"VALUES ('" +name+"', " +
								" '"+password+"', " +
								" "+type+", " +
								" "+locationID+", " +
								" '"+email+"', " +
								" "+age+", " +
								" NOW())");
		
		statement.executeUpdate();
	}
	
	/**
	 * A user is activated and, therefore, becomes a fully registered user.
	 * @param userID - an id of a user
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public void activateUser(int userID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
			("UPDATE users " +
			"SET activationStatus = 1, lastVisited= NOW() " +
			"WHERE userID = "+ userID +" "); 
		statement.executeUpdate();
	}
	
	/**
	 * A given user is deleted from the database. 
	 * @param userID - an id of a user
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public void deleteUser(int userID) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement
				("DELETE FROM users " +
				"WHERE userID = "+userID+" ");
		statement.executeUpdate();
	}
	
	public void testQuery() throws SQLException
	{
			PreparedStatement statement = connection.prepareStatement("SELECT E.title, E.venue " +
					                                                  "FROM events AS E");	
			ResultSet result = statement.executeQuery();
		
			while(result.next())
			{
				System.out.println(result.getString(1) + " " + result.getString(2));
			}
	}
}

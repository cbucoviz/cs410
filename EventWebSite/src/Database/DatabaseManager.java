package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;


public class DatabaseManager 
{	
	private static DatabaseManager instance;
	private Connection connection;	
	
	public enum SortReviews {
	    EventRating, Rating, UploadTime; 
	}
 
	public enum SortPosts {
	    Rating, RecentFirst, OldestFirst, NumberOfComments; 
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
		    case EventRating: 		  
				
				statement = connection.prepareStatement			
					("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
							"R.dateTime, R.eventRating " +
					 "FROM users AS U, reviews AS R " +
					 "WHERE R.eventID = "+eventID +" " +
					 	   "AND R.userID = U.userID " +
					 "ORDER BY R.eventRating DESC, R.goodRating DESC");
				result = statement.executeQuery();
				
			break;
		
		
		    case Rating:
				
				statement = connection.prepareStatement
					("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
							"R.dateTime, R.eventRating, R.goodRating - R.badRating AS goodVSbad " +
					 "FROM users AS U, reviews AS R " +
					 "WHERE R.eventID = "+eventID+" " +
					 	   "AND R.userID = U.userID" +
					 "ORDER BY goodVSbad DESC, R.goodRating DESC");
				result = statement.executeQuery();
				
			break;
		
		    case UploadTime:
			   
		    	statement = connection.prepareStatement
					("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
							"R.dateTime, R.eventRating " +
					 "FROM users AS U, reviews AS R " +
					 "WHERE R.eventID = "+ eventID +" " +
					 	   "AND R.userID = U.userID " +
					 "ORDER BY R.dateTime DESC");
				result = statement.executeQuery();
				
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
			case NumberOfComments:	
				
				statement = connection.prepareStatement
					("SELECT P.postID, P.userID, U.name, P.postContent, P.dateTime, " +
							"P.goodRating, P.goodRating - P.ratingDifference AS 'badRating', " +
							"P.numComments " +
					 "FROM discussionpost AS P, users AS U " +
					 "WHERE P.eventID = " + eventID + " " +
					 	   "AND P.userID = U.userID " +
					 "ORDER BY P.numComments DESC");
				result = statement.executeQuery();	
				
			break;
		
		
			case RecentFirst:
				
				statement = connection.prepareStatement
					("SELECT P.postID, P.userID, U.name, P.postContent, P.dateTime, " +
							"P.goodRating, P.goodRating - P.ratingDifference AS 'badRating'," +
							"P.numComments" +
					"FROM discussionpost AS P, users AS U " +
					"WHERE P.eventID = " + eventID + " " +
						  "AND P.userID = U.userID" +
					"ORDER BY P.dateTime DESC");
				result = statement.executeQuery();
				
			break;
		
			case OldestFirst:
				
				statement = connection.prepareStatement				
					("SELECT P.postID, P.userID, U.name, P.postContent, P.dateTime, " +
							"P.goodRating, P.goodRating - P.ratingDifference AS 'badRating', " +
							"P.numComments " +
					"FROM discussionpost AS P, users AS U " +
					"WHERE P.eventID = " + eventID + " " +
					      "AND P.userID = U.userID " +
					"ORDER BY P.dateTime ASC");
				result = statement.executeQuery();
				
			break;
		
			case Rating:
				
				statement = connection.prepareStatement
					("SELECT P.postID, P.userID, U.name, P.postContent, P.dateTime, " +
							"P.goodRating, P.goodRating - P.ratingDifference AS 'badRating', " +
							"P.numComments " +
					"FROM discussionpost AS P, users AS U " +
					"WHERE P.eventID = " + eventID + " " +
						  "AND P.userID = U.userID " +
					"ORDER BY P.ratingDifference DESC, P.goodRating DESC");
				result = statement.executeQuery();
				
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

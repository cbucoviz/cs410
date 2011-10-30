package Database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;


public class DatabaseManager 
{	
	public static DatabaseManager instance;
	public Connection connection;
	public static final int EVENT_RATING = 0;
	public static final int REVIEW_RATING = 1;
	public static final int REVIEW_UPLOAD_TIME = 2;
	
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
			/*
			   Create connection to MySQL:
			      - localhost: "port" <- whatever port a local database is located at
			      - name of database <- the title of a database to be connected to
			   Second parameter is a login name;
			   Third is a password;
			 */
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/name of database",
														"", "");		
		return con;
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
				 "FROM events AS E, eventcontent AS C, users AS U, locations AS L, eventtypes AS T "+
	             "WHERE E.eventID = " + eventID + "" +
	             	  " AND E.creatorID = U.userID " +
	             	  " AND E.locationID = L.locationID " +
	             	  " AND E.eventID = T.typeID");
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
	 *  - By upload time: the most obvious one. The latest reviews go on top. 
	 * 
	 * @param type - type of a review: <br/>
	 *                0 - by event rating;<br/>
	 *                1 - by rating; <br/>
	 *                2 - by upload time;
	 * @param eventID - id of the event
	 * @return - result set to be passed to the Controller.
	 * @throws SQLException - most likely various problems with syntax and/or some problems
	 *                        with the database (tables changed, etc.)
	 */
	public ResultSet sortReviews(int type, int eventID) throws SQLException
	{
		if(type == EVENT_RATING)
		{
			//eventRating
			PreparedStatement statement = connection.prepareStatement			
				("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
						"R.dateTime, R.eventRating " +
				 "FROM users AS U, reviews AS R " +
				 "WHERE R.eventID = "+eventID +" " +
				 	   "AND R.userID = U.userID " +
				 "ORDER BY R.eventRating DESC, R.goodRating DESC");
			ResultSet result = statement.executeQuery();
			return result;
		}
		
		else if(type == REVIEW_RATING)
		{
			//bestRated
			PreparedStatement statement = connection.prepareStatement
				("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
						"R.dateTime, R.eventRating, R.goodRating - R.badRating AS goodVSbad " +
				 "FROM users AS U, reviews AS R " +
				 "WHERE R.eventID = "+eventID+" " +
				 	   "AND R.userID = U.userID" +
				 "ORDER BY goodVSbad DESC, R.goodRating DESC");
			ResultSet result = statement.executeQuery();
			return result;
		}
	    
		else if(type == REVIEW_UPLOAD_TIME)
		{
		    //uploadTime
			PreparedStatement statement = connection.prepareStatement
				("SELECT U.userID, U.name, R.reviewContent, R.goodRating, R.badRating, " +
						"R.dateTime, R.eventRating " +
				 "FROM users AS U, reviews AS R " +
				 "WHERE R.eventID = "+ eventID +" " +
				 	   "AND R.userID = U.userID " +
				 "ORDER BY R.dateTime DESC");
			ResultSet result = statement.executeQuery();
			return result;
		}
		else
		{
			System.out.println("Wrong type! No such sorting for reviews exists!");
		}
		return null;
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

package test;



import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import Database.DatabaseManager;
import Database.DatabaseManager.UserType;
import Models.DiscussionPost.SortPosts;
import Models.Review.SortReviews;

public class DatabaseManagerTest {

	private static DatabaseManager test;
	
	@Test
	public void testGetInstance() throws ClassNotFoundException, SQLException {
			test = DatabaseManager.getInstance();
			assertNotSame(null, test);
			DatabaseManager test2 = DatabaseManager.getInstance();
			assertNotSame(null, test2);
			assertEquals(test, test2);
			
		
	}

	@Test
	public void testFindEvent() throws SQLException {
		ResultSet events = test.findEvent(1);
		if(events.next())
		{
			assertNotNull(events.getInt("eventID"));
		}
		else
		{
			fail();
		}
		
	}

	@Test
	public void testEventExists() throws SQLException {
		assertTrue(test.eventExists(1));
		assertFalse(test.eventExists(12334435));
	}

	@Test
	public void testFindEventTypes() throws SQLException {
		ResultSet result = test.findEventTypes(1);
		if(result.next())
		{
			assertEquals("Sports", result.getString("eventType"));
		}
		else
		{
			fail();
		}
		
	}

	@Test
	public void testSortReviews() throws SQLException {
		ResultSet result = test.sortReviews(SortReviews.EVENT_RATING, 1);
		if(result.next())
		{
			assertEquals(1,result.getInt("reviewID"));
		}
		else
		{
			fail();
		}
		result = test.sortReviews(SortReviews.RATING, 1);
		if(result.next())
		{
			assertEquals(1,result.getInt("reviewID"));
		}
		else
		{
			fail();
		}
		result = test.sortReviews(SortReviews.UPLOAD_TIME, 1);
		if(result.next())
		{
			int i = result.getInt("reviewID");
			assertEquals(3,i);
		}
		else
		{
			fail();
		}
				
	}

	@Test
	public void testSortPosts() throws SQLException {
		ResultSet result = test.sortPosts(SortPosts.NUMBER_OF_COMMENTS, 1);
		if(result.next())
		{
			assertEquals(2,result.getInt("userID"));
		}
		else
		{
			fail();
		}
		result = test.sortPosts(SortPosts.OLDEST_FIRST, 1);
		if(result.next())
		{
			assertEquals(1,result.getInt("postID"));
		}
		else
		{
			fail();
		}
		result = test.sortPosts(SortPosts.RECENT_FIRST, 1);
		if(result.next())
		{
			int check = result.getInt("postID");
			assertEquals(4,check);
		}
		else
		{
			fail();
		}
		result = test.sortPosts(SortPosts.RATING, 1);
		if(result.next())
		{
			assertEquals(1,result.getInt("postID"));
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testRevealComments() throws SQLException {
		ResultSet result = test.revealComments(1);
		if(result.next())
		{
			assertEquals(4,result.getInt("userID"));
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testShowAgeStatistics() throws SQLException {
		ResultSet result = test.showAgeStatistics(1);
		if(result.next())
		{
			assertNotNull(result);
		}
		else
		{
			fail();
		}
		
	}

	@Test
	public void testShowCityStatistics() throws SQLException {
		ResultSet result = test.showCityStatistics(1);
		if(result.next())
		{
			assertNotNull(result);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindGoogleEarthLocations() throws SQLException {
		ResultSet result = test.findGoogleEarthLocations();
		if(result.next())
		{
			assertEquals("Vancouver",result.getString("city"));
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindGoogleLocations() throws SQLException {
		ResultSet result = test.findGoogleLocations(null, "Vancouver", null, null, null, null);
		if(result.next())
		{
			assertEquals(1,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
		result = test.findGoogleLocations("Canucks", null, null, null, null, null);
		if(result.next())
		{
			assertEquals(1,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
		result = test.findGoogleLocations(null, null, "New York", null, null, null);
		if(result.next())
		{
			assertEquals(2,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
		result = test.findGoogleLocations(null, null, null, "Japan", null, null);
		if(result.next())
		{
			assertEquals(3,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
		result = test.findGoogleLocations("Yankovic", null, null, null, java.sql.Date.valueOf( "2011-11-07" ), null);
		if(result.next())
		{
			assertEquals(1,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
		result = test.findGoogleLocations(null, null, null, null, java.sql.Date.valueOf( "2011-11-07" ), null);
		if(result.next())
		{
			assertEquals(1,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
		result = test.findGoogleLocations(null, null, null, null, null, java.sql.Date.valueOf( "2011-11-07" ));
		if(result.next())
		{
			assertEquals(1,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
		result = test.findGoogleLocations("Yankovic", null, null, null, null, java.sql.Date.valueOf( "2011-11-30" ));
		if(result.next())
		{
			assertEquals(1,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
		
		
		
	}

	@Test
	public void testFindEventsLocations() throws SQLException {
		ResultSet result = test.findEventsLocations("Christmas");
		if(result.next())
		{
			assertEquals(2,result.getInt("locationID"));

		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindEventsLocationsByType() throws SQLException {
		ResultSet result = test.findEventsLocationsByType("Cultural");
		if(result.next())
		{
			int check = result.getInt("locationID");
			assertEquals(2,check);

		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindEventsIntString() throws SQLException {
		ResultSet result = test.findEvents(2, "Christmas");
		if(result.next())
		{
			assertEquals(10,result.getInt("eventID"));

		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindEventsByType() throws SQLException {
		ResultSet result = test.findEventsByType(2, 3);
		if(result.next())
		{
			assertEquals(10,result.getInt("eventID"));

		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindEventsByTypeLocation() throws SQLException {
		ResultSet result = test.findEventsByTypeLocation("Sports", "Vancouver");
		if(result.next())
		{
			assertEquals(1,result.getInt("eventID"));

		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindEventsByLocation() throws SQLException {
		ResultSet result = test.findEventsByLocation("Vancouver");
		if(result.next())
		{
			assertEquals(1,result.getInt("eventID"));

		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindEventsByRating() throws SQLException {
		ResultSet result = test.findEventsByRating(4);
		if(result.next())
		{
			assertEquals(2,result.getInt("eventID"));

		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindEventsByUser() throws SQLException {
		ResultSet result = test.findEventsByUser("Vitali");
		if(result.next())
		{
			assertEquals(1,result.getInt("eventID"));

		}
		else
		{
			fail();
		}
	}

	//This test may be removed because method not used/////////////////////////
	/*@Test
	public void testFilterEvents() throws SQLException {
		
		String[] types = {"1"};
		GregorianCalendar start = new GregorianCalendar(2011, 9,17);
		GregorianCalendar end = new GregorianCalendar(2011, 9,25);
		
		ResultSet result = test.filterEvents(1,start.getTime() , end.getTime() ,"Canucks", types);
		if(result.next())
		{
			int i = result.getInt("eventID");
			assertEquals(1,result.getInt("eventID"));

		}
		else
		{
			fail();
		}
	}*/

	//Method not used, Search method does not use it 
	/*
	@Test
	public void testFindEventsByDates() throws SQLException {
		
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 8, 17, 5, 30, 30);
		Calendar cal2 = Calendar.getInstance();
		cal.set(2012, 9, 25, 5, 30, 30);
		ResultSet result = test.findEventsByDates(cal.getTime(), cal2.getTime());
		if(result.next()) 
		{
			assertEquals(1,result.getInt("eventID"));

		}
		else
		{ 
			fail();
		}
	}*/

	@Test
	public void testFindMySubscribedEvents() throws SQLException {
		ResultSet result = test.findMySubscribedEvents(1);
		if(result.next()) 
		{
			int check = result.getInt("eventID");
			assertEquals(10,check);

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindMyAttendingEvents() throws SQLException {
		ResultSet result = test.findMyAttendingEvents(1);
		if(result.next()) 
		{
			assertEquals(1,result.getInt("eventID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindMyEvents() throws SQLException {
		ResultSet result = test.findMyEvents(1);
		if(result.next()) 
		{
			assertEquals(11,result.getInt("eventID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindSubscribedLocales() throws SQLException {
		ResultSet result = test.findSubscribedLocales(1);
		if(result.next()) 
		{
			assertEquals(2,result.getInt("locationID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testGetNewEvents() throws SQLException {
		ResultSet result = test.getNewEvents(1, 3);
		if(result.next()) 
		{
			int check = result.getInt("eventID");
			assertEquals(2,check);

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindSubscribedUsers() throws SQLException {
		ResultSet result = test.findSubscribedUsers(1);
		if(result.next()) 
		{
			assertEquals(4,result.getInt("userID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindEventSubsribers() throws SQLException {
		ResultSet result = test.findEventSubsribers(1);
		if(result.next()) 
		{
			assertEquals(1,result.getInt("userID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindLocaleSubsribers() throws SQLException {
		ResultSet result = test.findLocaleSubsribers(1);
		if(result.next()) 
		{
			assertEquals(1,result.getInt("userID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindUserSubsribers() throws SQLException {
		ResultSet result = test.findUserSubsribers(1);
		if(result.next()) 
		{
			assertEquals(2,result.getInt("userID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindAllNewEvents() throws SQLException {
		ResultSet result = test.findAllNewEvents(4, 3);
		if(result.next()) 
		{
			assertEquals(2,result.getInt("eventID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testFindEventsInt() throws SQLException {
		ResultSet result = test.findEvents(1);
		if(result.next()) 
		{
			assertEquals(11,result.getInt("eventID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testTopTenEvents() throws SQLException {
		ResultSet result = test.topTenEvents(1);
		if(result.next()) 
		{
			assertEquals(1,result.getInt("eventID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testTopTenUsers() throws SQLException {
		ResultSet result = test.topTenUsers(1);
		if(result.next()) 
		{
			assertEquals(1,result.getInt("userID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testGetLocationID() throws SQLException {
		ResultSet result = test.getLocationID("Vancouver", "British Columbia", "Canada");
		if(result.next()) 
		{
			assertEquals(1,result.getInt("locationID"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testGetLocation() throws SQLException {
		ResultSet result = test.getLocation(1);
		if(result.next()) 
		{
			assertEquals("Vancouver",result.getString("city"));

		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testNewUser() throws SQLException {
		test.newUser("TestUser", "abcdef", UserType.GENERAL, 1, "test@test.com",30);
		ResultSet result = test.getUser("test@test.com");
		if(result.next()) 
		{
			assertEquals("TestUser",result.getString("name"));
		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testGetUserString() throws SQLException {
		ResultSet result = test.getUser("test@test.com");
		if(result.next()) 
		{
			assertEquals("TestUser",result.getString("name"));
		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testGetUserInt() throws SQLException {
		ResultSet result = test.getUser(1);
		if(result.next()) 
		{
			assertEquals("Vitali",result.getString("name"));
		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testActivateUser() throws SQLException {
		test.activateUser(7);
		ResultSet result = test.getUser(7);
		if(result.next()) 
		{
			assertEquals(1,result.getInt("activationStatus"));
		}
		else
		{ 
			fail();
		}
	}

	@Test
	public void testDeleteUser() throws SQLException {
		ResultSet userResult = test.getUser("test@test.com");
		int id = 0;
		if(userResult.next()) 
		{
			id = userResult.getInt("userID");
		}
		test.deleteUser(id);
		ResultSet userResult2 = test.getUser(id);
		assertFalse(userResult2.next());
		
	}

	//@Test
	/*public void testNewEvent() throws SQLException {
		GregorianCalendar start = new GregorianCalendar(2011, 11,25);
		String[] types = {"Hockey"};
		int result = test.newEvent("test", 1, 1, "here", "there", start.getTime(), "20:00:00", "22:00:00", types);
		assertTrue(result > 0);
	}*/

	@Test
	public void testlogoff() throws SQLException
	{
		test.logoff(4);
		assertEquals(1,1);
	}
	
	

	@Test
	public void testFindAttendeesToMailTo() throws SQLException {
		ResultSet testSet = test.findAttendeesToMailTo();
		if(testSet.next())
		{
			assertTrue(testSet != null);
		}
		else
		{
			fail();
		}
	}


}

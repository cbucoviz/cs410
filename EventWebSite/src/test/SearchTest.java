package test;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.*;
import static org.junit.Assert.*;
import Models.Search;
import Models.Search.EventInfoSearch;
import Models.Search.ModeratorInfo;
import Models.Search.SubscribedLocaleInfo;
import Models.Search.UserInfoSearch;

import org.junit.Test;

public class SearchTest {
	
	Search testSearch  = new Search();;
	
	@Test
	public void testGetGoogleEarthLoc() {
		
		JsonArray test = testSearch.getGoogleEarthLoc();
		if(test != null)
		{
			assertTrue(test.size()>0);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testGetGoogleEarthLocStringStringStringStringStringString() {
		JsonArray test = testSearch.getGoogleEarthLoc("Canucks", "Vancouver", null, "Canada",null,null);
		JsonArray test2 = testSearch.getGoogleEarthLoc("Canucks", "New York City", null, "Canada","12/10/2011","30/12/2011");
		if(test != null)
		{
			assertTrue(test.size()>0);
			assertNotSame(test, test2);
			
		}
		else
		{
			fail();
		}
	}

	

	/*@Test
	public void testGetEventsBetweenDates() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testGetEventsWithRating() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testGetEventsByUser() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testGetGoogleMapEvents() {
		String[] types = {"Sports","Music"};
		JsonArray test = testSearch.getGoogleMapEvents(1, "Canucks", types, "10/10/2011");
		if(test != null)
		{
			assertTrue(test.size()>0);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindMySubscribedEvents() throws ClassNotFoundException, SQLException {
		List<Map<Search.EventInfoSearch,String>>  result = Search.findMySubscribedEvents(1);
		if(result != null)
		{
			assertTrue(result.size()>0);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindMyEvents() throws ClassNotFoundException, SQLException {
		List<Map<Search.EventInfoSearch,String>>  result = Search.findMyEvents(1);
		if(result != null)
		{
			assertTrue(result.size()>0);
		}
		else
		{
			fail();
		}
	}

	/*@Test
	public void testFindNewEventsByLoc() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testFindNewEventsByUser() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testFindSubscribedUsers() throws ClassNotFoundException, SQLException {
		 List<Map<Search.UserInfoSearch,String>>  result = Search.findSubscribedUsers(1);
		if(result != null)
		{
			assertTrue(result.size()>0);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFindSubscribedLocales() throws ClassNotFoundException, SQLException {
		List<Map<SubscribedLocaleInfo, String>>  result = Search.findSubscribedLocales(1);
		if(result != null)
		{
			assertTrue(result.size()>0);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testTopEvents() throws ClassNotFoundException, SQLException {
		List<Map<EventInfoSearch, String>>  result = Search.topEvents(1);
		if(result != null)
		{
			assertTrue(result.size()>0);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testTopUsers() throws ClassNotFoundException, SQLException {
		List<Map<UserInfoSearch, String>>  result = Search.topUsers(1);
		if(result != null)
		{
			assertTrue(result.size()>0);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testGetMods() throws ClassNotFoundException, SQLException {
		List<Map<ModeratorInfo, String>>  result = Search.getMods();
		if(result != null)
		{
			assertTrue(result.size()>0);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testFilterEvents() throws ClassNotFoundException, SQLException {
		String[] types = {"Sports","Music"};
		List<Map<EventInfoSearch, String>>  result = Search.filterEvents(1, "Canucks", types, null);
		if(result != null)
		{
			assertTrue(result.size()>0);
		}
		else
		{
			fail();
		}
	}

}

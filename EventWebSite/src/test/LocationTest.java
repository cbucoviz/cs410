package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import com.google.gson.JsonObject;

import Models.Location;
import Models.LocationAddress;
import Models.Event.EventInfo;

public class LocationTest {

	@Test
	public void testGetLocationAddress() {
		LocationAddress addr = Location.getLocationAddress(1);
		assertEquals("Vancouver",addr.city);
	}

	@Test
	public void testGetLocationId() {
		LocationAddress addr = Location.getLocationAddress(1);
		int id = Location.getLocationId(addr);
		assertEquals(1,id);
	}

	@Test
	public void testGetEventsAtLocation() {
		ArrayList<Map<EventInfo, String>> events = Location.getEventsAtLocation(1);
		assertNotNull(events.get(0));
	}

	@Test
	public void testSubscribeToLocation() {
		Location.subscribeToLocation(2, 4);
		assertTrue(Location.isSubscribed(4, 2));
	}
	
	@Test
	public void testIsSubscribed() {
		Boolean subed = Location.isSubscribed(4, 2);
		assertTrue(subed);
	}
	
	@Test
	public void testUnsubscribeFromLocation() {
		Location.unsubscribeFromLocation(2, 4);
		assertFalse(Location.isSubscribed(4, 2));
	}


	@Test
	public void testAddLocation() {
		LocationAddress addr = new LocationAddress("Delta","British Columbia","Canada");
		String check = addr.toString();
		assertNotNull(check);
		JsonObject obj = Location.addLocation(addr, 49.1519849, -123.51495161);
		assertNotNull(obj);
	}
	

}

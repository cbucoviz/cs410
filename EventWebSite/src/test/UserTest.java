package test;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import Models.CalendarEvent;
import Models.User;

public class UserTest {

	@Test
	public void testSubscribeToUser() {
		User.subscribeToUser(2, 1);
		assertTrue(true);
	}
	
	@Test
	public void testIsSubscribed() {
		Boolean check = User.isSubscribed(4, 1);
		assertEquals(true,check);
	}
	
	@Test
	public void testUnsubscribeFromUser() {
		User.unsubscribeFromUser(2, 1);
		assertTrue(true);
	}


	@Test
	public void testGetAttendingEvents() {
		HashSet<CalendarEvent> attEv = User.getAttendingEvents(1);
		assertNotNull(attEv);
	}

	@Test
	public void testMakeModerator() {
		Boolean test = User.makeModerator("vcrystal");
		assertEquals(true,test);
	}

	@Test
	public void testRemoveModerator() {
		Boolean test = User.removeModerator("vcrystal");
		assertEquals(true,test);
	}

	@Test
	public void testChangePassword() {
		Boolean test = User.changePassword(39, "testing");
		assertEquals(true, test);
	}

}

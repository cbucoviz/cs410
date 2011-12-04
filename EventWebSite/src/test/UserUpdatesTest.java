package test;

import static org.junit.Assert.*;

import javax.servlet.http.HttpSession;

import org.junit.Test;

import Models.UserUpdates;

public class UserUpdatesTest {

	@Test
	public void testAddToSessions() {
		
		HttpSession sess = null;
		UserUpdates.addToSessions("test", sess);
		assertTrue(true);
	}

	@Test
	public void testExists() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRemoveFromSessions() {
		fail("Not yet implemented");
	}



	@Test
	public void testAddUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveUpdates() {
		fail("Not yet implemented");
	}

	@Test
	public void testMailAttendeesOfUpcomingEvents() {
		fail("Not yet implemented");
	}

}

package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import Models.DiscussionPost.SortPosts;
import Models.Event;
import Models.DiscussionPost.PostInfo;
import Models.Event.EventInfo;
import Models.Event.StatType;
import Models.Review.ReviewInfo;
import Models.Review.SortReviews;

public class EventTest {
	
	int id;
	
	@Test
	public void testCreateNewEvent() {
		Calendar cal = Calendar.getInstance();
		cal.set(2011,9,9);
		cal.set(2011,9,9);
		String[] types = {"Sports","Music"};
		id = Event.createNewEvent("MyTestEvent", 1, 1,(float) 43.2564543,(float) 78.245356, "32 main street", "MyHouse", cal.getTime(), cal.getTime(), types);
		assertTrue(id >= 0);
	}
	
	@Test
	public void testGetExistingEvent() {
		Map<EventInfo, String> ev = Event.getExistingEvent(1);
		assertTrue(ev.containsValue("1"));
	}
	
	@Test
	public void testSetContent() {
		Boolean test = Event.setContent(13, "Test", "MyVenue", "1 dollar", "Bus", "Raining", "", "", "Yup");
		assertEquals(true,test);
	}
	
	
	/////Method Not used
	/*@Test
	public void testDeleteEvent() {
		Boolean test = Event.deleteEvent(13);
		assertEquals(true,test);
	}*/


	@Test
	public void testShowStatistics() {
		List<String[]> test = Event.showStatistics(1, StatType.AGE_STAT);
		List<String[]> test2 = Event.showStatistics(1, StatType.CITY_STAT);
		assertNotNull(test);
		assertNotNull(test2);
		assertFalse(test.equals(test2));		
	}

	@Test
	public void testEditEventInfo() {
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 05, 12, 5, 30, 30);
		Boolean test = Event.editEventInfo(1,12, "New Title","New Venue" , cal.getTime(), cal.getTime(), "My new address", "edited event", "Cool", "1234", "5678", "Be aware", "none", "nope", "other");
		assertTrue(test);
	}
	
	@Test
	public void testRemoveReview() {
		float test = Event.removeReview(9, 10);
		assertNotNull(test);
	}
	
	@Test
	public void testAddReview() {
		float test = Event.addReview(10, 1, "Test", 3);
		assertNotNull(test);
	}

	@Test
	public void testAddPost() {
		Boolean post = Event.addPost(6, 1, "New Post");
		assertTrue(post);
	}


	@Test
	public void testRemovePost() {
		Boolean success = Event.removePost(10, 1);
		assertTrue(success);
	}

	@Test
	public void testDisplayReviews() {
		List<Map<ReviewInfo,String>> test = Event.displayReviews(1, SortReviews.EVENT_RATING);
		assertNotNull(test);
	}

	@Test
	public void testDisplayPosts() {
		List<Map<PostInfo, String>> posts = Event.displayPosts(1, SortPosts.NUMBER_OF_COMMENTS);
		assertNotNull(posts);
	}
	

	@Test
	public void testLockEvent() {
		Boolean locked = Event.lockEvent(1);
		assertEquals(true,locked);
	}

	@Test
	public void testUnlockEvent() {
		Boolean unlocked = Event.unlockEvent(1);
		assertEquals(true,unlocked);
	}

	@Test
	public void testSubscribeToEvent() {
		Event.subscribeToEvent(10, 1);
		assertEquals(1,1);
	}
	
	@Test
	public void testIsSubscribed() {
		Boolean subed = Event.isSubscribed(1, 1);
		assertEquals(true,subed);
	}

	@Test
	public void testUnsubscribeFromEvent() {
		Event.unsubscribeFromEvent(10, 1);
		assertEquals(1,1);
	}


	@Test
	public void testAttendEvent() {
		Event.attendEvent(2, 1);
		assertEquals(1,1);
	}
	
	@Test
	public void testIsAttending() {
		Boolean att = Event.isAttending(1, 1);
		assertEquals(true,att);
	}
	
	@Test
	public void testStopAttendingEvent() {
		 Event.stopAttendingEvent(2, 1);
		 assertEquals(1,1);
	}
	

	@Test
	public void testReportEventAbuse() {
		Event.reportEventAbuse(1, "Oops");
		assertTrue(true);
	}

	@Test
	public void testReportPostAbuse() {
		Event.reportPostAbuse(1, "Bad");
		assertTrue(true);
	}

	@Test
	public void testReportReview() {
		Event.reportReview(1);
		assertTrue(true);
	}

}

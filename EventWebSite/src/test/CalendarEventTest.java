package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import Models.CalendarEvent;

public class CalendarEventTest {

	CalendarEvent ev;
	
	@Test
	public void testCalendarEvent() {
		ev = new CalendarEvent(1,"Test", new Date(), new Date());
		if(ev != null)
		{
			assertEquals(ev.getTitle() , "Test");
		}
		else
		{
			fail();
		}
	}
	
	@Test
	public void testHashCode() {
		ev = new CalendarEvent(1,"Test", new Date(), new Date());
		if(ev != null)
		{
			assertEquals(ev.hashCode() , 1);
		}
		else
		{
			fail();
		}
	}

	@Test
	public void testGetEventId() {
		ev = new CalendarEvent(1,"Test", new Date(), new Date());
		assertEquals(ev.getEventId() , 1);
	}

	@Test
	public void testGetTitle() {
		ev = new CalendarEvent(1,"Test", new Date(), new Date());
		assertEquals(ev.getTitle() , "Test");
	}

	@Test
	public void testGetStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(2011,9,9);
		ev = new CalendarEvent(1,"Test", cal.getTime(), new Date());
		assertEquals(ev.getStartDate() , cal.getTime());
	}

	@Test
	public void testGetEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(2011,9,9);
		ev = new CalendarEvent(1,"Test",new Date(), cal.getTime());
		assertEquals(ev.getEndDate(), cal.getTime());
	}

	

	@Test
	public void testGetJQueryCalendarString() {
		Calendar cal = Calendar.getInstance();
		cal.set(2011,9,9);
		Date testDate = cal.getTime();
		ev = new CalendarEvent(1,"Test",testDate, testDate);
		
		assertNotNull(ev.getJQueryCalendarString());
	}

}

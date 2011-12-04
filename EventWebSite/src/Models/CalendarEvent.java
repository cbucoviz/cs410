package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarEvent 
{
	private int eventId;
	private String title;
	private Date startDate;
	private Date endDate;
	
	public int getEventId() { return eventId; }
	public String getTitle() { return title; }
	public Date getStartDate() { return startDate; }
	public Date getEndDate() { return endDate; }
	
	public CalendarEvent(int eventId, String title, Date startDate, Date endDate)
	{
		this.eventId = eventId;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	
	public String getJQueryCalendarString()
	{
		SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
		String returnString = "";
		returnString += "{";
		returnString += "title: '" + title + "', ";
		returnString += "start: new Date('" + outputFormat.format(startDate) + "'), ";
		returnString += "end: new Date('" + outputFormat.format(endDate) + "'), ";
		returnString += "url: 'EventPage?eventID=" + eventId +"', ";
		returnString += "allDay: false";
		returnString += "},";
		
		return returnString;
	}
}

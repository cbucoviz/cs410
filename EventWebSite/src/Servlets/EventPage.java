package Servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Models.Event;
import Models.Event.EventInfo;


public class EventPage extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try {
			int eventID = Integer.parseInt(request.getParameter("eventID"));
			
			RequestDispatcher dispatcher = null;	
			
			Map<EventInfo,String> myEvent = Event.getExistingEvent(eventID);			
					
			String title =  myEvent.get(EventInfo.TITLE);
			String venue = myEvent.get(EventInfo.VENUE);
			String address = myEvent.get(EventInfo.ADDRESS);
			
				Date eventDate = new SimpleDateFormat("yyyy-mm-dd").parse(myEvent.get(EventInfo.EVENT_DATE));
				Date startTime = new SimpleDateFormat("HH:mm:ss").parse(myEvent.get(EventInfo.START_TIME));
				Date endTime = new SimpleDateFormat("HH:mm:ss").parse(myEvent.get(EventInfo.END_TIME));
			
			
			float rating = Float.parseFloat(myEvent.get(EventInfo.RATING));
			int numAttendees = Integer.parseInt(myEvent.get(EventInfo.NUM_ATTENDEES));			
			
			boolean isLocked = false;
			if(Integer.parseInt(myEvent.get(EventInfo.IS_LOCKED)) == 1)
			{isLocked = true;}
			else
			{isLocked = false;}
			
			String genDesc = myEvent.get(EventInfo.GEN_INFO);
			String venueDesc = myEvent.get(EventInfo.VENUE_INFO);		
			String priceDesc = myEvent.get(EventInfo.PRICE_INFO);
			String transportDesc = myEvent.get(EventInfo.TRANSPORT_INFO);		
			String awareInfo = myEvent.get(EventInfo.AWARENESS_INFO);
			String videos = myEvent.get(EventInfo.VIDEOS);		
			String links = myEvent.get(EventInfo.LINKS);
			String otherInfo = myEvent.get(EventInfo.OTHER_INFO);			
		    String creator = myEvent.get(EventInfo.CREATOR);
		    int creatorID = Integer.parseInt(myEvent.get(EventInfo.CREATOR_ID));
		    int locationID = Integer.parseInt(myEvent.get(EventInfo.LOCATION_ID));
		    String city = myEvent.get(EventInfo.CITY);		
			String state = myEvent.get(EventInfo.STATE);
			String country = myEvent.get(EventInfo.COUNTRY);
			String[] types = myEvent.get(EventInfo.EVENT_TYPES).split(",");
		
			request.setAttribute("title", title);
			request.setAttribute("venue", venue);
			request.setAttribute("address", address);
			request.setAttribute("eventDate", eventDate);
			request.setAttribute("startTime", startTime);
			request.setAttribute("endTime", endTime);
			request.setAttribute("rating", rating);
			request.setAttribute("numAttendees", numAttendees);
			
			request.setAttribute("isLocked", isLocked);
			request.setAttribute("genDesc", genDesc);
			request.setAttribute("venueDesc", venueDesc);
			request.setAttribute("priceDesc", priceDesc);
			request.setAttribute("transportDesc", transportDesc);
			request.setAttribute("awareInfo", awareInfo);			
			request.setAttribute("videos", videos);
			request.setAttribute("links", links);
			request.setAttribute("otherInfo", otherInfo);
			
			request.setAttribute("creator", creator);
			request.setAttribute("creatorID", creatorID);
			request.setAttribute("locationID", locationID);
			request.setAttribute("city", city);
			request.setAttribute("state", state);
			request.setAttribute("country", country);
			request.setAttribute("types", types);
			
			dispatcher = request.getRequestDispatcher("eventpage.jsp");
			dispatcher.forward(request, response);
			return;	
		
		} catch (ParseException e) {			
			e.printStackTrace();
		}
	}
	
	
}

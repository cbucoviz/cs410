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
			
			Map<String,String> myEvent = Event.getExistingEvent(eventID);			
					
			String title =  myEvent.get("title");
			String venue = myEvent.get("venue");
			String address = myEvent.get("address");
			
				Date eventDate = new SimpleDateFormat("yyyy-mm-dd").parse(myEvent.get("eventDate"));
				Date startTime = new SimpleDateFormat("HH:mm:ss").parse(myEvent.get("startTime"));
				Date endTime = new SimpleDateFormat("HH:mm:ss").parse(myEvent.get("endTime"));
			
			
			int rating = Integer.parseInt(myEvent.get("rating"));
			int numAttendees = Integer.parseInt(myEvent.get("numAttendees"));			
			
			boolean isLocked = false;
			if(Integer.parseInt(myEvent.get("isLocked")) == 1)
			{isLocked = true;}
			else
			{isLocked = false;}
			
			String genDesc = myEvent.get("genDesc");
			String venueDesc = myEvent.get("venueDesc");		
			String priceDesc = myEvent.get("priceDesc");
			String transportDesc = myEvent.get("transportDesc");		
			String awareInfo = myEvent.get("awareInfo");
			String videos = myEvent.get("videos");		
			String links = myEvent.get("links");
			String otherInfo = myEvent.get("otherInfo");			
		    String creator = myEvent.get("creator");
		    int creatorID = Integer.parseInt(myEvent.get("creatorID"));
		    int locationID = Integer.parseInt(myEvent.get("locationID"));
		    String city = myEvent.get("city");		
			String state = myEvent.get("state");
			String country = myEvent.get("country");
			String[] types = myEvent.get("eventTypes").split(",");
		
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

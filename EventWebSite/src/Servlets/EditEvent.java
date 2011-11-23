package Servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Event;
import Models.Location;
import Models.LocationAddress;
import Models.Event.EventInfo;

@WebServlet("/EditEvent")
public class EditEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditEvent() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String title = request.getParameter("title");

		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		
		LocationAddress location = new LocationAddress(city, state, country);
		int locationId = Location.getLocationId(location);
		
		String venue = request.getParameter("venue");
		String address = request.getParameter("address");
		String startDateString = request.getParameter("date") + " - " + request.getParameter("start_hour") + ":" + request.getParameter("start_minute") + " " + request.getParameter("start_am_pm").toUpperCase();
		String endTimeString = request.getParameter("end_hour") + ":" + request.getParameter("end_minute") + " " + request.getParameter("end_am_pm").toUpperCase();		

		Date startTime = null;
		Date endTime = null;
		
		try
		{
			startTime = new SimpleDateFormat("dd/MM/yyyy - hh:mm aa").parse(startDateString);
			endTime = new SimpleDateFormat("hh:mm aa").parse(endTimeString);
			
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		String eventDesc = request.getParameter("event_description");
		String venueDesc = request.getParameter("venue_description");
		String costDesc = request.getParameter("cost_description");
		String directions = request.getParameter("directions");
		String otherDesc = request.getParameter("other_description");
		String videos = request.getParameter("video_links");
		String links = request.getParameter("other_links");
		
		// TODO ktam:  need to hook this up to a dropdown
		String[] types = {"Hockey"};
		
		HttpSession session = request.getSession();
		Integer creatorID = (Integer) session.getAttribute(SessionVariables.USER_ID);
		
		
		int eventId = Event.createNewEvent(title, creatorID, locationId, address, venue, startTime, endTime, types);
		Event.setContent(eventId, eventDesc, venueDesc, costDesc, directions, null, videos, links, otherDesc);
		
		response.sendRedirect("EventPage?eventID=" + eventId);
	}
}

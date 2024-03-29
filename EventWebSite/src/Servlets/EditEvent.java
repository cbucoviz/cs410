package Servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
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
		String eventIdString = request.getParameter("eventId");
		Integer eventId = eventIdString != null ? Integer.parseInt(eventIdString) : null;
	
		String title = request.getParameter("title");

		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		
		int locationId = Integer.parseInt(request.getParameter("id"));
		float latitude = Float.parseFloat(request.getParameter("lat"));
		float longitude = Float.parseFloat(request.getParameter("lng"));
		
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
			RequestDispatcher dispatcher = null;
			request.setAttribute("dateErrorEdit", true);
			dispatcher = request.getRequestDispatcher("editevent.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		String eventDesc = request.getParameter("event_description");
		String venueDesc = request.getParameter("venue_description");
		String costDesc = request.getParameter("cost_description");
		String directions = request.getParameter("directions");
		String awareness = request.getParameter("awareness_info");
		String otherDesc = request.getParameter("other_description");
		String videos = request.getParameter("video_links");
		String links = request.getParameter("other_links");

		String cultural = request.getParameter("cultural_type");
		String education = request.getParameter("education_type");
		String music = request.getParameter("music_type");
		String sports = request.getParameter("sports_type");
		String other = request.getParameter("other_type");
		ArrayList<String> types = new ArrayList<String>();
		
		if(cultural != null) { types.add(cultural); }
		if(education != null) { types.add(education); }
		if(music != null) { types.add(music); }
		if(sports != null) { types.add(sports); }
		if(other != null) { types.add(other); }
		
		String[] formedTypes = types.toArray(new String[0]);
		
		HttpSession session = request.getSession();
		Integer userID = (Integer) session.getAttribute(SessionVariables.USER_ID);
		
		if(eventId == null)
		{
			eventId = Event.createNewEvent(title, userID, locationId, latitude, longitude, address, venue, startTime, endTime, formedTypes);	
			Event.setContent(eventId, eventDesc, venueDesc, costDesc, directions, awareness, videos, links, otherDesc);		
		}
		else
		{
			// VITALI TODO
			Event.editEventInfo(userID, eventId, title, venue, startTime, endTime, address,
					eventDesc, venueDesc, costDesc, directions, awareness, videos, links, otherDesc);	
		}
		

		response.sendRedirect("EventPage?eventID=" + eventId);
	}
}

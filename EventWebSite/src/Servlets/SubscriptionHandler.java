package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Event;
import Models.Location;
import Models.User;


@WebServlet("/SubscriptionHandler")
public class SubscriptionHandler extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public SubscriptionHandler() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String subscribeType = request.getParameter("subscribeType");
		String subscribe = request.getParameter("subscribe");
		String subscribeToId = request.getParameter("subscribeTo");
		
		HttpSession session = request.getSession();

		if(subscribe.equals("true"))
		{
			if(subscribeType.equals("event"))
			{
				Event.subscribeToEvent(Integer.parseInt(subscribeToId), (Integer) session.getAttribute(SessionVariables.USER_ID));
			}
			else if(subscribeType.equals("user"))
			{
				User.subscribeToUser(Integer.parseInt(subscribeToId), (Integer) session.getAttribute(SessionVariables.USER_ID));
			}
			else if(subscribeType.equals("locale"))
			{
				Location.subscribeToLocation(Integer.parseInt(subscribeToId), (Integer) session.getAttribute(SessionVariables.USER_ID));
			}
			else if(subscribeType.equals("attend"))
			{
				Event.attendEvent(Integer.parseInt(subscribeToId), (Integer) session.getAttribute(SessionVariables.USER_ID));
			}
		}
		else if(subscribe.equals("false"))
		{
			if(subscribeType.equals("event"))
			{
				Event.unsubscribeFromEvent(Integer.parseInt(subscribeToId), (Integer) session.getAttribute(SessionVariables.USER_ID));
			}
			else if(subscribeType.equals("user"))
			{
				User.unsubscribeFromUser(Integer.parseInt(subscribeToId), (Integer) session.getAttribute(SessionVariables.USER_ID));
			}
			else if(subscribeType.equals("locale"))
			{
				Location.unsubscribeFromLocation(Integer.parseInt(subscribeToId), (Integer) session.getAttribute(SessionVariables.USER_ID));
			}
			else if(subscribeType.equals("attend"))
			{
				Event.stopAttendingEvent(Integer.parseInt(subscribeToId), (Integer) session.getAttribute(SessionVariables.USER_ID));
			}
		}

	}
		
}

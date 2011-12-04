package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Models.Search;
import Models.Search.EventInfoSearch;
import Models.Search.SubscribedLocaleInfo;
import Models.Search.UserInfoSearch;

@WebServlet("/DashboardUpdater")
public class DashboardUpdater extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public DashboardUpdater() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			HttpSession session = request.getSession();
			Boolean loggedIn = (Boolean) session.getAttribute(SessionVariables.LOGGED_IN);
			
			if(loggedIn)
			{
				Integer userId = (Integer) session.getAttribute(SessionVariables.USER_ID);
				HashMap<String, String> myDashboardEvents = new HashMap<String, String>();
				
				String type = request.getParameter("type");

				if(type.equals("events"))
				{
					// reformat searched events into dashboard friendly events
					List<Map<EventInfoSearch, String>> mySearchedEvents = Search.findMyEvents(userId);
					
					Iterator<Map<EventInfoSearch, String>> legacyIterator = mySearchedEvents.iterator();
					while(legacyIterator.hasNext())
					{
						Map<EventInfoSearch, String> currentEvent = legacyIterator.next();
						myDashboardEvents.put(currentEvent.get(Search.EventInfoSearch.EVENT_ID), currentEvent.get(Search.EventInfoSearch.TITLE));
					}	
				}
				else if(type.equals("subEvents"))
				{
					// reformat searched events into dashboard friendly events
					List<Map<EventInfoSearch, String>> mySearchedEvents = Search.findMySubscribedEvents(userId);
					
					Iterator<Map<EventInfoSearch, String>> legacyIterator = mySearchedEvents.iterator();
					while(legacyIterator.hasNext())
					{
						Map<EventInfoSearch, String> currentEvent = legacyIterator.next();
						myDashboardEvents.put(currentEvent.get(Search.EventInfoSearch.EVENT_ID), currentEvent.get(Search.EventInfoSearch.TITLE));
					}	
				}
				else if(type.equals("subUsers"))
				{
					// reformat searched users into dashboard friendly events
					List<Map<UserInfoSearch, String>> mySearchedEvents = Search.findSubscribedUsers(userId);
					
					Iterator<Map<UserInfoSearch, String>> legacyIterator = mySearchedEvents.iterator();
					while(legacyIterator.hasNext())
					{
						Map<UserInfoSearch, String> currentEvent = legacyIterator.next();
						myDashboardEvents.put(currentEvent.get(Search.UserInfoSearch.USER_ID), currentEvent.get(Search.UserInfoSearch.NAME));
					}	
				}
				else if(type.equals("subLocales"))
				{
					// reformat searched events into dashboard friendly events
					List<Map<SubscribedLocaleInfo, String>> mySearchedEvents = Search.findSubscribedLocales(userId);
					
					Iterator<Map<SubscribedLocaleInfo, String>> legacyIterator = mySearchedEvents.iterator();
					while(legacyIterator.hasNext())
					{
						Map<SubscribedLocaleInfo, String> currentEvent = legacyIterator.next();
						myDashboardEvents.put(currentEvent.get(SubscribedLocaleInfo.LOCALE_ID), currentEvent.get(SubscribedLocaleInfo.CITY));
					}	
				}
				

				Gson gson = new GsonBuilder().serializeNulls().create();
				PrintWriter out = response.getWriter();
				String json = gson.toJson(myDashboardEvents);
				out.write(json);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}

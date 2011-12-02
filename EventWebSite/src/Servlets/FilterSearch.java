package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Search;
import Models.Search.EventInfoSearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet("/FilterSearch")
public class FilterSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public FilterSearch()
    {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("blah");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("hello");
		int locId = Integer.parseInt(request.getParameter("search_locId"));
		String keyword = request.getParameter("search_keyword");
		String dateString = request.getParameter("search_date");
		Date date = null;
		String[] eventTypes = request.getParameterValues("event_category");
		
		System.out.println(keyword);
		
		RequestDispatcher dispatcher = null;
		
		boolean searchError = false;
		
		if (keyword.isEmpty())
		{
			keyword = null;
		}
		
		if (!dateString.isEmpty())
		{
			try
			{
				date = new SimpleDateFormat("MM/dd/yy").parse(dateString);
			}
			catch (ParseException e) 
			{
				System.out.println("error");
				request.setAttribute("dateError", true);
				searchError = true;
				e.printStackTrace();
			}
		}
		else
		{
			date = null;
		}
		
		
		// insert the user into the database; if fails then user already exists
		//TODO ktam: fix the registration type when back end opens enum
		if(!searchError)
		{
			try 
			{
				List<Map<EventInfoSearch,String>> events = Search.filterEvents(locId, keyword, eventTypes, date);
				Gson gson = new GsonBuilder().serializeNulls().create();
				String myEventsInJson = gson.toJson(events);
				response.getWriter().write(myEventsInJson);
				System.out.println(events.size());
				
			} catch (ClassNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(searchError)
		{
			System.out.println("fail....");
			dispatcher = request.getRequestDispatcher("citypage.jsp?city=" + locId);
			dispatcher.forward(request, response);
			return;
		}
		else
		{
			response.sendRedirect("citypage.jsp?city=" + locId);
		}
		
	}
	

}
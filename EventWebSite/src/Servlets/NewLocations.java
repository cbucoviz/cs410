package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;


import Models.User;



/**
 * Servlet implementation class Google Earth locations
 */
@WebServlet("/Locations")
public class NewLocations extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
     
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Models.LocationAddress locAddr = new Models.LocationAddress(request.getParameter("city"), request.getParameter("state"), request.getParameter("country"));
		JsonObject result = new JsonObject();
		try
		{
			double lat = Double.parseDouble(request.getParameter("newLat"));
			double lng =  Double.parseDouble(request.getParameter("newLong"));
			result = Models.Location.addLocation(locAddr, lat, lng);
			PrintWriter out = response.getWriter();
			out.write(result.getAsString());
		}
		catch(Exception ex)
		{
			
		}
		
	}
	

}

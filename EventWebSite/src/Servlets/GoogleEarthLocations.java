package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Google Earth locations
 */
@WebServlet("/GElocations")
public class GoogleEarthLocations extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Search srch = new Search();
			JsonObject result = new JsonObject();
			JsonArray locations = srch.getGoogleEarthLoc();
			result.add("locations", locations);
			PrintWriter out = response.getWriter();
			out.write(result.toString());
		} catch(Exception e) {
			e.printStackTrace(System.err);
			response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
		}
	}

}

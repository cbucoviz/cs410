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

import Models.Search;
import Models.Search.ModeratorInfo;
import Models.User;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Google Earth locations
 */
@WebServlet("/moderators")
public class Moderators extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JsonArray returnValue = new JsonArray();
			JsonObject result = new JsonObject();
			List<Map<ModeratorInfo,String>> mods = Search.getMods();
			for(Map<ModeratorInfo,String> mod : mods)
			{
				JsonObject json = new JsonObject();
				json.addProperty("name", mod.get(ModeratorInfo.MOD_NAME));
				json.addProperty("id",  mod.get(ModeratorInfo.MOD_ID));
				returnValue.add(json);
			}
			result.add("Moderators", returnValue);
			PrintWriter out = response.getWriter();
			out.write(result.toString());
			
		} catch(Exception e) {
			e.printStackTrace(System.err);
			response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getParameter("addname") != null)
		{
			String encoding = request.getParameter("addname");
			User.makeModerator(encoding);
			
		}
		if(request.getParameter("removename") != null)
		{
			String encoding = request.getParameter("removename");
			User.removeModerator(encoding);
		}
	}
	

}


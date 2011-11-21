package Models;



import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Search {
	
	private static final String LOCATION = "city";
	private static final String LATITUDE = "Latitude";
	private static final String LONGITUDE = "Longitude";
	private static final String ID = "locationID";
	/**
	 * Used to get the name, latitude and longitude of an event city to be displayed
	 * on Google Earth.
	 * @return returns a Json Array with the Event Location information
	 */
	public JsonArray getGoogleEarthLoc()
	{
		JsonArray returnValue = new JsonArray();
		try
		{
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet locations = dbMan.findGoogleEarthLocations();
			while (locations.next()) 
			{
		        returnValue.add(
		        		createLocJson(	locations.getString(LOCATION),
		        						locations.getDouble(LATITUDE),
		        						locations.getDouble(LONGITUDE),
		        						locations.getInt(ID)
		        ));
		    }
		}
		catch(SQLException sExp)
		{
			sExp.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	/**
	 * creates a JsonObject that has the event city details as properties.
	 * 
	 * @param cityName - name of the event city
	 * @param latitude - latitude of the city
	 * @param longitude - longitude of the city
	 * @param id - id of the city
	 * @return JsonObject with the name, latitude and longitude
	 */
	private JsonObject createLocJson(String cityName, double latitude, double longitude, int id)
	{
		JsonObject json = new JsonObject();
		json.addProperty("name", cityName);
		json.addProperty("lat", latitude);
		json.addProperty("lng", longitude);
		json.addProperty("id", id);
		return json;
	}

}

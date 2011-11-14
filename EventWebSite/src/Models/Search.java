package Models;



import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Search {
	
	public JsonArray getGoogleEarthLoc()
	{
		JsonArray returnValue = new JsonArray();
		try
		{
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet locations = dbMan.findGoogleEarthLocations();
			while (locations.next()) 
			{
		        returnValue.add(createLocJson(locations.getString("city"),locations.getDouble("Latitude"),locations.getDouble("Longitude")));
		    }
		}
		catch(SQLException sExp)
		{
			sExp.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//returnValue.add(createLocJson("New York",40.69847032728747,-73.9514422416687));
		//returnValue.add(createLocJson("Wherever",43.69847032728747,-73.9514422416687));
		
		return returnValue;
	}
	
	private JsonObject createLocJson(String cityName, double latitude, double longitude)
	{
		JsonObject json = new JsonObject();
		json.addProperty("name", cityName);
		json.addProperty("lat", latitude);
		json.addProperty("lng", longitude);
		return json;
	}

}

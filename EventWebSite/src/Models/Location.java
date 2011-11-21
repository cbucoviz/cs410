package Models;

import java.sql.ResultSet;

import Database.DatabaseManager;


public class Location 
{
	
	public static LocationAddress getLocationAddress(int id)
	{
		try
		{
			DatabaseManager dbManager = DatabaseManager.getInstance();
			ResultSet result = dbManager.getLocation(id);
			if(result.next())
			{
				return new LocationAddress(	result.getString("city"),
											result.getString("state"),
											result.getString("country"));
			}
		} 
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		return null;
	}
}


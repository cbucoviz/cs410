package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseManager;
import Database.DatabaseManager.UserType;

public class Registration 
{

	public static Boolean registerUser(String name, String password, UserType type, String city, String state, String country, String email, int age)
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.getLocationID(city, state, country);
			int locID = 0;
			while(result.next())
			{			
				 locID = Integer.parseInt(result.getString(1));
			}			
			dbMan.newUser(name, password, type, locID, email, age);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
}

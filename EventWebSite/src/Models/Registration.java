package Models;

import java.sql.SQLException;

import Database.DatabaseManager;

public class Registration 
{

	public static Boolean registerUser(String name, String password, int type, String city, String state, String country, String email, int age)
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();
			int locID = 1;
			dbMan.newUser(name, password, type, locID, email, age);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
}

package Models;

import java.sql.SQLException;

import Database.DatabaseManager;

public class Registration 
{

	public static Boolean registerUser(String name, String password, int type,String location,String email,int age )
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();
			int locID = 0;
			dbMan.newUser(name, password, type, locID, email, age);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return true;
		}
		return true;
		
	}
}

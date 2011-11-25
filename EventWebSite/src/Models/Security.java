package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseManager;

public class Security 
{

	
	public static boolean isAdmin(int userID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isAdmin(userID);
			if(result.next())
			{
				return true;
			}		
			return false;
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isModerator(int userID, int locationID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isModerator(userID, locationID);
			if(result.next())
			{
				return true;
			}		
			return false;
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isOwner(int userID, int eventID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isOwner(userID, eventID);
			if(result.next())
			{
				return true;
			}		
			return false;
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isReviewOwner(int userID, int reviewID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isReviewOwner(userID, reviewID);
			if(result.next())
			{
				return true;
			}		
			return false;
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isPostOwner(int userID, int postID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isPostOwner(userID, postID);
			if(result.next())
			{
				return true;
			}		
			return false;
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isCommentOwner(int userID, int commentID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isCommentOwner(userID, commentID);
			if(result.next())
			{
				return true;
			}		
			return false;
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isLocked(int eventID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isLocked(eventID);
			while(result.next())
			{
				int isLocked = Integer.parseInt(result.getString("E.isLocked"));
				if(isLocked==0)
				{
					return false;
				}
				return true;
			}		
			return true;
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isPastEvent(int eventID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isPastEvent(eventID);
			if(result.next())
			{
				return true;
			}		
			return false;
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
}


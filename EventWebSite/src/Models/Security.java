package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseManager;

public class Security 
{
	public static boolean hasReview(int userID, int eventID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.hasReview(userID, eventID);
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
	public static boolean isModerator(int userID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.isModerator(userID);
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
	
	public static void createModerator(int userID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();	
			dbMan.createModerator(userID);
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
	}
	
	public static void removeModerator(int userID)
	{
		DatabaseManager dbMan;
		try {
			dbMan = DatabaseManager.getInstance();	
			dbMan.removeModerator(userID);
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
	}
}


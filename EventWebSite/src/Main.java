import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Database.DatabaseManager;
import Models.Event;
import Models.Event.EventContent;

//Testing testing....
//Testing AGAIN....

public class Main {

	private static DatabaseManager manager;
	
	public static void main(String args[])
	{				
		try {
			manager = DatabaseManager.getInstance();
		} catch (ClassNotFoundException e1) {
			System.out.println("Driver for MySQL (Java class) was not found!");
			e1.printStackTrace();
		} catch (SQLException e1) {
			System.out.println("Connection failed! Check login and password");
			e1.printStackTrace();
		}	
		
		try {				
		    manager.testQuery();
			//Calendar c = Calendar.getInstance();
			
			/*
			String title = "";
			int creatorID = ;
			int locationID = ;
			String address = "";
			String venue = "";
			
				c.set(year, month, dayOfMonth, hour, minute, second);
			Date startDateTime = c.getTime();			
				c.set(year, month, dayOfMonth, hour, minute, second);				
			Date endTime = c.getTime();
			
			String[] types = new String[2];
			types[0] = "";
			types[1] = "";
			
			String genDesc = "";
			String venueDesc = "";
			String priceInfo = "";
			String transport = "";
			String awareInfo = "";
			String videos = "";
			String links = "";
			String otherInfo = "";
			
			int eventID = Event.createNewEvent(title, creatorID, locationID, 
			                     address, venue, startDateTime, endTime, types);
			
			Event.setContent(eventID,genDesc, venueDesc, priceInfo, transport, awareInfo, videos, links, otherInfo);
		
						
			 */		 
						
		} catch (SQLException e) {
			System.out.println("Query failed! Could be a syntax error.");
			e.printStackTrace();
		}
			
		
	}
}




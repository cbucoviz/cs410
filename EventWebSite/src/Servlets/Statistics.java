package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Event;

@WebServlet("/Statistics")

public class Statistics  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/plain");
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		List<String[]> ageStat = Event.showStatistics(eventID, Event.StatType.AGE_STAT);
		List<String[]> cityStat = Event.showStatistics(eventID, Event.StatType.CITY_STAT);
		
		String statistics="<table id='statTable'> <tr> <td> " +
				" <p id='agetext'> Age Statistics </p> ";
		
		String ageTable = " <table id='ageTable'> <tr> "+
								"<th>Age group</th> <th>Ages</th>";				
		  
			for(int i=0; i< ageStat.size(); i++)
			{
				if(i%2==0)
				{
					ageTable = ageTable +"<tr class='even'>"+
											"<td> "+ageStat.get(i)[0]+"</td> "+
											"<td> "+ageStat.get(i)[1]+"</td> </tr>";
				}
				else
				{
					ageTable = ageTable +"<tr class='odd'>"+
										"<td> "+ageStat.get(i)[0]+"</td> "+
										"<td> "+ageStat.get(i)[1]+"</td> </tr>";
				}
			}		    
		  
		ageTable = ageTable +"</table> </td> <td> " +
				" <p id='citytext'> City Statistics </p> ";
		
		String cityTable = "  <table id='cityTable'> <tr> "+
				"<th>Location</th> <th>Attendees</th>";				

		for(int j=0; j< cityStat.size(); j++)
		{
			if(j%2==0)
			{
				cityTable = cityTable +"<tr class='even'>"+
										"<td> "+cityStat.get(j)[0]+"</td> "+
										"<td> "+cityStat.get(j)[1]+"</td> </tr>";
			}
		else
		{
			cityTable = cityTable +"<tr class='odd'>"+
								"<td> "+cityStat.get(j)[0]+"</td> "+
								"<td> "+cityStat.get(j)[1]+"</td> </tr>";
		}
		}		    
		
		cityTable = cityTable +" </table> </td> </tr> </table>";			
		
		statistics = statistics + ageTable + cityTable;
		
		PrintWriter out = response.getWriter();
		 out.print(statistics);
		 out.flush();
	        out.close();
	}
	
}


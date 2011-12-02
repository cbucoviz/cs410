package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Event;

@WebServlet("/ReportAbuse")
public class ReportAbuse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportAbuse() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String abuseType = request.getParameter("abuseType");
		Integer abuseId = Integer.parseInt(request.getParameter("abuseId"));
		String comment = request.getParameter("comment");
		
		if(abuseType.equals("event"))
		{
			Event.reportEventAbuse(abuseId, comment);
		}
		else if(abuseType.equals("post"))
		{
			Event.reportPostAbuse(abuseId, comment);
		}
	}

}

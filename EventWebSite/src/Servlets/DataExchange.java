package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/DataExchange")
public class DataExchange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataExchange() 
    {
        super();
    }

    /**
     * Use this function for data exchange via client and server.  Client should poll
     * this page when in need of session data.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		Enumeration<String> requestedData = request.getParameterNames();
		while(requestedData.hasMoreElements())
		{
			String currentRequest = requestedData.nextElement();
			dataMap.put(currentRequest, session.getAttribute(currentRequest));
		}
		
		Gson gson = new GsonBuilder().serializeNulls().create();
		PrintWriter out = response.getWriter();
		String json = gson.toJson(dataMap);
		out.write(json);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}

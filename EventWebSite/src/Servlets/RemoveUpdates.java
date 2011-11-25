package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.UserUpdates;

@WebServlet("/RemoveUpdates")
public class RemoveUpdates extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RemoveUpdates() 
	{
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		if(session.getAttribute(SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(SessionVariables.LOGGED_IN))
		{
			String userName = session.getAttribute(SessionVariables.USERNAME).toString();
			UserUpdates.removeUpdates(userName);
		}
	}

}

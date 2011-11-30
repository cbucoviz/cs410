package Servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DatabaseManager;
import Models.UserUpdates;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Login() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Boolean isLogin = new Boolean(request.getParameter("isLogin"));
		
		if(isLogin)
		{
			String email = request.getParameter(SessionVariables.EMAIL);
			String password = request.getParameter(SessionVariables.PASSWORD);
			
			try
			{
				// is the requested password valid for this user?
				DatabaseManager dbManager = DatabaseManager.getInstance();
				ResultSet user = dbManager.getUser(email);
				
				if(user.next())
				{
					if(!password.equals(user.getString("password")))
					{
						// passwords don't match
						return;
					}
				}
				else
				{
					// no such user
					return;
				}
				
				// if you reach this point the user has authenticated correctly
				HttpSession session = request.getSession();
				session.setAttribute(SessionVariables.USER_ID, Integer.parseInt(user.getString("userID")));
				session.setAttribute(SessionVariables.LOGGED_IN, true);
				session.setAttribute(SessionVariables.EMAIL, email);
				session.setAttribute(SessionVariables.USERNAME, user.getString("name"));
				session.setAttribute(SessionVariables.UPDATES, new ArrayList<String>());
				
				UserUpdates.addToSessions(
						session.getAttribute(SessionVariables.USERNAME).toString(),         	
			        	session);			
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		}
		else
		{
			// this is a logout
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
			
	}

}

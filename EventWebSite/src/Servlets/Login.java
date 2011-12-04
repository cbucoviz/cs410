package Servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Boolean isLogin = new Boolean(request.getParameter("isLogin"));
		Boolean isActivation = new Boolean((String) request.getAttribute("isActivation"));
		isLogin = (isActivation == true) ? true : isLogin;
		
		if(isLogin)
		{
			String email = "";
			String password = "";
			
			if(isActivation)
			{
				email = (String) request.getAttribute(SessionVariables.EMAIL);
				password = (String) request.getAttribute(SessionVariables.PASSWORD);
			}
			else
			{
				email = request.getParameter(SessionVariables.EMAIL);
				password = request.getParameter(SessionVariables.PASSWORD);	
			}
			RequestDispatcher dispatcher = null;
			try
			{
				// is the requested password valid for this user?
				DatabaseManager dbManager = DatabaseManager.getInstance();
				ResultSet user = dbManager.getUser(email);
				
				if(user.next())
				{					
					if(!password.equals(user.getString("password")))
					{	
						request.setAttribute("passWrong", true);
						dispatcher = request.getRequestDispatcher("index.jsp");
						dispatcher.forward(request, response);
						return;
					}					
					request.setAttribute("passWrong", false);
				}
				else
				{						
					request.setAttribute("noUser", true);					
					dispatcher = request.getRequestDispatcher("index.jsp");
					dispatcher.forward(request, response);	
					return;
				}
				
				request.setAttribute("noUser", false);
				
				// if you reach this point the user has authenticated correctly
				HttpSession session = request.getSession();
				session.setAttribute(SessionVariables.USER_ID, Integer.parseInt(user.getString("userID")));
				session.setAttribute(SessionVariables.LOGGED_IN, true);
				session.setAttribute(SessionVariables.PASSWORD, password);
				session.setAttribute(SessionVariables.EMAIL, email);
				session.setAttribute(SessionVariables.USERNAME, user.getString("name"));
				session.setAttribute(SessionVariables.UPDATES, new ArrayList<String>());
				
				UserUpdates.addToSessions(
						session.getAttribute(SessionVariables.USERNAME).toString(),         	
			        	session);			
				
				
				if(isActivation)
				{
					response.sendRedirect("index.jsp");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		}
		else
		{
			DatabaseManager dbMan;
			try {
				HttpSession session = request.getSession();	
				Object userN = session.getAttribute(SessionVariables.USERNAME);
				
				if(userN != null && UserUpdates.exists(userN.toString()))
				{	
					String userName = userN.toString();
					UserUpdates.removeFromSessions(
							userName);
					dbMan = DatabaseManager.getInstance();				
					dbMan.logoff(Integer.parseInt(session.getAttribute(SessionVariables.USER_ID).toString()));					
				}
			} 
			catch (Exception e) 
			{		
				e.printStackTrace();
			} 
			finally
			{
				HttpSession session = request.getSession();	
				session.invalidate();
				response.sendRedirect("index.jsp");
			}
		}
			
	}

}

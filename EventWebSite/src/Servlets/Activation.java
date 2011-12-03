package Servlets;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DatabaseManager;
import Models.Registration;

/**
 * Servlet for activation of accounts through a users email notification
 */

@WebServlet("/activate")
public class Activation extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Get the encoded parameter from the link.
		String encoding = request.getParameter("Id");
		//Try to activate the account.
		int activated = Registration.activateUser(encoding);
		//if activation was successful.
		if(activated >= 0)
		{
			try {
				
				DatabaseManager dbManager = DatabaseManager.getInstance();
				ResultSet user = dbManager.getUser(activated);
				if(user.next())
				{
					request.setAttribute(SessionVariables.EMAIL, user.getString("email"));
					request.setAttribute(SessionVariables.PASSWORD, user.getString("password"));
					request.setAttribute("isActivation", "true");
					RequestDispatcher dispatcher = request.getRequestDispatcher("Login");
					dispatcher.forward(request, response);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
		}
	}
	
}

package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(description = "Registration Servlet", urlPatterns = { "/Register" })
public class Register extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public Register() 
    {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// does the password match?
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		
		RequestDispatcher dispatcher = null;
		
		if(!password1.equals(password2))
		{
			// passwords don't match, send them back to the register page
			request.setAttribute("passwordMatch", false);
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		
		// insert the user into the database; if fails then user already exists
		// CALL TO DB GOES HERE
		String username = request.getParameter("username");
		
		HttpSession session = request.getSession();
		session.setAttribute("loggedIn", true);
		session.setAttribute("username", username);
		response.sendRedirect("index.jsp");
	}

}

package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DatabaseManager.UserType;
import Models.Registration;


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
		String username = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String email = request.getParameter("useremail");
		String ageInput = request.getParameter("age");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		
		RequestDispatcher dispatcher = null;
		
		boolean registrationError = false;
		
		boolean userEmpty = username.isEmpty();
		boolean password1Empty = password1.isEmpty();
		boolean password2Empty = password2.isEmpty();
		boolean emailEmpty = email.isEmpty();
		boolean ageEmpty = ageInput.isEmpty();
		boolean cityEmpty = city.isEmpty();
		boolean stateEmpty = state.isEmpty();
		boolean countryEmpty = country.isEmpty();
		
		request.setAttribute("missingUser", userEmpty);
		request.setAttribute("missingPassword1", password1Empty);
		request.setAttribute("missingPassword2", password2Empty);
		request.setAttribute("missingEmail", emailEmpty);
		request.setAttribute("missingAge", ageEmpty);
		request.setAttribute("missingCity", cityEmpty);
		request.setAttribute("missingState", stateEmpty);
		request.setAttribute("missingCountry", countryEmpty);
		
		boolean missingParam = 	userEmpty ||
								password1Empty ||
								password2Empty ||
								emailEmpty ||
								ageEmpty ||
								cityEmpty ||
								stateEmpty ||
								countryEmpty;
		
		registrationError = missingParam;
		
		if(!password1.equals(password2))
		{
			// passwords don't match, send them back to the register page
			request.setAttribute("passwordMatch", false);
			registrationError = true;
		}
		
		int age = 0;
		try
		{
			age = Integer.parseInt(ageInput);
		}
		catch(NumberFormatException e)
		{
			request.setAttribute("badAge", true);
			registrationError = true;
		}
		
		// insert the user into the database; if fails then user already exists
		//TODO ktam: fix the registration type when back end opens enum
		if(!registrationError)
		{
			UserType type = UserType.GENERAL;
			registrationError = !Registration.registerUser(username, password1, type, city, state, country, email, age);
			if(registrationError)
			{
				request.setAttribute("emailUsed", true);
			}
		}
		
		if(registrationError)
		{
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
			return;
		}
		else
		{
			HttpSession session = request.getSession();
			session.setAttribute(SessionVariables.LOGGED_IN, true);
			session.setAttribute(SessionVariables.USERNAME, username);
			response.sendRedirect("home.jsp");
		}
	}
}

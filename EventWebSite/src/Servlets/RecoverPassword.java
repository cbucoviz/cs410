package Servlets;

import java.io.IOException;
import java.sql.ResultSet;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.DatabaseManager;

@WebServlet("/RecoverPassword")
public class RecoverPassword extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public RecoverPassword() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			String email = request.getParameter("email");
			DatabaseManager dbManager = DatabaseManager.getInstance();
			ResultSet user = dbManager.getUser(email);
			
			if(user.next())
			{
				String messageContent = "Hi " + user.getString("name") + ",\n\n";
				messageContent += "Here is your login information for http://localhost:8080/EventWebSite:\n\n";
				messageContent += "Login: " + user.getString("email") + "\n";
				messageContent += "Password: " + user.getString("password") + "\n\n";
				messageContent += "Cheers,\nGlobal Events Team";

				Context initCtx = new InitialContext();
		        Context envCtx = (Context) initCtx.lookup("java:comp/env");
		        Session session = (Session) envCtx.lookup("mail/Session");
		        InternetAddress toAddress = new InternetAddress(email);
		        Message message = new MimeMessage(session);
		        message.setFrom(new InternetAddress("globalevents410@gmail.com"));
		        message.addRecipient(Message.RecipientType.TO, toAddress);
		        message.setSubject("Your Password");
		        message.setText(messageContent);
		        Transport.send(message);
			}
			
			response.sendRedirect("home.jsp");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

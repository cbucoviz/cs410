package Models;

import java.sql.ResultSet;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import Database.DatabaseManager;
import Database.DatabaseManager.UserType;

public class Registration 
{

	public static Boolean registerUser(String name, String password, UserType type, String city, String state, String country, String email, int age)
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.getLocationID(city, state, country);
			int locID = 0;
			while(result.next())
			{			
				 locID = Integer.parseInt(result.getString(1));
			}			
			dbMan.newUser(name, password, type, locID, email, age);
			ResultSet user = dbMan.getUser(email);
			int userID = -1;
			if(user.next())
			{
				userID = user.getInt("userID");
			}
			
			emailUser(name,password,email,userID);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public static void emailUser(String name, String password, String email, int userID)
	{
		try
		{
			String link = "http://localhost:8080/EventWebSite/activate?Id="+userID;
		 	Context initCtx = new InitialContext();
	        Context envCtx = (Context) initCtx.lookup("java:comp/env");
	        Session session = (Session) envCtx.lookup("mail/Session");
	        InternetAddress toAddress = new InternetAddress(email);
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("globalevents410@gmail.com"));
	        message.addRecipient(Message.RecipientType.TO, toAddress);
	        message.setSubject("Welcome "+name+" to Global Events");
	        message.setText("Welcome to Global Events, \n\n\tTo complete your registration follow this link: "+
	        				link +" \n\nThank you,\n\nThe Global Events Team");
	        Transport.send(message);
	        System.out.println("Done");
		}
		catch(AddressException e)
		{
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class SMTPAuthenticator extends Authenticator {
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("globalevents410", "Events1234");
		}
	};
	
	





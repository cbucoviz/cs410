package Models;

import java.sql.ResultSet;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import Database.DatabaseManager;
import Database.DatabaseManager.UserType;

public class Registration 
{
	
	private static final int fail = -1;
	
	/**
	 * Registers the user, calls the databaseManager to place user in DB and instantiates the 
	 * process of e-mailing the user the activation link.
	 * 
	 * @param name - name of the user
	 * @param password - password to be used for user
	 * @param type - the type of the user
	 * @param city - city of users address
	 * @param state - state or province of users address
	 * @param country - country of users address
	 * @param email - email of user
	 * @param age - age of user
	 * @return true if registration successful, false otherwise
	 */
	@SuppressWarnings("finally")
	public static Boolean registerUser(String name, String password, UserType type, String city, String state, String country, String email, int age)
	{
		Boolean registered = false;
		DatabaseManager dbMan = null;
		int userID = fail;
		try {
			dbMan = DatabaseManager.getInstance();
			ResultSet result = dbMan.getLocationID(city, state, country);
			int locID = 0;
			while(result.next())
			{			
				 locID = Integer.parseInt(result.getString(1));
			}			
			dbMan.newUser(name, password, type, locID, email, age);
			ResultSet user = dbMan.getUser(email);
			if(user.next())
			{
				userID = user.getInt("userID");
			}
			if(userID != fail)
			{
				emailUser(name,password,email,userID);
			}
			registered = true;
			
			
		} catch (Exception e) 
		{
			if(dbMan == null)
			{
				dbMan = DatabaseManager.getInstance();
			}
			if(userID != fail)
			{
				dbMan.deleteUser(userID);
			}
			e.printStackTrace();
			registered = false;
		}
		finally
		{
			return registered;
		}

		
	}
	
	
	/**
	 * E-mails the user the link to follow for activation of their account
	 * 
	 * @param name - the users name
	 * @param password - the users password
	 * @param email - the email that the activation link will be mailed to.
	 * @param userID - the new userId of the user
	 * @throws NamingException 
	 * @throws MessagingException 
	 */
	public static void emailUser(String name, String password, String email, int userID) throws NamingException, MessagingException
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
	}
}

class SMTPAuthenticator extends Authenticator {
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("globalevents410", "Events1234");
		}
	};
	
	





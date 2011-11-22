package Models;


import java.sql.ResultSet;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import biz.source_code.base64Coder.Base64Coder;







import Database.DatabaseManager;
import Database.DatabaseManager.UserType;

public class Registration 
{
	
	private static final int FAIL = -1;
	




	
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
		int userID = FAIL;
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
			if(userID != FAIL)
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
			if(userID != FAIL)
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
	
	public static int activateUser(String id)
	{
		int success = -1;
		DatabaseManager dbMan = null;
		try {
			String decoded = Base64Coder.decodeString(id);
			int userID = Integer.parseInt(decoded);
			dbMan = DatabaseManager.getInstance();
			dbMan.activateUser(userID);
			success = userID;
		}
		catch(Exception ex)
		{
			return -1;
		}
		return success;
	}
	
	
	/**
	 * E-mails the user the link to follow for activation of their account
	 * 
	 * @param name - the users name
	 * @param password - the users password
	 * @param email - the email that the activation link will be mailed to.
	 * @param userID - the new userId of the user
	 * @throws Exception 
	 */
	public static void emailUser(String name, String password, String email, int userID) throws Exception
	{
			email = email.trim();
			
			String id = String.valueOf(userID);
			
			String encoded = Base64Coder.encodeString(id);
			
			String link = "http://localhost:8080/EventWebSite/activate?Id="+encoded;
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
	
	





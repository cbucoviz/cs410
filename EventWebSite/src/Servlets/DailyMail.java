package Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import Models.DailyMailTask;

public class DailyMail extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException
    {
		DailyMailTask mailTask = new DailyMailTask(1);
		mailTask.start();
    }    
}

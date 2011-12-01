package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.DiscussionPost;
import Models.Review;
import Models.Review.ReviewInfo;


@WebServlet("/NewReview")

public class NewReview extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			
		
			
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{			
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		String content = request.getParameter("content");	
		int rating = Integer.parseInt(request.getParameter("rating"));
		int userID = Integer.parseInt(request.getParameter("reviewer"));	
		
		Review.createReview(eventID, userID, content, rating);
		
		String finalResult = "";
		PrintWriter out = response.getWriter();
		 out.print(finalResult);
		 out.flush();
	        out.close();
	}
	
}


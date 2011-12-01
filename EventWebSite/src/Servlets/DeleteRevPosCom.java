package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Comment;
import Models.DiscussionPost;
import Models.Review;



@WebServlet("/DeleteRevPosCom")

public class DeleteRevPosCom extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			
		
			
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{			
		
		String feature = request.getParameter("feature");
		
		int reviewID, postID, commentID;
		int eventID;
		
		if(feature.equals("review"))
		{
			eventID = Integer.parseInt(request.getParameter("additionalID"));
			reviewID = Integer.parseInt(request.getParameter("featureID"));
			Review.delete(reviewID, eventID);
		}
		else if(feature.equals("post"))
		{
			eventID = Integer.parseInt(request.getParameter("additionalID"));
			postID = Integer.parseInt(request.getParameter("featureID"));
			DiscussionPost.delete(postID, eventID);
		}
		else 
		{
			postID = Integer.parseInt(request.getParameter("additionalID"));
			commentID = Integer.parseInt(request.getParameter("featureID"));
			Comment.deleteComment(commentID, postID);
		}
		
		String finalResult = "";
		PrintWriter out = response.getWriter();
		 out.print(finalResult);
		 out.flush();
	        out.close();
	}
	
}


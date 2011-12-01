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



@WebServlet("/NewComment")

public class NewComment extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{			
		int postID = Integer.parseInt(request.getParameter("postID"));
		String content = request.getParameter("content");		
		int userID = Integer.parseInt(request.getParameter("poster"));	
		
		Comment.createComment(postID, userID, content);
		
		String finalResult = "";
		PrintWriter out = response.getWriter();
		 out.print(finalResult);
		 out.flush();
	        out.close();
	}
	
}

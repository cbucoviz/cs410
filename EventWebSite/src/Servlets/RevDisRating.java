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


@WebServlet("/RevDisRating")

public class RevDisRating extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			
		
			
			
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		
		String ratingType = request.getParameter("rating");
		String featureType = request.getParameter("feature");
		
		int newGoodRating = 0;
		int totalRating = 0;
		
		if(featureType.equals("review"))
		{
			int reviewID = Integer.parseInt(request.getParameter("reviewID"));
			if(ratingType.equals("up"))
			{
				Map<ReviewInfo, Integer> review = Review.rateUp(reviewID);
				newGoodRating = review.get(ReviewInfo.GOOD_RATING);
				totalRating = review.get(ReviewInfo.GOOD_RATING) + review.get(ReviewInfo.BAD_RATING);
			}
			else
			{
				Map<ReviewInfo, Integer> review = Review.rateDown(reviewID);
				newGoodRating = review.get(ReviewInfo.GOOD_RATING);
				totalRating = review.get(ReviewInfo.GOOD_RATING) + review.get(ReviewInfo.BAD_RATING);
			}
		}
		else
		{
			int postID = Integer.parseInt(request.getParameter("postID"));
			if(ratingType.equals("up"))
			{
				DiscussionPost.rateUp(postID);
			}
			else
			{
				DiscussionPost.rateDown(postID);
			}
		}
		
		String finalResult = "";
		PrintWriter out = response.getWriter();
		 out.print(finalResult);
		 out.flush();
	        out.close();
	}
	
}


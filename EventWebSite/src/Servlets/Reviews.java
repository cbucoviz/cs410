package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Event;
import Models.Review.ReviewInfo;
import Models.Review.SortReviews;
import Models.Search.EventInfoSearch;

public class Reviews  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
		response.setContentType("text/plain");
		
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		String type = request.getParameter("sort");
		List<Map<ReviewInfo, String>> reviews = null;
		
		if(type.equals("event_rating"))
		{
			reviews = Event.displayReviews(eventID, SortReviews.EVENT_RATING);
		}
		else if(type.equals("rating"))
		{
			reviews = Event.displayReviews(eventID, SortReviews.RATING);
		}
		else if(type.equals("uploadTime"))
		{
			reviews = Event.displayReviews(eventID, SortReviews.UPLOAD_TIME);
		}
		
		String allReviews="";
		for(int i=0; i< reviews.size(); i++)
		{
			String dateTime = reviews.get(i).get(ReviewInfo.DATE_TIME);
			
			String dateR = dateTime.substring(0, 10);
			
			String timeR = dateTime.substring(11, dateTime.length()-2);
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			Date timeOfReview;
			
				timeOfReview = new SimpleDateFormat("HH:mm:ss").parse(timeR);
			
			String time = df.format(timeOfReview);
		
			df = new SimpleDateFormat("MMMMMMMMM dd, yyyy");
			Date dateOfReview = new SimpleDateFormat("yyyy-MM-dd").parse(dateR);
			String date = df.format(dateOfReview);
			
			int total = Integer.parseInt(reviews.get(i).get(ReviewInfo.GOOD_RATING)) + Integer.parseInt(reviews.get(i).get(ReviewInfo.BAD_RATING));
			String totalRating = String.valueOf(total);
		String finalReview = "<div class='event_review_post'> "+
								"<hr style='width: 100%; background-color: purple; size: 10px;'/> "+
									"<div class='review_post_header'> "+
										"<table> <tr> <td> <b>Posted By:</b> </td>"+
											"<td>"+
												reviews.get(i).get(ReviewInfo.USER_NAME)+
											"</td>"+
											"<td>"+
												"(<a class='reviewLink' href='http://localhost/EventWebSite/subscribeToUser.jsp?sub="+reviews.get(i).get(ReviewInfo.USER_NAME)+"&usid="+reviews.get(i).get(ReviewInfo.USER_ID)+"'>Subscribe</a>)"+ 
											"</td>"+
											"<td>"+
												date +" at " + time +
											"</td>"+
											
											"<td>"+
												"<button type='button' name='like_rev_button' class='button1' value='like_like'>Useful</button>"+
											"</td>"+
											"<td>"+
												"<button type='button' name='dislike_rev_button' class='button1' value='dislike_like'>Not Useful</button>"+
											"</td>"+
											"<td rowspan='2'>"+
												"<b> RATING: </b>"+reviews.get(i).get(ReviewInfo.EVENT_RATING)+" "+
											"</td>"+
										"</tr> <tr> "+
				
										"<td colspan='4' style='text-align: center'>"+
											"("+reviews.get(i).get(ReviewInfo.GOOD_RATING)+" out of "+totalRating+" users found this review useful)"+
										"</td>"+
										"<td> &nbsp&nbsp&nbsp &nbsp&nbsp&nbsp</td>"+
										
									"</tr>"+
								"</table>"+
					"<hr style='width: 100%; background-color: purple; size: 1px;'/>"+
					"<p class='review_post'>"+
						reviews.get(i).get(ReviewInfo.CONTENT)+
					"</p> <br/>	<br/> </div> </div> ";
		  allReviews = allReviews + finalReview + " ";
		}
		
		
		String areaForAddingReviews = 
				"+<div id='post_review_box'>"+
					"<form action='Post_Review' method='POST'>"+
						"<h3>Write Reviews:</h3>"+
						"<textarea rows='5' cols='80' wrap='soft' style='border: 1px inset black;'></textarea>"+
						"<br/>	<br/>"+
						"<input type='submit' class='button1' value='Post' style='margin-left: 300px'/>"+
					"</form>"+
				"</div> ";
		
		allReviews = allReviews + areaForAddingReviews;
		PrintWriter out = response.getWriter();
		 out.print(allReviews);
		 out.flush();
	        out.close();	
	} catch (ParseException e) {				
		e.printStackTrace();
	}
	}
	
}

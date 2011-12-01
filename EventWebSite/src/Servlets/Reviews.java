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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Event;
import Models.Security;
import Models.DiscussionPost.PostInfo;
import Models.Review.ReviewInfo;
import Models.Review.SortReviews;
import Models.Search.EventInfoSearch;

@WebServlet("/Reviews")

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
		
		HttpSession session = request.getSession();
		Boolean loggedIn = (Boolean)session.getAttribute(SessionVariables.LOGGED_IN);
		
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
												"<b>"+reviews.get(i).get(ReviewInfo.USER_NAME)+"</b>"+
											"</td>";
											
											if(loggedIn != null && loggedIn == true)
											{
												finalReview=finalReview+
											
												"<td>"+
													"(<a class='reviewLink' href='http://localhost/EventWebSite/subscribeToUser.jsp?sub="+reviews.get(i).get(ReviewInfo.USER_NAME)+"&usid="+reviews.get(i).get(ReviewInfo.USER_ID)+"'>Subscribe</a>)"+ 
												"</td>";
											}
											finalReview=finalReview+
											"<td>"+
												date +" at " + time +
											"</td>";
												
											if(loggedIn != null && loggedIn == true)
											{
												finalReview=finalReview+
												"<td>"+
													"<button onclick=\"rateReview(" +
														""+eventID+",'event_rating',"+reviews.get(i).get(ReviewInfo.REVIEW_ID)+",'review','up');\" "+												
													"type='button' name='like_rev_button' class='button1' value='like_like'>Useful</button>"+
												"</td>"+
												"<td>"+
													"<button onclick=\"rateReview(" +
														""+eventID+",'event_rating',"+reviews.get(i).get(ReviewInfo.REVIEW_ID)+",'review','down');\" "+
													"type='button' name='dislike_rev_button' class='button1' value='dislike_like'>Not Useful</button>"+
												"</td>";
											}
											finalReview=finalReview+
											"<td rowspan='1'>"+
												"<b> RATING: </b>"+reviews.get(i).get(ReviewInfo.EVENT_RATING)+" "+
											"</td>";
											
											if(loggedIn != null && loggedIn == true && (Security.isAdmin((Integer) session.getAttribute(SessionVariables.USER_ID))
																						|| Security.isModerator((Integer) session.getAttribute(SessionVariables.USER_ID))
																						|| Security.isReviewOwner((Integer) session.getAttribute(SessionVariables.USER_ID), Integer.parseInt(reviews.get(i).get(ReviewInfo.REVIEW_ID)))))
											{
												finalReview = finalReview+
											"<td>"+
												"<button onclick=\"deleteReview("+eventID+","+reviews.get(i).get(ReviewInfo.REVIEW_ID)+"," +
													"'review','event_rating');\" " +
												"type='button' name='delete_review_button' class='button1' value='delete_review'>Delete</button>"+
											"</td>";
											}
											
									finalReview = finalReview+
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
		
		
		if(loggedIn != null && loggedIn == true && !Security.hasReview((Integer) session.getAttribute(SessionVariables.USER_ID), eventID))
		{
			Integer creatorID = (Integer) session.getAttribute(SessionVariables.USER_ID);
			String areaForAddingReviews = 
										
					"<div id='post_review_box'>"+
							"<h3>Write Review:</h3>"+
							
							"<select id='event_rating_menu'>"+
								"<option value='5'>Amazing (5 stars)</option>"+
								"<option value='4'>Good (4 stars)</option>"+
								"<option value='3'>Average (3 stars)</option>"+
								"<option value='2'>Bad (2 stars)</option>"+
								"<option value='1'>Awful (1 star)</option>"+
							"</select> "+
								
							"<textarea id='reviewTextArea' rows='5' cols='80' wrap='soft' style='border: 1px inset black;'></textarea>"+
							"<br/>	<br/>"+	
							"<button onclick=\"addReview(" +
							""+eventID+","+creatorID+",'event_rating');\" type='button' class='button1'>Post Review</button>"+
					"</div> ";
			
			allReviews = allReviews + areaForAddingReviews;
		}
	
		PrintWriter out = response.getWriter();
		 out.print(allReviews);
		 out.flush();
	        out.close();	
	} catch (ParseException e) {				
		e.printStackTrace();
	}
	}
	
}

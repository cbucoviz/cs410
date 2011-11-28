package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Comment.CommentInfo;
import Models.DiscussionPost;
import Models.DiscussionPost.PostInfo;
import Models.DiscussionPost.SortPosts;
import Models.Event;
import Models.Review.ReviewInfo;
import Models.Review.SortReviews;

public class PostsComments extends HttpServlet {

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
		
		List<Map<PostInfo, String>> posts = null;	
		
		
		if(type.equals("numComments"))
		{
			posts = Event.displayPosts(eventID, SortPosts.NUMBER_OF_COMMENTS);
		}
		else if(type.equals("rating"))
		{
			posts = Event.displayPosts(eventID, SortPosts.RATING);
		}
		else if(type.equals("recentFirst"))
		{
			posts = Event.displayPosts(eventID, SortPosts.RECENT_FIRST);
		}
		else if(type.equals("oldestFirst"))
		{
			posts = Event.displayPosts(eventID, SortPosts.OLDEST_FIRST);
		}
		
		String allPosts="";
		
		for(int i=0; i< posts.size(); i++)
		{
			String dateTime = posts.get(i).get(PostInfo.DATE_TIME);
			
			String dateR = dateTime.substring(0, 10);
			
			String timeR = dateTime.substring(11, dateTime.length()-2);
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			Date timeOfPost;			
			timeOfPost = new SimpleDateFormat("HH:mm:ss").parse(timeR);			
			String time = df.format(timeOfPost);
		
			df = new SimpleDateFormat("MMMMMMMMM dd, yyyy");
			Date dateOfPost = new SimpleDateFormat("yyyy-MM-dd").parse(dateR);
			String date = df.format(dateOfPost);			
			
			int postID = Integer.parseInt(posts.get(i).get(PostInfo.POST_ID));
			
			
			List<Map<CommentInfo, String>> comments = DiscussionPost.displayComments(postID);			
				
					
			String finalPost = 
					"<div class='event_discs_post'>	"+	
			"<hr style='width: 100%; background-color: purple; size: 10px;'/>"+
			"<div class='discs_post_header'>"+
				"<table>"+
					"<tr>"+
						"<td>"+
							posts.get(i).get(PostInfo.USER_NAME)+
						"</td>"+
						"<td>"+
							"(<a class='postLink' href='http://localhost/EventWebSite/subscribeToUser.jsp?sub="+posts.get(i).get(PostInfo.USER_NAME)+"&usid="+posts.get(i).get(PostInfo.USER_ID)+"'>Subscribe</a>)"+						
						"</td>"+
						"<td>"+
							date +" at " + time +
						"</td>"+
						"<td>"+
							"&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp"+
						"</td>"+
						"<td>"+
							"Like: "+posts.get(i).get(PostInfo.GOOD_RATING)+
						"</td>"+
						"<td>"+
							"<button type='button' name='like_post_button' class='button1' value='like_post'>Like</button>"+
						"</td>"+
						"<td>"+
							"Dislike: "+posts.get(i).get(PostInfo.BAD_RATING)+
						"</td>"+
						"<td>"+
							"<button type='button' name='dislike_post_button' class='button1' value='dislike_post'>Dislike</button>"+
						"</td>"+
						"<td>"+
							"&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp"+
						"</td>"+
						"<td>"+
							"<button type='button' name='report_post_button' class='button1' value='report_post'>Report</button>"+
						"</td>"+
					"</tr>"+
				"</table>"+
				"<hr style='width: 100%; background-color: purple; size: 1px;'/>"+
				"<p class='discs_post'>"+
					posts.get(i).get(PostInfo.CONTENT)+
				"</p>"+				
				"<button type='button' name='comment_post_button' class='button1' value='comment_post' style='float: right'>Comment Post</button>"+
				
				"<br/>";				
				
			if(comments.size()!=0)
			{
				finalPost = finalPost+" " +
"<p class='show_com' value='show' id='comFor"+posts.get(i).get(PostInfo.POST_ID)+"' " +
		"onClick=\"showComments('comments"+posts.get(i).get(PostInfo.POST_ID)+"'," +
								"'comFor"+posts.get(i).get(PostInfo.POST_ID)+"'," +
								""+posts.get(i).get(PostInfo.NUM_COMMENTS)+");\">Show comments ("+posts.get(i).get(PostInfo.NUM_COMMENTS)+") </p>";
			
				finalPost = finalPost+" <div class='comments' id='comments"+posts.get(i).get(PostInfo.POST_ID)+"'>";
				for(int j=0; j<comments.size(); j++)
				{
					String dateTimeC = comments.get(j).get(CommentInfo.DATE_TIME);
					
					String dateCom = dateTimeC.substring(0, 10);
					
					String timeCom = dateTimeC.substring(11, dateTime.length()-2);
					SimpleDateFormat dfC = new SimpleDateFormat("HH:mm");
					Date timeOfComment;			
					timeOfComment = new SimpleDateFormat("HH:mm:ss").parse(timeCom);			
					String timeC = dfC.format(timeOfComment);
				
					dfC = new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					Date dateOfComment = new SimpleDateFormat("yyyy-MM-dd").parse(dateCom);
					String dateC = dfC.format(dateOfComment);
					
					finalPost = finalPost+
				
					"<div>"+
					"<p class='post_comment'>"+
						"<b>"+comments.get(j).get(CommentInfo.USER_NAME)+":</b>"+
						" (" + dateC +" at " + timeC + ")"+
					"</p>"+
					"<p class='comment_content'>"+comments.get(j).get(CommentInfo.CONTENT)+"</p>"+
					"</div>";
				}	
				finalPost = finalPost+"</div>";
			}
			
			finalPost = finalPost +
			"</div>	</div>	<br/> <br/>";
			allPosts = allPosts + finalPost;
		}
		
		String areaForAddingReviews = 
		"<hr style='width: 100%; background-color: black; size: 20px;'/>"+
		
		"<!-- Area for writing and posting discussion posts. -->"+
		"<div id='post_discs_box'>"+
			"<form action='Post_Discussion' method='POST'>"+
				"<h3>Post Discussion:</h3>"+
				"<textarea rows='5' cols='80' wrap='soft' style='border: 1px inset black;'></textarea>"+
				"<br/>	<br/>"+
				"<input type='submit' class='button1' value='Post' style='margin-left: 300px'/>"+
			"</form></div>";
					
					
					
					
					
					
					
					
		
		allPosts = allPosts + areaForAddingReviews;
		PrintWriter out = response.getWriter();
		 out.print(allPosts);
		 out.flush();
	        out.close();	
	} catch (ParseException e) {				
		e.printStackTrace();
	}
}
}


package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseManager;
import Models.Comment.CommentInfo;
import Models.Review.ReviewInfo;
import Models.Review.SortReviews;
import Models.UserUpdates.UpdateType;

public class DiscussionPost
{
	public enum SortPosts {
	    RATING, 
	    RECENT_FIRST,
	    OLDEST_FIRST, 
	    NUMBER_OF_COMMENTS; 
	}
	
	public enum PostInfo
	{
		POST_ID,
		USER_ID,
		USER_NAME,
		CONTENT,
		DATE_TIME,
		GOOD_RATING,
		BAD_RATING,
		NUM_COMMENTS;
	}
	
	public static List<Map<PostInfo,String>> getPosts(int eventID, SortPosts type)
			throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet posts = dbMan.sortPosts(type, eventID);		
		List<Map<PostInfo, String>> postsToReturn = Collections.synchronizedList
						(new ArrayList<Map<PostInfo,String>>());
		
		while(posts.next())
		{
			Map<PostInfo, String> post = Collections.synchronizedMap(new HashMap<PostInfo,String>());
			post.put(PostInfo.POST_ID,posts.getString("P.postID"));
			post.put(PostInfo.USER_ID,posts.getString("P.userID"));
			post.put(PostInfo.USER_NAME,posts.getString("U.name"));
			post.put(PostInfo.CONTENT,posts.getString("P.postContent"));
			post.put(PostInfo.GOOD_RATING,posts.getString("P.goodRating"));
			post.put(PostInfo.BAD_RATING,posts.getString("badRating"));
			post.put(PostInfo.DATE_TIME,posts.getString("P.dateTime"));
			post.put(PostInfo.NUM_COMMENTS,posts.getString("P.numComments"));
			
			postsToReturn.add(post);
		}
	
		return postsToReturn;
	}
	
	
	public static List<Map<CommentInfo, String>> displayComments(int postID)
	{
		List<Map<CommentInfo, String>> posts;
		try {
			posts = Comment.getComments(postID);
			return posts;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;		
	}


	public static boolean createPost(int eventID, int userID, String content) {
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();		
			dbMan.newPost(eventID, userID, content);
			
			ResultSet edittedEventInfo = dbMan.edittedEventInfo(eventID, userID);
			edittedEventInfo.next();
			String updator = edittedEventInfo.getString("U.name");
			String location = edittedEventInfo.getString("L.city");
			String eventTitle = edittedEventInfo.getString("E.title");			
			int locationID =Integer.parseInt(edittedEventInfo.getString("L.locationID"));
			
			UserUpdates.addUpdate(UpdateType.NEW_DISCUSSION, eventTitle, 
									eventID, updator, userID, location, locationID, null);
			
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return false;
	}
	
	
	public static boolean delete(int postID, int eventID) 
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();		
			dbMan.deletePost(postID, eventID);
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return false;
	}

	public static boolean addComment(int postID, int userID, String content)
	{
		return Comment.createComment(postID, userID, content);
	}
	
	public static boolean deleteComment(int commentID, int postID)
	{
		return Comment.deleteComment(commentID, postID);
	}
	
	public static Map<PostInfo, Integer> rateUp(int postID) 
	{
		try {			
			DatabaseManager dbMan = DatabaseManager.getInstance();					
			dbMan.rateUpPost(postID);			
			return getRating(postID);			
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return null;			
	}

	
	public static Map<PostInfo, Integer> rateDown(int postID) {
		try {			
			DatabaseManager dbMan = DatabaseManager.getInstance();			
			dbMan.rateDownPost(postID);					
			return getRating(postID);
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return null;		
	}

	
	public static Map<PostInfo, Integer> getRating(int postID)
	{
		try {
			Map<PostInfo, Integer> rating = Collections.synchronizedMap(new HashMap<PostInfo,Integer>());
			DatabaseManager dbMan = DatabaseManager.getInstance();		
			
			ResultSet ratingInfo = dbMan.getPostRating(postID);
			
			while(ratingInfo.next())
			{				
				rating.put(PostInfo.GOOD_RATING, Integer.parseInt(ratingInfo.getString("P.goodRating")));
				rating.put(PostInfo.BAD_RATING, Integer.parseInt(ratingInfo.getString("badRating")));						
			}			
			return rating;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return null;		
	}

	public static boolean edit(int postID, String content) {
		try {			
			DatabaseManager dbMan = DatabaseManager.getInstance();			
			dbMan.editPost(postID, content);					
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return false;
	}
}

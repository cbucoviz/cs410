package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseManager;
import Models.Review.ReviewInfo;
import Models.Review.SortReviews;

public class Comment
{
	
	public enum CommentInfo
	{
		USER_ID,
		USER_NAME,
		CONTENT,
		DATE_TIME,
		COMMENT_ID;
	}
	
	public static List<Map<CommentInfo,String>> getComments(int postID)
							throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet reviews = dbMan.revealComments(postID);		
		List<Map<CommentInfo, String>> commentsToReturn = Collections.synchronizedList
								(new ArrayList<Map<CommentInfo,String>>());
		
		while(reviews.next())
		{
			Map<CommentInfo, String> comment = Collections.synchronizedMap(new HashMap<CommentInfo,String>());
			comment.put(CommentInfo.USER_ID,reviews.getString("C.userID"));
			comment.put(CommentInfo.USER_NAME,reviews.getString("U.name"));
			comment.put(CommentInfo.CONTENT,reviews.getString("C.commentBody"));
			comment.put(CommentInfo.DATE_TIME,reviews.getString("C.dateTime"));			
			comment.put(CommentInfo.COMMENT_ID,reviews.getString("C.commentID"));	
			
			commentsToReturn.add(comment);
		}
		
		return commentsToReturn;
	}
	
	public static boolean createComment(int postID, int userID, String content)
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();		
			dbMan.createComment(postID, userID, content);
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return false;
	}
	
	public static boolean deleteComment(int commentID, int postID)
	{
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();		
			dbMan.deleteComment(commentID, postID);
			return true;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return false;
	}
	
	
}

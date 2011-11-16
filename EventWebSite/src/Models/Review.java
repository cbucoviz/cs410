package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseManager;
import Models.Event.EventInfo;


public class Review
{	
	public enum SortReviews {
	    EVENT_RATING,
	    RATING,
	    UPLOAD_TIME; 
	}
	
	public enum ReviewInfo
	{
		REVIEW_ID,
		USER_ID,
		USER_NAME,
		CONTENT,
		DATE_TIME,
		GOOD_RATING,
		BAD_RATING,
		EVENT_RATING;
	}
	
	public static List<Map<ReviewInfo,String>> getReviews(int eventID, SortReviews type)
							throws ClassNotFoundException, SQLException
	{
		DatabaseManager dbMan = DatabaseManager.getInstance();
		ResultSet reviews = dbMan.sortReviews(type, eventID);		
		List<Map<ReviewInfo, String>> reviewsToReturn = Collections.synchronizedList
								(new ArrayList<Map<ReviewInfo,String>>());
		
		while(reviews.next())
		{
			Map<ReviewInfo, String> review = Collections.synchronizedMap(new HashMap<ReviewInfo,String>());
			review.put(ReviewInfo.REVIEW_ID,reviews.getString("R.reviewID"));
			review.put(ReviewInfo.USER_ID,reviews.getString("U.userID"));
			review.put(ReviewInfo.USER_NAME,reviews.getString("U.name"));
			review.put(ReviewInfo.CONTENT,reviews.getString("R.reviewContent"));
			review.put(ReviewInfo.GOOD_RATING,reviews.getString("R.goodRating"));
			review.put(ReviewInfo.BAD_RATING,reviews.getString("R.badRating"));
			review.put(ReviewInfo.DATE_TIME,reviews.getString("R.dateTime"));
			review.put(ReviewInfo.EVENT_RATING,reviews.getString("R.eventRating"));
			
			reviewsToReturn.add(review);
		}
		
		return reviewsToReturn;
	}

		
	public static float createReview(int eventID, int userID, String content, int rating) {
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();		
			float e_rating = dbMan.newReview(eventID, userID, content, rating);
			return e_rating;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return -1;
	}
	
	
	public static float delete(int reviewID, int eventID) {
		try {
			DatabaseManager dbMan = DatabaseManager.getInstance();		
			float e_rating  = dbMan.deleteReview(reviewID, eventID);
			return e_rating;
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return -1;
	}


	
	public int rateUp(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int rateDown(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getRating(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public boolean edit(int id, String content) {
		// TODO Auto-generated method stub
		return false;
	}
}

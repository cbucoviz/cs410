package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import Models.Comment.CommentInfo;
import Models.DiscussionPost;
import Models.DiscussionPost.PostInfo;

public class DiscussionPostTest {

	@Test
	public void testDisplayComments() {
		List<Map<CommentInfo, String>> comms = DiscussionPost.displayComments(3);
		String comment = comms.get(0).get(CommentInfo.CONTENT);
		assertEquals("Hi, my friend!",comment);
	}

	@Test
	public void testAddComment() {
		Boolean test = DiscussionPost.addComment(3, 1, "Test");
		assertTrue(test);
	}

	@Test
	public void testDeleteComment() {
		DiscussionPost.deleteComment(3, 3);
	}

	@Test
	public void testRateUp() {
		DiscussionPost.rateUp(1);
		assertEquals(1,1);
	}

	@Test
	public void testRateDown() {
		DiscussionPost.rateDown(1);
		assertEquals(1,1);
	}

	@Test
	public void testGetRating() {
		Map<PostInfo, Integer> test = DiscussionPost.getRating(1);
		int good = test.get(PostInfo.GOOD_RATING);
		int bad = test.get(PostInfo.BAD_RATING);
		assertTrue((good - bad) == 40);
		
	}

}

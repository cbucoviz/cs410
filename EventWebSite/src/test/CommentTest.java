package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import Models.Comment;
import Models.Comment.CommentInfo;

public class CommentTest {

	@Test
	public void testGetComments() throws ClassNotFoundException, SQLException {
		List<Map<CommentInfo, String>> comms = Comment.getComments(1);
		String id = comms.get(0).get(CommentInfo.USER_ID);
		assertEquals("4",id);
	}

	@Test
	public void testCreateComment() {
		Boolean test = Comment.createComment(1, 2, "Test Post");
		assertTrue(test);
	}

	@Test
	public void testDeleteComment() {
		Boolean test = Comment.deleteComment(13, 8);
		assertTrue(test);
	}

}

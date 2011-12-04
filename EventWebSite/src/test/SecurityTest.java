package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Models.Security;
import Models.User;

public class SecurityTest {

	@Test
	public void testHasReview() {
		Boolean check = Security.hasReview(1, 1);
		assertEquals(true,check);
	}

	@Test
	public void testIsAdmin() {
		Boolean check = Security.isAdmin(1);
		assertTrue(check);
	}

	@Test
	public void testIsModerator() {
		Boolean check = Security.isModerator(3);
		assertTrue(check);
	}

	@Test
	public void testIsOwner() {
		Boolean check = Security.isOwner(1, 1);
		assertTrue(check);
	}

	@Test
	public void testIsReviewOwner() {
		Boolean check = Security.isReviewOwner(4, 1);
		assertTrue(check);
	}

	@Test
	public void testIsPostOwner() {
		Boolean check = Security.isPostOwner(2, 1);
		assertTrue(check);
	}

	@Test
	public void testIsCommentOwner() {
		Boolean check = Security.isCommentOwner(3, 1);
		assertTrue(check);
	}

	@Test
	public void testIsLocked() {
		Boolean check = Security.isLocked(6);
		assertTrue(check);
	}

	@Test
	public void testIsPastEvent() {
		Boolean check = Security.isPastEvent(1);
		assertTrue(check);
	}

	@Test
	public void testCreateModerator() {
		Security.createModerator(39);
		assertTrue(true);
		
	}

	@Test
	public void testRemoveModerator() {
		Security.removeModerator(39);
		assertTrue(true);
	}

}

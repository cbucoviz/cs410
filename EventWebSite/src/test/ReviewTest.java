package test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import Models.Review;
import Models.Review.ReviewInfo;

public class ReviewTest {

	@Test
	public void testRateUp() {
		Map<ReviewInfo, Integer> rate = Review.rateUp(3);
		assertNotNull(rate);
	}

	@Test
	public void testRateDown() {
		Map<ReviewInfo, Integer> rate = Review.rateDown(3);
		assertNotNull(rate);
	}

	@Test
	public void testGetRating() {
		Map<ReviewInfo, Integer> rate = Review.getRating(3);
		int bad = rate.get(ReviewInfo.BAD_RATING);
		int good = rate.get(ReviewInfo.GOOD_RATING);
		assertTrue((good - bad)==2);
	}

	@Test
	public void testEdit() {
		Boolean test = Review.edit(3, "other content");
		assertTrue(test);
	}

}

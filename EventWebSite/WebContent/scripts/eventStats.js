function revealStats(id) {    
	$.post("http://localhost/EventWebSite/Statistics", {eventID:id}, function(data) {	    	
	    	$("#event_stat_tab").html(data);			
		});	
}

function revealReviews(id, sortType) {    
	$.post("http://localhost/EventWebSite/Reviews", {eventID:id, sort:sortType}, function(data) {	    	
	    	$("#event_reviews_tab").html(data);			
		});	
}

function revealPosts(id, sortType) {    
	$.post("http://localhost/EventWebSite/PostsComments", {eventID:id, sort:sortType}, function(data) {	    	
	    	$("#event_discs_tab").html(data);	
	    	$(".comments").hide();
		});	
}

function addReview(eventID, userID, sortType) { 
	var rating = $("#event_rating_menu").val();	
	var cont =$("#reviewTextArea").val();
	$.post("http://localhost/EventWebSite/NewReview", {eventID:eventID, reviewer:userID, rating:rating, sort:sortType, content:cont}, function(data) {	    	
	    	
		revealReviews(eventID, sortType);
		});	
}

function addPost(eventID, userID, sortType) { 
	var cont =$("#postTextArea").val();
	$.post("http://localhost/EventWebSite/NewPost", {eventID:eventID, poster:userID, sort:sortType, content:cont}, function(data) {	    	
	    	
		revealPosts(eventID, sortType);
		});	
}

function addComment(eventID, postID, userID,sortType) { 
		var cont =$("#comTextArea"+postID).val();		
		$.post("http://localhost/EventWebSite/NewComment", {postID:postID, poster:userID, content:cont}, function(data) {	    	
	    	
		revealPosts(eventID, sortType);
		});	
}


function rateReview(eventID, sortType,id, review) {  
	
	
	$.post("http://localhost/EventWebSite/RevDisRating", {reviewID:id, feature:review, rating:rating}, function(data) {	    	
	    	
		revealReviews(eventID, sortType);
		});	
}

function ratePost(eventID, sortType,id, post, rating) {    
	$.post("http://localhost/EventWebSite/RevDisRating", {postID:id, feature:post, rating:rating}, function(data) {	    	
	    	
		revealPosts(eventID, sortType);
		});	
}

function deleteReview(eventID, reviewID, type, sortType) {    
	$.post("http://localhost/EventWebSite/DeleteRevPosCom", {additionalID:eventID, featureID:reviewID, feature:type}, function(data) {	    	
	    	
		revealReviews(eventID, sortType);
		});	
}

function deletePost(eventID, postID, type, sortType) {    
	$.post("http://localhost/EventWebSite/DeleteRevPosCom", {additionalID:eventID, featureID:postID, feature:type}, function(data) {	    	
	    	
		revealPosts(eventID, sortType);
		});	
}

function deleteComment(eventID, postID, commentID, type, sortType) {    
	$.post("http://localhost/EventWebSite/DeleteRevPosCom", {additionalID:postID, featureID:commentID, feature:type}, function(data) {	    	
		
		revealPosts(eventID, sortType);	
		
		});	
}
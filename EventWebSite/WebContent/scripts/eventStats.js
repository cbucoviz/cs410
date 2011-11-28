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
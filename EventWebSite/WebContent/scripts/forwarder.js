$(document).ready(function()
{
	// handler for links
	$("a").click(function(event)
	{
		// get the original target and redirect them via the main content window
		var originalTarget = $(this).attr("href");
		var mainContent = $("#mainContent"); 
		mainContent.load(originalTarget);
		// stop them from proceeding normally
		event.preventDefault();
	});
	
	// handler for submits
	$("form").submit(function(event)
	{
		// is this the search form?
		var form = $(this);
		
		if(form.attr("id") == "searchForm")
		{
			// get the values and push them to javascript in ge
			var eventKeyword = form.find("input[name='eventKeyword']").val();
			var city = form.find("input[name='city']").val();
			var state = form.find("input[name='state']").val();
			var country = form.find("input[name='country']").val();
			var startDate = form.find("input[name='startDate']").val();
			var endDate = form.find("input[name='endDate']").val();
			
			geSearch(eventKeyword, city, state, country, startDate, endDate);
		}
		else
		{
			// get the original target and redirect them via the main content window
			var mainContent = $("#mainContent");
			
			if(form.attr("action") == "Login")
			{
				// login form is special, we don't want to reload content on login
				$.post(form.attr("action"), form.serialize());
			}
			else
			{
				$.post(form.attr("action"), form.serialize(), function(data){mainContent.html(data);});	
			}
		}
				
		event.preventDefault();
	});
	
	$("button[subscribe]").click(function(event) 
	{
		var button = $(this);
		
		var buttonLabel = button.html();
		var indexOfSubscribe = buttonLabel.toLowerCase().indexOf("subscribe to");
		var isSubscribe = indexOfSubscribe >= 0;
		
		if(isSubscribe)
		{
			// replace subscribe with unsubscribe
			buttonLabel = buttonLabel.replace("Subscribe to", "Unsubscribe from");
		}
		else
		{
			// replace unsubscribe with subscribe
			buttonLabel = buttonLabel.replace("Unsubscribe from", "Subscribe to");
		}
		
		button.html(buttonLabel);
		
		var subscribeTypeInput = button.attr("subscribe");
		var subscribeToInput = button.attr("subscribeId");
		
		var formData = {"subscribeType": subscribeTypeInput, "subscribeTo": subscribeToInput, "subscribe": isSubscribe};
		$.post("SubscriptionHandler", formData);
		
		
	});
	
	$("button[attend]").click(function(event) 
	{
		var button = $(this);
		
		var buttonLabel = button.html();
		var indexOfUnattend = buttonLabel.toLowerCase().indexOf("unattend");
		var isAttend = !(indexOfUnattend >= 0);
		
		if(isAttend)
		{
			buttonLabel = buttonLabel.replace("Attend", "Unattend");
		}
		else
		{
			buttonLabel = buttonLabel.replace("Unattend", "Attend");
		}
		
		var subscribeTypeInput = "attend";
		var subscribeToInput = button.attr("attend");
		
		var formData = {"subscribeType": subscribeTypeInput, "subscribeTo": subscribeToInput, "subscribe": isAttend};
		$.post("SubscriptionHandler", formData);
		
		button.html(buttonLabel);
		
		var attendeeLabel = $("#attendeesLabel");
		var numAttendees = parseInt(attendeeLabel.attr("numAttendees"));

		if(isAttend)
		{
			numAttendees = numAttendees + 1;
		}
		else
		{
			numAttendees = numAttendees - 1;
		}
		
		attendeeLabel.html(numAttendees + " people are attending this event!");
		attendeeLabel.attr("numAttendees", numAttendees);
		


		var eventButton = $("button[subscribe='event']");
		
		if(isAttend)
		{
			eventButton.html("Unsubscribe from the Event")
		}
		
	});

});

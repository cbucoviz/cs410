$(document).ready(registerForwarderListeners);

function registerForwarderListeners()
{
	// handler for links
	function linkHandler(event)
	{
		// get the original target and redirect them via the main content window
		var originalTarget = $(this).attr("href");
		if(originalTarget == "#")
		{
			event.preventDefault();
			return;
		}
		var mainContent = $("#mainContent"); 
		mainContent.load(originalTarget);
		
		// destroy all open qtips
		$('div.qtip:visible').qtip('hide');
		
		// destroy any dialogs
  		$("#report_abuse_form").dialog('destroy').remove()
		
		// stop them from proceeding normally
		event.preventDefault();
	}
	
	$("a").unbind('click.ForwarderEvents');
	$("a").bind('click.ForwarderEvents', linkHandler);
	
	// handler for submits
	$("form").unbind("submit");
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
			var startDate = form.find("input[name='start_date']").val();
			var endDate = form.find("input[name='end_date']").val();
			
			if($("#map3d").html() == null)
			{
				// we're not on google earth.. load it
				var url = "";
				url += "eventKeyword=" + encodeURIComponent(eventKeyword);
				url += "&city=" + encodeURIComponent(city);
				url += "&state=" + encodeURIComponent(state);
				url += "&country=" + encodeURIComponent(country);
				url += "&startDate=" + encodeURIComponent(startDate);
				url += "&endDate=" + encodeURIComponent(endDate);
				
				$("#mainContent").load("home.jsp?" + url);
				
			}
			else
			{
				// we're on google earth, let the search happen
				geSearch(eventKeyword, city, state, country, startDate, endDate);	
			}
		}
		else if(form.attr("id") == "ReportAbuseForm")
		{
			// do nothing, we post in the form dialog
		}
		else if(form.attr("id") == "EditEventForm")
		{
			// this function will do the posting to the form
			codeAddressForEventCreation(form);	
		}
		else if(form.attr("id") == "RegisterForm")
		{
			// this function will do the posting to the form
			codeAddressForUserCreation(form);	
		}
		else if(form.attr("id") == "filter_search_form")
		{
		   // get the values they used in the filter page, look up a couple lines in the forwarder.js, I have example of me doing it
			var keyword = form.find("input[name='search_keyword']").val();
			var city = form.find("input[name='search_locId']").val();
			var date = form.find("input[name='search_date']").val();
			var eventTypes = "";
			var types = new Array();
			var indexCounter = 0;
			
			if($("#cul_chkbx").is(':checked'))
			{
				types[indexCounter] = $("#cul_chkbx").val();
				indexCounter++;
			}
			if($("#ed_chkbx").is(':checked'))
			{
				types[indexCounter] = $("#ed_chkbx").val();
				indexCounter++;
			}
			if($("#music_chkbx").is(':checked'))
			{
				types[indexCounter] = $("#music_chkbx").val();
				indexCounter++;
			}
			if($("#sports_chkbx").is(':checked'))
			{
				types[indexCounter] = $("#sports_chkbx").val();
				indexCounter++;
			}
			if($("#others_chkbx").is(':checked'))
			{
				types[indexCounter] = $("#others_chkbx").val();
				indexCounter++;
			}
			
			for (var i = 0; i < types.length; i++)
			{
				if ((i > 0) && (eventTypes != ""))
				{
					eventTypes = eventTypes + "," + types[i];
				}
				else
				{
					eventTypes = types[i];
				}
			}
			
			

		  // setup the parameters, map the values with the keywords the servlet is expecting
		   var params = {
				   "search_keyword" : keyword, 
				   "search_date" : date, 
				   "search_locId" : city, 
				   "event_category" : eventTypes};

		  $.post('FilterSearch', params, function(data)   {
			  
	  			$("#cul_event_list").empty();
	  			$("#ed_event_list").empty();
	  			$("#music_event_list").empty();
	  			$("#sports_event_list").empty();
	  			$("#others_event_list").empty();
	  			
	  			$.each(data, function(key, val)      
				{      
		  			if (val.EVENT_TYPE == "Cultural")
		  			{
		  				$("#cul_event_list").append("<li class='event_item' popup='" + val.EVENT_ID + "'><a href='EventPage?eventID=" + val.EVENT_ID + "'>" + val.TITLE + "</a></li>");
		  			}
		  			if (val.EVENT_TYPE == "Educational")
		  			{
		  				$("#ed_event_list").append("<li class='event_item' popup='" + val.EVENT_ID + "'><a href='EventPage?eventID=" + val.EVENT_ID + "'>" + val.TITLE + "</a></li>");
		  			}
		  			if (val.EVENT_TYPE == "Music")
		  			{
		  				$("#music_event_list").append("<li class='event_item' popup='" + val.EVENT_ID + "'><a href='EventPage?eventID=" + val.EVENT_ID + "'>" + val.TITLE + "</a></li>");
		  			}
		  			if (val.EVENT_TYPE == "Sports")
		  			{
		  				$("#sports_event_list").append("<li class='event_item' popup='" + val.EVENT_ID + "'><a href='EventPage?eventID=" + val.EVENT_ID + "'>" + val.TITLE + "</a></li>");
		  			}
		  			if (val.EVENT_TYPE == "Others")
		  			{
		  				$("#others_event_list").append("<li class='event_item' popup='" + val.EVENT_ID + "'><a href='EventPage?eventID=" + val.EVENT_ID + "'>" + val.TITLE + "</a></li>");
		  			}
				});
				
	  			expandcontent('city_event_tab', $("#filterSearchRedirectTab"));
	  			registerForwarderListeners();
				});
		}
		else
		{
			// get the original target and redirect them via the main content window
			var mainContent = $("#mainContent");
			
			if(form.attr("action") == "Login")
			{
				// login form is special, we don't want to reload content on login
				var data = form.serialize();
				$.post(form.attr("action"), data);
				
				var isLogin = form.find("input[name='isLogin']").val();
				if(isLogin == "false")
				{
					// this is a logout, don't prevent default
					return;
				}
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
		var subscribeTypeInput = button.attr("subscribe");
		var subscribeToInput = button.attr("subscribeId");
		
		var buttonLabel = button.html();
		var indexOfSubscribe = buttonLabel.toLowerCase().indexOf("subscribe to");
		var isSubscribe = indexOfSubscribe >= 0;
		
		// find all buttons of this subscribe type and subscribe id and change them accordingly
		$("button[subscribe='" + subscribeTypeInput + "'][subscribeId='" + subscribeToInput+"']").each(function ()
				{
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
			
					$(this).html(buttonLabel);
			
				});
		
		
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
	
	$("button[name=report_button]").click(function()
	{
		$('#report_abuse_form').data('button', $(this)).dialog('open');
	});
}

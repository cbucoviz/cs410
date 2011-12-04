function updateDashboard()
{
	handleDashboardCase("events");
	handleDashboardCase("subEvents");
	handleDashboardCase("subLocales");
	handleDashboardCase("subUsers");
}

function handleDashboardCase(type)
{
	var addedElement = false;
	// get all the events on our dashboard
	var displayList = null;
	var linkAddress = "";
	if(type == "events")
	{
		linkAddress = "EventPage?eventID=";
		displayList = $("#my_event_list_container");
	}
	else if(type == "subEvents")
	{
		linkAddress = "EventPage?eventID=";
		displayList = $("#subs_event_list_container")
	}
	else if(type == "subLocales")
	{
		linkAddress = "citypage.jsp?city=";
		displayList = $("#subs_locale_list_container")
	}
	else if(type == "subUsers")
	{
		linkAddress = "#";
		displayList = $("#subs_user_list_container")
	}
		
	var dashboardEvents = [];
	displayList.children().each(function()
	{
		var child = $(this);
		var objectId = child.attr("objId");
		dashboardEvents.push(objectId);
	});
	
	// get the event info of the events we're supposed to have..
	var currentEvents = [];
	var currentEventsKeys = [];
	$.ajax({
		  url: 'DashboardUpdater',
		  async: false,
		  data: {"type":type},
		  type: "post",
		  dataType: 'json',
		  success: function (json) 
		  {
			  $.each(json, function(key, val) 
			  {
				  currentEvents[key] = val;
				  currentEventsKeys.push(key);
			  });
		  }
	});

	// now we have our old set of dashboard events and our new set
	// delete the ones that are no longer new
	var oldEvents = setSubtract(dashboardEvents, currentEventsKeys);
	for(x in oldEvents)
	{
		var child = displayList.children("div[objId='" + oldEvents[x] + "']");
		child.remove();
	}
	
	// now we just add back our new events
	var newEvents = setSubtract(currentEventsKeys, dashboardEvents);
	for(x in newEvents)
	{
		var newChild = "<div class='scroll_item' objId='" + newEvents[x] + "'>";
		newChild += "<a href='" + linkAddress + newEvents[x] + "'>" + currentEvents[newEvents[x]] + "</a></div>";
		displayList.append(newChild);
		var domChild = displayList.find("div[objId=" + newEvents[x] + "]").children("a");
		domChild.live('click.ForwarderEvents', linkHandler);
		addedElement = true;
	}
	
	return addedElement;
}

/*
 * 	Returns an array where the children is the result
 * 	of performing set subtraction via arr1 - arr2
 */
function setSubtract(arr1, arr2)
{
	var returnArray = [];
	for(x in arr1)
	{
		// if x is not in the second array we keep it
		if($.inArray(arr1[x], arr2) < 0)
		{
			returnArray.push(arr1[x]);
		}
	}
	
	return returnArray;
}

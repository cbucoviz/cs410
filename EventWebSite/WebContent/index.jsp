<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- BEGINNING OF INCLUDES  -->
<%@ page import="Servlets.SessionVariables" %>
<%@ include file="config/constants.jsp" %>
<link href="config/default.css" rel="stylesheet" type="text/css" />
<link href="config/ticker.css" rel="stylesheet" type="text/css" />
<script src="plugins/jquery.js" type="text/javascript"></script>
<script src="plugins/jquery-ui.js"></script>
<script src="plugins/marquee.js" type="text/javascript"></script>
<script src="scripts/forwarder.js" type="text/javascript"></script>
<!-- END OF INCLUDES -->

<script type="text/javascript">
$(document).ready(function()
{
	$jScroller.add("#scrollerContainer", "#scroller", "left", 1, true);	 
	$jScroller.start();	
	calibrateSize();
	
	$("input[type=text]").focus(function() 
	{	
		$(this).select();
	});
	
	$("input[type=password]").focus(function() 
	{	
		$(this).select();
	});
	
	update();
});

var firstLogin = true;

function update()
{
	var requestData = ["<%=SessionVariables.LOGGED_IN%>", "<%=SessionVariables.USERNAME%>"];
	var sessionData = getSessionData(requestData);

	if(firstLogin &&sessionData["<%=SessionVariables.LOGGED_IN%>"] == true)
	{
		firstLogin = false;
		$("#slidemenubar2").html("Welcome back " + sessionData["<%=SessionVariables.USERNAME%>"]);
	}
	
	setTimeout("update()", 1000);	
}

function calibrateSize()
{
	var windowWidth = $(window).innerWidth();
	var windowHeight = $(window).innerHeight() -10;
	
	var mainContent = $("#mainContent");
	 
	 mainContent.width(windowWidth * <%= FRAME_WIDTH %>);
	 mainContent.height((windowHeight * <%= FRAME_HEIGHT %>) - <%= TICKER_HEIGHT %> - <%= SEARCH_HEIGHT %>);
	
	var scroller = $("#scrollerContainer");
	scroller.width(windowWidth * <%= TICKER_WIDTH %>);	
	
	var search = $("#searchBox");
	search.width(<%= SEARCH_WIDTH %>);
	search.height(<%= SEARCH_HEIGHT %>);
	search.offset({ top: 0, left: (windowWidth - search.width())});
	
}

/*
 * Takes in a string array, creates a map out of these arrays and 
 * returns the values in another map
 */
function getSessionData(keywordArray)
{
	var dataMap = {};

	// create a map where keys are keywords, values are empty strings
	for(index in keywordArray)
	{
		dataMap[keywordArray[index]] = "";
	}

	// get the session variables requested	
	$.ajax({
		  url: 'DataExchange',
		  async: false,
		  data: dataMap,
		  dataType: 'json',
		  success: function (json) 
		  {
			  $.each(json, function(key, val) {
				    dataMap[key] = val;
				  });
		  }
		});
	
	return dataMap;
}

$(window).resize(function() 
{	
	calibrateSize();
});



</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= WEBSITE_TITLE %></title>

</head>
<body>

<%@ include file="dashboard.jsp" %>

<table></table>

<form action="Search" method="POST">
	<input type="text" id="searchBox" name='search' value='Search'/>
</form>


<div id="mainContent" class="scrollFrame">
		<%@ include file="home.jsp" %>
</div>


<div id="scrollerContainer">
		<div id="scroller">This is what the scroller looks like</div>
</div>



</body>
</html>

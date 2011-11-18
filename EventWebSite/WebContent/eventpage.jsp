<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- BEGINNING OF INCLUDES  -->
<%@ include file="config/constants.jsp" %>
<link href="config/default.css" rel="stylesheet" type="text/css"/>
<script src="plugins/jquery.js" type="text/javascript"></script>
<script src="scripts/forwarder.js" type="text/javascript"></script>
<!-- END OF INCLUDES -->


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= WEBSITE_TITLE %></title>

<script type="text/javascript">

/***********************************************
* DD Tab Menu II script- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

//Set tab to intially be selected when page loads:
//[which tab (1=first tab), ID of tab content to display (or "" if no corresponding tab content)]:
var initialtab=[1, "city_event_tab"]

//Turn menu into single level image tabs (completely hides 2nd level)?
var turntosingle=0 //0 for no (default), 1 for yes

//Disable hyperlinks in 1st level tab images?
var disabletablinks=0 //0 for no (default), 1 for yes


////////Stop editting////////////////

var previoustab="event_info_tab"

if (turntosingle==1)
{
	document.write('<style type="text/css">\n#event_tab_container{display: none;}\n</style>')
}

function expandcontent(cid, aobject){
	if (disabletablinks==1)
	{
		aobject.onclick=new Function("return false")
	}
	
	if (document.getElementById && turntosingle==0){
		highlighttab(aobject)
		if (previoustab!="")
		{
			document.getElementById(previoustab).style.display="none"
		}
		
		if (cid!=""){
			document.getElementById(cid).style.display="block"
			previoustab=cid
		}
	}
}

function highlighttab(aobject){
	if (typeof tabobjlinks=="undefined")
	{
		collectddimagetabs()
	}
	for (i=0; i<tabobjlinks.length; i++)
	{
		tabobjlinks[i].className=""
	}
	aobject.className="current"
}

function collectddimagetabs(){
	var tabobj=document.getElementById("ddimagetabs")
	tabobjlinks=tabobj.getElementsByTagName("A")
}

function do_onload(){
	collectddimagetabs()
	expandcontent(initialtab[1], tabobjlinks[initialtab[0]-1])
}

if (window.addEventListener)
{
	window.addEventListener("load", do_onload, false)
}
else if (window.attachEvent)
{
	window.attachEvent("onload", do_onload)
}
else if (document.getElementById)
{
	window.onload=do_onload
}
</script>

</head>
<body>
	<div class="event_container">
		<div class="event_name_header">
			<table class="event_name_header">
				<tr>
					<td colspan="2">
						<h2>SPORTS EVENT: Vancouver Canucks vs. Philadelphia Flyers</h2>
					</td>
					<td style="padding-left:20px" rowspan="2">
						<button type="button" name="subs_event_button" value="subscribe_event">Subscribe to This Event</button>
						<br>
						<br>
						<button type="button" name="report_event_button" value="report_event">Report This Event</button>
					</td>
				</tr>
				<tr>
					<td>
						<font size="2"><b>LOCATION: </b>Vancouver, Canada</font>
						<button type="button" name="subs_locale_button" value="subscribe_locale">Subscribe to Locale</button>
					</td>
					<td><font size="2"><b>POSTED BY: </b>User X</font>
						<button type="button" name="subs_user_button" value="subscribe_user">Subscribe to User</button>
					</td>
				</tr>
			</table>
		</div>
		<div class="event_content">
			<table class="event_content">
				<tr>
					<td style="width: 760px">
						<!-- Rating, Time, Address of Event -->
						Rating<br>
						Venue<br>
						Date & Time<br>
						Address
					</td>
					<td rowspan="2" style="width: 300px; vertical-align: top">
						<!-- Top X Events Recommendation -->
						<div class="top_event_recommendations">
							Top 5 Recommendations
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<!-- Tab pane for info, review, map, statistics -->
						<div id="ddimagetabs" class="halfmoon" style="width: 700px">
							<ul>
								<li class="selected"><a onmousedown="expandcontent('event_info_tab', this)">Event Information</a></li>
								<li><a onmousedown="expandcontent('event_discs_tab', this)">Discussions</a></li> <!-- user 'style="display:none"' to hide tab -->
								<li><a onmousedown="expandcontent('event_reviews_tab', this)">Reviews</a></li>
								<li><a onmousedown="expandcontent('event_map_tab', this)">Map</a></li>
								<li><a onmousedown="expandcontent('event_stat_tab', this)">Statistics</a></li>
							</ul>
						</div>
						
						<!--  Individual tab pages -->
						<div id="event_tab_container">
						
							<div id="event_info_tab" class="event_tab_page">
								<div id="event_desc_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Event Description:</h4>
									</div>
									<div class="event_info_content">
										<p>Event Description goes here!!! Feel free to edit when logged in as poster of this event. Thanks!</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_venue_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Venue Description:</h4>
									</div>
									<div class="event_info_content">
										<p>Venue Description goes here!!! Feel free to edit when logged in as poster of this event. Thanks!</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_cost_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Cost (& Methods of Payment, if applicable):</h4>
									</div>
									<div class="event_info_content">
										<p>Cost and Methods of Payment for event goes here!!! Feel free to edit when logged in as poster of this event. Thanks!</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_transport_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Transportation & Driving Directions:</h4>
									</div>
									<div class="event_info_content">
										<p>Transportation & Driving Directions to event goes here!!! Feel free to edit when logged in as poster of this event. Thanks!</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_others_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Other Useful Information:</h4>
									</div>
									<div class="event_info_content">
										<p>Other Useful Information related to the event goes here!!! Feel free to edit when logged in as poster of this event. Thanks!</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_vid_link_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Related Videos:</h4>
									</div>
									<div class="event_info_content">
										<p>Links for videos related to the event goes here!!! Feel free to edit when logged in as poster of this event. Thanks!</p>
									</div>
								</div>
								<br>
								<br>
								<div id="event_links_div" class="event_info_categories">
									<div class="event_info_header">
										<h4 style="margin-top: 5px;">Related Links:</h4>
									</div>
									<div class="event_info_content">
										<p>Other links related to the event goes here!!! Feel free to edit when logged in as poster of this event. Thanks!</p>
									</div>
								</div>
								
							</div>
							
							<div id="event_discs_tab" class="event_tab_page">
								Discussions goes here!!!
							</div>
							
							<div id="event_reviews_tab" class="event_tab_page">
								Reviews goes here!!!
							</div>
							
							<div id="event_map_tab" class="event_tab_page">
								Map goes here!!!
							</div>
							
							<div id="event_stat_tab" class="event_tab_page">
								Statistics goes here!!!
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>
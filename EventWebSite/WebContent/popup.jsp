<html> 
<head>    
    <title>Event Search</title>    
    <script src="scripts/forwarder.js" type="text/javascript"></script>
</head> 
<body>

    <% if(session.getAttribute(Servlets.SessionVariables.LOGGED_IN) != null && (Boolean) session.getAttribute(Servlets.SessionVariables.LOGGED_IN)) { %>					
						<button type="button" name="attend_button" class="button1" value="attend_event" style="float: right" attend="<%= request.getParameter("eventId") %>">
						<%
							if(Models.Event.isAttending(Integer.parseInt(request.getParameter("eventId")), (Integer) session.getAttribute(Servlets.SessionVariables.USER_ID)))
							{
								out.println("Unattend this Event");
							}
							else
							{
								out.println("Attend this Event");
							}
						%>
						</button>
    <% } %>
    <%@ page import="java.util.*" %>
    
	<%

		Map<Models.Event.EventInfo, String> event = Models.Event.getExistingEvent(Integer.parseInt(request.getParameter("eventId")));
		
		out.println("<b>Date: </b><i>" + event.get(Models.Event.EventInfo.EVENT_DATE) + "</i>");
		out.println("<br>");
		out.println("<b>Start Time: </b><i>" + event.get(Models.Event.EventInfo.START_TIME) + "</i>");
		out.println("<br>");
		
		if (event.get(Models.Event.EventInfo.VENUE) != null)
		{
			out.println("<b>Venue: </b><i>" + event.get(Models.Event.EventInfo.VENUE) + "</i>");
			out.println("<br>");
		}
		
		out.println("<b>Address: </b><i>" + event.get(Models.Event.EventInfo.ADDRESS) + "</i>");
		out.println("<br>");
		out.println("<br>");
		
		if (event.get(Models.Event.EventInfo.GEN_INFO).length() > 300)
		{
			out.println("<i>" + event.get(Models.Event.EventInfo.GEN_INFO).substring(0, 300) + "...</i>");
		}
		else
		{
			out.println("<i>" + event.get(Models.Event.EventInfo.GEN_INFO) + "</i>");
		}
		
	%>
</body> 
</html>

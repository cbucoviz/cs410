<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
  $(function(){

  	$("#report_abuse_form").dialog({
    autoOpen: false,
    height: 400,
    width: 500,
    modal: true,
    draggable: false,
    resizable: false,
    closeOnEscape: false,
    open: function(event, ui) 
    { 
    	$(".ui-dialog-titlebar-close").hide(); 
    },
    buttons: 
    {
     	'Report Abuse': function() 
     	{
     		// get the type of abuse and associated id
     		var button = $(this).data('button');
			var type = button.attr("abuseType");
			var id = button.attr("abuseId");
			
			var form = $("#ReportAbuseForm");
			var comment = form.serialize();
			var data = comment + "&abuseType=" + type + "&abuseId=" + id;

			$.post(form.attr("action"), data);
     		$(this).dialog('close');
      
		},
    	Cancel: function() 
    	{
      		$(this).dialog('close');
    	}
    },
    close: function() 
    {
    	allFields.val( "");
    }
   });
  });
</script>
 
 <style type="text/css">
   #report_abuse_form { font: 62.5% "Trebuchet MS", sans-serif; margin: 50px;}
  </style> 
 
 <div id="report_abuse_form" title="Report Abuse">

  <form action="ReportAbuse" id="ReportAbuseForm" method="POST">
  <fieldset>
   <label for="comment">Provide a comment:</label>
   <textarea cols="50" rows="7" name="comment" id="comment"></textarea>
   </fieldset>
  </form>
 </div>  

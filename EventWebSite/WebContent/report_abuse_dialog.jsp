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
    buttons: {
     'Report Abuse': function() {
    	$("#ReportAbuseForm").submit();
   		$(this).dialog('close');
      
     },
     Cancel: function() {
      $(this).dialog('close');
     }
    },
    close: function() {
     //ktam todo: remove stale values
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

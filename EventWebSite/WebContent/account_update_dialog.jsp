<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
  $(function(){
   $("#account_info_form").dialog({
    autoOpen: false,
    height: 400,
    width: 500,
    modal: true,
    buttons: {
     'Update Info': function() {
    	$("#AccountInfoForm").submit();
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
  
  
  $.fn.clearForm = function() {
      return this.each(function() {
        var type = this.type, tag = this.tagName.toLowerCase();
        if (tag == 'form')
          return $(':input',this).clearForm();
        if (type == 'text' || type == 'password' || tag == 'textarea')
          this.value = '';
        else if (type == 'checkbox' || type == 'radio')
          this.checked = false;
        else if (tag == 'select')
          this.selectedIndex = -1;
      });
    };

$(document).ready(function() {
	$('#AccountInfoForm').clearForm()
});
</script>
 
 <style type="text/css">
   #account_info_form { font: 62.5% "Trebuchet MS", sans-serif; margin: 50px;}
  </style> 
 
 <div id="account_info_form" title="Update Account Info" align="center">

  <form action="AccountInfoUpdate" id="AccountInfoForm" method="POST">
  <fieldset>
  	<table>
  		<tr>
			<td align="right">
				Old Password: 
			</td>
			<td>
				<input type="password" name='oldpassword' id='oldpassword' size="25"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				New Password:
			</td>
			<td>
				<input type="password" name='newpassword1' id='newpassword1' size="25"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				Confirm New Password:
			</td>
			<td>
				<input type="password" name='newpassword2' id='newpassword2' size="25"/>
			</td>
		</tr>
  	</table>
   </fieldset>
  </form>
 </div>  

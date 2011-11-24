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
});

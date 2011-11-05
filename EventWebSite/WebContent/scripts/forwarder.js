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
	$(":submit").click(function(event)
	{
		// get the original target and redirect them via the main content window
		var mainContent = $("#mainContent");
		var form = $("form");
		$.post(form.attr("action"), form.serialize(), function(data){mainContent.html(data)});
		event.preventDefault();
	});				
});

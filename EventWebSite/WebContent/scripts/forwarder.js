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
		// get the original target and redirect them via the main content window
		var mainContent = $("#mainContent");
		var form = $(this);
		console.log(form.attr("action"));
		$.post(form.attr("action"), form.serialize(), function(data){mainContent.html(data);});
		event.preventDefault();
	});				
});

function showComments(id, postID,numComID)
{
	if ($("#" + postID).attr('value') == 'show')
	{
		$("#" + id).show(250);
		$("#" + postID).attr('value', 'hide');
		$("#" + postID).text('Hide comments ('+numComID+')');
	}

	else
	{
		$("#" + id).hide(250);
		$("#" + postID).attr('value', 'show');
		$("#" + postID).text('Show comments ('+numComID+')');
	}
}
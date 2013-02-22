$(document).ready( function() {
	$('<a href="#top">Back to top</a>').insertAfter('div.chapter p');
	$('<a id="top"/>').prependTo( 'body');

});

$(document).ready(function() {
	var $note = $('<ol id="notes"></ol>').insertBefore('#footer');
	$('span.footnote').each( function(index) {
		
		$(this).before('<sup>' + (index + 1) + '</sup>')
			   .appendTo($note)
			   .wrap('<li></li>').after('<a>haha</a>').prepend('<a>wawa</a>');
		
	});
});
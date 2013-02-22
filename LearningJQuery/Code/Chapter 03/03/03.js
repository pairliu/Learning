$(document).ready(function() {

	$('#switcher h3').hover(function() {
		$(this).addClass('hover');
	}, function() {
		$(this).removeClass('hover');
	});
});

$(document).ready(function() {
	$('#switcher-default').addClass('selected');

	$('#switcher').click(function(event) {
		if ($(event.target).is('button')) {
			var bodyClass = event.target.id.split('-')[1];

			$('body').removeClass().addClass(bodyClass);

			$('#switcher button').removeClass('selected');

			$(event.target).addClass('selected');

			event.stopPropagation();
		} else {
			$('#switcher button').toggleClass('hidden');
		}
	});

	$('#switcher').trigger('click');
});

$(document).ready(function() {
	var triggers = {
		D : 'default',
		N : 'narrow',
		L : 'large'
	};
	$(document).keyup(function(event) {
		var key = String.fromCharCode(event.keyCode);
		if (key in triggers) {
			$('#switcher-' + triggers[key]).click();
		}
	});
});

$(document).ready( function() {
	$('.author').click( function() {
		$(this).addClass( 'selected');
	});
	
	$('.chapter-title').dblclick( function() {
		$(this).siblings().toggleClass( 'hidden' );
	});
});

$(document).ready( function() {
	$(document).mousemove( function(event) {
//		console.log( 'X: ' + event.pageX + '; Y:' + event.pageY );
	});
});
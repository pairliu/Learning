$.print = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  $('#button-1').show();
});

$(document).ready(function() {
  var button = document.getElementById('button-1');
  button.onclick = function() {
    $.print('hello');
    return false;
  };
});

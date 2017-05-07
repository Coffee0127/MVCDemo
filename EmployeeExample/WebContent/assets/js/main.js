$(function() {
  var url = window.location.href.substr(window.location.href.lastIndexOf('/') + 1)
  	.replace(window.location.search, '');
  $('a[href*="' + url + '"]').parent().addClass('active');
});

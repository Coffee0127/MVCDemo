$(function() {
  var url = window.location.href.substr(window.location.href.lastIndexOf('/') + 1)
    .replace(window.location.search, '');
  $('a[href*="' + url + '"]').parent().addClass('active');

  $.extend($.fn, {
    serializeObject: function() {
      var o = {};
      var a = this.serializeArray();
      $.each(a, function() {
        if (o[this.name] !== undefined) {
          if (!o[this.name].push) {
            o[this.name] = [o[this.name]];
          }
          o[this.name].push(this.value || '');
        } else {
          o[this.name] = this.value || '';
        }
      });
      return o;
    }
  });
});

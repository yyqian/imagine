var token = document.querySelector("meta[name='_csrf']") && document.querySelector("meta[name='_csrf']").getAttribute('content');
var header = document.querySelector("meta[name='_csrf_header']") && document.querySelector("meta[name='_csrf_header']").getAttribute('content');
function vote(node) {
  var v = node.id.split(/_/);
  if (v.length !== 3) {
    return false;
  }
  var arrow = v[0];
  var type = v[1];
  var id = v[2];
  var url = "/" + type + "/" + id + "/point";
  if (arrow === 'up') {
    ajax(url, 'POST', function() {
      hide('up_' + type + '_' + id);
    });
  } else if (arrow === 'down') {
    ajax(url, 'DELETE', function () {
      hide('down_' + type + '_' + id);
    });
  }
  return false;
}
function hide(id) {
  var el = document.getElementById(id);
  if (el) {
    el.style.visibility = 'hidden';
  }
}
function ajax(url, method, callback) {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      callback();
    }
  };
  xhr.open(method, url, true);
  if (header && token) xhr.setRequestHeader(header, token);
  xhr.send();
}
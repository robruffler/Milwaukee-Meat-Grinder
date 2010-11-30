(function() {
	var d=document,b=d.body,l=d.location;
	var i = d.createElement('iframe');
	b.appendChild(i);
	i.style.display = 'none';
	var doc = i.contentDocument ? i.contentDocument : i.contentWindow.document;
	doc.open();
	doc.write('<form action="http://fauxwerd.com/content/" method="post" id="fauxwerd"><input type="hidden" name="url" value="'+ encodeURIComponent(location.href) + '" /></form>');
	doc.close();
	var f = doc.getElementById('fauxwerd');
	f.submit();
	alert('Submitting');
})();
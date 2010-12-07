javascript:var t;function fauxwerd(at){var d=document,b=d.body;try{if(!b){throw(0);}var s=d.createElement('scr'+'ipt');s.src=d.location.protocol+'//localhost:8080/scripts/fauxwerd.js';b.appendChild(s);}catch(err){if(at == 10){clearTimeout(t);alert('Please allow the page to fully load, then click the bookmark again.');}else{t=setTimeout(fauxwerd,500,++at);}}}fauxwerd(1);

(function() {
	var d=document,b=d.body,l=d.location;
	var i = d.createElement('iframe');
	i.src = 'http://localhost:8080/iform?url=' + encodeURIComponent(location.href);
	b.appendChild(i);
	i.width = 0;
	i.height = 0;
	/*var doc = i.contentDocument ? i.contentDocument : i.contentWindow.document;
	doc.open();
	doc.write('<form action="http://fauxwerd.com/content/" method="post" id="fauxwerd"><input type="hidden" name="url" value="'+ encodeURIComponent(location.href) + '" /></form>');
	doc.close();
	var f = doc.getElementById('fauxwerd');*/
})();
(function() {
	function fw_strip(str) {return str.replace(/<\/?[^>]+(>|$)/g, "");}
	function fw_trim(str) {return str.replace(/^\s+|\s+$/g,"");}
	function fw_removeApostrophe(str) {return str.replace(/'/g, '%27');}
	function fw_combineMeta(meta) {
		var i = 0;
			len = meta.length,
			str = '',
			item = null;
		for (; i < len; i += 1) {
			item = meta.item(i);
			str += '<meta name="' + fw_removeApostrophe(item.name) + '" content="' + fw_removeApostrophe(item.content) + '" />';
		}
		item = null;
		return str;
	}
	function fw_content(content) {
		var c = content.replace(/<!--.*?-->/ig, '');
		c = c.replace(/(style|target|rel|border|cell[a-z]+|on[a-z]+)=".*?"/ig, '');
		c = c.replace(/[\s\t\r\n]+/ig, ' ');
		c = encodeURIComponent(c);
		c = fw_removeApostrophe(c);
		return c;
	}
	var h1 = document.getElementsByTagName('h1')[0], cTitle = '',fwPage = '<!doctype html><html lang="en">';
	fwPage += '<head><title>'+document.getElementsByTagName("title")[0]+'</title>'+fw_combineMeta(document.getElementsByTagName("meta"))+'</head>';
	fwPage += '<body>'+document.body.innerHTML+'</body></html>';
	fwPage = fw_content(fwPage);
	cTitle = getArticleTitle();
	FW.i = FW.d.createElement('iframe'); 
	var s=FW.i.style;
	s.position = 'fixed'; 
	s.left=(FW.d.documentElement.clientWidth/2) -300+'px'; 
	s.top = '0'; s.border = '0';
	s.zIndex='9999999';
	//FW.i.src='http://'+FW.env+'/iform?u='+FW.u+'&url='+encodeURIComponent(FW.l.href)+'&t='+encodeURIComponent(fw_trim(fw_trim(cTitle.replace(/'/g, '%27'))));
	FW.i.id='fauxwerd';
	FW.i.width='600px';
	FW.i.height='150px';
	FW.i.setAttribute('frameBorder','0px');
	FW.i.setAttribute('allowTransparency',true);
	FW.i.setAttribute('scrolling', 'no');
	FW.b.appendChild(FW.i);
	FW.fdoc = FW.i.contentDocument || FW.i.contentWindow.document;
	FW.fdoc.open();
	FW.fdoc.write('<!doctype html><html lang="en"><head><title>Fauxwerd</title></head><body>');
	FW.fdoc.write('<form name="fwForm" action="http://'+FW.env+'/iform" method="post">');
	FW.fdoc.write('<input type="hidden" name="u" value="'+FW.u+'" />');
	FW.fdoc.write('<input type="hidden" name="url" value="'+encodeURIComponent(FW.l.href)+'" />');
	FW.fdoc.write('<input type="hidden" name="p" value="'+fwPage+'" />');
	FW.fdoc.write('<input type="hidden" name="t" value="'+encodeURIComponent(fw_trim(fw_removeApostrophe(cTitle)))+'" />');
	FW.fdoc.write('</form>');
	FW.fdoc.write('</body></html>');
	FW.fdoc.close();
	FW.fdoc.fwForm.submit();
	fauxwerdBeGone(1);

	function getArticleTitle() {
		var curTitle = "",
			origTitle = "";

		try {
			curTitle = origTitle = document.title;

			if(typeof curTitle !== "string") { /* If they had an element with id "title" in their HTML */
				curTitle = origTitle = getInnerText(document.getElementsByTagName('title')[0]);
			}
	//        alert('2. curTitle = ' + curTitle);
		}
		catch(e) {}

		if(curTitle.match(/ [|\-] /)) {
			curTitle = origTitle.replace(/(.*)[|\-] .*/gi,'$1');
	//        alert('3. curTitle = ' + curTitle);
			if(curTitle.split(' ').length < 3) {
				curTitle = origTitle.replace(/[^|\-]*[|\-](.*)/gi,'$1');
	//            alert('4. curTitle = ' + curTitle);
			}
		}
	/* This seems broken to me    
		else if(curTitle.indexOf(': ') !== -1) {
			curTitle = origTitle.replace(/.*:(.*)/gi, '$1');
			alert('5. curTitle = ' + curTitle);

			if(curTitle.split(' ').length < 3) {
				curTitle = origTitle.replace(/[^:]*[:](.*)/gi,'$1');
				alert('6. curTitle = ' + curTitle);
			}
		}
	*/    
		else if(curTitle.length > 150 || curTitle.length < 15) {
			var hOnes = document.getElementsByTagName('h1');
			if(hOnes.length === 1) {
				curTitle = getInnerText(hOnes[0]);
	//            alert('7. curTitle = ' + curTitle);
			}
		}
		curTitle = curTitle.replace( /^\s+|\s+$/g, "" );
	//    alert('8. curTitle = ' + curTitle);

		if(curTitle.split(' ').length <= 4) {
			curTitle = origTitle;
	//        alert('9. curTitle = ' + curTitle);
		}

		return curTitle;
	}

	function getInnerText(e, normalizeSpaces) {
		var textContent    = "";

		if(typeof(e.textContent) === "undefined" && typeof(e.innerText) === "undefined") {
			return "";
		}

		normalizeSpaces = (typeof normalizeSpaces === 'undefined') ? true : normalizeSpaces;

		if (navigator.appName === "Microsoft Internet Explorer") {
			textContent = e.innerText.replace( /^\s+|\s+$/g, "" ); }
		else {
			textContent = e.textContent.replace( /^\s+|\s+$/g, "" ); }

		if(normalizeSpaces) {
			return textContent.replace( /\s{2,}/g, " "); }
		else {
			return textContent; }
	}
}())
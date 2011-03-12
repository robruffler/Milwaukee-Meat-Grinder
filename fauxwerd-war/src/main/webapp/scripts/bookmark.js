function fw_strip(str) {return str.replace(/<\/?[^>]+(>|$)/g, "");}
function fw_trim(str) {return str.replace(/^\s+|\s+$/g,"");}
var h1 = document.getElementsByTagName('h1')[0];
var title = document.getElementsByTagName('title')[0];
var cTitle = '';
/*
if (!h1 || h1 === 'undefined') {
	cTitle = (!title || title === 'undefined') ? '' : title.innerHTML;
} else {
	var strippedh1 = fw_strip(h1.innerHTML);
	if (!title || title === 'undefined') {
		cTitle = strippedh1;
	} else {
		cTitle = (title.innerHTML.indexOf(strippedh1) >= 0) ? strippedh1 : title.innerHTML;
	}
}
*/

cTitle = getArticleTitle();
FW.i = FW.d.createElement('iframe'); 
var s=FW.i.style; 
//FW.i.src='http://'+FW.env+'/iform?u='+FW.u+'&url='+encodeURIComponent('http://'+FW.l.hostname + FW.l.pathname + FW.l.search)+'&t='+encodeURIComponent(fw_trim(fw_trim(cTitle.replace(/'/g, '%27'))));
FW.i.src='http://'+FW.env+'/iform?u='+FW.u+'&url='+encodeURIComponent(FW.l.href)+'&t='+encodeURIComponent(fw_trim(fw_trim(cTitle.replace(/'/g, '%27'))));
FW.i.id='fauxwerd';
FW.i.width='600px';
FW.i.height='150px';
s.position = 'fixed'; 
s.left=(FW.d.documentElement.clientWidth/2) -300+'px'; 
s.top = '0'; s.border = '0';
s.zIndex='9999999';
FW.i.setAttribute('frameBorder','0px');
FW.i.setAttribute('allowTransparency',true);
FW.b.appendChild(FW.i); 
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
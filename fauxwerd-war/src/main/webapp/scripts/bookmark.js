var utils = document.createElement('script');
utils.src = 'http://'+FW.env+'/scripts/fw.js';
FW.b.appendChild(utils);
var h1 = document.getElementsByTagName('h1')[0];
var title = document.getElementsByTagName('title')[0];
var cTitle = '';
if (!h1 || h1 === 'undefined') {
	cTitle = (!title || title === 'undefined') ? '' : title.innerHTML;
} else {
	var strippedh1 = fw.utils.strip(h1.innerHTML);
	if (!title || title === 'undefined') {
		cTitle = strippedh1;
	} else {
		cTitle = (title.innerHTML.indexOf(strippedh1) >= 0) ? strippedh1 : title.innerHTML;
	}
}
FW.i = FW.d.createElement('iframe'); 
var s=FW.i.style; 
FW.i.src='http://'+FW.env+'/iform?u='+FW.u+'&url='+encodeURIComponent('http://'+FW.l.hostname + FW.l.pathname + FW.l.search)+'&t='+encodeURIComponent(fw.utils.trim(fw.utils.trim(cTitle.replace(/'/g, '%27'))));
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
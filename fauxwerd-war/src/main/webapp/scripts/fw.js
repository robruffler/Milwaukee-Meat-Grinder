var fw = (typeof fw === 'undefined' || !fw) ? {} : fw;
fw.dev = (window.location.hostname.indexOf("localhost") >= 0 || window.location.hostname.indexOf("l.fauxwerd.com") >= 0);
fw.live = !fw.dev;
fw.debug = fw.dev && window && typeof window.console !== "undefined";
fw.utils = {
	log: function() {
		if(!fw.debug) { return; }
		if(window.console && typeof window.console.debug !== "undefined") {
			window.console.debug.apply(window.console,arguments);
		} else {
			if(window.console && typeof window.console.log !== "undefined") {
				if(window.console.log.apply === "function") {
					window.console.log.apply(window.console,arguments);
				} else {
					window.console.log(arguments[0]);
				}
			}
		}
	},
	namespace: function (nodes,root) {
		root = root == null ? fw : root;
		var a = nodes, o = null, i, j, d;
		d = nodes.split(".");
		o = root;
		for(j = (d[0] == root) ? 1 : 0; j < d.length; j++){
			o[d[j]] = o[d[j]] || {};
			o = o[d[j]];
		}
		return o;
	},
	cookie: {
		eat: function(name) {
			var result;
			return (result = new RegExp('(?:^|; )' + encodeURIComponent(name) + '=([^;]*)').exec(document.cookie)) ? decodeURIComponent(result[1]) : null;
		},
		bake: function(name, value, days, path) {
			if (!document || !name || !value) return false;
			if (days) {
				var date = new Date();
				date.setDate(date.getDate() + days);
				var expires = "; expires="+date.toUTCString();
			} else {
				var expires = "";
			}
			var p = path ? path : "/";
			document.cookie = encodeURIComponent(name) + "=" + encodeURIComponent(String(value)) + expires + "; path=" + p;
			return true;
		},
		toss: function(name) {
			if (!document || !name) return false;
			document.cookie = encodeURIComponent(name) + "=null; expires=-1";
			return true;
		}
	}
};
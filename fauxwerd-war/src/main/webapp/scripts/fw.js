var fw = (typeof fw === 'undefined' || !fw) ? {} : fw;

fw.utils = {
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
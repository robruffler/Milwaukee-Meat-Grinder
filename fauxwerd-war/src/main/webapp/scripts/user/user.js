var fw = (typeof fw === "undefined" || !fw) ? {} : fw;
fw.user = (typeof fw.user === "undefined" || !fw.user) ? {} : fw.user;

fw.user = {
	login: function(uid) {
		fw.utils.cookie.bake('u', String(uid), 365);
	},
	logout: function() {
		fw.utils.cookie.toss('u');
	}
}
fw.namespace('modules');
fw.modules = {
	error: function(opts) {
		var msg = opts.msg || 'error';
		$('body').prepend(msg);
	}
};
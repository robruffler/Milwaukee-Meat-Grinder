<div class="success">Saved! Visit your <a href="#" id="profile">profile</a> to view your articles.</div>
<script type="text/javascript">
	$(window).load(function() {
		$('#profile').unbind('click').click(function(e) {
			e.preventDefault();
			parent.location = "http://fauxwerd.com/user/profile";
			return false;
		});
	});
</script>
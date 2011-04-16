<%@page contentType="text/html" pageEncoding="windows-1252"%>
	</section>
	<div id="fb-root"><!-- <%-- FB is the gay.--%> --></div>
	<script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
	<script src="http://connect.facebook.net/en_US/all.js"></script>
	<script>
		FB.init({
			appId  : '195555050485313',
			status : true, // check login status
			cookie : true, // enable cookies to allow the server to access the session
			xfbml  : true  // parse XFBML
		});
	</script>
	<script type="text/javascript">
		/* <![CDATA[ */
			$(document).ready(function() {
				$('#bookmarklet').unbind('click').click(function(e) { e.preventDefault; this.blur(); return false; });
			});
		/* ]]> */
	</script>
 </body>
</html>
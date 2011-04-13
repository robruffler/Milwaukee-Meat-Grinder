<%@page contentType="text/html" pageEncoding="windows-1252"%>
	</section>
	<script type="text/javascript">
		/* <![CDATA[ */
			$(document).ready(function() {
				$('#bookmarklet').unbind('click').click(function(e) { e.preventDefault; this.blur(); return false; });
			});
		/* ]]> */
	</script>
 </body>
</html>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
	</section>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
	<script type="text/javascript">
		/* <![CDATA[ */
			$(document).ready(function() {
				$('#bookmarklet').unbind('click').click(function(e) { e.preventDefault; this.blur(); return false; });
			});
		/* ]]> */
	</script>
    <!-- uservoice widget -->
	<script type="text/javascript">
	  var uservoiceOptions = {
	    key: 'fauxwerd',
	    host: 'fauxwerd.uservoice.com', 
	    forum: '104931',
	    alignment: 'left',
	    background_color:'#f00', 
	    text_color: 'white',
	    hover_color: '#06c',
	    lang: 'en',
	    showTab: true
	  };
	  function _loadUserVoice() {
	    var s = document.createElement('script');
	    s.src = ("https:" == document.location.protocol ? "https://" : "http://") + "cdn.uservoice.com/javascripts/widgets/tab.js";
	    document.getElementsByTagName('head')[0].appendChild(s);
	  }
	  _loadSuper = window.onload;
	  window.onload = (typeof window.onload != 'function') ? _loadUserVoice : function() { _loadSuper(); _loadUserVoice(); };
	</script>
 </body>
</html>
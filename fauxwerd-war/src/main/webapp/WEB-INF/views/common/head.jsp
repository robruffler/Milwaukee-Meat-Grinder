<%@ page
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en" xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml">
  <head>
    <meta charset="utf-8" />
	<meta property="og:type" content="article" />
	<meta property="og:site_name" content="fauxwerd" />
	<meta property="og:title" content="<%= request.getParameter("title") %> on Fauxwerd.com" />
	<meta property="og:app_id" content="195555050485313" />
	<meta property="og:url" content="${currentUrl}" />
	<meta property="og:image" content="http://www.fauxwerd.com/images/logo.png" />
    <title><%= request.getParameter("title") %> on Fauxwerd.com</title>
	<link rel="stylesheet" type="text/css" href="/styles/full-base.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"></script>
	<script src="/scripts/fw.js"><!----></script>
	<script src="/scripts/modules.js"><!----></script>
	<script src="/scripts/user/user.js"><!----></script>
	<!--[if IE]>
	<script type="text/javascript">
		document.createElement('header');
		document.createElement('section');
		document.createElement('article');
		document.createElement('nav');
	</script>
	<![endif]-->
    <!-- google analytics -->
    <script type="text/javascript">
	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-21602258-1']);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
    </script>
  </head>
  <body>

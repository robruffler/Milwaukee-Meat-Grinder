<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Fauxwerd - Submit URL</title>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"><!-- jquery for easy ajax --></script>
		<script type="text/javascript">
			alert('${url} ${u}');
		</script>
    </head>
    <body>
		<form name="fauxwerd" id="fauxwerd" action="content/" method="post">
			<input type="text" value="${url}" name="url" />
			<input type="text" value="${u}" name="u" />
		</form>
    </body>
</html>

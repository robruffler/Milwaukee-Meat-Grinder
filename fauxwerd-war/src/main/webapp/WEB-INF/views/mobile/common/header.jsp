<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<section class="header">
	<header class="logo"><a href="#" class="logo-link"><!-- fauxwerd --></a></header>
	<nav>
		<ul class="nav">
		<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
			<li><a href="/m/login">Login</a></li>
		</sec:authorize>
		<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
			<li>
				<a href="/m/articles">articles</a>
				<a href="/m/groups">groups</a>
				<a href="/m/topics">topics</a>
			</li>
		</sec:authorize>
		</ul>
	</nav>
</section>
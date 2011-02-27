<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<section id="wrapper">
	<header id="header">
		<a href="/" id="site-logo"><span class="seo">fauxwerd</span></a>
		<ul id="menu">
		<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
			<li><a href="/login">Login</a></li>
			<!--form id="login-form" action="j_spring_security_check" method="post">
				<fieldset class="form-fs">
					<legend>Log in</legend>
					<ul class="form-list">
						<li>
							<label for="email">Email</label>
							<input name="j_username" id="email" type="input" />
						</li>
						<li>
							<label for="password">Password</label>
							<input name="j_password" id="password" type="password" />
						</li>
						<li>
							<input type="hidden" name="_spring_security_remember_me" value="true" />
							<input type="submit" value="Log in" />
						</li>
					</ul>
				</fieldset>
			</form-->
		</sec:authorize>
		<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
				<li><a href="/profile">Profile</a></li>
				<li><a href="<spring:url value="/logout" htmlEscape="true" />" class="lesser">Logout</a></li>
		</sec:authorize>
		</ul>
	</header>
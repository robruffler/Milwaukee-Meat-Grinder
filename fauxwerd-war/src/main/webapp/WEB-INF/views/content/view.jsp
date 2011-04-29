<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fw" uri="/WEB-INF/tlds/fauxwerd.tld" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
    <jsp:param name="title" value="${content.title}" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<div id="dialog" title="Edit Topics">
	<ul id="topics-list" class="topics removable">
		<c:forEach items="${content.topics}" var="topic">
			<li><a href="/topic/${topic.id}" data-id="${topic.id}" class="topic-${topic.id}" title="Remove">${topic.name} [x]</a></li>
		</c:forEach>
	</ul>
	<form id="topic-form">
		<input type="text" id="topic-name" name="topic-name" autofocus /> <input type="submit" id="add-topic" value="Add Topic" />
	</form>
	<a href="#" id="all-set">Done</a>
</div>
<div id="external-view" title="Return to Fauxwerd">
<!-- -->
</div>

<section class="share">
	<a href="http://twitter.com/share" class="twitter-share-button" data-count="none" data-via="fauwerd">Tweet</a>
	<fb:like href="http://<%= request.getServerName() + request.getAttribute("javax.servlet.forward.request_uri").toString() %>" layout="button_count" show_faces="false" width="450" />
</section>
<article class="fauxwerd-article">
	<header>${content.title}</header>
	<cite>via <a href="${content.url}" class="external-view">${content.site.hostname}</a></cite>
	<ul id="topics" class="topics">
		<c:forEach items="${content.topics}" var="topic">
			<li><a href="/topic/${topic.id}" class="topic-${topic.id}">${topic.name}</a></li>
		</c:forEach>
		<li id="edit-topics-li">
			<a href="#" id="edit-topics" class="edit-topics">
			<c:choose>
				<c:when test="${fn:length(content.topics) > 0}">
					edit topics
				</c:when>
				<c:otherwise>
					add topics
				</c:otherwise>
			</c:choose>
			</a>
		</li>
	</ul>
	
	<fw:content contentId="${content.id}"/>
</article>
<script>
	(function($) {
		var $topics = $("#topics"),
			$list = $("#topics-list"),
			$dialog = $("#dialog"),
			$external = $("#external-view"),
			$window = $(window),
			firstRun = true;
		$(document).ready(function() {
			$dialog.dialog({
				autoOpen: false,
				modal: true,
				width: 500
			});
			$external.dialog({
				autoOpen: false,
				modal: true,
				width: $window.width() - 50,
				height: $window.height() - 50,
				draggable: false,
				close: function() {
					$(".external-article").remove();
				}
			});
			$("#edit-topics").click(function(e) {
				e.preventDefault();
				$dialog.dialog("open");
				$("#topic-name").get(0).focus();
				return false;
			});
			$("#all-set").click(function(e) {
				e.preventDefault();
				$dialog.dialog("close");
				return false;
			});
			$("#ui-dialog-title-external-view").click(function() {
				$external.dialog("close");
				$(".external-article").remove();
			});
			$(".external-view").click(function(e) {
				e.preventDefault();
				var width = $window.width() - 50,
					height = $window.height() - 50;
				$(".ui-dialog").css({ "width": width, "height": height });
				$('<iframe src="'+ $(this).attr('href') +'" class="external-article"></iframe>').css({"width": width - 5, "height": height - 40}).appendTo($external);
				$external.dialog("option", "width", width);
				$external.dialog("option", "height", height);
				$external.dialog("open");
				return false;
			});
			$("#topic-form").submit(function(e) {
				e.preventDefault();
				$.ajax({
					url: "/content/add-topic/${content.id}",
					data: {"topic": $("#topic-name").val() },
					type: "post",
					dataType: "json",
					success: function(data) {
						$('<li><a href="/topic/' + data.id + '" class="topic-' + data.id + '">' + data.name + '</a></li>').insertBefore("#edit-topics-li");
						$list.append('<li><a href="/topic/' + data.id + '" data-id="' + data.id + '" class="topic-' + data.id + '" title="Remove">' + data.name + ' [x]</a></li>');
						$("#topic-name").val('').get(0).focus();
						if (firstRun) {
							$("#edit-topics-li a").text("edit topics");
							firstRun = false;
						}
					},
					error: function( x, t, err) {
						fw.utils.log(err);
					}
				});
				return false;
			});
			$list.delegate('a', 'click', function(e) {
				e.preventDefault();
				var topicId = $(this).attr("data-id");
				$.ajax({
					url: '/content/remove-topic/${content.id}/' + topicId,
					type: 'get',
					dataType: 'json',
					success: function(data) {
						$(".topic-" + topicId).parent().remove();
					},
					error: function( x, t, err) {
						fw.utils.log(err);
					}
				});
				return false;
			});
		});
	}(jQuery));
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />

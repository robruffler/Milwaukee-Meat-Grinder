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
    <jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>

<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="${index.title}"/>
</jsp:include>

<p>
<ul id="topics">
    <c:forEach items="${content.topics}" var="topic">
        <li><a href="/topic/${topic.id}">${topic.name}</a> <span class="remove">[<a href="#" class="remove-topic" topicId="${topic.id}">x</a>]</span></li>
    </c:forEach>
</ul>
<a href="#" id="add-topics">Add Topics</a>
<a href="#" id="edit-topics">Edit Topics</a>
<div id="add-box">
    <form id="topic-form" >               
        <input type="text" name="topic" /> <button type="button" id="add-topic">Add</button> <a href="#" id="add-hide">Done</a> <a href="#" id="edit-hide">Done</a>
    </form> 
</div>        
</p>

<h1>${content.title}</h1>

<p><a href="${content.url}">View this article on ${content.site.hostname}</a></p>

<fw:content contentId="${content.id}"/>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />

<script language="javascript"><!-- 
$(document).ready(function() {
    // hides the add-box as soon as the DOM is ready
    $('#add-box').hide();
    $('#topics li').find('.remove').hide();    
    if ($('#topics li').length > 0) {
    	$('#add-topics').hide();
    	$('#add-hide').hide();
    } else {
    	$('#edit-topics').hide();
    	$('#edit-hide').hide();
    }
    
    // shows the add-box on clicking the noted link  
    $('#add-topics').click(function() {
        $('#add-box').show();
        $('#add-topics').hide();
        return false;
    });
        
    // hides the add-box on clicking the noted link  
    $('#add-hide').click(function() {
        $('#add-box').hide();
	    $('#add-topics').show();
	    return false;
	});
        	 	 
    // submits topic form
    $("#add-topic").click(function() {
        $.post("/content/add-topic/${content.id}", $("#topic-form").serialize(),
        		function( data ) {
              $("ul#topics").append('<li><a href="/topic/' + data.id + '" topicId="' + data.id + '">' + data.name + '</a> <span class="remove">[<a href="#" class="remove-topic" topicId="' + data.id + '">x</a>]</span></li>');
              $('#topics li').find('.remove').hide();
              $('form input:text[name="topic"]').val('');              
              $('#add-box').hide();
              $('#edit-topics').show();              
            }, "json");
	});
    
    //also submits topic form
    $('#topic-form input').bind('keypress', function(e) {
    	 var code = (e.keyCode ? e.keyCode : e.which);
    	 if(code == 13) { //Enter keycode
    	        e.preventDefault();
    	        $.post("/content/add-topic/${content.id}", $("#topic-form").serialize(),
    	                function( data ) {
    	              $("ul#topics").append('<li><a href="/topic/' + data.id + '" topicId="' + data.id + '">' + data.name + '</a> <span class="remove">[<a href="#" class="remove-topic" topicId="' + data.id + '">x</a>]</span></li>');
    	              $('#topics li').find('.remove').hide();
    	              $('form input:text[name="topic"]').val('');              
    	              $('#add-box').hide();
    	              $('#edit-topics').show();              
    	            }, "json");
    	 }    	    	
    });    
        
    // shows the edit-box on clicking the noted link  
    $('#edit-topics').click(function() {
        $('#add-box').show();
        $('#edit-topics').hide();
        $('#topics li').find('.remove').show();        
        return false;
    });
        
    // hides the add-box on clicking the noted link  
    $('#edit-hide').click(function() {
        $('#add-box').hide();
        $('#edit-topics').show();
        $('#topics li').find('.remove').hide();        
        return false;
    });
        
    $('.remove-topic').live('click', function(e) {
    	var topicId = $(this).attr("topicId");
        $.get('/content/remove-topic/${content.id}/' + topicId, "",
                function( data ) {
            return false;
        }, "json");
        $(this).parent().parent().remove();
        $('#add-box').hide();
        $('form input:text[name="topic"]').val('');
        if ($('#topics li').length > 0) {
            $('#edit-topics').show();

        } else {
            $('#add-topics').show();
        }

    });
    
});
--></script>
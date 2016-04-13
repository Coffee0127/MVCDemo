<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
$(function() {
	var path = location.pathname.substr(location.pathname.lastIndexOf('/') + 1);
	switch (path) {
	   case 'emp.do':
		   $('#mvc').addClass('active');
		   break;
	   case '':
	   default:
		   $('#index').addClass('active');
		   break;
	}
});
</script>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctxPath}">MVC Demo</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li id="index"><a href="${ctxPath}">Home</a></li>
                <li id="nomvc"><a href="${ctxPath}/hello.do">Without MVC</a></li>
                <li id="mvc"><a href="${ctxPath}/emp.do">With MVC</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>
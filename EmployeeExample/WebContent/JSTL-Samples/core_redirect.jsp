<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>JSTL - redirect</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!--[if lt IE 9]>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="container">
      <div class="row">
        <h1 class="text-center"><c:out value="<c:redirect> 的用法" /></h1>
      </div>
      <div class="row">
        <div class="col-xs-4">
          <div class="list-group">
            <a href="HelloJSTL.jsp" class="list-group-item">HelloJSTL</a>
            <a href="core_catch.jsp" class="list-group-item">core_catch</a>
            <a href="core_forEach01.jsp" class="list-group-item">core_forEach01</a>
            <a href="core_forEach02.jsp" class="list-group-item">core_forEach02</a>
            <a href="core_forEach03.jsp" class="list-group-item">core_forEach03</a>
            <a href="core_forEach04.jsp" class="list-group-item">core_forEach04</a>
            <a href="core_forEach05_List.jsp" class="list-group-item">core_forEach05_List</a>
            <a href="core_if.jsp" class="list-group-item">core_if</a>
            <a href="core_import.jsp" class="list-group-item">core_import</a>
            <a href="core_redirect.jsp" class="list-group-item">core_redirect</a>
            <a href="core_url.jsp" class="list-group-item">core_url</a>
          </div>
        </div>
        <div class="col-xs-8">
          <c:redirect url="https://tw.yahoo.com">
            <c:param name="name" value="Peter" />
          </c:redirect>
          <c:out value="不會執行!!!" />
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
      $(function() {
        var url = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
        $('a[href="' + url + '"]').addClass('active');
      });
    </script>
  </body>
</html>

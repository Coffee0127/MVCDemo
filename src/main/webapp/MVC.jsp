<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>MVC Sample</title>
    <link href="resources/bootstrap.min.css" rel="stylesheet">
    <link href="resources/starter-template.css" rel="stylesheet">
    <style>
    .thead-default th {
        color: #55595c;
        background-color: #eceeef;
        text-align: center;
    }
    </style>
</head>
<body>
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
                    <li><a href="${ctxPath}">Home</a></li>
                    <li><a href="${ctxPath}/hello.do">Without MVC</a></li>
                    <li class="active"><a href="${ctxPath}/emp.do">With MVC</a></li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </nav>

    <div class="container">
        <div class="starter-template">
            <h1>MVC Demo</h1>
            <table class="table table-hover">
                <thead class="thead-default">
                    <tr>
                        <th>員工編號</th>
                        <th>姓名</th>
                        <th>職稱</th>
                        <th>雇用日期</th>
                        <th>薪水</th>
                        <th>加給</th>
                        <th>部門編號</th>
                    </tr>
                </thead>
                <c:forEach var="empVO" items="${emps}">
                    <tr align="center">
                        <td>${empVO.empno}</td>
                        <td>${empVO.ename}</td>
                        <td>${empVO.job}</td>
                        <td>${empVO.hiredate}</td>
                        <td>${empVO.sal}</td>
                        <td>${empVO.comm}</td>
                        <td>${empVO.deptno}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <script src="resources/jquery.min.js"></script>
    <script src="resources/bootstrap.min.js"></script>
</body>
</html>
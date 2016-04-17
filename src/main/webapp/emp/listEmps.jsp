<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="org.json.*" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>MVC Sample</title>
    <jsp:include page="/WEB-INF/layout/meta.jsp" />
    <style>
    .thead-default th {
        color: #55595c;
        background-color: #eceeef;
        text-align: center;
        vertical-align: middle !important;
    }
    .table>tbody>tr>td {
        vertical-align: middle;
    }
    .btn:focus {
        outline: 0 !important;;
    }
    #myModal table tr th {
        width: 150px;
        text-align: right;
    }
    #myModal table tr:first-child th, #myModal table tr:first-child td {
        border-top: none;
    }
    .delete td {
        background-color: #D9534F;
    }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/layout/header.jsp" />

    <div class="container">
        <div class="starter-template">
            <h1>MVC Demo</h1>
            <table id="emps" class="table table-hover" style="border-bottom: 2px solid #ddd">
                <thead class="thead-default">
                    <tr>
                        <th>員工編號</th>
                        <th>姓名</th>
                        <th>職稱</th>
                        <th>雇用日期</th>
                        <th>部門編號</th>
                        <th><button type="button" class="btn btn-primary" onclick="location.href='${ctxPath}/emp/addEmp.jsp';return false;"><span style="padding-right: 5px;" class="glyphicon glyphicon-plus"></span>新增</button></th>
                    </tr>
                </thead>
                <% java.util.List list = (java.util.List) request.getAttribute("emps"); %>
                <%@ include file="/WEB-INF/pages/page1.file" %>
                <c:forEach var="empVO" items="${emps}" begin="<%=pageIndex%>" end="<%=pageIndex+pageSize-1%>">
                    <c:set var="empVO" value="${empVO}" scope="page" />
                    <% pageContext.setAttribute("empJSON", new JSONObject(pageContext.getAttribute("empVO"))); %>
                    <tr data-empno="${empVO.empno}" align="center">
                        <td>${empVO.empno}</td>
                        <td>${empVO.ename}</td>
                        <td>${empVO.job}</td>
                        <td>${empVO.hiredate}</td>
                        <td>${empVO.deptno}</td>
                        <td><button type="button" class="btn btn-info btnShowEmp" data-emp='${empJSON}' data-toggle="modal" data-target="#myModal"><span style="padding-right: 5px;" class="glyphicon glyphicon-th-list"></span>檢視</button></td>
                    </tr>
                </c:forEach>
            </table>
            <div style="padding: 0px 1.5em;">
                <%@ include file="/WEB-INF/pages/page2.file" %>
            </div>
        </div>
    </div>

    <script>
        $(function() {
            $("#myModal").attr({
                'data-keyboard': false
            }).on('show.bs.modal', function(event) {
                var btn = $(event.relatedTarget);      // Button that triggered the modal
                var emp = btn.data('emp');
                var $empTable = $('#myModal');
                for (var key in emp) {
                    $empTable.find('span.' + key).html(emp[key]);
                }
            });

            $('#btnDelete').on('click', function() {
                var check = confirm('資料經刪除後無法復原，確認刪除？');
                if (check){
                	var empno = $('#myModal .empno').html();
                    $.ajax({
                        url : '${ctxPath}/emp.do',
                        type : 'post',
                        data : {
                            empno: empno,
                            action: 'delete'
                        }
                    })
                    .done(function(msg) {
                    	var $td = $('<td>', { colspan: 7, class: 'delete' }).append($('<div>', {text: '資料已刪除' }));
                        $('#emps tr[data-empno="' + empno + '"]').html($td);
                        $('#myModal .close').click();
                    })
                    .fail(function() {
                        alert('刪除失敗!');
                    });
                }
            });
        });
    </script>

    <!-- Modal -->
    <div id="myModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <a class="close" data-dismiss="modal">&times;</a>
                    <h4 class="modal-title">EMP - <span class="ename"></span></h4>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered">
                        <tr>
                            <th>員工編號</th>
                            <td><span class="empno"></span></td>
                        </tr>
                        <tr>
                            <th>姓名</th>
                            <td><span class="ename"></span></td>
                        </tr>
                        <tr>
                            <th>職稱</th>
                            <td><span class="job"></span></td>
                        </tr>
                        <tr>
                            <th>雇用日期</th>
                            <td><span class="hiredate"></span></td>
                        </tr>
                        <tr>
                            <th>薪水</th>
                            <td><span class="sal"></span></td>
                        </tr>
                        <tr>
                            <th>加給</th>
                            <td><span class="comm"></span></td>
                        </tr>
                        <tr>
                            <th>部門編號</th>
                            <td><span class="deptno"></span></td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer" style="text-align: center;">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">確認</button>
                    <button id="btnDelete" type="button" class="btn btn-danger">刪除</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
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
        outline: 0 !important;
    }
    #empModal table tr th {
        width: 150px;
        text-align: right;
    }
    #empModal table tr:first-child th, #empModal table tr:first-child td {
        border-top: none;
    }
    tr.delete td {
        background-color: #D9534F;
        color: white;
    }
    tr.edited td {
        background-color: #EC971F;
        color: white;
    }
    table tr td input, table tr td select {
        border: 1px solid #ddd;
        padding: 2px 5px;
    }
    .error {
        color: red;
        margin-left: 1.5em;
    }
    .error:before {
        content: '*';
        padding-right: 0.5em;
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
                        <td>${deptMap[empVO.deptno]}</td>
                        <td><button type="button" class="btn btn-info btnShowEmp" data-emp='${empJSON}' data-toggle="modal" data-target="#empModal"><span style="padding-right: 5px;" class="glyphicon glyphicon-th-list"></span>檢視</button></td>
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
            var deptMap = JSON.parse('<%=new JSONObject((java.util.Map) application.getAttribute("deptMap"))%>');
            $("#empModal").attr({
                'data-keyboard': false
            }).on('show.bs.modal', function(event) {
                var btn = $(event.relatedTarget);      // Button that triggered the modal
                var emp = btn.data('emp');
                var $empTable = $('#empModal');
                for (var key in emp) {
                    switch (key) {
                        case 'deptno':
                            $empTable.find('select[name="' + key + '"]').val(emp[key]);
                            break;
                        case 'empno':
                        case 'ename':
                            $empTable.find('span.' + key).html(emp[key]);
                        default:
                            $empTable.find('input[name="' + key + '"]').val(emp[key]);
                            break;
                    }
                }
            }).on('hide.bs.modal', function(event) {
                $('span.error').empty().addClass('hide');
            });

            $('#btnUpdate').on('click', function() {
                var $form = $('#empModal form');
                var empno = $('#empModal .empno').html();
                $.ajax({
                    url: '${ctxPath}/emp.do',
                    method: 'post',
                    data: $form.serialize()
                })
                .done(function(msg) {
                    var result = JSON.parse(msg);
                    if (result.res == 0) {
                        alert('修改成功!');
                        $('.delete').removeClass('delete');
                        $('.edited').removeClass('edited');
                        var empTds = $('#emps tr[data-empno="' + empno + '"]').addClass('edited').find('td');
                        // for update View Button
                        var editedEmp = {};
                        var attrs = ['empno', 'ename', 'job', 'hiredate', 'deptno'];
                        for (var i = 0; i < attrs.length; i++) {
                            var value = $form.find('input,select').filter('[name="' + attrs[i] + '"]').val();
                            editedEmp[attrs[i]] = value;
                            if (attrs[i] == 'deptno') {
                                value = deptMap[value];
                            }
                            $(empTds[i]).html(value);
                        }
                        editedEmp['sal'] =  $form.find('input').filter('[name="sal"]').val();
                        editedEmp['comm'] =  $form.find('input').filter('[name="comm"]').val();
                        $(empTds[empTds.length - 1]).find('.btnShowEmp').data('emp', JSON.stringify(editedEmp));
                        $('#empModal .close').click();
                    } else {
                        $('span.error').empty().addClass('hide');
                        for (var key in result) {
                            $('input,select').filter('[name="' + key + '"]').next('span.error').html(result[key]).removeClass('hide');
                        }
                    }
                })
                .fail(function() {
                    alert('修改失敗！');
                });
            });

            $('#btnDelete').on('click', function() {
                var check = confirm('資料經刪除後無法復原，確認刪除？');
                if (check){
                    var empno = $('#empModal .empno').html();
                    $.ajax({
                        url : '${ctxPath}/emp.do',
                        type : 'post',
                        data : {
                            empno: empno,
                            action: 'delete'
                        }
                    })
                    .done(function(msg) {
                        $('.delete').removeClass('delete');
                        $('.edited').removeClass('edited');
                        var $td = $('<td>', { colspan: 7 }).append($('<div>', {text: '資料已刪除' }));
                        $('#emps tr[data-empno="' + empno + '"]').addClass('delete').html($td);
                        $('#empModal .close').click();
                    })
                    .fail(function() {
                        alert('刪除失敗!');
                    });
                }
            });
        });
    </script>

    <!-- Modal -->
    <div id="empModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <a class="close" data-dismiss="modal">&times;</a>
                    <h4 class="modal-title">EMP - <span class="ename"></span></h4>
                </div>
                <div class="modal-body">
                    <form action="${ctxPath}/emp.do" method="post" autocomplete="off">
                        <table class="table table-bordered">
                            <tr>
                                <th>員工編號</th>
                                <td><span class="empno"></span></td>
                            </tr>
                            <tr>
                                <th>姓名</th>
                                <td><input type="text" name="ename" value="${param.ename}" /><span class="error hide"></span></td>
                            </tr>
                            <tr>
                                <th>職稱</th>
                                <td><input type="text" name="job" value="${param.job}" /><span class="error hide"></span></td>
                            </tr>
                            <tr>
                                <th>雇用日期</th>
                                <td><input type="date" name="hiredate" value="${param.hiredate}" /><span class="error hide"></span></td>
                            </tr>
                            <tr>
                                <th>薪水</th>
                                <td><input type="number" name="sal" value="${param.sal}" /><span class="error hide"></span></td>
                            </tr>
                            <tr>
                                <th>加給</th>
                                <td><input type="number" name="comm" value="${param.comm}" /><span class="error hide"></span></td>
                            </tr>
                            <tr>
                                <th>部門編號</th>
                                <td><select name="deptno">
                                    <option value="">請選擇</option>
                                    <c:forEach var="deptVO" items="${depts}">
                                        <option value="${deptVO.deptno}"${param.deptno eq deptVO.deptno ? ' selected' : ''}>${deptVO.dname}</option>
                                    </c:forEach>
                                </select><span class="error hide"></span></td>
                            </tr>
                        </table>
                        <input type="hidden" name="action" value="update" />
                        <input type="hidden" name="empno" class="empno" />
                    </form>
                </div>
                <div class="modal-footer" style="text-align: center;">
                    <button type="button" class="btn btn-success" data-dismiss="modal"><span style="padding-right: 5px;" class="glyphicon glyphicon-ok"></span>確認</button>
                    <button id="btnUpdate" type="button" class="btn btn-primary"><span style="padding-right: 5px;" class="glyphicon glyphicon-pencil"></span>修改</button>
                    <button id="btnDelete" type="button" class="btn btn-danger"><span style="padding-right: 5px;" class="glyphicon glyphicon-remove"></span>刪除</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
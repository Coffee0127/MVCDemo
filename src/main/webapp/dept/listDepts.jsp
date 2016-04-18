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
    #deptModal table tr th {
        width: 150px;
        text-align: right;
    }
    #deptModal table tr:first-child th, #deptModal table tr:first-child td {
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
            <table id="depts" class="table table-hover" style="border-bottom: 2px solid #ddd">
                <thead class="thead-default">
                    <tr>
                        <th>部門編號</th>
                        <th>部門名稱</th>
                        <th>所在地</th>
                        <th><button type="button" class="btn btn-primary" onclick="location.href='${ctxPath}/dept/addDept.jsp';return false;"><span style="padding-right: 5px;" class="glyphicon glyphicon-plus"></span>新增</button></th>
                    </tr>
                </thead>
                <% java.util.List list = (java.util.List) request.getAttribute("depts"); %>
                <%@ include file="/WEB-INF/pages/page1.file" %>
                <c:forEach var="deptVO" items="${depts}" begin="<%=pageIndex%>" end="<%=pageIndex+pageSize-1%>">
                    <c:set var="deptVO" value="${deptVO}" scope="page" />
                    <% pageContext.setAttribute("deptJSON", new JSONObject(pageContext.getAttribute("deptVO"))); %>
                    <tr data-deptno="${deptVO.deptno}" align="center">
                        <td>${deptVO.deptno}</td>
                        <td>${deptVO.dname}</td>
                        <td>${deptVO.loc}</td>
                        <td><button type="button" class="btn btn-info btnShowDept" data-dept='${deptJSON}' data-toggle="modal" data-target="#deptModal"><span style="padding-right: 5px;" class="glyphicon glyphicon-th-list"></span>檢視</button></td>
                    </tr>
                </c:forEach>
            </table>
            <div style="padding: 0px 1.5em;">
                <% String action = "dept.do"; %>
                <%@ include file="/WEB-INF/pages/page2.file" %>
            </div>
        </div>
    </div>

    <script>
        $(function() {
            var deptMap = JSON.parse('<%=new JSONObject((java.util.Map) application.getAttribute("deptMap"))%>');
            $("#deptModal").attr({
                'data-keyboard': false
            }).on('show.bs.modal', function(event) {
                var btn = $(event.relatedTarget);      // Button that triggered the modal
                var dept = btn.data('dept');
                var $deptTable = $('#deptModal');
                for (var key in dept) {
                    switch (key) {
                        case 'deptno':
                        case 'dname':
                            $deptTable.find('span.' + key).html(dept[key]);
                        default:
                            $deptTable.find('input[name="' + key + '"]').val(dept[key]);
                            break;
                    }
                }
            }).on('hide.bs.modal', function(event) {
                $('span.error').empty().addClass('hide');
            });

            $('#btnUpdate').on('click', function() {
                var $form = $('#deptModal form');
                var deptno = $('#deptModal .deptno').html();
                $.ajax({
                    url: '${ctxPath}/dept.do',
                    method: 'post',
                    data: $form.serialize()
                })
                .done(function(msg) {
                    var result = JSON.parse(msg);
                    if (result.res == 0) {
                        alert('修改成功!');
                        $('.delete').removeClass('delete');
                        $('.edited').removeClass('edited');
                        var deptTds = $('#depts tr[data-deptno="' + deptno + '"]').addClass('edited').find('td');
                        // for update View Button
                        var editedDept = {};
                        var attrs = ['deptno', 'dname', 'loc'];
                        for (var i = 0; i < attrs.length; i++) {
                            var value = $form.find('input,select').filter('[name="' + attrs[i] + '"]').val();
                            editedDept[attrs[i]] = value;
                            $(deptTds[i]).html(value);
                        }
                        $(deptTds[deptTds.length - 1]).find('.btnShowDept').data('dept', JSON.stringify(editedDept));
                        $('#deptModal .close').click();
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
                    var deptno = $('#deptModal .deptno').html();
                    $.ajax({
                        url : '${ctxPath}/dept.do',
                        type : 'post',
                        data : {
                            deptno: deptno,
                            action: 'delete'
                        }
                    })
                    .done(function(msg) {
                        $('.delete').removeClass('delete');
                        $('.edited').removeClass('edited');
                        var $td = $('<td>', { colspan: 7 }).append($('<div>', {text: '資料已刪除' }));
                        $('#depts tr[data-deptno="' + deptno + '"]').addClass('delete').html($td);
                        $('#deptModal .close').click();
                    })
                    .fail(function() {
                        alert('刪除失敗!');
                    });
                }
            });
        });
    </script>

    <!-- Modal -->
    <div id="deptModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <a class="close" data-dismiss="modal">&times;</a>
                    <h4 class="modal-title">DEPT - <span class="dname"></span></h4>
                </div>
                <div class="modal-body">
                    <form action="${ctxPath}/dept.do" method="post" autocomplete="off">
                        <table class="table table-bordered">
                            <tr>
                                <th>部門編號</th>
                                <td><span class="deptno"></span></td>
                            </tr>
                            <tr>
                                <th>部門名稱</th>
                                <td><input type="text" name="dname" value="${param.dname}" /><span class="error hide"></span></td>
                            </tr>
                            <tr>
                                <th>部門所在地</th>
                                <td><input type="text" name="loc" value="${param.loc}" /><span class="error hide"></span></td>
                            </tr>
                        </table>
                        <input type="hidden" name="action" value="update" />
                        <input type="hidden" name="deptno" class="deptno" />
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
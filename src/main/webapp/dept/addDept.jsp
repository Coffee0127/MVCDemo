<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>MVC Sample</title>
    <jsp:include page="/WEB-INF/layout/meta.jsp" />
    <style>
    table tr th {
        width: 150px;
        text-align: right;
    }
    table tr td {
        text-align: left;
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
    <form action="${ctxPath}/dept.do" method="post" autocomplete="off">
        <div class="container">
            <div class="starter-template">
                <h1>MVC Demo</h1>
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">DEPT - 新增部門<span class="dname"></span></h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <tr>
                                    <th>部門名稱</th>
                                    <td><input type="text" name="dname" value="${param.dname}" /><span <c:if test="${not empty errorMsgs['dname']}">class="error"</c:if>>${errorMsgs['dname']}</span></td>
                                </tr>
                                <tr>
                                    <th>部門所在地</th>
                                    <td><input type="text" name="loc" value="${param.loc}" /><span <c:if test="${not empty errorMsgs['loc']}">class="error"</c:if>>${errorMsgs['loc']}</span></td>
                                </tr>
                            </table>
                        </div>
                        <div class="modal-footer" style="text-align: center;">
                            <button id="btnInsert" type="button" class="btn btn-primary" onclick="document.forms[0].submit();"><span style="padding-right: 5px;" class="glyphicon glyphicon-pencil"></span>新增</button>
                            <button id="btncancel" type="button" class="btn btn-danger" onclick="location.href='${ctxPath}/dept.do';"><span style="padding-right: 5px;" class="glyphicon glyphicon-remove"></span>取消</button>
                            <input name="action" type="hidden" value="add" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
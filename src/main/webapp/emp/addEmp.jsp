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
    <form action="${ctxPath}/emp.do" method="post".>
        <div class="container">
            <div class="starter-template">
                <h1>MVC Demo</h1>
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">EMP - 新增員工<span class="ename"></span></h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <tr>
                                    <th>姓名</th>
                                    
                                    <td><input type="text" name="ename" value="${param.ename}" /><span <c:if test="${not empty errorMsgs['ename']}">class="error"</c:if>>${errorMsgs['ename']}</span></td>
                                </tr>
                                <tr>
                                    <th>職稱</th>
                                    <td><input type="text" name="job" value="${param.job}" /><span <c:if test="${not empty errorMsgs['job']}">class="error"</c:if>>${errorMsgs['job']}</span></td>
                                </tr>
                                <tr>
                                    <th>雇用日期</th>
                                    <td><input type="date" name="hiredate" value="${param.hiredate}" /><span <c:if test="${not empty errorMsgs['hiredate']}">class="error"</c:if>>${errorMsgs['hiredate']}</span></td>
                                </tr>
                                <tr>
                                    <th>薪水</th>
                                    <td><input type="number" name="sal" value="${param.sal}" /><span <c:if test="${not empty errorMsgs['sal']}">class="error"</c:if>>${errorMsgs['sal']}</span></td>
                                </tr>
                                <tr>
                                    <th>加給</th>
                                    <td><input type="number" name="comm" value="${param.comm}" /><span <c:if test="${not empty errorMsgs['comm']}">class="error"</c:if>>${errorMsgs['comm']}</span></td>
                                </tr>
                                <tr>
                                    <th>部門編號</th>
                                    <td><select name="deptno">
                                        <option value="">請選擇</option>
                                    	<option value="10" ${param.deptno == 10 ? 'selected' : ''}>財務部</option>
                                    	<option value="20" ${param.deptno == 20 ? 'selected' : ''}>研發部</option>
                                    	<option value="30" ${param.deptno == 30 ? 'selected' : ''}>業務部</option>
                                    	<option value="40" ${param.deptno == 40 ? 'selected' : ''}>生管部</option>
                                    </select><span <c:if test="${not empty errorMsgs['deptno']}">class="error"</c:if>>${errorMsgs['deptno']}</span></td>
                                </tr>
                            </table>
                        </div>
                        <div class="modal-footer" style="text-align: center;">
                            <button id="btnInsert" type="button" class="btn btn-primary" onclick="document.forms[0].submit();">新增</button>
                            <button id="btncancel" type="button" class="btn btn-danger" onclick="location.href='${ctxPath}/emp.do';">取消</button>
                            <input name="action" type="hidden" value="add" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
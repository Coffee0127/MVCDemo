<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>員工 - MVC</title>
    <link rel="shortcut icon" type="image/x-icon" href="${ctxPath}/assets/images/favicon.ico">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!--[if lt IE 9]>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${ctxPath}/assets/css/main.css">
  </head>
  <body>
    <%@ include file="/WEB-INF/views/template/navbar.file" %>

    <div class="container">
      <div class="btn-group" style="padding: 15px;">
        <a href="${ctxPath}/emp.do?action=preAdd" class="btn btn-success" role="button">新增</a>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">查詢條件</h3>
        </div>
        <div class="panel-body">
          <form class="form-horizontal" action="${ctxPath}/emp.do" method="post" novalidate>
            <div class="form-group">
              <label for="empno" class="col-xs-12 col-sm-3 control-label">員工編號</label>
              <div class="col-xs-12 col-sm-4">
                <input type="text" name="empno" id="empno" placeholder="員工編號" class="form-control" />
              </div>
            </div>
            <div class="form-group">
              <label for="ename" class="col-xs-12 col-sm-3 control-label">員工姓名</label>
              <div class="col-xs-12 col-sm-4">
                <input type="text" name="ename" id="ename" placeholder="員工姓名" class="form-control">
              </div>
            </div>
            <div class="form-group">
              <label for="deptno" class="col-xs-12 col-sm-3 control-label">部門編號</label>
              <div class="col-xs-12 col-sm-4">
                <select name="deptno" class="form-control">
                  <option>請選擇</option>
                  <option value="10">財務部</option>
                  <option value="20">研發部</option>
                  <option value="30">業務部</option>
                  <option value="40">生管部</option>
                </select>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">查詢結果</h3>
        </div>
        <div class="panel-body">
          <table class="fju-list-table table table-hover">
            <thead>
              <tr>
                <th>員工編號</th>
                <th>員工姓名</th>
                <th>職位</th>
                <th>雇用日期</th>
                <th>薪水</th>
                <th>獎金</th>
                <th>部門</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <% java.util.List list = (java.util.List) request.getAttribute("empList"); %>
              <%@ include file="/WEB-INF/views/template/pagination/page1.file" %>
              <c:forEach var="empVO" items="${empList}" begin="<%=pageIndex%>" end="<%=pageIndex+pageSize-1%>">
                <tr class="${param.empno == empVO.empno ? 'fju-edited' : ''}">
                  <td>${empVO.empno}</td>
                  <td>${empVO.ename}</td>
                  <td>${empVO.job}</td>
                  <td>${empVO.hiredate}</td>
                  <td>${empVO.sal}</td>
                  <td>${empVO.comm}</td>
                  <td>${empVO.deptno}</td>
                  <td>
                    <form action="${ctxPath}/emp.do" method="post">
                      <button class="btn btn-info"><span style="padding-right: 5px;" class="glyphicon glyphicon-edit"></span>更新</button>
                      <input type="hidden" name="action" value="preUpdate" />
                      <input type="hidden" name="empno" value="${empVO.empno}" />
                      <input type="hidden" name="whichPage" value="<%=whichPage%>" />
                    </form>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <% String action = "emp.do"; %>
          <%@ include file="/WEB-INF/views/template/pagination/page2.file" %>
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${ctxPath}/assets/js/main.js"></script>
    <script>
    $(function() {
      $('#whichPage').on('change', function() {
        location.href = '${ctxPath}/<%=action%>?whichPage=' + $(this).val();
      });
    });
    </script>
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>部門 - MVC</title>
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
        <a href="${ctxPath}/dept.do?action=preAdd" class="btn btn-success" role="button"><span class="fju-btn-icon glyphicon glyphicon-plus"></span>新增</a>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">查詢結果</h3>
        </div>
        <div class="panel-body">
          <table class="fju-list-table table table-hover">
            <thead>
              <tr>
                <th>部門編號</th>
                <th>部門名稱</th>
                <th>部門基地</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <% java.util.List list = (java.util.List) request.getAttribute("deptList"); %>
              <%@ include file="/WEB-INF/views/template/pagination/page1.file" %>
              <c:forEach var="deptVO" items="${deptList}" begin="<%=pageIndex%>" end="<%=pageIndex+pageSize-1%>">
                <tr class="${param.deptno == deptVO.deptno ? 'fju-edited' : ''}">
                  <td>${deptVO.deptno}</td>
                  <td>${deptVO.dname}</td>
                  <td>${deptVO.loc}</td>
                  <td>
                    <form action="${ctxPath}/dept.do" method="post">
                      <button class="btn btn-info"><span style="padding-right: 5px;" class="glyphicon glyphicon-edit"></span>更新</button>
                      <input type="hidden" name="action" value="preUpdate" />
                      <input type="hidden" name="deptno" value="${deptVO.deptno}" />
                      <input type="hidden" name="whichPage" value="<%=whichPage%>" />
                    </form>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <% String action = "dept.do"; %>
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

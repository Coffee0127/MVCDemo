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
        <a id="btnBack" class="btn btn-default" role="button"><span class="fju-btn-icon glyphicon glyphicon-arrow-left"></span>返回</a>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">顯示部門 - ${deptVO.deptno}</h3>
        </div>
        <div class="panel-body">
          <form id="form" class="form-horizontal" action="${ctxPath}/dept.do" method="post" novalidate>
            <div class="form-group">
              <label for="deptno" class="col-xs-12 col-sm-3 control-label">部門編號</label>
              <div class="col-xs-12 col-sm-4 form-control-static">
                <span>${deptVO.deptno}</span>
              </div>
            </div>
            <div class="form-group">
              <label for="dname" class="col-xs-12 col-sm-3 control-label">部門名稱</label>
              <div class="col-xs-12 col-sm-4">
                <input type="text" name="dname" id="dname" placeholder="部門名稱" class="form-control" value="${deptVO.dname}" />
              </div>
              <c:if test="${not empty errorMsgs['dname']}">
                <div class="col-xs-12 col-sm-4 form-control-static">
                  <div class="fju-input-error">${errorMsgs['dname']}</div>
                </div>
              </c:if>
            </div>
            <div class="form-group">
              <label for="loc" class="col-xs-12 col-sm-3 control-label">部門基地</label>
              <div class="col-xs-12 col-sm-4">
                <input type="text" name="loc" id="loc" placeholder="部門基地" class="form-control" value="${deptVO.loc}" />
              </div>
              <c:if test="${not empty errorMsgs['loc']}">
                <div class="col-xs-12 col-sm-4 form-control-static">
                  <div class="fju-input-error">${errorMsgs['loc']}</div>
                </div>
              </c:if>
            </div>
            <div class="form-group">
              <div class="col-xs-12 col-sm-3 control-label">
                <button id="btnUpdate" type="button" class="btn btn-primary"><span class="fju-btn-icon glyphicon glyphicon-pencil"></span>儲存</button>
                <button id="btnDelete" type="button" class="btn btn-danger"><span class="fju-btn-icon glyphicon glyphicon-trash"></span>刪除</button>
              </div>
            </div>
            <input type="hidden" name="action" value="" />
            <input type="hidden" name="whichPage" value="${param.whichPage}" />
            <input type="hidden" name="deptno" value="${deptVO.deptno}" />
          </form>
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${ctxPath}/assets/js/main.js"></script>
    <script>
      $(function() {
        $('#btnBack').on('click', function() {
          var $form = $('#form');
          $form.find('input[name="action"]').val('query');
          $form.find('input[name="deptno"]').val('');
          $form.submit();
        });

        $('#btnUpdate').on('click', function() {
          var $form = $('#form');
          $form.find('input[name="action"]').val('update');
          $form.submit();
        });

        $('#btnDelete').on('click', function() {
          if (confirm('確定刪除部門 - ${deptVO.deptno} ? (關聯測試與交易-小心)')) {
            var $form = $('#form');
            $form.find('input[name="action"]').val('delete');
            $form.submit();
          }
        });
      });
    </script>
  </body>
</html>

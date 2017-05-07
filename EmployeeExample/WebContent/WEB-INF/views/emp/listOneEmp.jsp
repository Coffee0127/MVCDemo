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
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">顯示員工 - ${empVO.empno}</h3>
        </div>
        <div class="panel-body">
          <form class="form-horizontal" action="${ctxPath}/emp.do" method="post" novalidate>
            <div class="form-group">
              <label for="empno" class="col-xs-12 col-sm-3 control-label">員工編號</label>
              <div class="col-xs-12 col-sm-4 form-control-static">
                <span>${empVO.empno}</span>
              </div>
            </div>
            <div class="form-group">
              <label for="ename" class="col-xs-12 col-sm-3 control-label">員工姓名</label>
              <div class="col-xs-12 col-sm-4">
                <input type="text" name="ename" id="ename" placeholder="員工姓名" class="form-control" value="${empVO.ename}" />
              </div>
              <c:if test="${not empty errorMsgs['ename']}">
                <div class="col-xs-12 col-sm-4 form-control-static">
                  <div class="fju-input-error">${errorMsgs['ename']}</div>
                </div>
              </c:if>
            </div>
            <div class="form-group">
              <label for="job" class="col-xs-12 col-sm-3 control-label">職位</label>
              <div class="col-xs-12 col-sm-4">
                <input type="text" name="job" id="job" placeholder="職位" class="form-control" value="${empVO.job}">
              </div>
              <c:if test="${not empty errorMsgs['job']}">
                <div class="col-xs-12 col-sm-4 form-control-static">
                  <div class="fju-input-error">${errorMsgs['job']}</div>
                </div>
              </c:if>
            </div>
            <div class="form-group">
              <label for="hiredate" class="col-xs-12 col-sm-3 control-label">雇用日期</label>
              <div class="col-xs-12 col-sm-4">
                <input type="date" name="hiredate" id="hiredate" placeholder="雇用日期" class="form-control" value="${empVO.hiredate}">
              </div>
              <c:if test="${not empty errorMsgs['hiredate']}">
                <div class="col-xs-12 col-sm-4 form-control-static">
                  <div class="fju-input-error">${errorMsgs['hiredate']}</div>
                </div>
              </c:if>
            </div>
            <div class="form-group">
              <label for="sal" class="col-xs-12 col-sm-3 control-label">薪水</label>
              <div class="col-xs-12 col-sm-4">
                <input type="text" name="sal" id="sal" placeholder="薪水" class="form-control" value="${empVO.sal}">
              </div>
              <c:if test="${not empty errorMsgs['sal']}">
                <div class="col-xs-12 col-sm-4 form-control-static">
                  <div class="fju-input-error">${errorMsgs['sal']}</div>
                </div>
              </c:if>
            </div>
            <div class="form-group">
              <label for="comm" class="col-xs-12 col-sm-3 control-label">獎金</label>
              <div class="col-xs-12 col-sm-4">
                <input type="text" name="comm" id="comm" placeholder="獎金" class="form-control" value="${empVO.comm}">
              </div>
              <c:if test="${not empty errorMsgs['comm']}">
                <div class="col-xs-12 col-sm-4 form-control-static">
                  <div class="fju-input-error">${errorMsgs['comm']}</div>
                </div>
              </c:if>
            </div>
            <div class="form-group">
              <label for="deptno" class="col-xs-12 col-sm-3 control-label">部門編號</label>
              <div class="col-xs-12 col-sm-4">
                <select name="deptno" class="form-control">
                  <option>請選擇</option>
                  <option value="10" ${10 == empVO.deptno ? 'selected' : ''}>財務部</option>
                  <option value="20" ${20 == empVO.deptno ? 'selected' : ''}>研發部</option>
                  <option value="30" ${30 == empVO.deptno ? 'selected' : ''}>業務部</option>
                  <option value="40" ${40 == empVO.deptno ? 'selected' : ''}>生管部</option>
                </select>
              </div>
              <c:if test="${not empty errorMsgs['deptno']}">
                <div class="col-xs-12 col-sm-4 form-control-static">
                  <div class="fju-input-error">${errorMsgs['deptno']}</div>
                </div>
              </c:if>
            </div>
            <div class="form-group">
              <div class="col-xs-12 col-sm-8 col-sm-offset-2">
                <button id="btnBack" type="button" class="btn btn-default"><span style="padding-right: 5px;" class="glyphicon glyphicon-arrow-left"></span>返回</button>
                <button id="btnUpdate" type="button" class="btn btn-primary"><span style="padding-right: 5px;" class="glyphicon glyphicon-pencil"></span>儲存</button>
                <button id="btnDelete" type="button" class="btn btn-danger"><span style="padding-right: 5px;" class="glyphicon glyphicon-trash"></span>刪除</button>
              </div>
            </div>
            <input type="hidden" name="action" value="" />
            <input type="hidden" name="whichPage" value="${param.whichPage}" />
            <input type="hidden" name="empno" value="${empVO.empno}" />
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
        var $form = $(this).parents('form');
        $form.find('input[name="action"]').val('query');
        $form.submit();
      });

      $('#btnUpdate').on('click', function() {
        var $form = $(this).parents('form');
        $form.find('input[name="action"]').val('update');
        $form.submit();
      });

      $('#btnDelete').on('click', function() {
        if (confirm('確定刪除員工 - ${empVO.empno} ?')) {
          var $form = $(this).parents('form');
          $form.find('input[name="action"]').val('delete');
          $form.submit();
        }
      });
    });
    </script>
  </body>
</html>

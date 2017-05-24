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
        <a id="btnBack" class="btn btn-default" role="button"><span class="fju-btn-icon glyphicon glyphicon-arrow-left"></span>返回</a>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">新增員工(多筆新增)</h3>
        </div>
        <div id="formBody" class="panel-body">
          <c:forEach var="empVO" items="${empVOs}" varStatus="status">
            <form class="tmpForm form-horizontal" novalidate>
              <div class="form-group">
                <label class="col-xs-12 col-sm-3 control-label">
                  <button type="button" class="btnRemoveRow btn btn-danger"><span class="fju-btn-icon glyphicon glyphicon-minus"></span>刪除此項</button>
                </label>
              </div>
              <div class="form-group">
                <label for="ename" class="col-xs-12 col-sm-3 control-label">員工姓名</label>
                <div class="col-xs-12 col-sm-4">
                  <input type="text" name="ename" placeholder="員工姓名" class="form-control" value="${empVO.ename}" />
                </div>
                <c:if test="${not empty errorMsgsList[status.index]['ename']}">
                  <div class="col-xs-12 col-sm-4 form-control-static">
                    <div class="fju-input-error">${errorMsgsList[status.index]['ename']}</div>
                  </div>
                </c:if>
              </div>
              <div class="form-group">
                <label for="job" class="col-xs-12 col-sm-3 control-label">職位</label>
                <div class="col-xs-12 col-sm-4">
                  <input type="text" name="job" placeholder="職位" class="form-control" value="${empVO.job}">
                </div>
                <c:if test="${not empty errorMsgsList[status.index]['job']}">
                  <div class="col-xs-12 col-sm-4 form-control-static">
                    <div class="fju-input-error">${errorMsgsList[status.index]['job']}</div>
                  </div>
                </c:if>
              </div>
              <div class="form-group">
                <label for="hiredate" class="col-xs-12 col-sm-3 control-label">雇用日期</label>
                <div class="col-xs-12 col-sm-4">
                  <input type="date" name="hiredate" placeholder="雇用日期" class="form-control" value="${empVO.hiredate}">
                </div>
                <c:if test="${not empty errorMsgsList[status.index]['hiredate']}">
                  <div class="col-xs-12 col-sm-4 form-control-static">
                    <div class="fju-input-error">${errorMsgsList[status.index]['hiredate']}</div>
                  </div>
                </c:if>
              </div>
              <div class="form-group">
                <label for="sal" class="col-xs-12 col-sm-3 control-label">薪水</label>
                <div class="col-xs-12 col-sm-4">
                  <input type="text" name="sal" placeholder="薪水" class="form-control" value="${empVO.sal}">
                </div>
                <c:if test="${not empty errorMsgsList[status.index]['sal']}">
                  <div class="col-xs-12 col-sm-4 form-control-static">
                    <div class="fju-input-error">${errorMsgsList[status.index]['sal']}</div>
                  </div>
                </c:if>
              </div>
              <div class="form-group">
                <label for="comm" class="col-xs-12 col-sm-3 control-label">獎金</label>
                <div class="col-xs-12 col-sm-4">
                  <input type="text" name="comm" placeholder="獎金" class="form-control" value="${empVO.comm}">
                </div>
                <c:if test="${not empty errorMsgsList[status.index]['comm']}">
                  <div class="col-xs-12 col-sm-4 form-control-static">
                    <div class="fju-input-error">${errorMsgsList[status.index]['comm']}</div>
                  </div>
                </c:if>
              </div>
              <div class="form-group">
                <label for="deptno" class="col-xs-12 col-sm-3 control-label">部門編號</label>
                <div class="col-xs-12 col-sm-4">
                  <select name="deptno" class="form-control">
                    <option value="">請選擇</option>
                    <option value="10" ${10 == empVO.deptno ? 'selected' : ''}>財務部</option>
                    <option value="20" ${20 == empVO.deptno ? 'selected' : ''}>研發部</option>
                    <option value="30" ${30 == empVO.deptno ? 'selected' : ''}>業務部</option>
                    <option value="40" ${40 == empVO.deptno ? 'selected' : ''}>生管部</option>
                  </select>
                </div>
                <c:if test="${not empty errorMsgsList[status.index]['deptno']}">
                  <div class="col-xs-12 col-sm-4 form-control-static">
                    <div class="fju-input-error">${errorMsgsList[status.index]['deptno']}</div>
                  </div>
                </c:if>
              </div>
              <hr />
            </form>
          </c:forEach>
          <form id="form" class="form-horizontal" action="${ctxPath}/emp.do" method="post" novalidate>
            <div class="form-group">
              <div class="col-xs-12 col-sm-8 col-sm-offset-1">
                <button id="btnAddRow" type="button" class="btn btn-success"><span class="fju-btn-icon glyphicon glyphicon-plus"></span>新增一列</button>
                <button id="btnAdd" type="button" class="btn btn-primary"><span class="fju-btn-icon glyphicon glyphicon-pencil"></span>儲存</button>
              </div>
            </div>
            <input type="hidden" name="emps" value="" />
            <input type="hidden" name="action" value="" />
          </form>
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${ctxPath}/assets/js/main.js"></script>
    <script>
      $(function() {
        $('#btnAddRow').on('click', function() {
          var $tmpForm = $('#formBody .tmpForm:first').clone();
          // set form input
          $tmpForm[0].reset();
          // remove error messages
          $tmpForm.find('.fju-input-error').remove();
          $tmpForm.insertAfter($('#formBody .tmpForm:last'));
        });

        $('body').on('click', '.btnRemoveRow', function() {
          if ($('#formBody .tmpForm').size() > 1) {
            $(this).parents('.tmpForm').remove();
          } else {
            alert('已經是最後一項了！');
          }
        });

        $('#btnBack').on('click', function() {
          var $form = $('#form');
          $form.find('input[name="action"]').val('query');
          $form.find('input[name="empno"]').val('');
          $form.submit();
        });

        $('#btnAdd').on('click', function() {
          var $form = $(this).parents('form');
          $form.find('input[name="action"]').val('addEmps');
          var emps = [];
          $('#formBody .tmpForm').each(function() {
            emps.push($(this).serializeObject());
          });
          $form.find('input[name="emps"]').val(JSON.stringify(emps));
          $form.submit();
        });
      });
    </script>
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="java.sql.*, java.util.*, tw.edu.fju.sample.model.*" %>
<%
List<EmpVO> list = new ArrayList<>();

Connection con = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SCOTT", "TIGER");
    pstmt = con.prepareStatement("SELECT * FROM emp2 order by empno");
    rs = pstmt.executeQuery();

    while (rs.next()) {
        EmpVO empVO = new EmpVO();
        empVO.setEmpno(rs.getInt("empno"));
        empVO.setEname(rs.getString("ename"));
        empVO.setJob(rs.getString("job"));
        empVO.setHiredate(rs.getDate("hiredate"));
        empVO.setSal(rs.getInt("sal"));
        empVO.setComm(rs.getDouble("comm"));
        empVO.setDeptno(rs.getInt("deptno"));
        list.add(empVO); // Store the row in the list
    }

    // 小心！看得到吃不到！
    pageContext.setAttribute("list", list);
} catch (Exception e) {
    throw new RuntimeException("A error occured. " + e.getMessage());
} finally {
    if (rs != null) {
        try {
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace(System.err);
        }
    }
    if (pstmt != null) {
        try {
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace(System.err);
        }
    }
    if (con != null) {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
%>
<!DOCTYPE html>
<html lang="zh-Hant">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>員工 - No MVC</title>
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
      <div class="row">
        <div class="col-xs-12">
          <table class="table table-hover">
            <caption>員工清單</caption>
            <thead>
              <tr>
                <th>員工編號</th>
                <th>員工姓名</th>
                <th>職位</th>
                <th>雇用日期</th>
                <th>薪水</th>
                <th>獎金</th>
                <th>部門</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="empVO" items="${list}">
                <tr>
                  <td>${empVO.empno}</td>
                  <td>${empVO.ename}</td>
                  <td>${empVO.job}</td>
                  <td>${empVO.hiredate}</td>
                  <td>${empVO.sal}</td>
                  <td>${empVO.comm}</td>
                  <td>${empVO.deptno}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${ctxPath}/assets/js/main.js"></script>
  </body>
</html>

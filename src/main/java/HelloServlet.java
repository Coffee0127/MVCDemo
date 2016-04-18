import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iisigroup.sample.common.DBInfo;

@WebServlet("/hello.do")
public class HelloServlet extends HttpServlet {

    /** serialVersionUID */
    private static final long serialVersionUID = -6617893791621340358L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Class.forName(DBInfo.DRIVER);
            Connection con = DriverManager.getConnection(DBInfo.URL, DBInfo.USERID, DBInfo.PASSWD);
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM emp2 order by empno");
            ResultSet rs = pstmt.executeQuery();

            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8' />");
            out.println("    <title>MVC Sample</title>");
            out.println("    <link href='resources/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("    <link href='resources/css/starter-template.css' rel='stylesheet'>");
            out.println("    <style>");
            out.println("    .thead-default th {");
            out.println("        color: #55595c;");
            out.println("        background-color: #eceeef;");
            out.println("        text-align: center;");
            out.println("    }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <nav class='navbar navbar-inverse navbar-fixed-top'>");
            out.println("        <div class='container'>");
            out.println("            <div class='navbar-header'>");
            out.println("                <button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navbar' aria-expanded='false' aria-controls='navbar'>");
            out.println("                    <span class='sr-only'>Toggle navigation</span>");
            out.println("                    <span class='icon-bar'></span>");
            out.println("                    <span class='icon-bar'></span>");
            out.println("                    <span class='icon-bar'></span>");
            out.println("                </button>");
            out.println("                <a class='navbar-brand' href='" + request.getContextPath() + "'>MVC Demo</a>");
            out.println("            </div>");
            out.println("            <div id='navbar' class='collapse navbar-collapse'>");
            out.println("                <ul class='nav navbar-nav'>");
            out.println("                    <li><a href='" + request.getContextPath() + "'>Home</a></li>");
            out.println("                    <li class='active'><a href='" + request.getContextPath() + "/hello.do'>Without MVC</a></li>");
            out.println("                    <li><a href='" + request.getContextPath() + "/emp.do'>With MVC(員工)</a></li>");
            out.println("                    <li><a href='" + request.getContextPath() + "/dept.do'>With MVC(部門)</a></li>");
            out.println("                </ul>");
            out.println("            </div>");
            out.println("            <!--/.nav-collapse -->");
            out.println("        </div>");
            out.println("    </nav>");

            out.println("    <div class='container'>");
            out.println("        <div class='starter-template'>");
            out.println("            <h1>Withou MVC Demo</h1>");
            out.println("            <table class='table table-hover'>");
            out.println("                <thead class='thead-default'>");
            out.println("                    <tr>");
            out.println("                        <th>員工編號</th>");
            out.println("                        <th>姓名</th>");
            out.println("                        <th>職稱</th>");
            out.println("                        <th>雇用日期</th>");
            out.println("                        <th>薪水</th>");
            out.println("                        <th>加給</th>");
            out.println("                        <th>部門編號</th>");
            out.println("                    </tr>");
            out.println("                </thead>");

            while (rs.next()) {
                // empVO 也稱為 Domain objects
                StringBuilder emp = new StringBuilder("<tr align='center'>");
                emp.append("<td>").append(rs.getInt("empno")).append("</td>");
                emp.append("<td>").append(rs.getString("ename")).append("</td>");
                emp.append("<td>").append(rs.getString("job")).append("</td>");
                emp.append("<td>").append(rs.getDate("hiredate")).append("</td>");
                emp.append("<td>").append(rs.getInt("sal")).append("</td>");
                emp.append("<td>").append(rs.getDouble("comm")).append("</td>");
                emp.append("<td>").append(rs.getInt("deptno")).append("</td>");
                emp.append("</tr>");
                out.println(emp.toString());
            }
            out.println("            </table>");
            out.println("        </div>");
            out.println("    </div>");

            out.println("    <script src='resources/js/jquery.min.js'></script>");
            out.println("    <script src='resources/js/bootstrap.min.js'></script>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

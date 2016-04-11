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
        out.println("<HTML>");
        out.println("<BODY>");
        out.println("<h1>Hello Emp List</h1>");

        try {
            Class.forName(DBInfo.DRIVER);
            Connection con = DriverManager.getConnection(DBInfo.URL, DBInfo.USERID, DBInfo.PASSWD);
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM emp2 order by empno");
            ResultSet rs = pstmt.executeQuery();
            out.println("<table border='1'><tr><th>員工編號</th><th>姓名</th><th>職稱</th><th>雇用日期</th><th>薪水</th><th>加給</th><th>部門編號</th></tr>");
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
            out.print("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</BODY></HTML>");
    }
}

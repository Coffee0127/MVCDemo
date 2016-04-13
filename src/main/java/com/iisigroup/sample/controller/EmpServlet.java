package com.iisigroup.sample.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iisigroup.sample.model.EmpDAO;
import com.iisigroup.sample.model.EmpJDBCDAO;
import com.iisigroup.sample.model.EmpVO;

@WebServlet("/emp.do")
public class EmpServlet extends HttpServlet {

    /** serialVersionUID */
    private static final long serialVersionUID = -842663933045186021L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        EmpDAO dao = new EmpJDBCDAO();
        String path = null;

        String action = request.getParameter("action");
        switch (action == null ? "query" : action) {
            case "delete":
                dao.delete(Integer.parseInt(request.getParameter("empno")));
                break;
            case "query":
            default:
                List<EmpVO> emps = dao.getAll();
                request.setAttribute("emps", emps);
                path = "/emp/listEmps.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(path);
                dispatcher.forward(request, response);
                break;
        }
    }
}

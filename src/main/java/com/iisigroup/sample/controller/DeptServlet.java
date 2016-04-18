package com.iisigroup.sample.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.iisigroup.sample.model.DeptDAO;
import com.iisigroup.sample.model.DeptJDBCDAO;
import com.iisigroup.sample.model.DeptVO;

@WebServlet("/dept.do")
public class DeptServlet extends HttpServlet {

    /** serialVersionUID */
    private static final long serialVersionUID = 8129655548622438676L;

    private DeptDAO deptDAO = new DeptJDBCDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String path = null;
        String action = request.getParameter("action");
        switch (action == null ? "query" : action) {
            case "add":
                path = doAddAction(request);
                break;
            case "update":
                doUpdateAction(request, response);
                return;
            case "delete":
                doDeleteAction(request);
                return;
            case "query":
            default:
                path = doQueryAction(request);
                break;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private String doAddAction(HttpServletRequest request) {
        Map<String, String> errorMsgs = validateRequest(request);
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            return "/dept/addDept.jsp";
        }

        DeptVO DeptVO = retrieveDeptVO(request);
        deptDAO.insert(DeptVO);

        updateContext();
        return doQueryAction(request);
    }

    private void doUpdateAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();

        Map<String, String> errorMsgs = validateRequest(request);
        String paramDeptno = request.getParameter("deptno");
        if (isBlank(paramDeptno) || !isNaturalNumbers(paramDeptno)) {
            errorMsgs.put("deptno", "請填寫部門編號");
        }
        if (!errorMsgs.isEmpty()) {
            pw.write(new JSONObject(errorMsgs).toString());
            pw.flush();
            return;
        }

        DeptVO deptVO = retrieveDeptVO(request);
        deptVO.setDeptno(Integer.parseInt(request.getParameter("deptno")));
        deptDAO.update(deptVO);
        updateContext();
        pw.println(new JSONObject("{res:0}"));
        pw.flush();
    }

    private void doDeleteAction(HttpServletRequest request) {
        deptDAO.delete(Integer.parseInt(request.getParameter("deptno")));
        updateContext();
    }

    private String doQueryAction(HttpServletRequest request) {
        List<DeptVO> depts = deptDAO.getAll();
        request.setAttribute("depts", depts);
        return "/dept/listDepts.jsp";
    }

    private DeptVO retrieveDeptVO(HttpServletRequest request) {
        DeptVO deptVO = new DeptVO();
        deptVO.setDname(request.getParameter("dname"));
        deptVO.setLoc(request.getParameter("loc"));
        return deptVO;
    }

    private Map<String, String> validateRequest(HttpServletRequest request) {
        Map<String, String> errorMsgs = new HashMap<String, String>();
        if (isBlank(request.getParameter("dname"))) {
            errorMsgs.put("dname", "請填寫部門名稱");
        }
        if (isBlank(request.getParameter("loc"))) {
            errorMsgs.put("loc", "請填寫部門所在地");
        }
        return errorMsgs;
    }

    private void updateContext() {
        List<DeptVO> depts = deptDAO.getAll();
        Map<Integer, String> deptMap = new HashMap<Integer, String>();
        for (DeptVO dept : depts) {
            deptMap.put(dept.getDeptno(), dept.getDname());
        }
        getServletContext().setAttribute("depts", depts);
        getServletContext().setAttribute("deptMap", deptMap);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    private boolean isNaturalNumbers(String number) {
        return number.matches("\\d*") && Integer.parseInt(number) > 0;
    }
}

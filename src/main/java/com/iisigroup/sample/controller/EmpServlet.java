package com.iisigroup.sample.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private EmpDAO empDAO = new EmpJDBCDAO();

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
            case "delete":
                empDAO.delete(Integer.parseInt(request.getParameter("empno")));
                return;
            case "query":
            default:
                path = doQueryAction(request);
                break;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    /**
     * @param request
     * @return
     */
    private String doQueryAction(HttpServletRequest request) {
        List<EmpVO> emps = empDAO.getAll();
        request.setAttribute("emps", emps);
        return "/emp/listEmps.jsp";
    }

    /**
     * @param request
     * @param path
     * @return
     */
    private String doAddAction(HttpServletRequest request) {
        Map<String, String> errorMsgs = new HashMap<String, String>();
        String ename = request.getParameter("ename");
        if (isBlank(ename)) {
            errorMsgs.put("ename", "請填寫員工姓名");
        }
        String job = request.getParameter("job");
        if (isBlank(job)) {
            errorMsgs.put("job", "請填寫職稱");
        }
        String paramHiredate = request.getParameter("hiredate");
        Date hiredate = null;
        if (isBlank(paramHiredate)) {
            errorMsgs.put("hiredate", "請填寫雇用日期");
        } else {
            try {
                hiredate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(paramHiredate).getTime());
            } catch (ParseException e) {
                errorMsgs.put("hiredate", "請填寫正確日期格式");
            }
        }
        String paramSal = request.getParameter("sal");
        Integer sal = null;
        if (isBlank(paramSal) || !isNaturalNumbers(paramSal)) {
            errorMsgs.put("sal", "請填寫正確薪水");
        } else {
            sal = Integer.parseInt(paramSal);
        }
        String paramComm = request.getParameter("comm");
        Double comm = null;
        if (isBlank(paramComm) || !isPositiveDouble(paramComm)) {
            errorMsgs.put("comm", "請填寫正確加給");
        } else {
            comm = Double.parseDouble(paramComm);
        }
        String paramDeptno = request.getParameter("deptno");
        Integer deptno = null;
        if (isBlank(paramDeptno) || !isNaturalNumbers(paramDeptno)) {
            errorMsgs.put("deptno", "請選擇部門");
        } else {
            deptno = Integer.parseInt(paramDeptno);
        }
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            return "/emp/addEmp.jsp";
        }

        EmpVO empVO = new EmpVO();
        empVO.setEname(ename);
        empVO.setJob(job);
        empVO.setHiredate(hiredate);
        empVO.setSal(sal);
        empVO.setComm(comm);
        empVO.setDeptno(deptno);
        empDAO.insert(empVO);

        return doQueryAction(request);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    private boolean isNaturalNumbers(String number) {
        return number.matches("\\d*") && Integer.parseInt(number) > 0;
    }

    private boolean isPositiveDouble(String number) {
        return number.matches("\\d*|\\d*\\.\\d*") && Double.parseDouble(number) > 0;
    }
}

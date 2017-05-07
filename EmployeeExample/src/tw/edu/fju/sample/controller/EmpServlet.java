package tw.edu.fju.sample.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.edu.fju.sample.model.EmpVO;
import tw.edu.fju.sample.service.EmpService;

public class EmpServlet extends HttpServlet {

    private static final String PAGE_ADD_EMP = "/WEB-INF/views/emp/addEmp.jsp";
    private static final String PAGE_UPDATE_EMP = "";
    private static final String PAGE_ONE_EMP = "";
    private static final String PAGE_LIST_EMP = "/WEB-INF/views/emp/listAllEmp.jsp";

    private EmpService empService = new EmpService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = null;
        String action = request.getParameter("action");
        switch (action == null ? "query" : action) {
            case "preAdd":
                path = PAGE_ADD_EMP;
                break;
            case "add":
                path = doAddAction(request);
                break;
            case "update":
                path = doUpdateAction(request, response);
                break;
            case "delete":
                path = doDeleteAction(request);
                break;
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
            return "/emp/addEmp.jsp";
        }

        EmpVO empVO = retrieveEmpVO(request);
        empService.insert(empVO);

        return doQueryAction(request);
    }

    private String doUpdateAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return doQueryAction(request);
    }

    private String doDeleteAction(HttpServletRequest request) {
        empService.delete(Integer.parseInt(request.getParameter("empno")));
        return doQueryAction(request);
    }

    private String doQueryAction(HttpServletRequest request) {
        List<EmpVO> emps = empService.findAll();
        request.setAttribute("empList", emps);
        return PAGE_LIST_EMP;
    }

    private EmpVO retrieveEmpVO(HttpServletRequest request) {
        EmpVO empVO = new EmpVO();
        empVO.setEname(request.getParameter("ename"));
        empVO.setJob(request.getParameter("job"));
        empVO.setHiredate(new Date(parseDate(request.getParameter("hiredate")).getTime()));
        empVO.setSal(Integer.parseInt(request.getParameter("sal")));
        empVO.setComm(Double.parseDouble(request.getParameter("comm")));
        empVO.setDeptno(Integer.parseInt(request.getParameter("deptno")));
        return empVO;
    }

    private Map<String, String> validateRequest(HttpServletRequest request) {
        Map<String, String> errorMsgs = new HashMap<>();
        if (isBlank(request.getParameter("ename"))) {
            errorMsgs.put("ename", "請填寫員工姓名");
        }
        if (isBlank(request.getParameter("job"))) {
            errorMsgs.put("job", "請填寫職稱");
        }
        String paramHiredate = request.getParameter("hiredate");
        if (isBlank(paramHiredate) || !isValidDate(paramHiredate)) {
            errorMsgs.put("hiredate", "請填寫正確雇用日期");
        }
        String paramSal = request.getParameter("sal");
        if (isBlank(paramSal) || !isNaturalNumbers(paramSal)) {
            errorMsgs.put("sal", "請填寫正確薪水");
        }
        String paramComm = request.getParameter("comm");
        if (isBlank(paramComm) || !isPositiveDouble(paramComm)) {
            errorMsgs.put("comm", "請填寫正確加給");
        }
        String paramDeptno = request.getParameter("deptno");
        if (isBlank(paramDeptno) || !isNaturalNumbers(paramDeptno)) {
            errorMsgs.put("deptno", "請選擇部門");
        }
        return errorMsgs;
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

    private boolean isValidDate(String dateString) {
        try {
            parseDate(dateString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private java.util.Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}

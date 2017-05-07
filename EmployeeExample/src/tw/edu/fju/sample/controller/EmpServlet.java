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
    private static final String PAGE_UPDATE_EMP = "/WEB-INF/views/emp/listOneEmp.jsp";
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
            // 轉交至新增頁面
            case "preAdd":
                path = PAGE_ADD_EMP;
                break;
            // 執行新增
            case "add":
                path = doAddAction(request);
                break;
            // 轉交至更新頁面
            case "preUpdate":
                path = doFindOneAction(request);
                break;
            // 執行更新
            case "update":
                path = doUpdateAction(request, response);
                break;
            // 執行刪除
            case "delete":
                path = doDeleteAction(request);
                break;
            // 執行查詢
            case "query":
            default:
                path = doFindAction(request);
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private String doAddAction(HttpServletRequest request) {
        Map<String, String> errorMsgs = new HashMap<>();
        EmpVO empVO = retrieveEmpVO(request, errorMsgs);
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            request.setAttribute("empVO", empVO);
            return PAGE_ADD_EMP;
        }

        empService.insert(empVO);
        return doFindAction(request);
    }

    private String doUpdateAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> errorMsgs = new HashMap<>();
        EmpVO empVO = retrieveEmpVO(request, errorMsgs);
        empVO.setEmpno(Integer.parseInt(request.getParameter("empno")));
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            request.setAttribute("empVO", empVO);
            return PAGE_UPDATE_EMP;
        }

        empService.update(empVO);
        return doFindAction(request);
    }

    private String doDeleteAction(HttpServletRequest request) {
        empService.delete(Integer.parseInt(request.getParameter("empno")));
        return doFindAction(request);
    }

    private String doFindOneAction(HttpServletRequest request) {
        String empno = request.getParameter("empno");
        EmpVO empVO = empService.findByPrimaryKey(Integer.parseInt(empno));
        request.setAttribute("empVO", empVO);
        return PAGE_UPDATE_EMP;
    }

    private String doFindAction(HttpServletRequest request) {
        List<EmpVO> emps = empService.findAll();
        request.setAttribute("empList", emps);
        return PAGE_LIST_EMP;
    }

    /**
     * @param errorMsgs 用來存放請求參數輸入格式錯誤
     */
    private EmpVO retrieveEmpVO(HttpServletRequest request, Map<String, String> errorMsgs) {
        EmpVO empVO = new EmpVO();

        String ename = request.getParameter("ename");
        if (isBlank(ename)) {
            errorMsgs.put("ename", "請填寫員工姓名");
        } else {
            empVO.setEname(ename.trim());
        }

        String job = request.getParameter("job");
        if (isBlank(job)) {
            errorMsgs.put("job", "請填寫職稱");
        } else {
            empVO.setJob(job.trim());
        }

        String paramHiredate = request.getParameter("hiredate");
        if (isBlank(paramHiredate) || !isValidDate(paramHiredate)) {
            errorMsgs.put("hiredate", "請填寫正確雇用日期");
        } else {
            empVO.setHiredate(new Date(parseDate(request.getParameter("hiredate").trim()).getTime()));
        }

        String paramSal = request.getParameter("sal");
        if (isBlank(paramSal) || !isNaturalNumbers(paramSal)) {
            errorMsgs.put("sal", "請填寫正確薪水");
        } else {
            empVO.setSal(Integer.parseInt(request.getParameter("sal")));
        }

        String paramComm = request.getParameter("comm");
        if (isBlank(paramComm) || !isPositiveDouble(paramComm)) {
            errorMsgs.put("comm", "請填寫正確獎金");
        } else {
            empVO.setComm(Double.parseDouble(request.getParameter("comm")));
        }

        String paramDeptno = request.getParameter("deptno");
        if (isBlank(paramDeptno) || !isNaturalNumbers(paramDeptno)) {
            errorMsgs.put("deptno", "請選擇部門");
        } else {
            empVO.setDeptno(Integer.parseInt(request.getParameter("deptno")));
        }

        return empVO;
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

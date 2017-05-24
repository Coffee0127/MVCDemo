package tw.edu.fju.sample.controller;

import static tw.edu.fju.sample.utils.ValidateUtils.isBlank;
import static tw.edu.fju.sample.utils.ValidateUtils.isNaturalNumbers;
import static tw.edu.fju.sample.utils.ValidateUtils.isPositiveDouble;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tw.edu.fju.sample.model.EmpJSON;
import tw.edu.fju.sample.model.EmpVO;
import tw.edu.fju.sample.service.EmpService;
import tw.edu.fju.sample.utils.GsonTypes;

public class EmpServlet extends HttpServlet {

    private static final String PAGE_ADD_EMP = "/WEB-INF/views/emp/addEmp.jsp";
    private static final String PAGE_ADD_EMPS = "/WEB-INF/views/emp/addEmps.jsp";
    private static final String PAGE_UPDATE_EMP = "/WEB-INF/views/emp/listOneEmp.jsp";
    private static final String PAGE_LIST_EMP = "/WEB-INF/views/emp/listAllEmp.jsp";
    private static final String PAGE_LIST_EMP_BY_COMPOSITE = "/WEB-INF/views/emp/listAllEmp_ByCompositeQuery.jsp";

    private static final Gson GSON = new Gson();

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
        /*************************** 根據 action 不同呼叫對應的 doXXXAction 處理邏輯 ***************************/
        switch (action == null ? "query" : action) {
            // 轉交至新增頁面
            case "preAdd":
                path = PAGE_ADD_EMP;
                break;
            // 執行新增
            case "add":
                path = doAddAction(request);
                break;
                // 轉交至新增頁面
            case "preAddEmps":
                path = doPreAddEmpsAction(request);
                break;
                // 執行新增
            case "addEmps":
                path = doAddEmpsAction(request);
                break;
            // 轉交至更新頁面
            case "preUpdate":
                path = doFindOneAction(request);
                break;
            // 執行更新
            case "update":
                path = doUpdateAction(request);
                break;
            // 執行刪除
            case "delete":
                path = doDeleteAction(request);
                break;
            // 執行複合查詢
            case "queryByComposite":
                path = doCompositeFindAction(request);
                break;
            // 執行查詢
            case "query":
            default:
                path = doFindAction(request);
                break;
        }

        // 根據 doXXXAction 處理結果進行轉交
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private String doAddAction(HttpServletRequest request) {
        Map<String, String> errorMsgs = new HashMap<>();
        /*************************** 接收請求參數 並做 輸入格式的錯誤處理 ***************************/
        EmpVO empVO = retrieveEmpVO(request, errorMsgs);
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            // 含有輸入格式錯誤的empVO物件，也存入 HttpServletRequest
            request.setAttribute("empVO", empVO);
            // 將頁面轉交回原本新增頁面
            return PAGE_ADD_EMP;
        }

        /*************************** 開始新增資料 ***************************/
        empService.insert(empVO);
        return doFindAction(request);
    }

    private String doPreAddEmpsAction(HttpServletRequest request) {
        List<EmpVO> empVOs = new ArrayList<>();
        empVOs.add(new EmpVO());
        request.setAttribute("empVOs", empVOs);
        return PAGE_ADD_EMPS;
    }

    private String doAddEmpsAction(HttpServletRequest request) {
        /** 1. Retrieve Form Data */
        List<EmpJSON> empJSONs = GSON.fromJson(request.getParameter("emps"), GsonTypes.LIST_EMP_JSON_TYPE);

        /** 2. Convert Form Data & 3. Validate Form Data */
        List<EmpVO> empVOs = new ArrayList<>();
        List<Map<String, String>> errorMsgsList = new ArrayList<>();
        for (EmpJSON json : empJSONs) {
            EmpVO empVO = new EmpVO();
            Map<String, String> errorMsgs = new HashMap<>();

            String ename = json.getEname();
            if (isBlank(ename)) {
                errorMsgs.put("ename", "請填寫員工姓名");
            } else {
                empVO.setEname(ename.trim());
            }

            String job = json.getJob();
            if (isBlank(job)) {
                errorMsgs.put("job", "請填寫職稱");
            } else {
                empVO.setJob(job.trim());
            }

            String paramHiredate = json.getHiredate();
            if (isBlank(paramHiredate) || !isValidDate(paramHiredate)) {
                errorMsgs.put("hiredate", "請填寫正確雇用日期");
            } else {
                empVO.setHiredate(new Date(parseDate(json.getHiredate().trim()).getTime()));
            }

            String paramSal = json.getSal();
            if (isBlank(paramSal) || !isNaturalNumbers(paramSal)) {
                errorMsgs.put("sal", "請填寫正確薪水");
            } else {
                empVO.setSal(Integer.parseInt(json.getSal()));
            }

            String paramComm = json.getComm();
            if (isBlank(paramComm) || !isPositiveDouble(paramComm)) {
                errorMsgs.put("comm", "請填寫正確獎金");
            } else {
                empVO.setComm(Double.parseDouble(json.getComm()));
            }

            String paramDeptno = json.getDeptno();
            if (isBlank(paramDeptno) || !isNaturalNumbers(paramDeptno)) {
                errorMsgs.put("deptno", "請選擇部門");
            } else {
                empVO.setDeptno(Integer.parseInt(json.getDeptno()));
            }

            empVOs.add(empVO);
            if (!errorMsgs.isEmpty()) {
                errorMsgsList.add(errorMsgs);
            }
        }

        if (!errorMsgsList.isEmpty()) {
            request.setAttribute("errorMsgsList", errorMsgsList);
            // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
            request.setAttribute("empVOs", empVOs);
            // 將頁面轉交回原本新增頁面
            return PAGE_ADD_EMPS;
        }

        /** 4. Invoke Business Logic */
        empService.insert(empVOs);

        /** 5. Select Next View */
        return doFindAction(request);
    }

    private String doUpdateAction(HttpServletRequest request) {
        Map<String, String> errorMsgs = new HashMap<>();
        /*************************** 接收請求參數 並做 輸入格式的錯誤處理 ***************************/
        EmpVO empVO = retrieveEmpVO(request, errorMsgs);
        empVO.setEmpno(Integer.parseInt(request.getParameter("empno")));
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            // 含有輸入格式錯誤的empVO物件，也存入 HttpServletRequest
            request.setAttribute("empVO", empVO);
            // 將頁面轉交回原本修改頁面
            return PAGE_UPDATE_EMP;
        }

        /*************************** 開始修改資料 ***************************/
        empService.update(empVO);
        return doFindAction(request);
    }

    private String doDeleteAction(HttpServletRequest request) {
        /*************************** 開始刪除資料 ***************************/
        empService.delete(Integer.parseInt(request.getParameter("empno")));
        return doFindAction(request);
    }

    private String doFindOneAction(HttpServletRequest request) {
        // 小心！傳入的 empno 可能不是數字
        String empno = request.getParameter("empno");
        /*************************** 開始查詢資料 ***************************/
        EmpVO empVO = empService.findByPrimaryKey(Integer.parseInt(empno));
        request.setAttribute("empVO", empVO);
        return PAGE_UPDATE_EMP;
    }

    private String doFindAction(HttpServletRequest request) {
        List<EmpVO> emps = empService.findAll();
        /*************************** 開始查詢資料 ***************************/
        request.setAttribute("empList", emps);
        return PAGE_LIST_EMP;
    }

    @SuppressWarnings("unchecked")
    private String doCompositeFindAction(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Map<String, String> conditions = (Map<String, String>) session.getAttribute("conditions");
        if (isBlank(request.getParameter("whichPage"))) {
            HashMap<String, String> conditionMap = new HashMap<>();
            conditionMap.put("empno", request.getParameter("empno"));
            conditionMap.put("ename", request.getParameter("ename"));
            conditionMap.put("deptno", request.getParameter("deptno"));
            conditions = (Map<String, String>) conditionMap.clone();
            session.setAttribute("conditions", conditionMap);
        }

        List<EmpVO> emps = empService.findAll(conditions);
        request.setAttribute("empList", emps);
        return PAGE_LIST_EMP_BY_COMPOSITE;
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

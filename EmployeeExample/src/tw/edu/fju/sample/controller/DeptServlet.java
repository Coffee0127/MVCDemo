package tw.edu.fju.sample.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.edu.fju.sample.model.DeptVO;
import tw.edu.fju.sample.service.DeptService;
import tw.edu.fju.sample.utils.ValidateUtils;

public class DeptServlet extends HttpServlet {

    private static final String PAGE_ADD_DEPT = "/WEB-INF/views/dept/addDept.jsp";
    private static final String PAGE_UPDATE_DEPT = "/WEB-INF/views/dept/listOneDept.jsp";
    private static final String PAGE_LIST_DEPT = "/WEB-INF/views/dept/listAllDept.jsp";

    private DeptService deptService = new DeptService();

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
                path = PAGE_ADD_DEPT;
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

    private String doFindAction(HttpServletRequest request) {
        request.setAttribute("deptList", deptService.findAll());
        return PAGE_LIST_DEPT;
    }

    private String doAddAction(HttpServletRequest request) {
        Map<String, String> errorMsgs = new HashMap<>();
        /*************************** 接收請求參數 並做 輸入格式的錯誤處理 ***************************/
        DeptVO deptVO = retrieveDeptVO(request, errorMsgs);
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            // 含有輸入格式錯誤的deptVO物件，也存入 HttpServletRequest
            request.setAttribute("deptVO", deptVO);
            // 將頁面轉交回原本新增頁面
            return PAGE_ADD_DEPT;
        }

        /*************************** 開始新增資料 ***************************/
        deptService.insert(deptVO);
        return doFindAction(request);
    }

    private String doFindOneAction(HttpServletRequest request) {
        String deptno = request.getParameter("deptno");
        DeptVO deptVO = deptService.findByPrimaryKey(Integer.parseInt(deptno));
        request.setAttribute("deptVO", deptVO);
        return PAGE_UPDATE_DEPT;
    }

    private String doUpdateAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> errorMsgs = new HashMap<>();
        /*************************** 接收請求參數 並做 輸入格式的錯誤處理 ***************************/
        DeptVO deptVO = retrieveDeptVO(request, errorMsgs);
        deptVO.setDeptno(Integer.parseInt(request.getParameter("deptno")));
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            // 含有輸入格式錯誤的deptVO物件，也存入 HttpServletRequest
            request.setAttribute("deptVO", deptVO);
            // 將頁面轉交回原本修改頁面
            return PAGE_UPDATE_DEPT;
        }

        /*************************** 開始修改資料 ***************************/
        deptService.update(deptVO);
        return doFindAction(request);
    }

    private String doDeleteAction(HttpServletRequest request) {
        deptService.delete(Integer.parseInt(request.getParameter("deptno")));
        return doFindAction(request);
    }

    /**
     * @param errorMsgs 用來存放請求參數輸入格式錯誤
     */
    private DeptVO retrieveDeptVO(HttpServletRequest request, Map<String, String> errorMsgs) {
        DeptVO deptVO = new DeptVO();

        /*
         ***************************************************************
         * String someProperty = request.getParameter("someProperty"); *
         * 如果 someProperty 不合法，將錯誤訊息存入 errorMsgs          *
         * 否則 將 someProperty 存入 deptVO                            *
         ***************************************************************
         */

        String dname = request.getParameter("dname");
        if (ValidateUtils.isBlank(dname)) {
            errorMsgs.put("dname", "請填寫部門名稱");
        } else {
            deptVO.setDname(dname.trim());
        }

        String loc = request.getParameter("loc");
        if (ValidateUtils.isBlank(loc)) {
            errorMsgs.put("loc", "請填寫部門基地");
        } else {
            deptVO.setLoc(loc.trim());
        }

        return deptVO;
    }
}

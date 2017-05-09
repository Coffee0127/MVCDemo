package tw.edu.fju.sample.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.edu.fju.sample.model.DeptVO;
import tw.edu.fju.sample.service.DeptService;

// TODO 08. 於 web.xml 註冊 DeptServlet
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

        // TODO 09-1. 測試 web.xml 是否已正確註冊
        // http://localhost:8080/EmployeeExample/dept.do
        // 測試完畢後將以下三行程式碼註解 / 刪除
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>Hello World!!</h1>");

        // TODO 09-2. 先將以下轉交程式碼註解取消，並實作查詢 action
//        String path = null;
//        String action = request.getParameter("action");
//        switch (action == null ? "query" : action) {
//            // 轉交至新增頁面
//            /*case "preAdd":
//                path = PAGE_ADD_DEPT;
//                break;*/
//            // 執行新增
//            /*case "add":
//                path = doAddAction(request);
//                break;*/
//            // 轉交至更新頁面
//            /*case "preUpdate":
//                path = doFindOneAction(request);
//                break;*/
//            // 執行更新
//            /*case "update":
//                path = doUpdateAction(request, response);
//                break;*/
//            // 執行刪除
//            /*case "delete":
//                path = doDeleteAction(request);
//                break;*/
//            // 執行查詢
//            /*case "query":
//            default:
//                path = doFindAction(request);
//                break;*/
//        }
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//        dispatcher.forward(request, response);
    }

    private String doFindAction(HttpServletRequest request) {
        // TODO 10. 透過 DeptService 實作 doFindAction
        // 同時需修改 /WEB-INF/views/dept/listAllDept.jsp 取出查詢結果
        return PAGE_LIST_DEPT;
    }

    private String doAddAction(HttpServletRequest request) {
        // TODO 11-1. 透過 DeptService 實作 doAddAction
        // Hint: 需檢查前端送上來的資料格式是否正確，若有錯誤需導回 PAGE_ADD_DEPT；無錯誤則導回查詢頁面
        // 同時需修改 /WEB-INF/views/dept/addDept.jsp 讀取資料格式正確的 DeptVO 及 錯誤訊息
        return doFindAction(request);
    }

    private String doFindOneAction(HttpServletRequest request) {
        // TODO 12. 透過 DeptService 實作 doFindOneAction
        // Hint: 透過主鍵查詢 DeptVO 物件轉交 PAGE_UPDATE_DEPT 進行修改
        // 同時修改 /WEB-INF/views/dept/listOneDept.jsp 讀取主鍵查詢的 DeptVO 物件
        return PAGE_UPDATE_DEPT;
    }

    private String doUpdateAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO 13. 透過 DeptService 實作 doUpdateAction
        // Hint: 需檢查前端送上來的資料格式是否正確，若有錯誤需導回 PAGE_UPDATE_DEPT；無錯誤則導回查詢頁面
        // 同時修改 /WEB-INF/views/dept/listOneDept.jsp 讀取資料格式正確的 DeptVO 及 錯誤訊息
        return doFindAction(request);
    }

    private String doDeleteAction(HttpServletRequest request) {
        // TODO 14. 透過 DeptService 實作 doDeleteAction
        // Hint: 刪除完後導回查詢頁面
        return doFindAction(request);
    }

    /**
     * @param errorMsgs 用來存放請求參數輸入格式錯誤
     */
    private DeptVO retrieveDeptVO(HttpServletRequest request, Map<String, String> errorMsgs) {
        DeptVO deptVO = new DeptVO();
        // TODO 11-2. 從 HttpServletRequest 讀取前端送入參數，進行格式檢查及將有效格式參數存入 DeptVO

        /*
         ***************************************************************
         * String someProperty = request.getParameter("someProperty"); *
         * 如果 someProperty 不合法，將錯誤訊息存入 errorMsgs          *
         * 否則 將 someProperty 存入 deptVO                            *
         ***************************************************************
         */

        return deptVO;
    }
}

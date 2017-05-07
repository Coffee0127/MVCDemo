package tw.edu.fju.sample.common.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("ctxPath", servletContext.getContextPath());

        Map<Integer, String> deptMap = new HashMap<>();
        // TODO 改為讀取 DEPT2 表格
        deptMap.put(10, "財務部");
        deptMap.put(20, "研發部");
        deptMap.put(30, "業務部");
        deptMap.put(40, "生管部");
        servletContext.setAttribute("deptMap", deptMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}

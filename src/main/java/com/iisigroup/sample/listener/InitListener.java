package com.iisigroup.sample.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.iisigroup.sample.common.DBInfo;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce)  {
        try {
            Class.forName(DBInfo.DRIVER);
            String sql =
                    // EMP2
                    "DROP TABLE IF EXISTS EMP2;CREATE TABLE EMP2(EMPNO INT AUTO_INCREMENT NOT NULL,ENAME VARCHAR(10),JOB VARCHAR(9),HIREDATE DATE,SAL INT,COMM DOUBLE,DEPTNO INT NOT NULL,CONSTRAINT EMP2_EMPNO_PK PRIMARY KEY(EMPNO));INSERT INTO EMP2 VALUES(7001,'馬裕雪','董事長',PARSEDATETIME('1981-11-17','yyyy-MM-dd'),5000,2.5,10);INSERT INTO EMP2 VALUES(7002,'林家慶','經理',PARSEDATETIME('1981-05-01','yyyy-MM-dd'),2850,1.8,30);INSERT INTO EMP2 VALUES(7003,'李凱鈞','經理',PARSEDATETIME('1981-01-09','yyyy-MM-dd'),2450,1.8,10);INSERT INTO EMP2 VALUES(7004,'林芳易','經理',PARSEDATETIME('1981-04-02','yyyy-MM-dd'),2975,1.8,20);INSERT INTO EMP2 VALUES(7005,'黃彥恭','業務',PARSEDATETIME('1981-09-28','yyyy-MM-dd'),1250,1.5,30);INSERT INTO EMP2 VALUES(7006,'李惠婷','業務',PARSEDATETIME('1981-02-02','yyyy-MM-dd'),1600,1.5,30);INSERT INTO EMP2 VALUES(7007,'羅淑真','業務',PARSEDATETIME('1981-09-28','yyyy-MM-dd'),1500,1.5,30);INSERT INTO EMP2 VALUES(7008,'程啟亦','助理專員',PARSEDATETIME('1981-12-03','yyyy-MM-dd'),950,0.8,30);INSERT INTO EMP2 VALUES(7009,'盧晉一','業務',PARSEDATETIME('1981-02-22','yyyy-MM-dd'),1250,1.5,30);INSERT INTO EMP2 VALUES(7010,'宋力行','財務',PARSEDATETIME('1981-12-03','yyyy-MM-dd'),3000,1.0,20);INSERT INTO EMP2 VALUES(7011,'劉曜宇','助理專員',PARSEDATETIME('1980-12-17','yyyy-MM-dd'),800,0.8,20);INSERT INTO EMP2 VALUES(7012,'林珮君','財務',PARSEDATETIME('1981-12-09','yyyy-MM-dd'),3000,1.0,20);INSERT INTO EMP2 VALUES(7013,'顏俊廷','助理專員',PARSEDATETIME('1983-01-12','yyyy-MM-dd'),1100,0.8,20);INSERT INTO EMP2 VALUES(7014,'牛佳蓉','助理專員',PARSEDATETIME('1982-01-23','yyyy-MM-dd'),1300,0.8,10);" +
                    // DEPT2
                    "DROP TABLE IF EXISTS DEPT2;CREATE TABLE DEPT2(DEPTNO INT AUTO_INCREMENT NOT NULL,DNAME VARCHAR(14),LOC VARCHAR(13),CONSTRAINT DEPT2_PRIMARY_KEY PRIMARY KEY(DEPTNO));INSERT INTO DEPT2 VALUES(10,'財務部','臺灣台北');INSERT INTO DEPT2 VALUES(20,'研發部','臺灣新竹');INSERT INTO DEPT2 VALUES(30,'業務部','美國紐約');INSERT INTO DEPT2 VALUES(40,'生管部','中國上海');";
            Connection conn = DriverManager.getConnection(DBInfo.URL, DBInfo.USERID, DBInfo.PASSWD);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sce.getServletContext().setAttribute("ctxPath", sce.getServletContext().getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)  {
    }
}

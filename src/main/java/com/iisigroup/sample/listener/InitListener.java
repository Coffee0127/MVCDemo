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
            String sql = "DROP TABLE IF EXISTS EMP2;CREATE TABLE EMP2(EMPNO INT AUTO_INCREMENT NOT NULL,ENAME VARCHAR(10),JOB VARCHAR(9),HIREDATE DATE,SAL INT,COMM DOUBLE,DEPTNO INT NOT NULL,CONSTRAINT EMP2_EMPNO_PK PRIMARY KEY(EMPNO));INSERT INTO EMP2 VALUES(7001,'KING','PRESIDENT',PARSEDATETIME('1981-11-17','yyyy-MM-dd'),5000,2.5,10);INSERT INTO EMP2 VALUES(7002,'BLAKE','MANAGER',PARSEDATETIME('1981-05-01','yyyy-MM-dd'),2850,1.8,30);INSERT INTO EMP2 VALUES(7003,'CLARK','MANAGER',PARSEDATETIME('1981-01-09','yyyy-MM-dd'),2450,1.8,10);INSERT INTO EMP2 VALUES(7004,'JONES','MANAGER',PARSEDATETIME('1981-04-02','yyyy-MM-dd'),2975,1.8,20);INSERT INTO EMP2 VALUES(7005,'MARTIN','SALESMAN',PARSEDATETIME('1981-09-28','yyyy-MM-dd'),1250,1.5,30);INSERT INTO EMP2 VALUES(7006,'ALLEN','SALESMAN',PARSEDATETIME('1981-02-02','yyyy-MM-dd'),1600,1.5,30);INSERT INTO EMP2 VALUES(7007,'TURNER','SALESMAN',PARSEDATETIME('1981-09-28','yyyy-MM-dd'),1500,1.5,30);INSERT INTO EMP2 VALUES(7008,'JAMES','CLERK',PARSEDATETIME('1981-12-03','yyyy-MM-dd'),950,0.8,30);INSERT INTO EMP2 VALUES(7009,'WARD','SALESMAN',PARSEDATETIME('1981-02-22','yyyy-MM-dd'),1250,1.5,30);INSERT INTO EMP2 VALUES(7010,'FORD','ANALYST',PARSEDATETIME('1981-12-03','yyyy-MM-dd'),3000,1.0,20);INSERT INTO EMP2 VALUES(7011,'SMITH','CLERK',PARSEDATETIME('1980-12-17','yyyy-MM-dd'),800,0.8,20);INSERT INTO EMP2 VALUES(7012,'SCOTT','ANALYST',PARSEDATETIME('1981-12-09','yyyy-MM-dd'),3000,1.0,20);INSERT INTO EMP2 VALUES(7013,'ADAMS','CLERK',PARSEDATETIME('1983-01-12','yyyy-MM-dd'),1100,0.8,20);INSERT INTO EMP2 VALUES(7014,'MILLER','CLERK',PARSEDATETIME('1982-01-23','yyyy-MM-dd'),1300,0.8,10);";
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

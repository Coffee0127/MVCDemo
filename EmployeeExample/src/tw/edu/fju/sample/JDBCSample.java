package tw.edu.fju.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCSample {

    public static void main(String[] args) {
        final String DRIVER = "oracle.jdbc.driver.OracleDriver";
        final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        final String USERID = "SCOTT";
        final String PASSWD = "TIGER";

        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USERID, PASSWD);
            System.out.println("資料庫連線測試成功！");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage(), e);
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage(), se);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}

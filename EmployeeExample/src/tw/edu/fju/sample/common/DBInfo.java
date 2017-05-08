package tw.edu.fju.sample.common;

public final class DBInfo {
    // TODO 00. 修改 DB 連線資訊
    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    public static final String USERID = "SCOTT";
    public static final String PASSWD = "TIGER";

    private DBInfo() {
    }
}

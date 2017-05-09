package tw.edu.fju.sample.dao;

import java.util.List;

import tw.edu.fju.sample.common.DBInfo;
import tw.edu.fju.sample.model.DeptVO;

public class DeptJDBCDAO implements DeptDAO {

    private static final String INSERT_STMT = "INSERT INTO dept2 (dname, loc) VALUES (?, ?)";
    private static final String GET_ALL_STMT = "SELECT * FROM dept2";
    private static final String GET_ONE_STMT = "SELECT * FROM dept2 where deptno = ?";
    private static final String DELETE_EMPS_STMT = "DELETE FROM emp2 where deptno = ?";
    private static final String DELETE_DEPT_STMT = "DELETE FROM dept2 where deptno = ?";
    private static final String UPDATE = "UPDATE dept2 set dname=?, loc=? where deptno = ?";

    private static final String driver = DBInfo.DRIVER;
    private static final String url = DBInfo.URL;
    private static final String userid = DBInfo.USERID;
    private static final String passwd = DBInfo.PASSWD;

    static {
        // TODO 02. 載入驅動程式
    }

    @Override
    public void insert(DeptVO deptVO) {
        // TODO 03-1. 完成新增程式碼
        // Hint: java.sql.PreparedStatement.executeUpdate()
    }

    @Override
    public void update(DeptVO deptVO) {
        // TODO 04-1. 完成修改程式碼
        // Hint: java.sql.PreparedStatement.executeUpdate()
    }

    @Override
    public void delete(Integer deptno) {
        // TODO 05-1. 完成刪除程式碼
        // Hint: java.sql.PreparedStatement.executeUpdate()
        // 先刪除員工，再刪除部門
    }

    @Override
    public DeptVO findByPrimaryKey(Integer deptno) {
        // TODO 06-1. 完成主鍵查詢程式碼
        // Hint: java.sql.PreparedStatement.executeQuery()
        return null;
    }

    @Override
    public List<DeptVO> findAll() {
        // TODO 07-1. 完成查詢程式碼
        // Hint: java.sql.PreparedStatement.executeQuery()
        return null;
    }

    public static void main(String[] args) {
        DeptDAO dao = new DeptJDBCDAO();

        // TODO 03-2. 測試新增
//        DeptVO deptVO1 = new DeptVO();
//        deptVO1.setDname("製造部");
//        deptVO1.setLoc("中國江西");
//        dao.insert(deptVO1);

        // TODO 04-2. 測試修改
//        DeptVO deptVO2 = new DeptVO();
//        deptVO2.setDeptno(10);
//        deptVO2.setDname("財務部2");
//        deptVO2.setLoc("臺灣台北2");
//        dao.update(deptVO2);

        // TODO 05-2. 測試刪除
//        dao.delete(30);

        // TODO 06-2. 測試主鍵查詢
//        DeptVO deptVO3 = dao.findByPrimaryKey(10);
//        System.out.print(deptVO3.getDeptno() + ",");
//        System.out.print(deptVO3.getDname() + ",");
//        System.out.println(deptVO3.getLoc());

        // TODO 07-2. 測試查詢
//        List<DeptVO> list = dao.getAll();
//        for (DeptVO aDept : list) {
//            System.out.print(aDept.getDeptno() + ",");
//            System.out.print(aDept.getDname() + ",");
//            System.out.print(aDept.getLoc());
//            System.out.println();
//        }
    }
}

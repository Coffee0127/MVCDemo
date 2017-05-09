package tw.edu.fju.sample.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.edu.fju.sample.common.DBInfo;
import tw.edu.fju.sample.model.DeptVO;

public class DeptJDBCDAO implements DeptDAO {

    private static final String INSERT_STMT = "INSERT INTO dept2 (deptno, dname, loc) VALUES (dept2_seq.NEXTVAL, ?, ?)";
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
        try {
            Class.forName(driver);
            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        }
    }

    @Override
    public void insert(DeptVO deptVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setString(1, deptVO.getDname());
            pstmt.setString(2, deptVO.getLoc());

            pstmt.executeUpdate();

            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public void update(DeptVO deptVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, deptVO.getDname());
            pstmt.setString(2, deptVO.getLoc());
            pstmt.setInt(3, deptVO.getDeptno());

            pstmt.executeUpdate();

            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public void delete(Integer deptno) {
        // 先刪除員工，再刪除部門
        int updateCountEMPs = 0;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);

            // 1●設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);

            // 先刪除員工
            pstmt = con.prepareStatement(DELETE_EMPS_STMT);
            pstmt.setInt(1, deptno);
            updateCountEMPs = pstmt.executeUpdate();
            // 再刪除部門
            pstmt = con.prepareStatement(DELETE_DEPT_STMT);
            pstmt.setInt(1, deptno);
            pstmt.executeUpdate();

            // 2●設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("刪除部門編號" + deptno + "時,共有員工" + updateCountEMPs + "人同時被刪除");

            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
                    // 3●設定於當有exception發生時之catch區塊內
                    con.rollback();
                } catch (SQLException excep) {
                    throw new RuntimeException("rollback error occured. " + excep.getMessage());
                }
            }
            throw new RuntimeException("A database error occured. " + se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public DeptVO findByPrimaryKey(Integer deptno) {
        DeptVO deptVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, deptno);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // deptVO 也稱為 Domain objects
                deptVO = new DeptVO();
                deptVO.setDeptno(rs.getInt("deptno"));
                deptVO.setDname(rs.getString("dname"));
                deptVO.setLoc(rs.getString("loc"));
            }

            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return deptVO;
    }

    @Override
    public List<DeptVO> findAll() {
        List<DeptVO> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DeptVO deptVO = new DeptVO();
                deptVO.setDeptno(rs.getInt("deptno"));
                deptVO.setDname(rs.getString("dname"));
                deptVO.setLoc(rs.getString("loc"));
                list.add(deptVO); // Store the row in the list
            }

            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
}

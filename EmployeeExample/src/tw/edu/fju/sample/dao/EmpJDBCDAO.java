package tw.edu.fju.sample.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tw.edu.fju.sample.common.DBInfo;
import tw.edu.fju.sample.model.EmpVO;

public class EmpJDBCDAO implements EmpDAO {

    private static final String INSERT_STMT =
        "INSERT INTO emp2 (ename,job,hiredate,sal,comm,deptno) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_STMT =
        "SELECT * FROM emp2";
    private static final String GET_ALL_BASE_STMT =
        "SELECT * FROM emp2 WHERE 1 = 1";
    private static final String GET_ONE_STMT =
        "SELECT * FROM emp2 where empno = ?";
    private static final String DELETE =
        "DELETE FROM emp2 where empno = ?";
    private static final String UPDATE =
        "UPDATE emp2 set ename=?, job=?, hiredate=?, sal=?, comm=?, deptno=? where empno = ?";

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
    public void insert(EmpVO empVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setString(1, empVO.getEname());
            pstmt.setString(2, empVO.getJob());
            pstmt.setDate(3, empVO.getHiredate());
            pstmt.setDouble(4, empVO.getSal());
            pstmt.setDouble(5, empVO.getComm());
            pstmt.setInt(6, empVO.getDeptno());
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
    public void update(EmpVO empVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, empVO.getEname());
            pstmt.setString(2, empVO.getJob());
            pstmt.setDate(3, empVO.getHiredate());
            pstmt.setDouble(4, empVO.getSal());
            pstmt.setDouble(5, empVO.getComm());
            pstmt.setInt(6, empVO.getDeptno());
            pstmt.setInt(7, empVO.getEmpno());
            int updateCountEMPs = pstmt.executeUpdate();
            System.out.println("已更新員工 " + updateCountEMPs + " 人!");

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
    public void delete(Integer empno) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);
            pstmt.setInt(1, empno);
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
    public EmpVO findByPrimaryKey(Integer empno) {
        EmpVO empVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);
            pstmt.setInt(1, empno);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // empVo 也稱為 Domain objects
                empVO = new EmpVO();
                empVO.setEmpno(rs.getInt("empno"));
                empVO.setEname(rs.getString("ename"));
                empVO.setJob(rs.getString("job"));
                empVO.setHiredate(rs.getDate("hiredate"));
                empVO.setSal(rs.getInt("sal"));
                empVO.setComm(rs.getDouble("comm"));
                empVO.setDeptno(rs.getInt("deptno"));
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
        return empVO;
    }

    @Override
    public List<EmpVO> findAll() {
        return this.doFindAll(GET_ALL_STMT);
    }

    @Override
    public List<EmpVO> findAll(Map<String, String> conditions) {
        StringBuilder sql = new StringBuilder(GET_ALL_BASE_STMT);
        if (conditions != null) {
            for (Entry<String, String> entry : conditions.entrySet()) {
                String condition = transformCondition(entry.getKey(), entry.getValue());
                if (condition != null) {
                    sql.append(" and ").append(condition);
                }
            }
        }
        System.out.println("●●finalSQL = " + sql);
        return this.doFindAll(sql.toString());
    }

    private String transformCondition(String columnName, String value) {
        /*
         * TODO [進階] 思考 in、between、>=、<=、… 時該怎麼撰寫程式碼
         * 提示：
         *   1. ServletRequest.getParameterMap() 方法 回傳 java.util.Map<java.lang.String,java.lang.String[]>
         *   2. 修改方法參數(如使用 enum、常數…等)，使程式可以判斷使用何種 where operator
         */
        switch (columnName) {
            case "empno":
            case "sal":
            case "comm":
            case "deptno":
                return columnName + "=" + value;
            case "ename":
            case "job":
                return columnName + " like '%" + value + "%'";
            case "hiredate":
                return "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";
            default:
                return null;
        }
    }

    private List<EmpVO> doFindAll(String sql) {
        List<EmpVO> list = new ArrayList<>();
        EmpVO empVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // empVO 也稱為 Domain objects
                empVO = new EmpVO();
                empVO.setEmpno(rs.getInt("empno"));
                empVO.setEname(rs.getString("ename"));
                empVO.setJob(rs.getString("job"));
                empVO.setHiredate(rs.getDate("hiredate"));
                empVO.setSal(rs.getInt("sal"));
                empVO.setComm(rs.getDouble("comm"));
                empVO.setDeptno(rs.getInt("deptno"));
                list.add(empVO); // Store the row in the list
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
        return list;
    }
}
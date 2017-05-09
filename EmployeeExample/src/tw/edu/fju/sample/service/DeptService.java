package tw.edu.fju.sample.service;

import java.util.List;

import tw.edu.fju.sample.dao.DeptDAO;
import tw.edu.fju.sample.dao.DeptJDBCDAO;
import tw.edu.fju.sample.model.DeptVO;

public class DeptService {

    private DeptDAO dao = new DeptJDBCDAO();

    public void insert(DeptVO deptVO) {
        this.dao.insert(deptVO);
    }

    public void update(DeptVO deptVO) {
        this.dao.update(deptVO);
    }

    public void delete(Integer deptno) {
        this.dao.delete(deptno);
    }

    public DeptVO findByPrimaryKey(Integer deptno) {
        return this.dao.findByPrimaryKey(deptno);
    }

    public List<DeptVO> findAll() {
        return this.dao.findAll();
    }

}

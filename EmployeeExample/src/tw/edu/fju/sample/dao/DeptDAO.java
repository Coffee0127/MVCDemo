package tw.edu.fju.sample.dao;

import java.util.List;

import tw.edu.fju.sample.model.DeptVO;

public interface DeptDAO {
    void insert(DeptVO deptVO);
    void update(DeptVO deptVO);
    void delete(Integer deptno);
    DeptVO findByPrimaryKey(Integer deptno);
    List<DeptVO> findAll();
}

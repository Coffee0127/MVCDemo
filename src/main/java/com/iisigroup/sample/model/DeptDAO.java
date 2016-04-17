package com.iisigroup.sample.model;

import java.util.List;

public interface DeptDAO {
    void insert(DeptVO deptVO);
    void update(DeptVO deptVO);
    void delete(Integer deptno);
    DeptVO findByPrimaryKey(Integer deptno);
    List<DeptVO> getAll();
}

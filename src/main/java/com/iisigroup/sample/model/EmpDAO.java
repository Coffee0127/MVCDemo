package com.iisigroup.sample.model;

import java.util.List;

public interface EmpDAO {
    void insert(EmpVO empVO);
    void update(EmpVO empVO);
    void delete(Integer empno);
    EmpVO findByPrimaryKey(Integer empno);
    List<EmpVO> getAll();
}

package tw.edu.fju.sample.dao;

import java.util.List;
import java.util.Map;

import tw.edu.fju.sample.model.EmpVO;

public interface EmpDAO {
    void insert(EmpVO empVO);
    void update(EmpVO empVO);
    void delete(Integer empno);
    EmpVO findByPrimaryKey(Integer empno);
    List<EmpVO> findAll();

    /**
     * 複合查詢
     * @param 查詢條件 key = 欄位名稱，value = 查詢條件
     * @return
     */
    List<EmpVO> findAll(Map<String, String> conditions);
}

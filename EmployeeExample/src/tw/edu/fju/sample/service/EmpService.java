package tw.edu.fju.sample.service;

import java.util.List;
import java.util.Map;

import tw.edu.fju.sample.dao.EmpDAO;
import tw.edu.fju.sample.dao.EmpJDBCDAO;
import tw.edu.fju.sample.model.EmpVO;

public class EmpService {

    private EmpDAO dao = new EmpJDBCDAO();

    public void insert(EmpVO empVO) {
        this.dao.insert(empVO);
    }

    public void update(EmpVO empVO) {
        this.dao.update(empVO);
    }

    public void delete(Integer empno) {
        this.dao.delete(empno);
    }

    public EmpVO findByPrimaryKey(Integer empno) {
        return this.dao.findByPrimaryKey(empno);
    }

    public List<EmpVO> findAll() {
        return this.dao.findAll();
    }

    public List<EmpVO> findAll(Map<String, String> conditions) {
        return this.dao.findAll(conditions);
    }

    public void insert(List<EmpVO> empVOs) {
        this.dao.insert(empVOs);
    }
}

package ma.jberrich.dao;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

import ma.jberrich.bean.Emp;
import ma.jberrich.model.Employe;
import ma.jberrich.model.Service;
import ma.jberrich.util.BeanMapper;

public class EmployeDAO implements IEmployeDAO {

	private IServiceDAO sdao = new ServiceDAO();
	
	private final static String QUERY_INSERT_EMPLOYE      = "INSERT INTO EMP(ENAME, JOB, AGE, SAL, DEPTNO) VALUES (:name, :job, :age, :sal, :dept)";
	private final static String QUERY_SELECT_ALL_EMPLOYES = "SELECT * FROM EMP";

	@Override
	public void enregistrerEmploye(Employe emp) {
		try (Connection connection = DatabaseSource.getInstance().getTransaction()) {
			connection.createQuery(QUERY_INSERT_EMPLOYE)
						.addParameter("name", emp.getNom())
						.addParameter("job" , emp.getFonction())
						.addParameter("age" , emp.getAge())
						.addParameter("sal" , emp.getSalaire())
						.addParameter("dept", emp.getService().getId())
							.executeUpdate();

			connection.commit();
		}
	}

	@Override
	public List<Employe> getListeEmployes() {
		List<Employe> employes = new ArrayList<>();
		
		try (Connection connection = DatabaseSource.getInstance().getConnection()) {
			List<Emp> emps = connection.createQuery(QUERY_SELECT_ALL_EMPLOYES).executeAndFetch(Emp.class);
			for (Emp emp : emps) {
				Employe employe = BeanMapper.getInstance().map(emp);
				Service service = sdao.getServiceByID(employe.getService().getId());
				employe.setService(service);
				employes.add(employe);
			}
		}
		
		return employes;
	}

}

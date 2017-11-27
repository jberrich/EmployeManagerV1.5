package ma.jberrich.dao;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

import ma.jberrich.bean.Dept;
import ma.jberrich.model.Service;
import ma.jberrich.util.BeanMapper;

public class ServiceDAO implements IServiceDAO {

	private final static String QUERY_SELECT_SERVICE_BY_NAME = "SELECT * FROM DEPT WHERE DNAME = :name";
	private final static String QUERY_SELECT_SERVICE_BY_ID   = "SELECT * FROM DEPT WHERE DEPTNO = :id";
	private final static String QUERY_SELECT_ALL_SERVICES    = "SELECT * FROM DEPT";
	
	@Override
	public Service getService(String nom) {
		Service service = null;

		try (Connection connection = DatabaseSource.getInstance().getConnection()) {
			Dept dept = connection.createQuery(QUERY_SELECT_SERVICE_BY_NAME).addParameter("name", nom).executeAndFetchFirst(Dept.class);
			service = BeanMapper.getInstance().map(dept);
		}

		return service;
	}

	@Override
	public Service getServiceByID(int id) {
		Service service = null;

		try (Connection connection = DatabaseSource.getInstance().getConnection()) {
			Dept dept = connection.createQuery(QUERY_SELECT_SERVICE_BY_ID).addParameter("id", id).executeAndFetchFirst(Dept.class);
			service = BeanMapper.getInstance().map(dept);
		}

		return service;
	}

	@Override
	public List<Service> getListeServices() {
		List<Service> services = new ArrayList<>();
		try (Connection connection = DatabaseSource.getInstance().getConnection()) {
			List<Dept> depts = connection.createQuery(QUERY_SELECT_ALL_SERVICES).executeAndFetch(Dept.class);
			for (Dept dept : depts) {
				Service service = BeanMapper.getInstance().map(dept);
				services.add(service);
			}
		}
		return services;
	}

}

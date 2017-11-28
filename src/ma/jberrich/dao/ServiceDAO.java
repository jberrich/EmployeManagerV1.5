package ma.jberrich.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;

import ma.jberrich.model.Service;

public class ServiceDAO implements IServiceDAO {

	@Override
	public Service getService(String nom) {
		Service service = null;

		try {
			QueryBuilder<Service, Integer> builder = DatabaseSource.getInstance().getDeptDao().queryBuilder();
			builder.where().like("dname", nom);
			service = DatabaseSource.getInstance().getDeptDao().queryForFirst(builder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return service;
	}

	@Override
	public Service getServiceByID(int id) {
		Service service = null;

		try {
			service = DatabaseSource.getInstance().getDeptDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return service;
	}

	@Override
	public List<Service> getListeServices() {
		List<Service> services = new ArrayList<>();

		try {
			services = DatabaseSource.getInstance().getDeptDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return services;
	}

}

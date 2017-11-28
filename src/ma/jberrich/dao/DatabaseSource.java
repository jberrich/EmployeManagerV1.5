package ma.jberrich.dao;

import java.sql.SQLException;

import org.aeonbits.owner.ConfigFactory;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import ma.jberrich.model.Employe;
import ma.jberrich.model.Service;

public class DatabaseSource {

	private static DatabaseSource database;
	private JdbcConnectionSource source;

	private Dao<Service, Integer> deptDao;
	private Dao<Employe, Integer> empDao;
	
	private DatabaseSource() {
		Configuration configuration = ConfigFactory.create(Configuration.class);
		try {
			Class.forName(configuration.getDriver());
			source = new JdbcConnectionSource(configuration.getUrl(), 
											  configuration.getUsername(),
											  configuration.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DatabaseSource getInstance() {
		if (database == null) {
			database = new DatabaseSource();
			return database;
		} else {
			return database;
		}
	}

	 public Dao<Service, Integer> getDeptDao() throws SQLException {
		 if(deptDao == null) {
			 deptDao = DaoManager.createDao(source, Service.class);
			 return this.deptDao;
		 } else {
			 return this.deptDao; 
		 }
	 }
	
	 public Dao<Employe, Integer> getEmpDao() throws SQLException {
		 if(empDao == null) {
			 empDao = DaoManager.createDao(source, Employe.class);
			 return this.empDao;
		 } else {
			 return this.empDao; 
		 }
	 }

}

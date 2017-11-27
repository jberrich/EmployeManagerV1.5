package ma.jberrich.dao;

import org.aeonbits.owner.ConfigFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DatabaseSource {

	private static DatabaseSource database;
	private Sql2o sql2o;

	private DatabaseSource() {
		Configuration configuration = ConfigFactory.create(Configuration.class);
		try {
			Class.forName(configuration.getDriver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		sql2o = new Sql2o(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
	}

	public static DatabaseSource getInstance() {
		if(database == null) {
			database = new DatabaseSource();
			return database;
		}else {
			return database;
		}
	}

	public Connection getConnection() {
		return this.sql2o.open();
	}

	public Connection getTransaction() {
		return this.sql2o.beginTransaction();
	}
	
}

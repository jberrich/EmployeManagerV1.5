package ma.jberrich.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.jberrich.model.Employe;

public class EmployeDAO implements IEmployeDAO {

	@Override
	public void enregistrerEmploye(Employe emp) {
		try {
			DatabaseSource.getInstance().getEmpDao().create(emp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Employe> getListeEmployes() {
		List<Employe> employes = new ArrayList<>();

		try {
			employes = DatabaseSource.getInstance().getEmpDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employes;
	}

}

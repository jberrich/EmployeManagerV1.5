package ma.jberrich.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@DatabaseTable(tableName = "EMP")
public class Employe {
	
	@DatabaseField(generatedId = true, columnName = "EMPNO")
	private int id;
	
	@NonNull
	@DatabaseField(columnName = "ENAME")
	private String nom;
	
	@DatabaseField(columnName = "AGE")
	private int age;
	
	@DatabaseField(columnName = "JOB")
	private String fonction;
	
	@DatabaseField(columnName = "SAL")
	private int salaire;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "DEPTNO")
	private Service service;
	
}

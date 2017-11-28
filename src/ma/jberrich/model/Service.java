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
@DatabaseTable(tableName = "DEPT")
public class Service {

	@DatabaseField(generatedId = true, columnName = "DEPTNO")
	private int id;
	
	@NonNull
	@DatabaseField(columnName = "DNAME")
	private String nom;
	
	@NonNull
	@DatabaseField(columnName = "LOC")
	private String local;

}

package ma.jberrich.model;

import lombok.Data;

@Data
public class Employe {
	private int id;
	private String nom;
	private int age;
	private String fonction;
	private int salaire;
	private Service service;
}

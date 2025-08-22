package model.dto;

import model.entity.enumeration.Role;

public class Userdto {
	
	private int id;
	private String name;
	private String email ;
	private String password ;
	private String role;
	private String fonction;
	
	public Userdto(int id, String name, String email, String password, String role, String fonction) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.fonction = fonction;
	}
	
	public Userdto(String name, String email, String password, String role, String fonction) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.fonction = fonction;
	}


	public Userdto() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	@Override
	public String toString() {
		return "Userdto [name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", fonction=" + fonction + "]";
	}

	

	public static boolean loginUser(String email, String password) {
		return false;
		
		
	}

	public static boolean registerUser(String nom, String email, String password, Role role , String fonction) {
		return false;
		
	}

	public void setRole(Object role) {
		// TODO Auto-generated method stub
		
	}

	

	
}

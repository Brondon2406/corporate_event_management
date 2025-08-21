package model.entity;

import model.entity.enumeration.Role;

public class Users {

	private int id;
	private String name;
	private String email ;
	private String password ;
	private Role role;
	private String fonction;
	
	
	public Users() {}
	
	public Users(int id, String name, String email, String password, Role role, String fonction) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.fonction = fonction;
	}
	
	public Users(String name, String email, String password, Role role, String fonction) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.fonction = fonction;
	}

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
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
		return "Users [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", fonction=" + fonction + "]";
	}

	
}

package model.entity;

public class EtatThread {
	
	private int id;
	private String name;
	private String etat;
	
	public EtatThread(String name, String etat) {
		super();
		this.name = name;
		this.etat = etat;
	}
	public EtatThread(int id, String name, String etat) {
		super();
		this.id = id;
		this.name = name;
		this.etat = etat;
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
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
}

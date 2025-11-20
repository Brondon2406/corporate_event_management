package model.entity;

import java.time.LocalDate;

import model.dto.Userdto;

public class Planning {

	private int id;
	private String motif;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private Userdto tutorPlanning;
	
	public Planning (int id, String motif, LocalDate dateDebut, LocalDate dateFin, Userdto tutorPlanning  ) {
		super();
		this.id = id;
		this.motif = motif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		
		this.tutorPlanning = tutorPlanning;
		
	}
	
	public Planning (String motif, LocalDate dateDebut, LocalDate dateFin, Userdto tutorPlanning  ) {
		super();
		this.motif = motif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.tutorPlanning = tutorPlanning;
		
	}
	
	

	public Planning() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public Userdto getTutorPlanning() {
		return tutorPlanning;
	}

	public void setTutorPlanning(Userdto userdto) {
		this.tutorPlanning = userdto;
	}

	
	
	
	
	
	
	
}
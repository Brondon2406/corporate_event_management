package model.entity;

import java.time.LocalDate;

public class Planning {

	private int id;
	private String motif;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private int numberOfWeek;
	private String tutorPlanning;
	
	public Planning (int id, String motif, LocalDate dateDebut, LocalDate dateFin, int numberOfWeek, String tutorPlanning  ) {
		super();
		this.id = id;
		this.motif = motif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.numberOfWeek = numberOfWeek;
		this.tutorPlanning = tutorPlanning;
		
	}
	
	public Planning (String motif, LocalDate dateDebut, LocalDate dateFin, int numberOfWeek, String tutorPlanning  ) {
		super();
		this.motif = motif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.numberOfWeek = numberOfWeek;
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

	public int getNumberOfWeek() {
		return numberOfWeek;
	}

	public void setNumberOfWeek(int numberOfWeek) {
		this.numberOfWeek = numberOfWeek;
	}

	public String getTutorPlanning() {
		return tutorPlanning;
	}

	public void setTutorPlanning(String tutorPlanning) {
		this.tutorPlanning = tutorPlanning;
	}

	
	
	
	
	
	
	
}
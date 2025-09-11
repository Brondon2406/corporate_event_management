package model.dto;

import java.time.LocalDate;

public class Planningdto {

	private int id;
	private String motif;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private Userdto tutorPlanning;

	public Planningdto(String motif, LocalDate dateDebut, LocalDate dateFin, Userdto tutorPlanning) {
		super();
		this.motif = motif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.tutorPlanning = tutorPlanning;
	}

	public Planningdto(int id, String newMotif, LocalDate newDateDebut, LocalDate newDateFin,
			Userdto newTutorPlanning) {
		super();
		this.motif = newMotif;
		this.dateDebut = newDateDebut;
		this.dateFin = newDateFin;
		this.tutorPlanning = newTutorPlanning;
	}

	
	
	public Planningdto() {
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

	public void setDateDebut(LocalDate dateDebut2) {
		this.dateDebut = dateDebut2;
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

	public void setTutorPlanning(Userdto tutor) {
		this.tutorPlanning = tutor;
	}

	@Override
	public String toString() {
		return "Planningdto [id=" + id + ", motif=" + motif + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
				+ ", tutorPlanning=" + tutorPlanning + "]";
	}

}

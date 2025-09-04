package model.dto;

import java.time.LocalDate;

public class Planningdto {

	private int id;
	private String motif;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private String tutorPlanning;

	public Planningdto(int id, String motif, LocalDate dateDebut, LocalDate dateFin, String tutorPlanning) {
		super();
		this.id = id;
		this.motif = motif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.tutorPlanning = tutorPlanning;
	}

	public Planningdto(String motif, LocalDate dateDebut, LocalDate dateFin, String tutorPlanning) {
		super();
		this.motif = motif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.tutorPlanning = tutorPlanning;
	}

	public Planningdto() {
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

	public String getTutorPlanning() {
		return tutorPlanning;
	}

	public void setTutorPlanning(String tutorPlanning) {
		this.tutorPlanning = tutorPlanning;
	}

	@Override
	public String toString() {
		return "Planingdto [id=" + id + ", motif=" + motif + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
				+ ", numberOfWeek=" + ", tutorPlanning=" + tutorPlanning + "]";

	}

	public void setDateFin(Object object) {
		// TODO Auto-generated method stub

	}

	public void setDateDebut(Object object) {
		// TODO Auto-generated method stub

	}

}

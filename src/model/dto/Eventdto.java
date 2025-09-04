package model.dto;

import java.time.LocalDateTime;

import java.util.List;

import model.entity.EventRoom;

public class Eventdto {

	private int id;
	private String title;
	private LocalDateTime dateDebut;
	private LocalDateTime dateFin;
	private String typeEvent;
	private EventRoomdto eventRoom;
	private String format;
	private String moderator;
	private String tutor;
	private int idPlanning;
	private List<Userdto> users;
	private List<String> externalParticipantsEmails;

	public Eventdto(int id, String title, LocalDateTime dateDebut, LocalDateTime dateFin, String typeEvent,
			EventRoomdto eventRoom, String format, String moderator, String tutor,
			List<String> externalParticipantsEmails, List<Userdto> users, int idPlanning) {
		this.id = id;
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
		this.format = format;
		this.moderator = moderator;
		this.idPlanning = idPlanning;
		this.tutor = tutor;
		this.users = users;
		this.externalParticipantsEmails = externalParticipantsEmails;
	}

	

	public Eventdto(String title, LocalDateTime dateDebut, LocalDateTime dateFin, String typeEvent,
			EventRoomdto eventRoom, List<Userdto> users, String moderator, String tutor, int idPlanning) {
		super();
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
		this.users = users;
		this.format = moderator;
		this.tutor = tutor;
		this.idPlanning = idPlanning;

	}
	
	public Eventdto(int id, String string, LocalDateTime localDateTime, LocalDateTime localDateTime2, String string2,
			String string3, String string4, String string5, EventRoom eventRoom2) {
	}

	public Eventdto(String title2, LocalDateTime dateDebut2, LocalDateTime dateFin2, String typeEvent2,
			EventRoom eventroom2, List<Userdto> users2, Userdto moderator2, Userdto tutor2) {
	}

	public Eventdto(int id, String newTitle, LocalDateTime newDateDebut, LocalDateTime newDateFin, String newTypeEvent,
			EventRoomdto newRoom, String newFormat, String email, String email2, int i, List<String> newExternal,
			List<Userdto> newUsers) {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDateTime dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDateTime getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDateTime dateFin) {
		this.dateFin = dateFin;
	}

	public String getTypeEvent() {
		return typeEvent;
	}

	public void setTypeEvent(String typeEvent) {
		this.typeEvent = typeEvent;
	}

	public EventRoomdto getEventRoom() {
		return eventRoom;
	}

	public void setEventRoom(EventRoomdto neweventRoom) {
		this.eventRoom = neweventRoom;
	}

	public List<Userdto> getUsers() {
		return users;
	}

	public void setUsers(List<Userdto> users) {
		this.users = users;
	}

	public List<String> getExternalParticipantsEmails() {
		return externalParticipantsEmails;
	}

	public void setExternalParticipantsEmails(List<String> externalParticipantsEmails) {
		this.externalParticipantsEmails = externalParticipantsEmails;
	}

	public String getModerator() {
		return moderator;
	}

	public void setModerator(String moderator) {
		this.moderator = moderator;
	}

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getIdPlanning() {
		return idPlanning;
	}

	public void setIdPlanning(int idPlanning) {
		this.idPlanning = idPlanning;
	}

	@Override
	public String toString() {
		return "Eventdto [id=" + id + ", title=" + title + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
				+ ", typeEvent=" + typeEvent + ", eventRoom=" + eventRoom + ", format=" + format + ", moderator="
				+ moderator + ", tutor=" + tutor + ", idPlanning=" + idPlanning + ", users=" + users
				+ ", externalParticipantsEmails=" + externalParticipantsEmails + "]";
	}

}

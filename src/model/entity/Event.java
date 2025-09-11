package model.entity;

import java.time.LocalDateTime;
import java.util.List;

import model.entity.enumeration.TypeEvent;
import model.dto.EventRoomdto;
import model.dto.Planningdto;
import model.dto.Userdto;
import model.entity.enumeration.Format;
import model.entity.enumeration.StatusEvents;

public class Event {

	private int id;
	private String title;
	private LocalDateTime dateDebut;
	private LocalDateTime dateFin;
	private TypeEvent typeEvent;
	private Format format;
	private EventRoomdto eventRoom;
	private StatusEvents status;
	private String tutor;
	private String moderator;
	private Planningdto idPlanning;
	private List<Userdto> users;
	private List<String> externalParticipantsEmails;

	public Event(int id, String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent,
			Format format, EventRoomdto eventRoom, StatusEvents status, String tutor,
			String moderator, List<Userdto> users, List<String> externalParticipantsEmails, Planningdto idPlanning) {
		super();
		this.id = id;
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.format = format;
		this.eventRoom = eventRoom;
		this.idPlanning = idPlanning;
		this.status = status;
		this.tutor = tutor;
		this.moderator = moderator;
		this.users = users;
		this.externalParticipantsEmails = externalParticipantsEmails;
	}

	public Event(String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent, Format format,
			EventRoomdto eventRoom, Planningdto idPlanning, StatusEvents status, String tutor, String moderator,
			List<Userdto> users, List<String> externalParticipantsEmails) {
		super();
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.format = format;
		this.eventRoom = eventRoom;
		this.idPlanning = idPlanning;
		this.status = status;
		this.tutor = tutor;
		this.moderator = moderator;
		this.users = users;
		this.externalParticipantsEmails = externalParticipantsEmails;
	}

	public Event() {
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

	public TypeEvent getTypeEvent() {
		return typeEvent;
	}

	public void setTypeEvent(TypeEvent typeEvent) {
		this.typeEvent = typeEvent;
	}

	public EventRoomdto getEventRoom() {
		return eventRoom;
	}

	public void setEventRoom(EventRoomdto eventRoom) {
		this.eventRoom = eventRoom;
	}

	public Planningdto getIdPlanning() {
		return idPlanning;
	}

	public void setIdPlanning(Planningdto idPlanning) {
		this.idPlanning = idPlanning;
	}

	public StatusEvents getStatus() {
		return status;
	}

	public void setStatus(StatusEvents status) {
		this.status = status;
	}

	public List<Userdto> getUsers() {
		return users;
	}

	public void setUsers(List<Userdto> users) {
		this.users = users;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
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

	public void setTutor(String string) {
		this.tutor = string;
	}

	public List<String> getExternalParticipantsEmails() {
		return externalParticipantsEmails;
	}

	public void setExternalParticipantsEmails(List<String> externalParticipantsEmails) {
		this.externalParticipantsEmails = externalParticipantsEmails;
	}

}

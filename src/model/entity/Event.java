package model.entity;

import java.time.LocalDateTime;
import java.util.List;

import model.entity.enumeration.TypeEvent;
import model.dto.Userdto;
import model.entity.enumeration.Format;

public class Event {

	private int id;
	private String title;
	private LocalDateTime dateDebut;
	private LocalDateTime dateFin;
	private TypeEvent typeEvent;
	private EventRoom eventRoom;
	private Format format;
	private String moderator;
	private String tutor;
	private List<Userdto> users;
	private List<String> externalParticipantsEmails;

	public Event(int id, String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent,
			EventRoom eventRoom, Format format,List<String> externalParticipantsEmails, String moderator, String tutor, List<Userdto> users) {
		this.id = id;
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
		this.format = format;
		this.moderator = moderator;
		this.tutor = tutor;
		this.users = users;
		this.externalParticipantsEmails = externalParticipantsEmails;
	}

	public Event(String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent,
			EventRoom eventRoom, Format format, String moderator, String tutor, List<Userdto> users, List<String> externalParticipantsEmails) {
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
		this.format = format;
		this.moderator = moderator;
		this.tutor = tutor;
		this.users = users;
		this.externalParticipantsEmails = externalParticipantsEmails;
	}

	
	public Event(String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent, EventRoom eventRoom,
			List<Userdto> users, Format format, String moderator, String tutor) {
		super();
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
		this.format = format;
		this.users = users;

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

	public EventRoom getEventRoom() {
		return eventRoom;
	}

	public void setEventRoom(EventRoom eventRoom) {
		this.eventRoom = eventRoom;
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

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}
	
	public List<String> getExternalParticipantsEmails() {
		return externalParticipantsEmails;
	}

	public void setExternalParticipantsEmails(List<String> externalParticipantsEmails) {
		this.externalParticipantsEmails = externalParticipantsEmails;
	}




}

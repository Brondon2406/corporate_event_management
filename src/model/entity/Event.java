package model.entity;

import java.time.LocalDateTime;
import java.util.List;

import model.entity.enumeration.TypeEvent;

public class Event {

	private int id;
	private String title;
	private LocalDateTime dateDebut;
	private LocalDateTime dateFin;
	private TypeEvent typeEvent;
	private EventRoom eventRoom;
	private List<Users> users;

	public Event(int id, String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent,
			EventRoom eventRoom) {
		this.id = id;
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
	}

	public Event(String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent,
			EventRoom eventRoom) {
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
	}

	public Event(String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent, EventRoom eventRoom,
			List<Users> users) {
		super();
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
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

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
}

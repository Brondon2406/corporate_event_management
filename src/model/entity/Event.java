package model.entity;

import java.time.LocalDateTime;
import java.util.List;

import model.entity.enumeration.TypeEvent;

public class Event {

	private int id ;
	private String title ;
	private LocalDateTime dateDebut ;
	private LocalDateTime dateFin ;
	private TypeEvent typeEvent;
	private EventRoom evenRoom;
	private List<Users> users;
	
	public Event(int id, String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent,
			EventRoom evenRoom) {
		this.id = id;
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.evenRoom = evenRoom;
	}
	public Event(String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent,
			EventRoom evenRoom) {
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.evenRoom = evenRoom;
	}
	
	public Event(String title, LocalDateTime dateDebut, LocalDateTime dateFin, TypeEvent typeEvent, EventRoom evenRoom,
			List<Users> users) {
		super();
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.evenRoom = evenRoom;
		this.users = users;
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
	public EventRoom getEvenRoom() {
		return evenRoom;
	}
	public void setEvenRoom(EventRoom evenRoom) {
		this.evenRoom = evenRoom;
	}
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
}

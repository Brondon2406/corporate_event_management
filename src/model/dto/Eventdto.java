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
	private EventRoom eventRoom;
	private List<Userdto> users;

	public Eventdto(int id, String title, LocalDateTime dateDebut, LocalDateTime dateFin, String typeEvent,
			EventRoom eventRoom) {
		this.id = id;
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
	}

	public Eventdto(String title, LocalDateTime dateDebut, LocalDateTime dateFin, String typeEvent, EventRoom eventRoom,
			List<Userdto> users) {
		super();
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.eventRoom = eventRoom;
		this.users = users;
	}

	public Eventdto() {
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
}

package model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class Eventdto {
	
	private int id ;
	private String title ;
	private LocalDateTime dateDebut ;
	private LocalDateTime dateFin ;
	private String typeEvent;
	private EventRoomdto evenRoom;
	private List<Userdto> users;
	
	public Eventdto(int id, String title, LocalDateTime dateDebut, LocalDateTime dateFin, String typeEvent,
			EventRoomdto evenRoom) {
		this.id = id;
		this.title = title;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.typeEvent = typeEvent;
		this.evenRoom = evenRoom;
	}

	public Eventdto(String title, LocalDateTime dateDebut, LocalDateTime dateFin, String typeEvent,
			EventRoomdto evenRoom, List<Userdto> users) {
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

	public String getTypeEvent() {
		return typeEvent;
	}

	public void setTypeEvent(String typeEvent) {
		this.typeEvent = typeEvent;
	}

	public EventRoomdto getEvenRoom() {
		return evenRoom;
	}

	public void setEvenRoom(EventRoomdto evenRoom) {
		this.evenRoom = evenRoom;
	}

	public List<Userdto> getUsers() {
		return users;
	}

	public void setUsers(List<Userdto> users) {
		this.users = users;
	}
}

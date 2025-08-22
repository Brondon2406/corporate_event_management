package model.dto;

import java.util.List;

public class Planingdto {
	
	private int id;
	private int numberOfWeek;
	private List<Eventdto> events;
	
	public Planingdto(int id, int numberOfWeek, List<Eventdto> events) {
		this.id = id;
		this.numberOfWeek = numberOfWeek;
		this.events = events;
	}
	
	public Planingdto(int numberOfWeek, List<Eventdto> events) {
		this.numberOfWeek = numberOfWeek;
		this.events = events;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumberOfWeek() {
		return numberOfWeek;
	}
	public void setNumberOfWeek(int numberOfWeek) {
		this.numberOfWeek = numberOfWeek;
	}
	public List<Eventdto> getEvents() {
		return events;
	}
	public void setEvents(List<Eventdto> events) {
		this.events = events;
	}
}

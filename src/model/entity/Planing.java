package model.entity;

import java.util.List;

public class Planing {

	private int id;
	private int numberOfWeek;
	private List<Event> events;
	
	public Planing(int id, int numberOfWeek, List<Event> events) {
		this.id = id;
		this.numberOfWeek = numberOfWeek;
		this.events = events;
	}

	public Planing(int numberOfWeek, List<Event> events) {
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

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
}

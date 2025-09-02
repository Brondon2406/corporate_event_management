package model.dto;

public class EventRoomdto {

	private int id;
	private String name;
	private int capacity;
	private boolean active;

	public EventRoomdto() {
	}

	public EventRoomdto(int id, String name, int capacity, boolean active) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "EventRoomdto [id=" + id + ", name=" + name + ", capacity=" + capacity + ", active=" + active + "]";
	}
}

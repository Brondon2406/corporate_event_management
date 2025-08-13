package model.entity;

import java.time.LocalDateTime;

public class LogEvent {
	
	private LocalDateTime date;
	private String action;
	private String description;
	
	public LogEvent(LocalDateTime date, String action, String description) {
		this.date = date;
		this.action = action;
		this.description = description;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "LogEvent [date=" + date + ", action=" + action + ", description=" + description + "]";
	}
}

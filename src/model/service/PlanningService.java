package model.service;

import java.time.LocalDate;

import java.util.List;

import model.dto.Planningdto;
import model.entity.Planning;

public interface PlanningService {
	
	public Planningdto createPlanning(Planning planning);

	public boolean updatePlanning(Planningdto planning);

	public boolean deletePlanning(int planningId);

	public Planningdto getPlanningById(int planningId);

	public List<Planningdto> getEventsByPeriod(LocalDate debut, LocalDate fin);

	public List<Planningdto> getAllPlannings();

}

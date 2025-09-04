package model.service;

import model.dto.Planningdto;

public interface PlanningService {
	
	public Planningdto createPlanning(Planningdto planning);

	public boolean updatePlanning(Planningdto planning);

	public boolean deletePlanning(int planningId);

	public Planningdto getPlanningById(int planningId);

}

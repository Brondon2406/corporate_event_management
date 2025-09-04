package model.service;

import model.dto.Planningdto;
import model.entity.Planning;

public interface PlanningService {
	
	public Planningdto createPlanning(Planning planning);

	public boolean updatePlanning(Planningdto planning);

	public boolean deletePlanning(int planningId);

	public Planningdto getPlanningById(int planningId);

}

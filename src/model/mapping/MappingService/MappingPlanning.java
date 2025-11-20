package model.mapping.MappingService;

import model.dto.Planningdto;
import model.entity.Planning;

public interface MappingPlanning {
	
	Planningdto convertPlanningToPlanningdto (Planning planning);
	Planning convertPlanningdtoToPlanning (Planningdto planningdto);

}

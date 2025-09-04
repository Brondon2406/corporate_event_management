package controller;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Planningdto;
import model.entity.Planning;
import model.mapping.MappingService.MappingPlanning;
import model.mapping.MappingServiceImpl.MappingPlanningImpl;
import model.service.PlanningService;
import model.service.implementation.PlanningServiceImpl;

import util.constants.Constants;

public class PlanningController {
	private static final Logger LOG = LogManager.getLogger(PlanningController.class);
	private static PlanningService planningService = new PlanningServiceImpl();
	private MappingPlanning mapper = new MappingPlanningImpl();

	public boolean planningCreatController(Planningdto planningdto) {
		Planning planning = mapper.convertPlanningdtoToPlanning(planningdto);

		if (planning == null) {
			LOG.error(Constants.EMPTY_PLANNING_DTO);
			return false;
		}

		Planningdto dto = planningService.createPlanning(planning);

		if (dto != null) {
			LOG.info("Planning créé avec succès !");
			return true;
		} else {
			LOG.error(Constants.ERROR_DURING_PLANNING_INSERTION);
			return false;
		}
	}

	public void updatePlanningController(Planningdto planningDTO, String newMotif, LocalDate newdateDebut,
			LocalDate newdateFin) {
		planningDTO.setMotif(newMotif);
		planningDTO.setDateDebut(newdateDebut);
		planningDTO.setDateFin(newdateFin);

		boolean success = planningService.updatePlanning(planningDTO);
		if (success) {
			LOG.info("planning mis à jour avec succès !");
		} else {
			LOG.error(Constants.ERROR_UPDATE_PLANNING);
		}
	}

	public boolean planningDeleteController(int planningId) {
		return planningService.deletePlanning(planningId);
	}

	public Planningdto getEventByIdController(int planningId) {
		return planningService.getPlanningById(planningId);
	}
}

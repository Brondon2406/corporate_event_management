package controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Planningdto;
import model.dto.Userdto;
import model.entity.Planning;
import model.mapping.MappingService.MappingPlanning;
import model.mapping.MappingServiceImpl.MappingPlanningImpl;
import model.service.PlanningService;
import model.service.implementation.PlanningServiceImpl;

import util.constants.Constants;

/**
 * Controller responsible for managing plannings.
 * It acts as an intermediary between the {@link views} layer (UI)
 * and the {@link model.service.PlanningService} layer.
 * 
 * <p>
 * This controller provides:
 * <ul>
 *   <li>Planning creation</li>
 *   <li>Planning update</li>
 *   <li>Planning deletion</li>
 *   <li>Retrieval of a planning by ID</li>
 *   <li>Retrieval of plannings by period</li>
 *   <li>Retrieval of all plannings</li>
 * </ul>
 * </p>
 * 
 * @author Severin Kengne
 */
public class PlanningController {
	private static final Logger LOG = LogManager.getLogger(PlanningController.class);
	private static PlanningService planningService = new PlanningServiceImpl();
	private MappingPlanning mapper = new MappingPlanningImpl();
	
	/**
     * Creates a new planning in the database.
     *
     * @param planningdto DTO containing the planning details to create
     * @return true if the planning is successfully created, false otherwise
     */
	public boolean createPlanning(Planningdto planningdto) {
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
	
	/**
     * Updates an existing planning.
     *
     * @param planningDTO Planningdto object to update
     * @param id Planning identifier
     * @param newMotif New reason (motif) of the planning
     * @param newDateDebut New start date
     * @param newDateFin New end date
     * @param currentUser User performing the update
     * @return true if the update succeeds, false otherwise
     */	
	public boolean updatePlanningController(Planningdto planningDTO, int id, String newMotif, LocalDate newDateDebut,
			LocalDate newDateFin, Userdto currentUser) {
		planningDTO.setId(id);
		planningDTO.setMotif(newMotif);
		planningDTO.setDateDebut(newDateDebut);
		planningDTO.setDateFin(newDateFin);
		planningDTO.setTutorPlanning(currentUser);

		boolean success = planningService.updatePlanning(planningDTO);
		if (success) {
			LOG.info("planning mis à jour avec succès !");
		} else {
			LOG.error(Constants.ERROR_UPDATE_PLANNING);
		}
		return success;
	}
	
	/**
     * Deletes a planning by its ID.
     *
     * @param planningId ID of the planning to delete
     * @return true if the deletion succeeds, false otherwise
     */
	public boolean planningDeleteController(int planningId) {
		return planningService.deletePlanning(planningId);
	}
	
	/**
     * Retrieves a planning by its identifier.
     *
     * @param planningId Planning identifier
     * @return Planningdto if found, otherwise null
     */
	public Planningdto getEventByIdController(int planningId) {
		return planningService.getPlanningById(planningId);
	}
	
	/**
     * Retrieves plannings within a date range.
     *
     * @param dateDebut Start date of the period
     * @param dateFin End date of the period
     * @return List of matching plannings
     */
	public List<Planningdto> getEventsByPeriod(LocalDate dateDebut, LocalDate dateFin) {
		return planningService.getEventsByPeriod(dateDebut, dateFin);
	}
	
	/**
     * Retrieves all available plannings.
     *
     * @return List of plannings
     */
	public List<Planningdto> getAllPlannings() {
		return planningService.getAllPlannings();
	}

}

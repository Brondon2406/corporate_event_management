package model.mapping.MappingServiceImpl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.dto.Planningdto;
import model.entity.LogEvent;
import model.entity.Planning;
import model.mapping.MappingService.MappingPlanning;
import util.constants.Constants;

public class MappingPlanningImpl implements MappingPlanning {
	private static final Logger LOG = LogManager.getLogger(MappingEventImpl.class);
	private LogEvent logEvent;

	public MappingPlanningImpl(LogEvent logEvent) {
		this.logEvent = logEvent;
	}

	public MappingPlanningImpl() {
		this.logEvent = new LogEvent();
	}

	@Override
	public Planningdto convertPlanningToPlanningdto(Planning planning) {
		if (planning == null) {
			logEvent.setAction("convert Planning to Planningdto");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(Constants.PLANNING_IS_EMPTY);
			LOG.info(logEvent.toString());
			return null;
		}
			try {
				Planningdto planningdto = new Planningdto();
				planningdto.setDateDebut(planning.getDateDebut());
				planningdto.setDateFin(planning.getDateFin());
				planningdto.setId(planning.getId());
				planningdto.setMotif(planning.getMotif());
				planningdto.setTutorPlanning(planning.getTutorPlanning());
				return planningdto;

			} catch (Exception e) {
				logEvent.setAction("convert Planning to Planningdto");
				logEvent.setDate(LocalDateTime.now());
				logEvent.setDescription(
						String.format(Constants.MAPPING_USER_DTO_ERROR, planning.toString(), e.getMessage()));
				LOG.info(logEvent);
			}
			return null;
		}

	@Override
	public Planning convertPlanningdtoToPlanning(Planningdto planningdto) {
		if (planningdto == null) {
			logEvent.setAction("convert Planningdto to Planning");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(Constants.PLANNING_DTO_IS_EMPTY);
			LOG.info(logEvent.toString());
			return null;
		}

		try {
			Planning planning = new Planning();
			planning.setDateDebut(planningdto.getDateDebut());
			planning.setDateFin(planningdto.getDateFin());
			planning.setId(planningdto.getId());
			planning.setMotif(planningdto.getMotif());
			planning.setTutorPlanning(planningdto.getTutorPlanning());
			return planning;
		} catch (Exception e) {
			logEvent.setAction("convert Planningdto to Planning");
			logEvent.setDate(LocalDateTime.now());
			logEvent.setDescription(
					String.format(Constants.MAPPING_PLANNING_ERROR, planningdto.toString(), e.getMessage()));
			LOG.info(logEvent.toString());
			return null;

		}

	}
}

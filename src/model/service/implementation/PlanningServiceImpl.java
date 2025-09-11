package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.UserController;
import model.database.DatabaseConnection;
import model.dto.Planningdto;
import model.dto.Userdto;
import model.entity.Planning;
import model.service.PlanningService;
import model.service.sql.Query;
import util.constants.Constants;

public class PlanningServiceImpl implements PlanningService {
	private static final Logger LOG = LogManager.getLogger(PlanningServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();

	@Override
	public Planningdto createPlanning(Planning planning) {
		String query = Query.CREATE_PLANNING;

		try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, planning.getMotif());
			ps.setObject(2, planning.getDateDebut());
			ps.setObject(3, planning.getDateFin());
			ps.setInt(4, planning.getTutorPlanning().getId());

			int result = ps.executeUpdate();
			if (result <= 0) {
				LOG.error(Constants.ERROR_DURING_PLANNING_INSERTION);
				return null;
			}
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					planning.setId(rs.getInt(1));
				}
			}

			Planningdto planningDTO = new Planningdto();
			planningDTO.setDateDebut(planning.getDateDebut());
			planningDTO.setDateFin(planning.getDateFin());
			planningDTO.setId(planning.getId());
			planningDTO.setMotif(planning.getMotif());
			planningDTO.setTutorPlanning(planning.getTutorPlanning());

			LOG.info("Planning créé avec succès : {}", planning.getMotif());
			return planningDTO;

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_CREATE_PLANNING + " Planning=" + planning, e);
			return null;
		}
	}

	@Override
	public boolean updatePlanning(Planningdto planning) {
		String query = Query.UPDATE_PLANNING;

		if (planning.getTutorPlanning() == null) {
			planning.setTutorPlanning(UserController.getCurrentUser());
			if (planning.getTutorPlanning() == null) {
				LOG.error("Impossible de mettre à jour le planning : aucun utilisateur connecté pour être tuteur !");
				return false;
			}
		}

		try (PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setString(1, planning.getMotif());
			ps.setObject(2, planning.getDateDebut());
			ps.setObject(3, planning.getDateFin());
			ps.setInt(4, planning.getTutorPlanning().getId());
			ps.setInt(5, planning.getId());

			int rows = ps.executeUpdate();
			if (rows > 0) {
				LOG.info("Planning mis à jour avec succès : {}", planning.getMotif());
				return true;
			} else {
				LOG.warn("Aucun planning trouvé à mettre à jour pour l'ID {}", planning.getId());
				return false;
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_PLANNING + " Planning=" + planning, e);
			return false;
		}
	}

	@Override
	public boolean deletePlanning(int planningId) {

		String query1 = Query.DELETE_EVENTS_IN_PLANNING;
		try {
			PreparedStatement ps = connection.prepareStatement(query1);
			ps.setInt(1, planningId);
			ps.executeUpdate();
		} catch (SQLException e) {
			LOG.error("Erreur lors de la suppression des événements liés au planning", e);
		}

		String query = Query.DELETE_PLANNING;
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, planningId);
			int rows = ps.executeUpdate();

			if (rows > 0) {
				LOG.info("Utilisateur avec ID {} supprimé avec succès", planningId);
				return true;
			} else {
				LOG.warn(Constants.NO_PLANNING_FOUND, planningId);
				return false;
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_DELETE_PLANNING + " PlanningId=" + planningId, e);
			return false;
		}
	}

	@Override
	public Planningdto getPlanningById(int planningId) {
		String query = Query.GET_PLANNING_BY_ID;
		Planningdto planning = null;

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, planningId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					planning = new Planningdto();
					planning.setId(rs.getInt("id"));
					planning.setMotif(rs.getString("motif"));
					planning.setDateDebut(rs.getDate("date_debut").toLocalDate());
					planning.setDateFin(rs.getDate("date_fin").toLocalDate());
					int tutorId = rs.getInt("tutor_planning");
					Userdto tutor = new Userdto();
					tutor.setId(tutorId);

				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_PLANNING_BY_ID + " ID=" + planningId, e);
		}
		return planning;
	}

	@Override
	public List<Planningdto> getEventsByPeriod(LocalDate debut, LocalDate fin) {
		String query = Query.GET_PLANNINGS_BY_PERIOD;
		List<Planningdto> plannings = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setObject(1, debut);
			ps.setObject(2, fin);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Planningdto planning = new Planningdto();
					planning.setId(rs.getInt("id"));
					planning.setMotif(rs.getString("motif"));
					planning.setDateDebut(rs.getDate("date_debut").toLocalDate());
					planning.setDateFin(rs.getDate("date_fin").toLocalDate());
					int tutorId = rs.getInt("tutor_id");
					if (tutorId != 0) {
						Userdto tutor = new Userdto();
						tutor.setId(tutorId);
						tutor.setName(rs.getString("tutor_name"));
						tutor.setFirstName(rs.getString("tutor_first_name"));
						planning.setTutorPlanning(tutor);
					}

					plannings.add(planning);
				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_PLANNING_BY_PERIOD, e);
		}

		return plannings;
	}

	@Override
	public List<Planningdto> getAllPlannings() {
		String query = Query.GET_ALL_PLANNING;
		List<Planningdto> planningdto = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Planningdto planning = new Planningdto();
				planning.setId(rs.getInt("id"));
				planning.setMotif(rs.getString("motif"));
				planning.setDateDebut(rs.getDate("date_debut").toLocalDate());
				planning.setDateFin(rs.getDate("date_fin").toLocalDate());

				int tutorId = rs.getInt("tutor_planning");
				Userdto tutor = new Userdto();
				tutor.setId(tutorId);
				planning.setTutorPlanning(tutor);

				planningdto.add(planning);
			}

		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_ALL_PLANNING, e);
		}

		return planningdto;
	}

}

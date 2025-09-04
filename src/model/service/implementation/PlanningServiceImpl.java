package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.database.DatabaseConnection;
import model.dto.Planningdto;
import model.service.PlanningService;
import model.service.sql.Query;
import util.constants.Constants;

public class PlanningServiceImpl implements PlanningService {
	private static final Logger LOG = LogManager.getLogger(PlanningServiceImpl.class);
	Connection connection = DatabaseConnection.getInstance();

	@Override
	public Planningdto createPlanning(Planningdto planning) {
		String query = Query.CREATE_PLANNING;

		try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, planning.getMotif());
			ps.setObject(2, planning.getDateDebut());
			ps.setObject(3, planning.getDateFin());
			ps.setString(4, planning.getTutorPlanning());

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
			LOG.error(Constants.ERROR_CREATE_PLANNING+ " Planning=" + planning, e);
			return null;
		}
	}

	@Override
	public boolean updatePlanning(Planningdto planning) {
		String query = Query.UPDATE_PLANNING;
		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, planning.getMotif());
			ps.setObject(2, planning.getDateDebut());
			ps.setObject(3, planning.getDateFin());

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_UPDATE_PLANNING + " Planning=" + planning, e);
			return false;
		}
	}

	@Override
	public boolean deletePlanning(int planningId) {		
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
			LOG.error(Constants.ERROR_DELETE_PLANNING+ " PlanningId=" + planningId, e);
			return false;
		}
	}

	@Override
	public Planningdto getPlanningById(int planningId) {
		String query = Query.SELECT_PLANNING_BY_ID;
		Planningdto planning = null;

		try  {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, planningId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					planning = new Planningdto();
					planning.setId(rs.getInt("id"));
					planning.setMotif(rs.getString("motif"));
					planning.setDateDebut(rs.getObject("dateDebut"));					
					planning.setDateFin(rs.getObject("dateFin"));
					planning.setTutorPlanning(rs.getString("tutorPlanning"));

				}
			}
		} catch (SQLException e) {
			LOG.error(Constants.ERROR_GET_PLANNING_BY_ID + " ID=" + planningId, e);
		}

		return planning;
	}

}

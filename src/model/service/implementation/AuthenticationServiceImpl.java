package model.service.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.dto.Userdto;
import model.database.DatabaseConnection;
import model.entity.Users;
import model.service.AuthenticationService;
import model.service.sql.Query;
import util.Constants;

public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOG = LogManager.getLogger(AuthenticationServiceImpl.class);

    @Override
    public Userdto registerUser(Users user) {
        String query = Query.CREATE_USER;

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().name());
            ps.setString(5, user.getFonction());

            int result = ps.executeUpdate();
            if (result <= 0) {
                LOG.error(Constants.ERROR_DURING_USER_INSERTION);
                return null;
            }

           
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }

          
            Userdto userDTO = new Userdto();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setRole(user.getRole().name());
            userDTO.setFonction(user.getFonction());

            LOG.info("Utilisateur créé avec succès : {}", user.getEmail());
            return userDTO;

        } catch (SQLException e) {
            LOG.error(Constants.ERROR_CREATE_USER, e);
            return null;
        }
    }

    @Override
    public Userdto loginUser(Users user) {
        String query = Query.GET_USER;

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());

            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    Userdto userDTO = new Userdto();
                    userDTO.setId(result.getInt("id"));
                    userDTO.setName(result.getString("name"));
                    userDTO.setEmail(result.getString("email"));
                    userDTO.setRole(result.getString("role"));
                    userDTO.setFonction(result.getString("fonction"));

                    LOG.info("Connexion réussie pour l’utilisateur : {}", user.getEmail());
                    return userDTO;
                } else {
                    LOG.warn(Constants.ERROR_DURING_USER_SELECTION);
                    return null;
                }
            }

        } catch (SQLException e) {
            LOG.error(Constants.ERROR_GET_USER,  e.getMessage());
            return null;
        }
    }
}

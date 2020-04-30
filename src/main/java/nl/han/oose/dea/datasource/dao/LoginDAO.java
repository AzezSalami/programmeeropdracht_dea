package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;

import java.sql.*;

import nl.han.oose.dea.controllers.dto.LoginDTO;
import nl.han.oose.dea.datasource.datamapper.LoginDataMapper;
import nl.han.oose.dea.datasource.datamapper.UserDataMapper;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;

public class LoginDAO {

    private DatabaseConnection databaseConnection;
    private LoginDataMapper loginDataMapper;
    private UserDataMapper userDataMapper;
    Connection connection;

    @Inject
    public void setLoginDataMapper(LoginDataMapper loginDataMapper) {
        this.loginDataMapper = loginDataMapper;
    }
    @Inject
    public void setUserDataMapper(UserDataMapper userDataMapper) {
        this.userDataMapper = userDataMapper;
    }

    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) throws SQLException {
        this.databaseConnection = databaseConnection;
        makeConnection();
    }

    private void makeConnection() throws SQLException {
        connection = databaseConnection.getConnection();
    }

    public LoginDAO() {
    }

    public LoginDTO findUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users where username =?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return loginDataMapper.toDTO(resultSet);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    public LoginRespondeDTO findData(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users where username =?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return userDataMapper.toDTO(resultSet);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}

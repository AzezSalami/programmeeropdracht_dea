package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;
import nl.han.oose.dea.datasource.connection.DatabaseProperties;
import java.sql.*;
import nl.han.oose.dea.controllers.dto.LoginDTO;

import javax.inject.Inject;

public class LoginDAO {

    private DatabaseConnection databaseConnection;
    Connection connection;

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

            while (resultSet.next()) {
                return new LoginDTO(
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginRespondeDTO findData(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from users where username =?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new LoginRespondeDTO(
                        resultSet.getString("token"),
                        resultSet.getString("login_name")
                );
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

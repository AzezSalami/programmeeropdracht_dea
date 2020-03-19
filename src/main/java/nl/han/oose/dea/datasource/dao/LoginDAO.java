package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;
import nl.han.oose.dea.datasource.DatabaseProperties;
import java.sql.*;
import nl.han.oose.dea.controllers.dto.LoginDTO;

public class LoginDAO {

    private DatabaseProperties databaseProperties;

    public LoginDAO(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public LoginDTO findUser(String username) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("select * from users where username =?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new LoginDTO(
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginRespondeDTO findData(String username) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("select * from users where username =?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new LoginRespondeDTO(
                        resultSet.getString("token"),
                        resultSet.getString("login_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

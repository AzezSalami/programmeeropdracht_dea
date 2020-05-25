package nl.han.oose.dea.datasource.datamapper;

import nl.han.oose.dea.controller.dto.LoginRespondeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataMapper implements DataMapper<LoginRespondeDTO> {

    public LoginRespondeDTO mapResultSetToDTO(ResultSet resultSet) throws SQLException {
        LoginRespondeDTO loginRespondeDTO = new LoginRespondeDTO();

        while (resultSet.next()) {
            loginRespondeDTO = new LoginRespondeDTO(
                    resultSet.getString("token"),
                    resultSet.getString("login_name")
            );
        }
        return loginRespondeDTO;
    }
}

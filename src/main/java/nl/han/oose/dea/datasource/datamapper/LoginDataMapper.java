package nl.han.oose.dea.datasource.datamapper;

import nl.han.oose.dea.controller.dto.LoginDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDataMapper  implements DataMapper<LoginDTO>{

    public LoginDTO mapResultSetToDTO(ResultSet resultSet) throws SQLException {
        LoginDTO loginDTO = new LoginDTO();

        while (resultSet.next()) {
            loginDTO = new LoginDTO(
                    resultSet.getString("username"),
                    resultSet.getString("password")
            );
        }
        return loginDTO;
    }
}

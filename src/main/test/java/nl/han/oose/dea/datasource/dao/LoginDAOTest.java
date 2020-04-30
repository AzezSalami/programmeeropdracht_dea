package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.PlaylistController;
import nl.han.oose.dea.controllers.dto.LoginDTO;
import nl.han.oose.dea.controllers.dto.LoginRespondeDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginDAOTest {
    private LoginDAO sut;
    private DatabaseConnection mockedDatabaseConnection;

    @BeforeEach
    public void setup() throws SQLException {
        sut = new LoginDAO();
        this.mockedDatabaseConnection = mock(DatabaseConnection.class);
        this.sut.setDatabaseConnection(mockedDatabaseConnection);
    }

    @Test
    public void findUserTester() throws SQLException {
        // Arrange
        var user = "azezsalami";
        var password = "password";
        var token = "1234567";
        var login_name = "azez salami";
        var loginDTO = new LoginDTO(user,password);
        var loginRespondeDTO = new LoginRespondeDTO(token, login_name);
        // Act
        var result = sut.findUser(user);
        // Assert
        //verify(mockedDatabaseConnection).getConnection();
        assertEquals(loginDTO,result);

    }

    @Test
    public void findDataTester() throws SQLException {
        // Arrange
        var user = "azezsalami";
        var token = "1234567";
        var login_name = "azez salami";
        var loginRespondeDTO = new LoginRespondeDTO(token, login_name);
        // Act
        var result = sut.findData(user);
        // Assert
        verify(mockedDatabaseConnection).getConnection();

        assertEquals(loginRespondeDTO,result);
    }
}

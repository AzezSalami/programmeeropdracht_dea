package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controller.dto.LoginDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;
import nl.han.oose.dea.datasource.datamapper.LoginDataMapper;
import nl.han.oose.dea.datasource.datamapper.UserDataMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.InternalServerErrorException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginDAOTest {
    public static final String USERNAME = "azezsalami";
    private LoginDAO sut;
    private DatabaseConnection databaseConnection;
    private PreparedStatement statement;
    private LoginDataMapper loginDataMapper;
    private UserDataMapper userDataMapper;

    @BeforeEach
    public void setup() throws SQLException {
        sut = new LoginDAO();
        databaseConnection = mock(DatabaseConnection.class);
        Connection connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        loginDataMapper = mock(LoginDataMapper.class);
        userDataMapper = mock(UserDataMapper.class);
        sut.setDatabaseConnection(databaseConnection);
        sut.setLoginDataMapper(loginDataMapper);
        sut.setUserDataMapper(userDataMapper);
        when(databaseConnection.getConnection()).thenReturn(connection);

        sut.setDatabaseConnection(databaseConnection);
        when(connection.prepareStatement(any())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

    }

    @Test
    public void tesSetDatabaseConnectionCallsGetConnection() throws SQLException {
        // Act
        sut.findUser(USERNAME);
        // Assert
        verify(databaseConnection).getConnection();
    }

    @Test
    public void testFindUserCreateCorrectStatement() throws SQLException {
        // Arrange
        String username = "azezsalami";
        String password = "password";
        LoginDTO loginDTO = new LoginDTO(username, password);
        // Act
        var result = sut.findUser(USERNAME);
        // Assert
        verify(loginDataMapper).mapResultSetToDTO(any());
        verify(statement).setString(1, USERNAME);
    }

    @Test
    public void testFindDataCreateCorrectStatement() throws SQLException {
        // Arrange

        // Act
        sut.findData(USERNAME);
        // Assert
        verify(userDataMapper).mapResultSetToDTO(any());
        verify(statement).setString(1, USERNAME);
    }

    @Test
    public void testFindUserThrowsError() throws SQLException {
        // Arrange

        // Act
        sut.findUser(USERNAME);
        // Assert
        verify(statement).setString(1, USERNAME);
        doThrow(SQLException.class).when(loginDataMapper).mapResultSetToDTO(any());

        // Run the tes
        // Verify the results
        Assertions.assertThrows(InternalServerErrorException.class, () -> sut.findUser(USERNAME));
    }

    @Test
    public void testFindDataThrowsError() throws SQLException {
        // Arrange

        // Act
        sut.findData(USERNAME);
        // Assert
        verify(statement).setString(1, USERNAME);
        doThrow(SQLException.class).when(userDataMapper).mapResultSetToDTO(any());

        // Run the tes
        // Verify the results
        Assertions.assertThrows(InternalServerErrorException.class, () -> sut.findData(USERNAME));
    }

}

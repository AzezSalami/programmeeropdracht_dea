package nl.han.oose.dea.datasource.datamapper;

import nl.han.oose.dea.controllers.dto.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginDataMapperTest {

    private LoginDataMapper sut;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        this.sut = new LoginDataMapper();
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void toDTOTest() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        // Act
        LoginDTO result = sut.toDTO(resultSet);

        // Assert
        assertEquals(LoginDTO.class, result.getClass());

    }

    @Test
    public void toDTOTestThrowException() throws SQLException {
        // Arrange
        when(resultSet.next()).thenThrow(SQLException.class);
        // Act

        // Assert
        assertThrows(SQLException.class, () -> {
            sut.toDTO(resultSet);
        });
    }
}

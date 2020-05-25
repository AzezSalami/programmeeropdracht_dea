package nl.han.oose.dea.datasource.datamapper;

import nl.han.oose.dea.controller.dto.LoginRespondeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDataMapperTest {

    private UserDataMapper sut;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        this.sut = new UserDataMapper();
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void toDTOTest() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        // Act
        LoginRespondeDTO result = sut.mapResultSetToDTO(resultSet);

        // Assert
        assertEquals(LoginRespondeDTO.class, result.getClass());

    }

    @Test
    public void toDTOTestThrowException() throws SQLException {
        // Arrange
        when(resultSet.next()).thenThrow(SQLException.class);
        // Act

        // Assert
        assertThrows(SQLException.class, () -> {
            sut.mapResultSetToDTO(resultSet);
        });
    }
}

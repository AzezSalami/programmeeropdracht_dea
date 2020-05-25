package nl.han.oose.dea.datasource.datamapper;

import nl.han.oose.dea.controller.dto.TrackDTO;
import nl.han.oose.dea.controller.dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TracksDataMapperTest {
    private TracksDataMapper sut;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        this.sut = new TracksDataMapper();
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void toDTOTest() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        // Act
        List<TrackDTO> result = sut.mapResultSetToDTO(resultSet);
        TracksDTO tracksDTO= new TracksDTO(result);

        // Assert
        assertEquals(TracksDTO.class, tracksDTO.getClass());
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

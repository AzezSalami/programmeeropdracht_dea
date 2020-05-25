package nl.han.oose.dea.datasource.datamapper;

import nl.han.oose.dea.controller.dto.PlaylistDTO;
import nl.han.oose.dea.controller.dto.PlaylistsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistsDataMapperTest {
    private PlaylistsDataMapper sut;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        this.sut = new PlaylistsDataMapper();
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void toDTOTest() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        // Act
        List<PlaylistDTO> result = sut.mapResultSetToDTO(resultSet);
        PlaylistsDTO playlistsDTO= new PlaylistsDTO(result,775);

        // Assert
        assertEquals(PlaylistsDTO.class, playlistsDTO.getClass());
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

package nl.han.oose.dea.datasource.datamapper;

import nl.han.oose.dea.controller.dto.TrackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TracksDataMapper implements DataMapper<List<TrackDTO>> {
    @Override
    public List<TrackDTO> mapResultSetToDTO(ResultSet resultSet) throws SQLException {
        List<TrackDTO> tracks = new ArrayList<>();
        while (resultSet.next()) {
            tracks.add(new TrackDTO(
                    resultSet.getInt("trackId"),
                    resultSet.getString("title"),
                    resultSet.getString("performer"),
                    resultSet.getInt("duration"),
                    resultSet.getString("album"),
                    resultSet.getInt("playcount"),
                    resultSet.getString("publicationDate"),
                    resultSet.getBoolean("offlineAvailable")));
        }
        return tracks;
    }
}

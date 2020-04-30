package nl.han.oose.dea.datasource.datamapper;

import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.controllers.dto.PlaylistsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlaylistsDataMapper implements DataMapper<List<PlaylistDTO>>{
    @Override
    public List<PlaylistDTO> toDTO(ResultSet resultSet) throws SQLException {
        List<PlaylistDTO> playlists = new ArrayList<>();
        while (resultSet.next()) {
            playlists.add(new PlaylistDTO(
                    resultSet.getInt("playlistId"),
                    resultSet.getString("name"),
                    resultSet.getBoolean("owner"),
                    new ArrayList<>()));
        }
        return playlists;
    }
}

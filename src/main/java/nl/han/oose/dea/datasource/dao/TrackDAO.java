package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controller.dto.TrackDTO;
import nl.han.oose.dea.controller.dto.TracksDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;
import nl.han.oose.dea.datasource.datamapper.TracksDataMapper;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.sql.*;
import java.util.List;

public class TrackDAO {
    private DatabaseConnection databaseConnection;
    private TracksDataMapper tracksDataMapper;
    Connection connection;

    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) throws SQLException {
        this.databaseConnection = databaseConnection;
        makeConnection();
    }

    @Inject
    private void setTracksDataMapper(TracksDataMapper tracksDataMapper) {
        this.tracksDataMapper = tracksDataMapper;
    }

    private void makeConnection() throws SQLException {
        connection = databaseConnection.getConnection();
    }

    public TrackDAO() {
    }

    public List<TrackDTO> getAllTracksNotInPlaylist(String token, int playlistId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from track where trackId not in (select trackId from tracks_in_playlist " +
                            "where playlistId  in (select playlistId from playlist where token = ? and playlistId =?))");
            statement.setString(1, token);
            statement.setInt(2, playlistId);
            ResultSet resultSet = statement.executeQuery();
            return tracksDataMapper.mapResultSetToDTO(resultSet);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    public TracksDTO getTracksDTO(String token, int playlistId){
        return new TracksDTO(getAllTracksNotInPlaylist(token,playlistId));
    }

}

package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {
    private DatabaseConnection databaseConnection;
    Connection connection;

    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) throws SQLException {
        this.databaseConnection = databaseConnection;
        makeConnection();
    }

    private void makeConnection() throws SQLException {
        connection = databaseConnection.getConnection();
    }

    public TrackDAO() {
    }

    public List<TrackDTO> getAllTracksNotInPlaylist(String token, int playlistId) {
        List<TrackDTO> trackDTOS = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from track where trackId not in (select trackId from tracks_in_playlist " +
                            "where playlistId  in (select playlistId from playlist where token = ? and playlistId =?))");
            statement.setString(1, token);
            statement.setInt(2, playlistId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trackDTOS.add(new TrackDTO(
                        resultSet.getInt("trackId"),
                        resultSet.getString("title"),
                        resultSet.getString("performer"),
                        resultSet.getInt("duration"),
                        resultSet.getString("album"),
                        resultSet.getInt("playcount"),
                        resultSet.getString("publicationDate"),
                        resultSet.getBoolean("offlineAvailable")
                ));
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackDTOS;
    }

}

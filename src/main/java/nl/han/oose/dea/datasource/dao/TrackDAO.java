package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.datasource.DatabaseProperties;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {

    private DatabaseProperties databaseProperties;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public TrackDAO() {
    }

    public List<TrackDTO> getAllTracksNotInPlaylist(String token, int playlistId) {
        List<TrackDTO> trackDTOS = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
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
                trackDTOS.forEach(i -> System.out.println(i.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackDTOS;
    }

    public List<TrackDTO> getAllTracksInPlaylist(String token, int playlistId) {
        List<TrackDTO> trackDTOS = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement(
                    "select * from track where trackId in (select trackId from tracks_in_playlist " +
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
                trackDTOS.forEach(i -> System.out.println(i.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackDTOS;
    }

    public void deleteTrackFromPlaylist(String token, int playlistId, int trackId) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM tracks_in_playlist " +
                            "where trackId = ?" +
                            "AND playlistId  in (select playlistId from playlist where token = ? and playlistId =?)");
            statement.setInt(1, trackId);
            statement.setString(2, token);
            statement.setInt(3, playlistId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTrackToPlaylist(String token, int playlistId, TrackDTO trackDTO) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement selectStatement = connection.prepareStatement("select *  from playlist where token = ?");
            PreparedStatement statement = connection.prepareStatement("insert into tracks_in_playlist values (?,?)");
            statement.setString(1,token);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()){
                statement.setInt(1,playlistId);
                statement.setInt(2,trackDTO.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

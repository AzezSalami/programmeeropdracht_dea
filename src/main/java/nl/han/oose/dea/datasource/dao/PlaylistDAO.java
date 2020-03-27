package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;
import nl.han.oose.dea.datasource.connection.DatabaseProperties;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
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

    public PlaylistDAO() {
    }

    public List<PlaylistDTO> getPlaylists(String token){
        List <PlaylistDTO> playlistDTO = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("select * from playlist where token =?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                playlistDTO.add( new PlaylistDTO(
                            resultSet.getInt("playlistId"),
                            resultSet.getString("name"),
                            resultSet.getBoolean("owner"),
                            new ArrayList<TrackDTO>()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlistDTO;
    }

    public void deletePlaylists(String token, int id){

        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM playlist WHERE token = ? AND playlistId = ?");
            statement.setString(1, token);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlaylists(String token, PlaylistDTO playlistDTO){

        try{
            PreparedStatement selectStatement = connection.prepareStatement("select max(playlistId) as \"playlistId\" from playlist where token =?");
            PreparedStatement statement = connection.prepareStatement("insert into playlist values (?,?,?,?)");
            selectStatement.setString(1, token);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                if (playlistDTO.getId() == -1 & !playlistDTO.isOwner()) {
                    statement.setInt(1, resultSet.getInt("playlistId") + 1);
                    statement.setString(2, playlistDTO.getName());
                    statement.setBoolean(3, true);
                    statement.setString(4, token);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void editPlaylists(String token, int id, PlaylistDTO playlistDTO){

        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE playlist SET name = ? WHERE token = ? AND playlistId = ?");
            statement.setString(1,playlistDTO.getName());
            statement.setString(2, token);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTrackToPlaylist(String token, int playlistId, TrackDTO trackDTO) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement("select * from playlist where token = ?");
            PreparedStatement statement = connection.prepareStatement("insert into tracks_in_playlist values (?,?)");
            selectStatement.setString(1,token);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()){
                statement.setInt(1,playlistId);
                statement.setInt(2,trackDTO.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTrackFromPlaylist(String token, int playlistId, int trackId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM tracks_in_playlist where trackId = ? AND playlistId  in (select playlistId from playlist where token = ? AND playlistId = ?)");
            statement.setInt(1, trackId);
            statement.setString(2, token);
            statement.setInt(3, playlistId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TrackDTO> getAllTracksInPlaylist(String token, int id) {
        List<TrackDTO> trackDTOS = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from track where trackId in (select trackId from tracks_in_playlist " +
                            "where playlistId  in (select playlistId from playlist where token = ? and playlistId =?))");
            statement.setString(1, token);
            statement.setInt(2, id);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackDTOS;
    }

    public int getLengthOfPlaylist (String token) {
        try {
            PreparedStatement statement = connection.prepareStatement("select sum(duration) as lengthOfPlaylist  from track where trackid in (select trackid from tracks_in_playlist where playlistId in (select playlistId from playlist where token = ?))");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("lengthOfPlaylist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

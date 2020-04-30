package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.datasource.connection.DatabaseConnection;
import nl.han.oose.dea.datasource.connection.DatabaseProperties;
import nl.han.oose.dea.datasource.datamapper.PlaylistsDataMapper;
import nl.han.oose.dea.datasource.datamapper.TracksDataMapper;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    private DatabaseConnection databaseConnection;
    private PlaylistsDataMapper playlistsDataMapper;
    private TracksDataMapper tracksDataMapper;
    Connection connection;

    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) throws SQLException {
        this.databaseConnection = databaseConnection;
        makeConnection();
    }

    @Inject
    public void setPlaylistsDataMapper(PlaylistsDataMapper playlistsDataMapper) {
        this.playlistsDataMapper = playlistsDataMapper;
    }

    @Inject
    public void setTracksDataMapper(TracksDataMapper tracksDataMapper) {
        this.tracksDataMapper = tracksDataMapper;
    }

    private void makeConnection() throws SQLException {
        connection = databaseConnection.getConnection();
    }

    public PlaylistDAO() {
    }

    public List<PlaylistDTO> getPlaylists(String token) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from playlist where token =?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            return playlistsDataMapper.toDTO(resultSet);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    public void deletePlaylists(String token, int id) {

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM playlist WHERE token = ? AND playlistId = ?");
            statement.setString(1, token);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlaylists(String token, PlaylistDTO playlistDTO) {

        try {
            PreparedStatement statement = connection.prepareStatement("insert into playlist (name,owner,token) values (?,?,?)");
            if (playlistDTO.getId() == -1 & !playlistDTO.isOwner()) {
                statement.setString(1, playlistDTO.getName());
                statement.setBoolean(2, true);
                statement.setString(3, token);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void editPlaylists(String token, int id, PlaylistDTO playlistDTO) {

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE playlist SET name = ? WHERE token = ? AND playlistId = ?");
            statement.setString(1, playlistDTO.getName());
            statement.setString(2, token);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTrackToPlaylist(String token, int playlistId, TrackDTO trackDTO) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into tracks_in_playlist values (?,?)");
            statement.setInt(1, playlistId);
            statement.setInt(2, trackDTO.getId());
            statement.executeUpdate();
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
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from track where trackId in (select trackId from tracks_in_playlist " +
                            "where playlistId  in (select playlistId from playlist where token = ? and playlistId =?))");
            statement.setString(1, token);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            return tracksDataMapper.toDTO(resultSet);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    public int getLengthOfPlaylist(String token) {
        try {
            PreparedStatement statement = connection.prepareStatement("select sum(duration) as lengthOfPlaylist  from track where trackid in (select trackid from tracks_in_playlist where playlistId in (select playlistId from playlist where token = ?))");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("lengthOfPlaylist");
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return 0;
    }
}

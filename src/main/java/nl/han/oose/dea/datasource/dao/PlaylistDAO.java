package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.datasource.DatabaseProperties;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    private DatabaseProperties databaseProperties;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties){
        this.databaseProperties = databaseProperties;
    }

    public PlaylistDAO() {
    }

    public List<PlaylistDTO> getPlaylists(String token){
        List <PlaylistDTO> playlistDTO = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
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
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
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
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
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
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("UPDATE playlist SET name = ? WHERE token = ? AND playlistId = ?");
            statement.setString(1,playlistDTO.getName());
            statement.setString(2, token);
            statement.setInt(3, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

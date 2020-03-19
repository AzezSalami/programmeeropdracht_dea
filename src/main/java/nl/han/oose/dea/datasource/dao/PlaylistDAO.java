package nl.han.oose.dea.datasource.dao;

import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.datasource.DatabaseProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    private DatabaseProperties databaseProperties;

    public PlaylistDAO(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
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
                            new ArrayList<String>()
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM playlist WHERE token = ? AND id = ?");
            statement.setString(1, token);
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

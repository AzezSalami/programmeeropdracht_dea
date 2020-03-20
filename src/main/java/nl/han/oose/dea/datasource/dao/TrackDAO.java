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
    public void setDatabaseProperties(DatabaseProperties databaseProperties){
        this.databaseProperties = databaseProperties;
    }

    public TrackDAO() {
    }

//    public List<TrackDTO> getAllTracks(String token , int playlistId ){
//        List<TrackDTO> trackDTOS = new ArrayList<>();
//        try{
//            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
//            PreparedStatement statement = connection.prepareStatement("select * from track where token =?");
//            statement.setString(1, token);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()){
//                trackDTOS.add( new
//                        );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return trackDTOS;
//    }
}

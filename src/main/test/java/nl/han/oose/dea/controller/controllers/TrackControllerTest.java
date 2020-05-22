package nl.han.oose.dea.controller.controllers;

import nl.han.oose.dea.controller.controllers.TrackController;
import nl.han.oose.dea.controller.dto.TrackDTO;
import nl.han.oose.dea.controller.dto.TracksDTO;
import nl.han.oose.dea.datasource.dao.TrackDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackControllerTest {
    private TrackController sut;
    private TrackDAO mockedTrackDAO;
    private static final int HTTP_OK = 200;

    private final int ID = 1;
    private final String TOKEN = "1234567";

    private TrackDTO track1;
    private TrackDTO track2;
    private TrackDTO track3;
    private List<TrackDTO> tracks;
    private TracksDTO tracksDTO;

    @BeforeEach
    public void setup(){
        sut = new TrackController();
        this.mockedTrackDAO = mock(TrackDAO.class);
        this.sut.setTrackDAO(mockedTrackDAO);
        track1 = new TrackDTO(3,"Ocean and a rock","Lisa Hannigan",337,"Sea sew",0,null,false);
        track2 = new TrackDTO(4,"So Long, Marianne","Leonard Cohen",546,"Songs of Leonard Cohen",0,null,false);
        track3 = new TrackDTO(5,"One","Metallica",423,null,37,"18-03-2001",true);
        tracks = new ArrayList<>();
        tracks.add(track1);
        tracks.add(track2);
        tracks.add(track3);
        tracksDTO = new TracksDTO(tracks);
    }

    @Test
    public void getAllTracksNotInPlaylistTestStatus(){
        // Arrange


        when(mockedTrackDAO.getAllTracksNotInPlaylist(TOKEN,ID)).thenReturn(tracks);
        // Act
        var response = sut.getAllTracksNotInPlaylist(ID,TOKEN);
        // Assert
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void getAllTracksNotInPlaylistTest(){
        // Arrange
        when(mockedTrackDAO.getTracksDTO(TOKEN,ID)).thenReturn(tracksDTO);
        // Act
        var response = sut.getAllTracksNotInPlaylist(ID,TOKEN);
        // Assert
        assertEquals(tracksDTO,response.getEntity());
    }
}

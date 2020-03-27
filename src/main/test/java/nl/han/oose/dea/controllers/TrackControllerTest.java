package nl.han.oose.dea.controllers;

import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.controllers.dto.PlaylistsDTO;
import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.controllers.dto.TracksDTO;
import nl.han.oose.dea.datasource.dao.TrackDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.Track;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackControllerTest {
    private TrackController sut;
    private TrackDAO mockedTrackDAO;
    private static final int HTTP_OK = 200;

    @BeforeEach
    public void setup(){
        sut = new TrackController();
        this.mockedTrackDAO = mock(TrackDAO.class);
        this.sut.setTrackDAO(mockedTrackDAO);
    }

    @Test
    public void getAllTracksNotInPlaylistTest(){
        // Arrange
        var id = 1;
        var token = "1234567";
        var track1 = new TrackDTO(3,"Ocean and a rock","Lisa Hannigan",337,"Sea sew",0,null,false);
        var track2 = new TrackDTO(4,"So Long, Marianne","Leonard Cohen",546,"Songs of Leonard Cohen",0,null,false);
        var track3 = new TrackDTO(5,"One","Metallica",423,null,37,"18-03-2001",true);
        List<TrackDTO> Tracks = new ArrayList<>();
        Tracks.add(track1);
        Tracks.add(track2);
        Tracks.add(track3);

        when(mockedTrackDAO.getAllTracksNotInPlaylist(token,id)).thenReturn(Tracks);
        // Act
        var response = sut.getAllTracksNotInPlaylist(id,token);
        var tracksDTO = new TracksDTO(Tracks);
        // Assert
        //assertEquals(tracksDTO,response.getEntity());
        assertEquals(HTTP_OK,response.getStatus());
    }
}

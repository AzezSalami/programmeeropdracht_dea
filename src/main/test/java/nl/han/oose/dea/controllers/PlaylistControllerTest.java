package nl.han.oose.dea.controllers;

import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.controllers.dto.PlaylistsDTO;
import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.controllers.dto.TracksDTO;
import nl.han.oose.dea.datasource.dao.PlaylistDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlaylistControllerTest {
    private PlaylistController sut;
    private PlaylistDAO mockedPlaylistDAO;
    private static final int HTTP_OK = 200;
    private static final int HTTP_CREATED = 201;

    @BeforeEach
    public void setup() {
        sut = new PlaylistController();
        this.mockedPlaylistDAO = mock(PlaylistDAO.class);
        this.sut.setPlaylistDAO(mockedPlaylistDAO);
    }

    @Test
    public void acquirePlaylistsTest(){
        // Arrange
        var token = "1234567";
        int length = 773;

        var playlistDTO = new PlaylistDTO(1,"Death metal",true ,new ArrayList<TrackDTO>());
        var playlistDTO1 = new PlaylistDTO(2,"Pop",false ,new ArrayList<TrackDTO>());
        List<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlistDTO);
        playlists.add(playlistDTO1);
        var playlistsDTO = new PlaylistsDTO(playlists,length);
        when(mockedPlaylistDAO.getPlaylists(token)).thenReturn(playlists);
        when(mockedPlaylistDAO.getLengthOfPlaylist(token)).thenReturn(length);
        // Act
        var response = sut.acquirePlaylists(token);
        // Assert
       // assertEquals(playlistsDTO,response.getEntity());
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void deletePlaylistTest(){
        // Arrange
        var playlistDTO = new PlaylistDTO(1,"Death metal",true ,new ArrayList<TrackDTO>());
        var token = "1234567";
        int id = playlistDTO.getId();
        // Act
        var response = sut.deletePlaylist(token,id);
        // Assert
        verify(mockedPlaylistDAO).deletePlaylists(token,id);
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void addPlaylistTest(){
        // Arrange
        var playlistDTO = new PlaylistDTO(-1,"Hip hop",true ,new ArrayList<TrackDTO>());
        var token = "1234567";
        int id = playlistDTO.getId();
        // Act
        var response = sut.addPlaylist(token,playlistDTO);
        // Assert
        verify(mockedPlaylistDAO).addPlaylists(token,playlistDTO);
        assertEquals(HTTP_OK,response.getStatus());

    }

    @Test
    public void editPlaylistTest(){
        // Arrange
        var playlistDTO = new PlaylistDTO(1,"metal",true ,new ArrayList<TrackDTO>());        var token = "1234567";
        int id = playlistDTO.getId();
        // Act
        var response = sut.editPlaylist(token,id,playlistDTO);
        // Assert
        verify(mockedPlaylistDAO).editPlaylists(token,id,playlistDTO);
        assertEquals(HTTP_OK,response.getStatus());

    }

    @Test
    public void getAllTracksInPlaylistTest() {
        // Arrange
        var id = 1;
        var token = "1234567";
        var track1 = new TrackDTO(1,"The cost","The Frames",350,"The cost",0,null,false);
        var track2 = new TrackDTO(2,"Song for someone","The Frames",423,null,37,"19-03-2006",true);
        List<TrackDTO> trackDTOS = new ArrayList<>();
        trackDTOS.add(track1);
        trackDTOS.add(track2);
        when(mockedPlaylistDAO.getAllTracksInPlaylist(token,id)).thenReturn(trackDTOS);
        // Act
        var response = sut.getAllTracksInPlaylist(token,id);
        var tracksDTO = new TracksDTO(trackDTOS);
        // Assert
       // assertEquals(tracksDTO,response.getEntity());
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void deleteTrackFromPlaylistTest(){
        // Arrange
        var token = "1234567";
        var playlistDTO = new PlaylistDTO(1,"metal",true ,new ArrayList<TrackDTO>());
        var track = new TrackDTO(2,"Song for someone","The Frames",423,null,37,"19-03-2006",true);

        var id = playlistDTO.getId();
        var trackId = track.getId();
        // Act
        var response = sut.deleteTrackFromPlaylist(token,id,trackId);
        // Assert
        verify(mockedPlaylistDAO).deleteTrackFromPlaylist(token,id,trackId);
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void addTrackToPlaylistTest(){
        // Arrange
        var token = "1234567";
        var playlistDTO = new PlaylistDTO(1,"metal",true ,new ArrayList<TrackDTO>());
        var track = new TrackDTO(3,"Ocean and a rock","Lisa Hannigan",337,"Sea sew",0,null,false);
        var id = playlistDTO.getId();

        // Act
        var response = sut.addTrackToPlaylist(token,id,track);
        // Assert
        verify(mockedPlaylistDAO).addTrackToPlaylist(token,id,track);
        assertEquals(HTTP_OK,response.getStatus());
    }
}

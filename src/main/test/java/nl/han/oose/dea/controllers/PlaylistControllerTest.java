package nl.han.oose.dea.controllers;

import nl.han.oose.dea.controllers.dto.*;
import nl.han.oose.dea.datasource.dao.PlaylistDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlaylistControllerTest {
    private PlaylistController sut;
    private PlaylistDAO mockedPlaylistDAO;
    private static final int HTTP_OK = 200;
    private static final int HTTP_CREATED = 201;

    private final String TOKEN = "1234567";
    private final int LENGTH = 773;

    private PlaylistsDTO playlistsDTO;
    private PlaylistDTO playlistDTO;
    private PlaylistDTO playlistDTO1;
    private List<PlaylistDTO> playlists;

    private TrackDTO track1;
    private TrackDTO track2;
    private List<TrackDTO> tracks;
    private TracksDTO tracksDTO;


    @BeforeEach
    public void setup() {
        sut = new PlaylistController();
        this.mockedPlaylistDAO = mock(PlaylistDAO.class);
        this.sut.setPlaylistDAO(mockedPlaylistDAO);

        track1 = new TrackDTO(1,"The cost","The Frames",350,"The cost",0,null,false);
        track2 = new TrackDTO(2,"Song for someone","The Frames",423,null,37,"19-03-2006",true);
        tracks = new ArrayList<>();
        tracks.add(track1);
        tracks.add(track2);
        tracksDTO = new TracksDTO(tracks);
        playlistDTO = new PlaylistDTO(1,"Death metal",true ,tracks);
        playlistDTO1 = new PlaylistDTO(2,"Pop",false ,new ArrayList<TrackDTO>());
        playlists = new ArrayList<>();
        playlists.add(playlistDTO);
        playlists.add(playlistDTO1);
        playlistsDTO = new PlaylistsDTO(playlists,LENGTH);
    }

    @Test
    public void acquirePlaylistsTestStatus(){
        // Arrange
        when(mockedPlaylistDAO.getPlaylists(TOKEN)).thenReturn(playlists);
        when(mockedPlaylistDAO.getLengthOfPlaylist(TOKEN)).thenReturn(LENGTH);
        // Act
        var response = sut.acquirePlaylists(TOKEN);
        // Assert
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void acquirePlaylistsTestEntity(){
        // Arrange
        when(mockedPlaylistDAO.getPlaylistsDTO(TOKEN)).thenReturn(playlistsDTO);
        when(mockedPlaylistDAO.getLengthOfPlaylist(TOKEN)).thenReturn(LENGTH);
        // Act
        var response = sut.acquirePlaylists(TOKEN);
        // Assert
        assertEquals(playlistsDTO,response.getEntity());

    }

    @Test
    public void deletePlaylistTestStatus(){
        // Arrange
        int id = playlistDTO1.getId();
        // Act
        var response = sut.deletePlaylist(TOKEN,id);
        // Assert
        verify(mockedPlaylistDAO).deletePlaylists(TOKEN,id);
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void deletePlaylistTestEntity(){
        // Arrange
        int id = playlistDTO1.getId();
        when(mockedPlaylistDAO.getPlaylistsDTO(TOKEN)).thenReturn(playlistsDTO);
        when(mockedPlaylistDAO.getLengthOfPlaylist(TOKEN)).thenReturn(LENGTH);
        // Act
        var response = sut.deletePlaylist(TOKEN,id);
        // Assert
        assertEquals(playlistsDTO,response.getEntity());
    }

    @Test
    public void addPlaylistTestStatus(){
        // Arrange
        int id = playlistDTO.getId();
        // Act
        var response = sut.addPlaylist(TOKEN,playlistDTO);
        // Assert
        verify(mockedPlaylistDAO).addPlaylists(TOKEN,playlistDTO);
        assertEquals(HTTP_OK,response.getStatus());

    }

    @Test
    public void addPlaylistTestEntity(){
        // Arrange
        int id = playlistDTO.getId();
        when(mockedPlaylistDAO.getPlaylistsDTO(TOKEN)).thenReturn(playlistsDTO);
        // Act
        var response = sut.addPlaylist(TOKEN,playlistDTO);
        // Assert
        verify(mockedPlaylistDAO).addPlaylists(TOKEN,playlistDTO);
        assertEquals(playlistsDTO,response.getEntity());
    }
    @Test
    public void editPlaylistTestStatus(){
        // Arrange
        int id = playlistDTO.getId();
        // Act
        var response = sut.editPlaylist(TOKEN,id,playlistDTO);
        // Assert
        verify(mockedPlaylistDAO).editPlaylists(TOKEN,id,playlistDTO);
        assertEquals(HTTP_OK,response.getStatus());

    }

    @Test
    public void editPlaylistTestEntity(){
        // Arrange
        int id = playlistDTO.getId();
        when(mockedPlaylistDAO.getPlaylistsDTO(TOKEN)).thenReturn(playlistsDTO);
        // Act
        var response = sut.editPlaylist(TOKEN,id,playlistDTO);
        // Assert
        verify(mockedPlaylistDAO).editPlaylists(TOKEN,id,playlistDTO);
        assertEquals(playlistsDTO,response.getEntity());

    }

    @Test
    public void getAllTracksInPlaylistTestStatus() {
        // Arrange
        var id = 1;

        when(mockedPlaylistDAO.getTracksDTO(TOKEN,id)).thenReturn(tracksDTO);
        // Act
        var response = sut.getAllTracksInPlaylist(TOKEN,id);
        // Assert
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void getAllTracksInPlaylistTestEntity() {
        // Arrange
        var id = 1;

        when(mockedPlaylistDAO.getTracksDTO(TOKEN,id)).thenReturn(tracksDTO);
        // Act
        var response = sut.getAllTracksInPlaylist(TOKEN,id);
        // Assert
        assertEquals(tracksDTO,response.getEntity());
    }

    @Test
    public void deleteTrackFromPlaylistTestStatus(){
        // Arrange
        var id = playlistDTO.getId();
        var trackId = track2.getId();
        when(mockedPlaylistDAO.getTracksDTO(TOKEN,id)).thenReturn(tracksDTO);

        // Act
        var response = sut.deleteTrackFromPlaylist(TOKEN,id,trackId);
        // Assert
        verify(mockedPlaylistDAO).deleteTrackFromPlaylist(TOKEN,id,trackId);
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void deleteTrackFromPlaylistTestEntity(){
        // Arrange
        var id = playlistDTO.getId();
        var trackId = track2.getId();
        when(mockedPlaylistDAO.getTracksDTO(TOKEN,id)).thenReturn(tracksDTO);

        // Act
        var response = sut.deleteTrackFromPlaylist(TOKEN,id,trackId);
        // Assert
        verify(mockedPlaylistDAO).deleteTrackFromPlaylist(TOKEN,id,trackId);
        assertEquals(tracksDTO,response.getEntity());
    }

    @Test
    public void deleteTrackFromPlaylistTestThrow() {
        // Arrange
        var id = playlistDTO.getId();
        var trackId = 3;
        when(mockedPlaylistDAO.getTracksDTO(TOKEN,id)).thenReturn(tracksDTO);

        doThrow(InternalServerErrorException.class).when(mockedPlaylistDAO).deleteTrackFromPlaylist(TOKEN,id,trackId);

        // Run the tes
        // Verify the results
        Assertions.assertThrows(InternalServerErrorException.class, () -> sut.deleteTrackFromPlaylist(TOKEN,id,trackId));
    }

    @Test
    public void addTrackToPlaylistTestStatus(){
        // Arrange
        var track = new TrackDTO(3,"Ocean and a rock","Lisa Hannigan",337,"Sea sew",0,null,false);
        var id = playlistDTO.getId();
        when(mockedPlaylistDAO.getTracksDTO(TOKEN,id)).thenReturn(tracksDTO);
        // Act
        var response = sut.addTrackToPlaylist(TOKEN,id,track);
        // Assert
        verify(mockedPlaylistDAO).addTrackToPlaylist(TOKEN,id,track);
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void addTrackToPlaylistTestEntity(){
        // Arrange
        var track = new TrackDTO(3,"Ocean and a rock","Lisa Hannigan",337,"Sea sew",0,null,false);
        var id = playlistDTO.getId();
        when(mockedPlaylistDAO.getTracksDTO(TOKEN,id)).thenReturn(tracksDTO);
        // Act
        var response = sut.addTrackToPlaylist(TOKEN,id,track);
        // Assert
        verify(mockedPlaylistDAO).addTrackToPlaylist(TOKEN,id,track);
        assertEquals(tracksDTO,response.getEntity());
    }

    @Test
    public void addTrackToPlaylistTestThrow() {
        // Arrange
        var track = new TrackDTO(7,"Ocean and a rock","Lisa Hannigan",337,"Sea sew",0,null,false);
        var id = playlistDTO.getId();
        doThrow(InternalServerErrorException.class).when(mockedPlaylistDAO).addTrackToPlaylist(TOKEN, id,track);

        // Run the tes
        // Verify the results
        Assertions.assertThrows(InternalServerErrorException.class, () -> sut.addTrackToPlaylist(TOKEN, id,track));
    }
}

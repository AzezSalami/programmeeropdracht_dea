package controllers;

import nl.han.oose.dea.controllers.LoginController;
import nl.han.oose.dea.controllers.PlaylistController;
import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.controllers.dto.PlaylistsDTO;
import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.datasource.dao.LoginDAO;
import nl.han.oose.dea.datasource.dao.PlaylistDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
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
        PlaylistDTO playlistDTO = new PlaylistDTO(1,"Death metal",true ,new ArrayList<TrackDTO>());
        PlaylistDTO playlistDTO1 = new PlaylistDTO(2,"Pop",false ,new ArrayList<TrackDTO>());
        List<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlistDTO);
        playlists.add(playlistDTO1);
        PlaylistsDTO playlistsDTO = new PlaylistsDTO(playlists,123456);
        String token = "1234567";
        when(mockedPlaylistDAO.getPlaylists(token)).thenReturn(playlists);

        Response response = sut.acquirePlaylists(token);

        assertEquals(playlistsDTO,response.getEntity());
        assertEquals(HTTP_OK,response.getStatus());
    }

    @Test
    public void deletePlaylistTest(){
        PlaylistDTO playlistDTO = new PlaylistDTO(1,"Death metal",true ,new ArrayList<TrackDTO>());

        String token = "1234567";
        int id = playlistDTO.getId();
        verify(mockedPlaylistDAO).deletePlaylists(token,id);

        Response response = sut.deletePlaylist(token,id);

        assertEquals(HTTP_OK,response.getEntity());
    }

    @Test
    public void addPlaylistTest(){
        PlaylistDTO playlistDTO = new PlaylistDTO(-1,"Hip hop",true ,new ArrayList<TrackDTO>());

        String token = "1234567";
        int id = playlistDTO.getId();

    }
}

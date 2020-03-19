package nl.han.oose.dea.controllers;
import nl.han.oose.dea.controllers.dto.PlaylistsDTO;
import nl.han.oose.dea.datasource.dao.PlaylistDAO;
import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.datasource.DatabaseProperties;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/playlists")
public class PlaylistController {
 //   DatabaseProperties databaseProperties = new DatabaseProperties();
    PlaylistDAO playlistDAO ;//= new PlaylistDAO(databaseProperties);

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO ){
        this.playlistDAO = playlistDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response acquirePlaylists(@QueryParam("token") String token){
        PlaylistsDTO playlistsDTO = new PlaylistsDTO(playlistDAO.getPlaylists(token),123456);

        return Response.ok().entity(playlistsDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylists(@QueryParam("token") String token, @PathParam("id") int id){
        //playlistDAO.deletePlaylists(token,id);
        PlaylistsDTO playlistsDTO = new PlaylistsDTO(playlistDAO.getPlaylists(token),123456);

        return Response.ok().entity(playlistsDTO).build();
    }
}

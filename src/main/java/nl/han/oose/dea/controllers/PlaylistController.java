package nl.han.oose.dea.controllers;

import nl.han.oose.dea.controllers.dto.PlaylistsDTO;
import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.controllers.dto.TracksDTO;
import nl.han.oose.dea.datasource.dao.PlaylistDAO;
import nl.han.oose.dea.controllers.dto.PlaylistDTO;
import nl.han.oose.dea.datasource.dao.TrackDAO;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/playlists")
public class PlaylistController {
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;

    public PlaylistController() {
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response acquirePlaylists(@QueryParam("token") String token) {
        PlaylistsDTO playlistsDTO = new PlaylistsDTO(playlistDAO.getPlaylists(token), 123456);

        return Response.ok().entity(playlistsDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        playlistDAO.deletePlaylists(token, id);

        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO) {
        playlistDAO.addPlaylists(token, playlistDTO);

        return Response.ok().entity(new PlaylistsDTO(playlistDAO.getPlaylists(token), 123456)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, @PathParam("id") int id, PlaylistDTO playlistDTO) {
        playlistDAO.editPlaylists(token, id, playlistDTO);

        return Response.ok().entity(new PlaylistsDTO(playlistDAO.getPlaylists(token), 123456)).build();
    }

    @GET
    @Path("{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksInPlaylist(@QueryParam("token") String token,@PathParam("id") int id) {
        TracksDTO tracksDTO = new TracksDTO(playlistDAO.getAllTracksInPlaylist(token, id));
        return Response.ok().entity(tracksDTO).build();
    }

    @DELETE
    @Path("{playlistId}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token , @javax.ws.rs.PathParam("playlistId") int playlistId , @javax.ws.rs.PathParam("trackId") int trackId){
        playlistDAO.deleteTrackFromPlaylist(token ,playlistId, trackId);
        return Response.ok().entity(new TracksDTO(playlistDAO.getAllTracksInPlaylist(token, playlistId))).build();
    }

    @POST
    @Path("{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token , @PathParam("playlistId") int playlistId , TrackDTO trackDTO){
        playlistDAO.addTrackToPlaylist(token ,playlistId, trackDTO);
        return Response.ok().entity(new TracksDTO(playlistDAO.getAllTracksInPlaylist(token, playlistId))).build();
    }

}

package nl.han.oose.dea.controllers;

import nl.han.oose.dea.controllers.dto.TrackDTO;
import nl.han.oose.dea.controllers.dto.TracksDTO;
import nl.han.oose.dea.datasource.dao.PlaylistDAO;
import nl.han.oose.dea.datasource.dao.TrackDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackController {
    private TrackDAO trackDAO ;

    public TrackController() {
    }

    @Inject
    public void setPlaylistDAO(TrackDAO trackDAO){
        this.trackDAO = trackDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("forPlaylist") int forPlaylist, @QueryParam("token") String token){

        return Response.ok().entity(new TracksDTO(trackDAO.getAllTracks(token, forPlaylist))).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token ,@PathParam("playlistId") int playlistId , @PathParam("trackId") int trackId){
        return Response.ok().entity(trackDAO.deleteTrackFromPlaylist(token ,playlistId, trackId)).build();
    }
}

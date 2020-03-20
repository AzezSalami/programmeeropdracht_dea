package nl.han.oose.dea.controllers.dto;

import java.util.List;

public class TracksDTO {
    private List<TrackDTO> Tracks;

    public TracksDTO() {
    }

    public TracksDTO(List<TrackDTO> tracks) {
        Tracks = tracks;
    }

    public List<TrackDTO> getTracks() {
        return Tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        Tracks = tracks;
    }
}

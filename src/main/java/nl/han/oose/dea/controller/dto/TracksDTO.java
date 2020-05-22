package nl.han.oose.dea.controller.dto;

import java.util.List;

public class TracksDTO {
    private List<TrackDTO> tracks;

    public TracksDTO() {
    }

    public TracksDTO(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}

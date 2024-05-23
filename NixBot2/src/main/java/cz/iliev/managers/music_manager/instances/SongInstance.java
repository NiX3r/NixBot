package cz.iliev.managers.music_manager.instances;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class SongInstance {
    private long addedTime;
    private String playerName;
    private AudioTrack track;

    public SongInstance(long addedTime, String playerName, AudioTrack track) {
        this.addedTime = addedTime;
        this.playerName = playerName;
        this.track = track;
    }

    public long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(long addedTime) {
        this.addedTime = addedTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public AudioTrack getTrack() {
        return track;
    }

    public void setTrack(AudioTrack track) {
        this.track = track;
    }
}
package cz.nix3r.events;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;

public class nTrackEndEvent implements AudioEventListener {
    @Override
    public void onEvent(AudioEvent audioEvent) {

        if (audioEvent instanceof TrackEndEvent) {
            LogSystem.log(LogType.INFO, "Current song ended. Start playing a new one");
            if(((TrackEndEvent)audioEvent).endReason != AudioTrackEndReason.STOPPED && ((TrackEndEvent)audioEvent).endReason != AudioTrackEndReason.REPLACED){
                CommonUtils.musicManager.playNext();
                LogSystem.log(LogType.INFO, "Next song played");
            }
        }

    }

}

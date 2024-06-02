package cz.iliev.managers.music_manager.events;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class TrackEndEvent implements AudioEventListener {
    @Override
    public void onEvent(AudioEvent audioEvent) {

        if (audioEvent instanceof com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent) {
            LogUtils.info("Current song ended. Start playing a new one");
            if(((com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent)audioEvent).endReason != AudioTrackEndReason.STOPPED && ((com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent)audioEvent).endReason != AudioTrackEndReason.REPLACED){
                CommonUtils.musicManager.playNext();
                LogUtils.info("Next song played");
            }
        }

    }

}
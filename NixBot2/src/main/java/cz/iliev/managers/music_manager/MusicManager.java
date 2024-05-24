package cz.iliev.managers.music_manager;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cz.iliev.interfaces.IManager;
import cz.iliev.managers.music_manager.commands.*;
import cz.iliev.managers.music_manager.instances.LavaplayerAudioSource;
import cz.iliev.managers.music_manager.instances.SongInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public class MusicManager implements IManager {

    private boolean ready;
    private AudioPlayerManager playerManager;
    private AudioPlayer player;
    private LavaplayerAudioSource audioSource;

    private AudioConnection audioConnection;
    private List<SongInstance> audioList;

    public MusicManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start MusicManager");
        ready = true;
        LogUtils.info("MusicManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill MusicManager");
        ready = false;
        LogUtils.info("MusicManager killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {
        switch (interaction.getCommandName()){
            case "skip":
                new SkipCommand().run(interaction);
                break;
            case "pause":
                new PauseCommand().run(interaction);
                break;
            case "unpause":
                new UnpauseCommand().run(interaction);
                break;
            case "volume":
                new VolumeCommand().run(interaction);
                break;
            case "play":
                new PlayCommand().run(interaction);
                break;
            case "queue":
                new QueueCommand().run(interaction);
                break;
        }
    }

    @Override
    public void onConsoleCommand(Object data) {
        return;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    public void playNext(){
        if(audioList.size() == 0){
            player.stopTrack();
            CommonUtils.bot.getYourself().getConnectedVoiceChannels().forEach(ServerVoiceChannel::disconnect);
            CommonUtils.botActivityManager.setBasicActivity();
            return;
        }
        player.stopTrack();
        player.playTrack(audioList.get(0).getTrack());
        CommonUtils.announcementManager.sendCurrentSong(audioList.get(0));
        CommonUtils.botActivityManager.setActivity(ActivityType.PLAYING, "\uD83C\uDFB5 " + audioList.get(0).getTrack().getInfo().title);
        audioList.remove(0);
    }

    public void clearQueue(){
        audioList.clear();
        playNext();
    }

    public boolean isPlaying(){
        return player.getPlayingTrack() != null && (!player.isPaused());
    }

    public boolean isPaused(){
        return player.isPaused();
    }

    public void setPause(boolean pause){
        player.setPaused(pause);
    }

    public void setVolume(int volume){
        player.setVolume(volume);
    }

    public void playMusic(SlashCommandInteraction interaction, String url){

        User user = interaction.getUser();
        Server server = interaction.getServer().get();

        if(player.getPlayingTrack() != null){

            playerManager.loadItem(url, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    SongInstance song = new SongInstance(System.currentTimeMillis(), user.getDisplayName(server), null);
                    song.setTrack(track);
                    audioList.add(song);
                    interaction.createImmediateResponder().setContent("Added song " + track.getInfo().title + "\nWaiting in queue: " + audioList.size()).respond();
                    LogUtils.info("Successfully added '" + track.getInfo().title + "' to music manager");
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    for (AudioTrack track : playlist.getTracks()) {
                        SongInstance song = new SongInstance(System.currentTimeMillis(), user.getDisplayName(server), null);
                        song.setTrack(track);
                        audioList.add(song);
                    }
                    interaction.createImmediateResponder().setContent("All songs from playlist was added. Added songs: " + playlist.getTracks().size() + "\nWaiting in queue: " + audioList.size()).respond();
                    LogUtils.info("Successfully added " + playlist.getTracks().size() + " songs to music manager");
                }

                @Override
                public void noMatches() {
                    LogUtils.warning("No matches for this song");
                }

                @Override
                public void loadFailed(FriendlyException throwable) {
                    CommonUtils.throwException(throwable);
                }
            });

        }
        else {
            user.getConnectedVoiceChannel(server).ifPresent(channel -> {

                channel.connect().thenAcceptAsync(connection -> {
                    audioConnection = connection;
                    connection.setAudioSource(audioSource);

                    playerManager.loadItem(url, new AudioLoadResultHandler() {
                        @Override
                        public void trackLoaded(AudioTrack track) {
                            SongInstance song = new SongInstance(System.currentTimeMillis(), user.getDisplayName(server), null);
                            song.setTrack(track);
                            audioList.add(song);
                            interaction.createImmediateResponder().setContent("Playing song " + track.getInfo().title).respond();
                            LogUtils.info("Start playing '" + track.getInfo().title + "'");
                            playNext();
                        }

                        @Override
                        public void playlistLoaded(AudioPlaylist playlist) {
                            for (AudioTrack track : playlist.getTracks()) {
                                SongInstance song = new SongInstance(System.currentTimeMillis(), user.getDisplayName(server), null);
                                song.setTrack(track);
                                audioList.add(song);
                            }
                            interaction.createImmediateResponder().setContent("All songs from playlist was added. Start playing. Total songs: " + playlist.getTracks().size()).respond();
                            LogUtils.info("Successfully added " + playlist.getTracks().size() + " songs to music manager. Start playing first in queue");
                            playNext();
                        }

                        @Override
                        public void noMatches() {
                            LogUtils.warning("No matches for this song");
                        }

                        @Override
                        public void loadFailed(FriendlyException throwable) {
                            CommonUtils.throwException(throwable);
                        }
                    });
                });

            });
        }

    }

    public List<SongInstance> getAudioList(){
        return audioList;
    }
}

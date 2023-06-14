package cz.nix3r.managers;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cz.nix3r.events.nTrackEndEvent;
import cz.nix3r.instances.LavaplayerAudioSource;
import cz.nix3r.instances.SongInstance;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.List;

public class MusicManager {

    private AudioPlayerManager playerManager;
    private AudioPlayer player;
    private LavaplayerAudioSource audioSource;

    private AudioConnection audioConnection;
    private List<SongInstance> audioList;

    public MusicManager(){

        playerManager = new DefaultAudioPlayerManager();
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        player = playerManager.createPlayer();
        audioSource = new LavaplayerAudioSource(CommonUtils.bot, player);
        audioList = new ArrayList<>();

        player.addListener(new nTrackEndEvent());

    }

    public void playNext(){
        if(audioList.size() == 0){
            ((Server)CommonUtils.bot.getServers().toArray()[0]).getTextChannelById(CommonUtils.CMD_CHANNEL_ID).ifPresent(channel -> {
                player.stopTrack();
                CommonUtils.bot.getYourself().getConnectedVoiceChannel(((Server) CommonUtils.bot.getServers().toArray()[0])).get().disconnect().join();
                channel.sendMessage("Out of songs. Please provide me some more :muscle:").join();
            });
            return;
        }
        player.stopTrack();
        player.playTrack(audioList.get(0).getTrack());
        ((Server)CommonUtils.bot.getServers().toArray()[0]).getTextChannelById(CommonUtils.CMD_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(DiscordUtils.createNextSongEmbed(audioList.get(0)));
        });
        audioList.remove(0);
    }

    public void clearQueue(){
        audioList.clear();
        playNext();
    }

    public boolean isPlaying(){
        return player.getPlayingTrack() == null ?
                false :
                (player.isPaused() ?
                        false :
                        true);
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
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    for (AudioTrack track : playlist.getTracks()) {
                        SongInstance song = new SongInstance(System.currentTimeMillis(), user.getDisplayName(server), null);
                        song.setTrack(track);
                        audioList.add(song);
                    }
                    interaction.createImmediateResponder().setContent("All songs from playlist was added. Added songs: " + playlist.getTracks().size() + "\nWaiting in queue: " + audioList.size()).respond();
                }

                @Override
                public void noMatches() {
                    System.out.println("no matches");
                }

                @Override
                public void loadFailed(FriendlyException throwable) {
                    System.out.println("load failed");
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
                            playNext();
                        }

                        @Override
                        public void noMatches() {
                            System.out.println("no matches");
                        }

                        @Override
                        public void loadFailed(FriendlyException throwable) {
                            System.out.println("load failed");
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

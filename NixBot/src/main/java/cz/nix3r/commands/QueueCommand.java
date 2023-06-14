package cz.nix3r.commands;

import cz.nix3r.instances.SongInstance;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.List;

public class QueueCommand {
    public static void run(SlashCommandInteraction interaction) {

        switch (interaction.getOptions().get(0).getName()){
            case "clear":
                clearQueue(interaction);
                break;
            case "view":
                viewQueue(interaction);
                break;
        }

    }

    private static void clearQueue(SlashCommandInteraction interaction){
        CommonUtils.musicManager.clearQueue();
        interaction.createImmediateResponder().setContent("Queue cleared.").respond();
    }

    private static void viewQueue(SlashCommandInteraction interaction){
        List<SongInstance> tracks = CommonUtils.musicManager.getAudioList();
        String trackList = "";
        int counter = 0;

        long totalDuration = 0;

        for(SongInstance track : tracks){
            if(counter < 10)
                trackList += "**" + (counter + 1) + ".** " + track.getTrack().getInfo().title + "\n";
            counter++;
            totalDuration += track.getTrack().getDuration();
        }

        if(counter >= 10)
            trackList += "And " + (counter - 9) + " more songs ...\n";
        trackList = trackList.substring(0, trackList.length() - 1);

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Music Queue")
                .addField("Total length", "`" + DiscordUtils.formatTimeToMinutes(totalDuration) + "`", true)
                .addField("Total songs", "`" + counter + "`", true)
                .setDescription(trackList)
                .setColor(Color.decode("#2100FF"));

        interaction.createImmediateResponder().addEmbed(builder).respond();
    }

}

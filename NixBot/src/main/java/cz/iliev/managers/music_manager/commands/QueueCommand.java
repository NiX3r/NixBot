package cz.iliev.managers.music_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.music_manager.instances.SongInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.List;

public class QueueCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
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
        LogUtils.info("End of command queue clear by '" + interaction.getUser().getName() + "'");
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
                .addField("Total length", "`" + CommonUtils.formatTimeToMinutes(totalDuration) + "`", true)
                .addField("Total songs", "`" + counter + "`", true)
                .setDescription(trackList)
                .setColor(Color.decode("#2100FF"));

        interaction.createImmediateResponder().addEmbed(builder).respond();
        LogUtils.info("End of command queue view by '" + interaction.getUser().getName() + "'");
    }
}

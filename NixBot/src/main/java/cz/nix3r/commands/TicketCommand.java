package cz.nix3r.commands;

import com.vdurmont.emoji.EmojiParser;
import cz.nix3r.instances.Ticket;
import cz.nix3r.utils.CommandUtils;
import cz.nix3r.utils.CommonUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;

public class TicketCommand {

    public static void run(SlashCommandInteraction interaction){
        switch (interaction.getOptions().get(0).getName()){
            case "message":
                message(interaction);
                break;
            case "close":
                close(interaction);
                break;
            case "resolve":
                resolve(interaction);
                break;
            case "add":
                add(interaction);
                break;
            case "remove":
                remove(interaction);
                break;
        }
    }

    private static void message(SlashCommandInteraction interaction){

        if(!interaction.getServer().isPresent()){
            interaction.createImmediateResponder().setContent("This command is only for on server use.").respond();
            return;
        }

        if(!CommandUtils.hasSenderAdminPermission(interaction)){
            interaction.createImmediateResponder().setContent("This command is only for administrators.").respond();
            return;
        }

        TextChannel channel = interaction.getServer().get().getTextChannelById(CommonUtils.SUBMIT_CHANNEL_ID).get();

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#61B23A"))
                .setTitle("Ticket System")
                .setDescription("Ahoj! :wave: \nNestyď se a zeptej se na cokoliv!")
                .addField("Rule #1", "Nezneužívej tento systém")
                .addField("Rule #2", "Po založení podpory co nejvíce specifikuj problém/otázku")
                .addField("Rule #3", "Konverzace v systému je zálohována pro další potřeby vývoje")
                .setFooter("Version: " + CommonUtils.version);

        new MessageBuilder()
                .setEmbed(builder)
                .addComponents(
                        ActionRow.of(Button.secondary("nix-ticket-support", "Podpora", EmojiParser.parseToUnicode(":question:"))),
                        ActionRow.of(Button.secondary("nix-ticket-reclamation", "Reklamace", EmojiParser.parseToUnicode(":wrench:"))),
                        ActionRow.of(Button.secondary("nix-ticket-sponsor", "Sponzoří", EmojiParser.parseToUnicode(":busts_in_silhouette:"))),
                        ActionRow.of(Button.secondary("nix-ticket-other", "Ostatní", EmojiParser.parseToUnicode(":telephone:")))
                )
                .send(channel);

        interaction.createImmediateResponder().setContent("Sent").respond();

    }

    private static void close(SlashCommandInteraction interaction){
        close(interaction, false);
    }

    private static void resolve(SlashCommandInteraction interaction){
        close(interaction, true);
    }

    private static void close(SlashCommandInteraction interaction, boolean isResolved){
        interaction.getServer().ifPresent(server -> {
            interaction.getChannel().ifPresent(textChannel -> {

                if((!textChannel.asChannelCategory().isPresent()) ||
                        (!textChannel.asChannelCategory().get().getIdAsString().equals(CommonUtils.SUBMIT_CATEGORY_ID))){
                    interaction.createImmediateResponder().setContent("V tomto kanálu není povolen tento příkaz").setFlags(MessageFlag.EPHEMERAL).respond();
                    return;
                }

                switch (Ticket.closeTicket(server, textChannel, interaction.getUser(), isResolved)){
                    case -1:
                        interaction.createImmediateResponder().setContent("Konverzace nenalezena").setFlags(MessageFlag.EPHEMERAL).respond();
                        break;
                    case -2:
                        interaction.createImmediateResponder().setContent("Nemáš právo na uzavření").setFlags(MessageFlag.EPHEMERAL).respond();
                        break;
                }
            });

        });
    }

    private static void add(SlashCommandInteraction interaction){

    }

    private static void remove(SlashCommandInteraction interaction){

    }

}

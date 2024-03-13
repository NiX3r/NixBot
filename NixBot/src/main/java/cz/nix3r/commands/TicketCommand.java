package cz.nix3r.commands;

import com.vdurmont.emoji.EmojiParser;
import cz.nix3r.enums.LogType;
import cz.nix3r.instances.Ticket;
import cz.nix3r.instances.TicketMember;
import cz.nix3r.utils.CommandUtils;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.Set;

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
            interaction.createImmediateResponder().setContent("Tento příkaz je pouze pro kanál.").respond();
            return;
        }

        if(!CommandUtils.hasSenderAdminPermission(interaction)){
            interaction.createImmediateResponder().setContent("Tento příkaz je pouze pro administrátory serveru.").respond();
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
                        ActionRow.of(Button.secondary("nix-ticket-support", "Podpora", EmojiParser.parseToUnicode(":question:")), Button.secondary("nix-ticket-reclamation", "Reklamace", EmojiParser.parseToUnicode(":wrench:"))),
                        ActionRow.of(Button.secondary("nix-ticket-engrave", "Gravírování", EmojiParser.parseToUnicode(":straight_ruler:")), Button.secondary("nix-ticket-software", "Software", EmojiParser.parseToUnicode(":computer:"))),
                        ActionRow.of(Button.secondary("nix-ticket-sponsor", "Sponzoří", EmojiParser.parseToUnicode(":busts_in_silhouette:")), Button.secondary("nix-ticket-other", "Ostatní", EmojiParser.parseToUnicode(":telephone:")))
                )
                .send(channel);

        interaction.createImmediateResponder().setContent("Sent").respond();
        LogSystem.log(LogType.INFO, "End of command message by '" + interaction.getUser().getName() + "'");

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

                if((!textChannel.asServerTextChannel().isPresent()) ||
                        (!textChannel.asServerTextChannel().get().getCategory().isPresent()) ||
                        (!textChannel.asServerTextChannel().get().getCategory().get().getIdAsString().equals(CommonUtils.SUBMIT_CATEGORY_ID))){
                    interaction.createImmediateResponder().setContent("V tomto kanálu není povolen tento příkaz" + textChannel.asChannelCategory().get().getIdAsString()).setFlags(MessageFlag.EPHEMERAL).respond();
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
        LogSystem.log(LogType.INFO, "End of " + (isResolved ? "resolve" : "close") + " message by '" + interaction.getUser().getName() + "'");
    }

    private static void add(SlashCommandInteraction interaction){
        member(interaction, true);
    }

    private static void remove(SlashCommandInteraction interaction){
        member(interaction, false);
    }

    private static void member(SlashCommandInteraction interaction, boolean add){
        interaction.getArguments().get(0).getStringValue().ifPresent(userNick -> {
            interaction.getServer().ifPresent(server -> {
                interaction.getChannel().ifPresent(textChannel -> {

                    if((!textChannel.asServerTextChannel().isPresent()) ||
                            (!textChannel.asServerTextChannel().get().getCategory().isPresent()) ||
                            (!textChannel.asServerTextChannel().get().getCategory().get().getIdAsString().equals(CommonUtils.SUBMIT_CATEGORY_ID))){
                        interaction.createImmediateResponder().setContent("V tomto kanálu není povolen tento příkaz" + textChannel.asChannelCategory().get().getIdAsString()).setFlags(MessageFlag.EPHEMERAL).respond();
                        return;
                    }

                    Set<User> users = server.getMembersByName(userNick);
                    if(users == null){
                        interaction.createImmediateResponder().setContent("Nebyl nalezen žádný uživatel s tímto nickem").respond();
                        return;
                    }
                    if(users.size() != 1){
                        interaction.createImmediateResponder().setContent("'" + userNick + "' obsahuje v nicku více uživatelů").respond();
                        return;
                    }
                    User user = null;
                    for(User item : users){
                        user = item;
                    }

                    Ticket ticket = CommonUtils.ticketManager.getActiveTickets().get(textChannel.getId());

                    if(ticket == null){
                        interaction.createImmediateResponder().setContent("Tento kanál nebyl nalezen jako aktivní kanál podpory").respond();
                        return;
                    }

                    if((!CommonUtils.isUserAdmin(server, interaction.getUser())) || (ticket.getAuthor().getId() != interaction.getUser().getId())){
                        interaction.createImmediateResponder().setContent("Nejsi administrátor ani autor podpory, tedy nemůžeš použít tento příkaz").respond();
                        return;
                    }

                    if(add){
                        TicketMember member = new TicketMember(user.getId(), user.getName());
                        ticket.getMembers().add(member);

                        if(textChannel.asServerTextChannel().isPresent()){
                            textChannel.asServerTextChannel().get().createUpdater().addPermissionOverwrite(user, Permissions.fromBitmask(1024)).update();
                        }

                        interaction.createImmediateResponder().setContent("Uživatel '" + userNick + "¨ přidán").respond();
                    }
                    else {
                        if(textChannel.asServerTextChannel().isPresent()){
                            textChannel.asServerTextChannel().get().createUpdater().removePermissionOverwrite(user).update();
                        }
                        interaction.createImmediateResponder().setContent("Uživatel '" + userNick + "¨ odebrán").respond();
                    }

                });
            });
        });

        LogSystem.log(LogType.INFO, "End of command " + (add ? "add" : "remove") + " by '" + interaction.getUser().getName() + "'");
    }

}

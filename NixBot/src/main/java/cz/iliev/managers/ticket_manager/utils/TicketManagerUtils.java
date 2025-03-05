package cz.iliev.managers.ticket_manager.utils;

import cz.iliev.managers.ticket_manager.TicketManager;
import cz.iliev.managers.ticket_manager.instances.TicketInstance;
import cz.iliev.managers.ticket_manager.instances.TicketMemberInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.Set;

public class TicketManagerUtils {

    public static void close(SlashCommandInteraction interaction, boolean isResolved){
        interaction.getServer().ifPresent(server -> {
            interaction.getChannel().ifPresent(textChannel -> {
                switch (CommonUtils.ticketManager.closeTicket(server, textChannel, interaction.getUser(), isResolved)){
                    case -1:
                        interaction.createImmediateResponder().setContent("Ticket closed").setFlags(MessageFlag.EPHEMERAL).respond();
                        break;
                    case -2:
                        interaction.createImmediateResponder().setContent("You have no permission to close this ticket").setFlags(MessageFlag.EPHEMERAL).respond();
                        break;
                }
            });
        });
        LogUtils.info("End of " + (isResolved ? "resolve" : "close") + " message by '" + interaction.getUser().getName() + "'");
    }

    public static void member(SlashCommandInteraction interaction, boolean add){
        interaction.getArguments().get(0).getStringValue().ifPresent(userNick -> {
            interaction.getServer().ifPresent(server -> {
                interaction.getChannel().ifPresent(textChannel -> {
                    Set<User> users = server.getMembersByName(userNick);
                    if(users == null){
                        interaction.createImmediateResponder().setContent("No user with this name found").respond();
                        return;
                    }
                    if(users.size() != 1){
                        interaction.createImmediateResponder().setContent("'" + userNick + "' contains more users").respond();
                        return;
                    }
                    User user = null;
                    for(User item : users){
                        user = item;
                    }

                    TicketInstance ticket = CommonUtils.ticketManager.getActiveTickets().get(textChannel.getId());

                    if(ticket == null){
                        interaction.createImmediateResponder().setContent("This channel was not found as active ticket channel").respond();
                        return;
                    }

                    if((!CommonUtils.isUserAdmin(interaction.getUser())) || (ticket.getAuthor().getId() != interaction.getUser().getId())){
                        interaction.createImmediateResponder().setContent("You have no permission to close this ticket").respond();
                        return;
                    }

                    if(add){
                        TicketMemberInstance member = new TicketMemberInstance(user.getId(), user.getName());
                        ticket.getMembers().add(member);

                        if(textChannel.asServerTextChannel().isPresent()){
                            textChannel.asServerTextChannel().get().createUpdater().addPermissionOverwrite(user, Permissions.fromBitmask(1024)).update();
                        }

                        interaction.createImmediateResponder().setContent("Member `" + userNick + "` added").respond();
                        LogUtils.info("Added '" + userNick + "' to ticket '" + (ticket.getId() + "-" + ticket.getAuthor().getName()) + "'");
                    }
                    else {
                        if(textChannel.asServerTextChannel().isPresent()){
                            textChannel.asServerTextChannel().get().createUpdater().removePermissionOverwrite(user).update();
                        }
                        interaction.createImmediateResponder().setContent("Member `" + userNick + "` removed").respond();
                        LogUtils.info("Removed '" + userNick + "' to ticket '" + (ticket.getId() + "-" + ticket.getAuthor().getName()) + "'");
                    }

                });
            });
        });

        LogUtils.info("End of command " + (add ? "add" : "remove") + " by '" + interaction.getUser().getName() + "'");
    }

}

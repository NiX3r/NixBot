package cz.iliev.managers.ticket_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.ticket_manager.commands.*;
import cz.iliev.managers.ticket_manager.enums.TicketStatus;
import cz.iliev.managers.ticket_manager.instances.TicketInstance;
import cz.iliev.managers.ticket_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.HashMap;
import java.util.List;

public class TicketManager implements IManager {

    private boolean ready;
    private HashMap<Long, TicketInstance> activeTickets;

    public static final String TICKET_CHANNEL_ID = "1216822816062701618";
    public static final String TICKET_CATEGORY_ID = "1216859370269311026";

    public TicketManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start InviteManager");
        activeTickets = FileUtils.loadActiveTickets();
        ready = true;
        LogUtils.info("InviteManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill InviteManager");
        FileUtils.saveActiveTickets(activeTickets);
        ready = false;
        LogUtils.info("InviteManager killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {
        switch (interaction.getOptions().get(0).getName()){
            case "message":
                new MessageCommand().run(interaction);
                break;
            case "close":
                if(inTicketCategory(interaction))
                    new CloseCommand().run(interaction);
                break;
            case "resolve":
                if(inTicketCategory(interaction))
                    new ResolveCommand().run(interaction);
                break;
            case "add":
                if(inTicketCategory(interaction))
                    new AddCommand().run(interaction);
                break;
            case "remove":
                if(inTicketCategory(interaction))
                    new RemoveCommand().run(interaction);
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

    private boolean inTicketCategory(SlashCommandInteraction interaction){
        if((!interaction.getServer().isPresent()) || (!interaction.getChannel().isPresent())){
            return false;
        }
        var textChannel = interaction.getChannel().get();
        if((!textChannel.asServerTextChannel().isPresent()) ||
                (!textChannel.asServerTextChannel().get().getCategory().isPresent()) ||
                (!textChannel.asServerTextChannel().get().getCategory().get().getIdAsString().equals(TicketManager.TICKET_CATEGORY_ID))){
            interaction.createImmediateResponder().setContent("Command's not available in this channel" + textChannel.asChannelCategory().get().getIdAsString()).setFlags(MessageFlag.EPHEMERAL).respond();
            return false;
        }
        return true;
    }

    public HashMap<Long, TicketInstance> getActiveTickets(){
        return this.activeTickets;
    }

    public int closeTicket(Server server, TextChannel channel, User user, boolean isResolved){

        LogUtils.info("Trying to close a ticket");
        TicketInstance ticket = activeTickets.get(channel.getId());

        if(ticket == null){
            LogUtils.warning("Ticket not found. Can't find ticket by channel id");
            return -1;
        }

        if(!CommonUtils.isUserAdmin(user) && ticket.getAuthor().getId() != user.getId()){
            LogUtils.warning("User has no permission to close ticket");
            return -2;
        }

        if(isResolved)
            ticket.setStatus(TicketStatus.RESOLVED);
        else
            ticket.setStatus(TicketStatus.CLOSED);

        FileUtils.archiveTicket(ticket);
        removeTicketFromCacheByOwnerId(ticket.getAuthor().getId());

        server.getChannelById(channel.getId()).get().delete().join();

        server.getMemberById(ticket.getAuthor().getId()).ifPresent(serverUser -> {
            serverUser.sendMessage("Your ticket was closed by " + (
                    user.getName().equals(ticket.getAuthor().getName()) ?
                            "you" :
                            ("'" + user.getName() + "'")
            )).join();
        });

        LogUtils.info("Ticket '" + (ticket.getId() + "-" + ticket.getAuthor().getName()) + "' closed");
        return 1;

    }

    private void removeTicketFromCacheByOwnerId(long id){
        for(TicketInstance ticket : activeTickets.values()){
            if(ticket.getAuthor().getId() == id){
                activeTickets.remove(ticket.getChannelId());
            }
        }
    }
}

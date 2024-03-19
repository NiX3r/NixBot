package cz.nix3r.listeners;

import com.vdurmont.emoji.EmojiParser;
import cz.nix3r.enums.TicketStatus;
import cz.nix3r.instances.RoleSetterInstance;
import cz.nix3r.instances.Ticket;
import cz.nix3r.instances.TicketMember;
import cz.nix3r.instances.TicketMessage;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.FileUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.awt.*;
import java.util.ArrayList;

public class nMessageComponentCreateListener implements MessageComponentCreateListener {

    @Override
    public void onComponentCreate(MessageComponentCreateEvent messageComponentCreateEvent) {
        MessageComponentInteraction messageComponentInteraction = messageComponentCreateEvent.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();
        messageComponentInteraction.getServer().ifPresent(server -> {
            messageComponentInteraction.getChannel().ifPresent(textChannel -> {
                if(customId.contains("nix-ticket-")){
                    if(customId.equals("nix-ticket-close") || customId.equals("nix-ticket-resolve"))
                        switch (Ticket.closeTicket(server, textChannel, messageComponentInteraction.getUser(), customId.equals("nix-ticket-resolve"))){
                            case -1:
                                messageComponentInteraction.createImmediateResponder().setContent("Konverzace nenalezena").setFlags(MessageFlag.EPHEMERAL).respond();
                                break;
                            case -2:
                                messageComponentInteraction.createImmediateResponder().setContent("Nemáš právo na uzavření").setFlags(MessageFlag.EPHEMERAL).respond();
                                break;
                        }
                    else
                        createTicket(messageComponentCreateEvent.getInteraction(), customId.replace("nix-ticket-", ""));
                }
                else if(customId.contains("nix-role-")){
                    String roleId = customId.replace("nix-role-", "");
                    RoleSetterInstance setter = CommonUtils.getRoleSetterByRoleId(roleId);
                    if(setter == null){
                        messageComponentInteraction.createImmediateResponder().setContent("Bohužel tato role neexisstuje. Kontaktuj @nix3r").setFlags(MessageFlag.EPHEMERAL).respond();
                        return;
                    }
                    roleSetter(messageComponentCreateEvent.getInteraction(), setter);
                }
            });
        });
    }

    private void roleSetter(Interaction interaction, RoleSetterInstance setter){
        interaction.getServer().ifPresent(server -> {
            server.getRoleById(setter.getRoleId()).ifPresent(role -> {
                boolean done = false;
                for(Role item : interaction.getUser().getRoles(server)){
                    if(item.getId() == role.getId()){
                        interaction.getUser().removeRole(item);
                        interaction.createImmediateResponder().setContent("Role " + item.getMentionTag() + " odebrána").setFlags(MessageFlag.EPHEMERAL).respond();
                        done = true;
                    }
                }
                if(!done){
                    interaction.getUser().addRole(role);
                    interaction.createImmediateResponder().setContent("Role " + role.getMentionTag() + " přidána").setFlags(MessageFlag.EPHEMERAL).respond();
                }
            });
        });
    }

    private void createTicket(Interaction interaction, String type){


        if(!interaction.getServer().isPresent()){
            interaction.createImmediateResponder().setContent("Toto tlačítko je funkční pouze na serveru").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        User user = interaction.getUser();
        Server server = interaction.getServer().get();

        if(CommonUtils.ticketManager.getTicketByOwnerId(user.getId()) != null){
            interaction.createImmediateResponder().setContent("Nemůžeš založt další konverzaci s podporou. První uzavři starši konverzaci.").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        TicketMember author = new TicketMember(user.getId(), user.getName());
        Ticket ticket = new Ticket(CommonUtils.ticketManager.getIndex(),
                author,
                System.currentTimeMillis(),
                -1,
                type,
                TicketStatus.WAITING_FOR_USER,
                new ArrayList<TicketMember>(){{add(author);}},
                new ArrayList<TicketMessage>());

        TextChannel channel = server.createTextChannelBuilder()
                .setCategory(server.getChannelCategoryById(CommonUtils.SUBMIT_CATEGORY_ID).get())
                .setName(ticket.getId() + " " + ticket.getAuthor().getName())
                .setTopic("**Vytvořil:** " + ticket.getAuthor().getName() + " **| Nadpis:** " + ticket.getTopic())
                .addPermissionOverwrite(server.getEveryoneRole(), Permissions.fromBitmask(0, 1024))
                .addPermissionOverwrite(user, Permissions.fromBitmask(1024))
                .create().join();

        ticket.setChannelId(channel.getId());

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#61B23A"))
                .setTitle("Vytvořená konverzace s podporou")
                .setDescription("Tento kanál byl vytvořen za účelem řešení Tvého problému/otázky. Prosím zkus co nejvíce přiblíž co potřebuješ. Díky")
                .setFooter("Version: " + CommonUtils.version);

        new MessageBuilder()
                .setEmbed(builder)
                .addComponents(
                        ActionRow.of(Button.secondary("nix-ticket-close", "Zavřit konverzaci", EmojiParser.parseToUnicode(":no_entry_sign:"))),
                        ActionRow.of(Button.secondary("nix-ticket-resolve", "Označit konverzaci za vyřešenou", EmojiParser.parseToUnicode(":white_check_mark:")))
                )
                .send(channel);

        CommonUtils.ticketManager.addTicket(ticket);
        CommonUtils.ticketManager.updateIndex();
        interaction.createImmediateResponder().setContent("Vytvořená konverzace s podporou. Můžeš kliknout zde " + channel.asServerTextChannel().get().getMentionTag()).setFlags(MessageFlag.EPHEMERAL).respond();

    }

}

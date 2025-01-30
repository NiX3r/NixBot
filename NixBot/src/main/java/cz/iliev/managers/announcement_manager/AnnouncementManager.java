package cz.iliev.managers.announcement_manager;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.interfaces.IManager;
import cz.iliev.managers.announcement_manager.commands.AnnouncementCommand;
import cz.iliev.managers.announcement_manager.instances.MessagesInstance;
import cz.iliev.managers.announcement_manager.listeners.AnnouncementServerMemberBanListener;
import cz.iliev.managers.announcement_manager.listeners.AnnouncementServerMemberLeaveListener;
import cz.iliev.managers.announcement_manager.utils.AnnouncementManagerUtils;
import cz.iliev.managers.announcement_manager.utils.FileUtils;
import cz.iliev.managers.command_manager.CommandManager;
import cz.iliev.managers.music_manager.instances.SongInstance;
import cz.iliev.managers.role_manager.RoleManager;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.managers.statistics_manager.listeners.StatisticsManagerServerMemberLeaveListener;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.MessageUpdater;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.List;

public class AnnouncementManager implements IManager {

    private boolean ready;
    private MessagesInstance messages;

    private final String WELCOME_CHANNEL_ID = "611985124057284621";
    public static final String WEATHER_CHANNEL_ID = "1315993344475791470";
    public static final String NEWS_CHANNEL_ID = "1219218631632748655";
    public static final String NIXBOT_CHANNEL_ID = "1058017127988211822";

    public AnnouncementManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start AnnouncementManager");
        messages = FileUtils.loadMessages();
        CommonUtils.bot.addServerMemberBanListener(new AnnouncementServerMemberBanListener());
        CommonUtils.bot.addServerMemberLeaveListener(new AnnouncementServerMemberLeaveListener());
        ready = true;
        LogUtils.info("AnnouncementManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("kill AnnouncementManager");
        FileUtils.saveMessages(messages);
        ready = false;
        LogUtils.info("AnnouncementManager killed");
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
            case "announcement":
                new AnnouncementCommand().run(interaction);
                break;
        }
    }

    @Override
    public void onConsoleCommand(Object data) {
        String[] command = ((String)data).split(" ");

        switch (command[0]){
            case "restart": case "r":
                sendRestart(command);
                break;
        }
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "Announcement manager";
    }

    @Override
    public String managerDescription() {
        return "Manager for sending announcements\nFeatures:\n- Restart announcement\n- Exception embed\n- Current song playing\n- Role setter\n- Welcome message";
    }

    @Override
    public String color() {
        return "#c52ae0";
    }

    private void sendRestart(String[] command){

        if(command.length != 3){
            LogUtils.warning("Bad command usage. Usage: 'announcements restart <time to restart>'");
            return;
        }

        CommonUtils.getNixCrew().getTextChannelById(NEWS_CHANNEL_ID).ifPresent(textChannel -> {

            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("Restart bota")
                    .setDescription("Dojde k automatickému restartu bota.")
                    .addField("Restart za", command[2] + " minut")
                    .setColor(Color.GREEN)
                    .setThumbnail("https://pluspng.com/img-png/restart-png-restart-icon-1600.png")
                    .setFooter("Console | Version: " + CommonUtils.VERSION);

            textChannel.sendMessage(builder);

        });
    }
    
    public void sendException(Exception exception, boolean isFatal){
        CommonUtils.getNixCrew().getTextChannelById(NIXBOT_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(AnnouncementManagerUtils.createExceptionEmbed(exception, isFatal));
        });
    }

    public void sendCurrentSong(SongInstance song){
        CommonUtils.getNixCrew().getTextChannelById(CommandManager.CMD_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(AnnouncementManagerUtils.createCurrentSongEmbed(song));
        });
    }

    public void sendRoleSetter(List<RoleSetterInstance> roleSetter){
        CommonUtils.getNixCrew().getTextChannelById(RoleManager.ROLES_CHANNEL_ID).ifPresent(channel -> {
            Message msg = null;
            try{
                msg = channel.getMessageById(CommonUtils.settings.getRolesMessageId()).get();
            }
            catch (Exception ex){
                LogUtils.warning("Role message does not exists. Creating a new one");
            }
            if(msg == null){
                MessageBuilder builder = new MessageBuilder()
                        .setEmbed(AnnouncementManagerUtils.createRoleSetterEmbed(roleSetter.size()));
                roleSetter.forEach(setter -> {
                    builder.addComponents(
                            ActionRow.of(Button.secondary("nix-role-" + setter.getRoleId(), setter.getComponentLabel(), EmojiParser.parseToUnicode(setter.getEmoji())))
                    );
                });
                msg = builder.send(channel).join();
                CommonUtils.settings.setRolesMessageId(msg.getId());
                LogUtils.info("Role message crated and sent. Message ID saved");
            }
            else{
                MessageUpdater updater = msg.createUpdater();
                updater.removeAllComponents().removeAllEmbeds()
                        .addEmbed(AnnouncementManagerUtils.createRoleSetterEmbed(roleSetter.size()));

                roleSetter.forEach(setter -> {
                    updater.addComponents(
                            ActionRow.of(Button.secondary("nix-role-" + setter.getRoleId(), setter.getComponentLabel(), EmojiParser.parseToUnicode(setter.getEmoji())))
                    );
                });

                updater.applyChanges().join();
                LogUtils.info("Role message updated");
            }
        });
    }

    public void sendWelcome(String user, Icon avatar, String inviter){
        var server = CommonUtils.getNixCrew();
        var embed = AnnouncementManagerUtils.createJoinEmbed(user, inviter, avatar, server);
        server.getTextChannelById(WELCOME_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(embed);
        });
    }

    public void sendLeave(String user, Icon avatar, boolean isBan){

        var server = CommonUtils.getNixCrew();
        var embed = AnnouncementManagerUtils.createLeaveEmbed(user, avatar, isBan, server);
        server.getTextChannelById(WELCOME_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(embed).join();
        });
    }

    public void sendRestart(String minutes){
        var server = CommonUtils.getNixCrew();
        var embed = AnnouncementManagerUtils.createRestartAnnouncementEmbed(minutes);
        server.getTextChannelById(NEWS_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(embed);
        });
    }

    public void sendWeather(String color, String avgTemp, String avgFeels){
        var server = CommonUtils.getNixCrew();
        var embed = AnnouncementManagerUtils.createWeatherEmbed(color, avgTemp, avgFeels);
        server.getTextChannelById(WEATHER_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(embed);
        });
    }

    public MessagesInstance getMessages() {
        return messages;
    }
}

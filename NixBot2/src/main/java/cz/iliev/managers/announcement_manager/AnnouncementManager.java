package cz.iliev.managers.announcement_manager;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.interfaces.IManager;
import cz.iliev.managers.announcement_manager.utils.AnnouncementManagerUtils;
import cz.iliev.managers.command_manager.CommandManager;
import cz.iliev.managers.music_manager.instances.SongInstance;
import cz.iliev.managers.role_manager.RoleManager;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.MessageUpdater;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public class AnnouncementManager implements IManager {

    private boolean ready;

    private final String WELCOME_CHANNEL_ID = "611985124057284621";
    private final String NEWS_CHANNEL_ID = "1219218631632748655";
    private final String NIXBOT_CHANNEL_ID = "1058017127988211822";

    public AnnouncementManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start AnnouncementManager");

        ready = true;
        LogUtils.info("AnnouncementManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {

    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {

    }

    @Override
    public void onConsoleCommand(Object data) {

    }

    @Override
    public boolean isReady() {
        return ready;
    }

    public void sendException(Exception exception, boolean isFatal){
        CommonUtils.bot.getServers().forEach(server -> {
            if(!server.getIdAsString().equals(CommonUtils.NIX_CREW_ID)){
                LogUtils.warning("Leaving server '" + server.getName() + "'");
                server.leave().join();
            }
            else{
                server.getTextChannelById(NIXBOT_CHANNEL_ID).ifPresent(channel -> {
                    channel.sendMessage(AnnouncementManagerUtils.createExceptionEmbed(exception, isFatal));
                });
            }
        });
    }

    public void sendCurrentSong(SongInstance song){
        CommonUtils.bot.getServers().forEach(server -> {
            server.getTextChannelById(CommandManager.CMD_CHANNEL_ID).ifPresent(channel -> {
                channel.sendMessage(AnnouncementManagerUtils.createCurrentSongEmbed(song));
            });
        });
    }

    public void sendRoleSetter(List<RoleSetterInstance> roleSetter){
        CommonUtils.bot.getServers().forEach(server -> {
            server.getTextChannelById(RoleManager.ROLES_CHANNEL_ID).ifPresent(channel -> {
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
        });
    }

}

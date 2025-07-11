package cz.iliev.managers.ban_list_manager;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.interfaces.IManager;
import cz.iliev.managers.ban_list_manager.commands.BanCommand;
import cz.iliev.managers.ban_list_manager.commands.KickCommand;
import cz.iliev.managers.ban_list_manager.commands.MuteCommand;
import cz.iliev.managers.ban_list_manager.commands.UnbanCommand;
import cz.iliev.managers.ban_list_manager.enums.BanType;
import cz.iliev.managers.ban_list_manager.instances.MemberInstance;
import cz.iliev.managers.ban_list_manager.instances.PunishmentInstance;
import cz.iliev.managers.ban_list_manager.listeners.BanListManagerMessageComponentCreateListener;
import cz.iliev.managers.ban_list_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BanListManager implements IManager {

    private boolean ready;
    private HashMap<Long, PunishmentInstance> bans;
    private HashMap<Long, PunishmentInstance> banCache;

    public BanListManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());
        bans = FileUtils.loadActiveBans();
        banCache = new HashMap<Long, PunishmentInstance>();
        CommonUtils.bot.addMessageComponentCreateListener(new BanListManagerMessageComponentCreateListener());
        LogUtils.info("Check real bans with load active bans");
        var server = CommonUtils.getNixCrew();
        server.getBans().join().forEach(ban -> {
            LogUtils.debug("Check ban of '" + ban.getUser().getName() + "'");
            if(!bans.containsKey(ban.getUser().getId())){
                LogUtils.info("Member '" + ban.getUser().getName() + "' was unbanned");
                server.unbanUser(ban.getUser().getId());
            }
        });

        ready = true;
        LogUtils.info(managerName() + " loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill " + managerName());
        ready = false;
        LogUtils.info(managerName() + " killed");
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
            case "ban":
                new BanCommand().run(interaction);
                break;
            case "unban":
                new UnbanCommand().run(interaction);
                break;
            case "kick":
                new KickCommand().run(interaction);
                break;
            case "mute":
                new MuteCommand().run(interaction);
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

    @Override
    public String managerName() {
        return "Ban list manager";
    }

    @Override
    public String managerDescription() {
        return "Manager to view ban list\nFeatures: \n- Bans\n- Unbans\n- Kicks\n- Mutes";
    }

    @Override
    public String color() {
        return "#8c0404";
    }

    public HashMap<Long, PunishmentInstance> getBans() { return this.bans; }

    public HashMap<Long, PunishmentInstance> getBanCache() {
        return banCache;
    }


    public boolean addCachePunishment(User toBan, User admin, String reason, long duration, BanType type){
        var server = CommonUtils.getNixCrew();

        var toBanRoles = new ArrayList<String>();
        toBan.getRoles(server).forEach(role -> toBanRoles.add(role.getName()));

        var adminRoles = new ArrayList<String>();
        toBan.getRoles(server).forEach(role -> adminRoles.add(role.getName()));

        var ban = new PunishmentInstance(
                type,
                new MemberInstance(
                        toBan.getId(),
                        toBan.getName(),
                        toBanRoles
                ),
                new MemberInstance(
                        admin.getId(),
                        admin.getName(),
                        adminRoles
                ),
                System.currentTimeMillis(),
                duration,
                reason
        );

        if(CommonUtils.banListManager.getBanCache().containsKey(ban.getMember().getMemberId())){
            return false;
        }
        CommonUtils.banListManager.getBanCache().put(ban.getMember().getMemberId(), ban);
        return true;
    }
}

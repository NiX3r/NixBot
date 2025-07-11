package cz.iliev.managers.security_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.security_manager.instances.UserElo;
import cz.iliev.managers.security_manager.interfaces.IScam;
import cz.iliev.managers.security_manager.listeners.SecurityManagerUserChangeNameListener;
import cz.iliev.managers.security_manager.listeners.SecurityManagerUserChangeNicknameListener;
import cz.iliev.managers.security_manager.utils.FileUtils;
import cz.iliev.managers.security_manager.utils.SecurityManagerUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.Dictionary;
import java.util.HashMap;

public class SecurityManager implements IManager {

    private boolean ready;
    private HashMap<Long, UserElo> usersELO;

    public static final String PUNISH_CHANNEL_ID = "1369567897067847760";

    public SecurityManager() { setup(); }

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());
        usersELO = new HashMap<Long, UserElo>();
        FileUtils.loadUsersElo().forEach(elo -> usersELO.put(elo.getUserId(), elo));
        if(usersELO == null || usersELO.isEmpty())
            usersELO = new HashMap<Long, UserElo>();
        CommonUtils.bot.addUserChangeNameListener(new SecurityManagerUserChangeNameListener());
        CommonUtils.bot.addUserChangeNicknameListener(new SecurityManagerUserChangeNicknameListener());
        ready = true;
        LogUtils.info(managerName() + " loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill " + managerName());
        FileUtils.saveUsersElo(usersELO.values().stream().toList());

        LogUtils.info("Initializing " + managerName() + " listeners");
        CommonUtils.bot.addUserChangeNameListener(new SecurityManagerUserChangeNameListener());
        CommonUtils.bot.addUserChangeNicknameListener(new SecurityManagerUserChangeNicknameListener());

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

    }

    @Override
    public void onConsoleCommand(Object data) {

    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "SecurityManager";
    }

    @Override
    public String managerDescription() {
        return "Manager for determines users credibility. In case of low ELO ban user";
    }

    @Override
    public String color() {
        return "#eda81e";
    }

    public void addElo(long userId, int elo){
        if(usersELO.containsKey(userId)){
            usersELO.get(userId).addElo(elo);
        }
        else{
            usersELO.put(userId, new UserElo(userId, 1000 + elo));
        }
    }

    public void sendAnnouncement(IScam scam, User user){
        CommonUtils.getNixCrew().getTextChannelById(PUNISH_CHANNEL_ID).ifPresent(channel -> {
            int currentElo = usersELO.get(user.getId()).getElo();
            channel.sendMessage(SecurityManagerUtils.createPunishLogEmbed(scam, user, currentElo));
        });
    }
}

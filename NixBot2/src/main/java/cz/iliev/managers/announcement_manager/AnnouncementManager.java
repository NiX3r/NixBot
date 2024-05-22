package cz.iliev.managers.announcement_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.announcement_manager.utils.AnnouncementManagerUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommandInteraction;

public class AnnouncementManager implements IManager {

    private boolean ready;

    private final String WELCOME_CHANNEL_ID = "611985124057284621";
    private final String NIXBOT_CHANNEL_ID = "1058017127988211822";

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

}

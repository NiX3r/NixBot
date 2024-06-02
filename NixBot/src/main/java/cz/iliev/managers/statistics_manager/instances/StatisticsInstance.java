package cz.iliev.managers.statistics_manager.instances;

import java.util.HashMap;

public class StatisticsInstance {

    private long currentTime;
    private CommandStatsInstance commandStatsInstance;
    private ManagerStatsInstance managerStatsInstance;
    private MemberStatsInstance memberStatsInstance;
    private ServerStatsInstance serverStatsInstance;
    private TextChannelStatsInstance textChannelStatsInstance;
    private VoiceChannelStatsInstance voiceChannelStatsInstance;

    // Key = user id | Value = join time
    private HashMap<Long, Long> memberJoinVoiceTime;

    public StatisticsInstance(long currentTime, CommandStatsInstance commandStatsInstance, ManagerStatsInstance managerStatsInstance, MemberStatsInstance memberStatsInstance, ServerStatsInstance serverStatsInstance, TextChannelStatsInstance textChannelStatsInstance, VoiceChannelStatsInstance voiceChannelStatsInstance, HashMap<Long, Long> memberJoinVoiceTime) {
        this.currentTime = currentTime;
        this.commandStatsInstance = commandStatsInstance;
        this.managerStatsInstance = managerStatsInstance;
        this.memberStatsInstance = memberStatsInstance;
        this.serverStatsInstance = serverStatsInstance;
        this.textChannelStatsInstance = textChannelStatsInstance;
        this.voiceChannelStatsInstance = voiceChannelStatsInstance;
        this.memberJoinVoiceTime = memberJoinVoiceTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public ManagerStatsInstance getManagerStatsInstance() {
        return managerStatsInstance;
    }

    public void setManagerStatsInstance(ManagerStatsInstance managerStatsInstance) {
        this.managerStatsInstance = managerStatsInstance;
    }

    public MemberStatsInstance getMemberStatsInstance() {
        return memberStatsInstance;
    }

    public void setMemberStatsInstance(MemberStatsInstance memberStatsInstance) {
        this.memberStatsInstance = memberStatsInstance;
    }

    public ServerStatsInstance getServerStatsInstance() {
        return serverStatsInstance;
    }

    public void setServerStatsInstance(ServerStatsInstance serverStatsInstance) {
        this.serverStatsInstance = serverStatsInstance;
    }

    public TextChannelStatsInstance getTextChannelStatsInstance() {
        return textChannelStatsInstance;
    }

    public void setTextChannelStatsInstance(TextChannelStatsInstance textChannelStatsInstance) {
        this.textChannelStatsInstance = textChannelStatsInstance;
    }

    public VoiceChannelStatsInstance getVoiceChannelStatsInstance() {
        return voiceChannelStatsInstance;
    }

    public void setVoiceChannelStatsInstance(VoiceChannelStatsInstance voiceChannelStatsInstance) {
        this.voiceChannelStatsInstance = voiceChannelStatsInstance;
    }

    public CommandStatsInstance getCommandStatsInstance() {
        return commandStatsInstance;
    }

    public void setCommandStatsInstance(CommandStatsInstance commandStatsInstance) {
        this.commandStatsInstance = commandStatsInstance;
    }

    public HashMap<Long, Long> getMemberJoinVoiceTime() {
        return memberJoinVoiceTime;
    }

    public void setMemberJoinVoiceTime(HashMap<Long, Long> memberJoinVoiceTime) {
        this.memberJoinVoiceTime = memberJoinVoiceTime;
    }
}

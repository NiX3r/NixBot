package cz.iliev.managers.statistics_manager.instances;

import java.util.HashMap;

public class StatisticsInstance {

    public long currentTime;

    // Server boost counter
    public long boost;
    public long boostMonth;
    public long boostYear;

    // Call time counter
    public long callTime;
    public long callTimeDay;
    public long callTimeMonth;

    // Command use counter (counts each command name)
    public HashMap<String, Long> commandUse;
    public HashMap<String, Long> commandUseMonth;
    public HashMap<String, Long> commandUseYear;

    // Server member join counter
    public long memberJoin;
    public long memberJoinMonth;
    public long memberJoinYear;

    // Server member leave counter
    public long memberLeave;
    public long memberLeaveMonth;
    public long memberLeaveYear;

    // Songs play counter
    public long musicPlay;
    public long musicPlayMonth;

    // Play time counter
    public long musicPlayTime;
    public long musicPlayTimeDay;
    public long musicPlayTimeMonth;

    // Server reaction (counts each reaction emoji)
    public HashMap<String, Long> reaction;
    public HashMap<String, Long> reactionDay;
    public HashMap<String, Long> reactionMonth;

    // Temporary channel created
    public long temporaryChannel;
    public long temporaryChannelMonth;

    // Text send
    public long text;
    public long textDay;
    public long textMonth;

    // Text channel used (counts each text channel id)
    public HashMap<Long, Long> textChannel;
    public HashMap<Long, Long> textChannelMonth;

    // Ticket created
    public long ticket;
    public long ticketDay;
    public long ticketMonth;

    // User activity (counts each user)
    public HashMap<Long, HashMap<String, Long>> userActivity;
    public HashMap<Long, HashMap<String, Long>> userActivityMonth;
    public HashMap<Long, HashMap<String, Long>> userActivityYear;

    // User activity time (counts each user)
    public HashMap<Long, HashMap<String, Long>> userActivityTime;
    public HashMap<Long, HashMap<String, Long>> userActivityTimeDay;
    public HashMap<Long, HashMap<String, Long>> userActivityTimeMonth;

    // User call time (counts each user)
    public HashMap<Long, Long> userCallTime;
    public HashMap<Long, Long> userCallTimeDay;
    public HashMap<Long, Long> userCallTimeMonth;
    public HashMap<Long, Long> userCallTimeYear;

    // User use command (counts each user with each command)
    public HashMap<Long, HashMap<String, Long>> userCommandUse;
    public HashMap<Long, HashMap<String, Long>> userCommandUseMonth;
    public HashMap<Long, HashMap<String, Long>> userCommandUseYear;

    // User react (counts each user with each reaction emoji)
    public HashMap<Long, HashMap<String, Long>> userReaction;
    public HashMap<Long, HashMap<String, Long>> userReactionMonth;
    public HashMap<Long, HashMap<String, Long>> userReactionYear;

    // User text (counts each user)
    public HashMap<Long, Long> userText;
    public HashMap<Long, Long> userTextMonth;
    public HashMap<Long, Long> userTextYear;

    // Voice channel use (counts each voice channel)
    public HashMap<Long, Long> voiceChannel;
    public HashMap<Long, Long> voiceChannelMonth;
    public HashMap<Long, Long> voiceChannelYear;

    public StatisticsInstance(long currentTime, long boost, long boostMonth, long boostYear, long callTime, long callTimeDay,
                              long callTimeMonth, HashMap<String, Long> commandUse, HashMap<String, Long> commandUseMonth,
                              HashMap<String, Long> commandUseYear, long memberJoin, long memberJoinMonth, long memberJoinYear,
                              long memberLeave, long memberLeaveMonth, long memberLeaveYear, long musicPlay, long musicPlayMonth,
                              long musicPlayTime, long musicPlayTimeDay, HashMap<String, Long> reaction,
                              HashMap<String, Long> reactionDay, HashMap<String, Long> reactionMonth, long temporaryChannel,
                              long temporaryChannelMonth, long text, long textDay, long textMonth, HashMap<Long, Long> textChannel,
                              HashMap<Long, Long> textChannelMonth, long ticket, long ticketDay, long ticketMonth,
                              HashMap<Long, HashMap<String, Long>> userActivity, HashMap<Long, HashMap<String, Long>> userActivityMonth,
                              HashMap<Long, HashMap<String, Long>> userActivityYear, HashMap<Long, HashMap<String, Long>> userActivityTime,
                              HashMap<Long, HashMap<String, Long>> userActivityTimeDay, HashMap<Long, HashMap<String, Long>> userActivityTimeMonth,
                              HashMap<Long, Long> userCallTime, HashMap<Long, Long> userCallTimeDay,
                              HashMap<Long, Long> userCallTimeMonth, HashMap<Long, Long> userCallTimeYear,
                              HashMap<Long, HashMap<String, Long>> userCommandUse,
                              HashMap<Long, HashMap<String, Long>> userCommandUseMonth,
                              HashMap<Long, HashMap<String, Long>> userCommandUseYear,
                              HashMap<Long, HashMap<String, Long>> userReaction,
                              HashMap<Long, HashMap<String, Long>> userReactionMonth,
                              HashMap<Long, HashMap<String, Long>> userReactionYear, HashMap<Long, Long> userText,
                              HashMap<Long, Long> userTextMonth, HashMap<Long, Long> userTextYear, HashMap<Long, Long> voiceChannel,
                              HashMap<Long, Long> voiceChannelMonth, HashMap<Long, Long> voiceChannelYear) {
        this.currentTime = currentTime;
        this.boost = boost;
        this.boostMonth = boostMonth;
        this.boostYear = boostYear;
        this.callTime = callTime;
        this.callTimeDay = callTimeDay;
        this.callTimeMonth = callTimeMonth;
        this.commandUse = commandUse;
        this.commandUseMonth = commandUseMonth;
        this.commandUseYear = commandUseYear;
        this.memberJoin = memberJoin;
        this.memberJoinMonth = memberJoinMonth;
        this.memberJoinYear = memberJoinYear;
        this.memberLeave = memberLeave;
        this.memberLeaveMonth = memberLeaveMonth;
        this.memberLeaveYear = memberLeaveYear;
        this.musicPlay = musicPlay;
        this.musicPlayMonth = musicPlayMonth;
        this.musicPlayTime = musicPlayTime;
        this.musicPlayTimeDay = musicPlayTimeDay;
        this.reaction = reaction;
        this.reactionDay = reactionDay;
        this.reactionMonth = reactionMonth;
        this.temporaryChannel = temporaryChannel;
        this.temporaryChannelMonth = temporaryChannelMonth;
        this.text = text;
        this.textDay = textDay;
        this.textMonth = textMonth;
        this.textChannel = textChannel;
        this.textChannelMonth = textChannelMonth;
        this.ticket = ticket;
        this.ticketDay = ticketDay;
        this.ticketMonth = ticketMonth;
        this.userActivity = userActivity;
        this.userActivityMonth = userActivityMonth;
        this.userActivityYear = userActivityYear;
        this.userActivityTime = userActivityTime;
        this.userActivityTimeDay = userActivityTimeDay;
        this.userActivityTimeMonth = userActivityTimeMonth;
        this.userCallTime = userCallTime;
        this.userCallTimeDay = userCallTimeDay;
        this.userCallTimeMonth = userCallTimeMonth;
        this.userCallTimeYear = userCallTimeYear;
        this.userCommandUse = userCommandUse;
        this.userCommandUseMonth = userCommandUseMonth;
        this.userCommandUseYear = userCommandUseYear;
        this.userReaction = userReaction;
        this.userReactionMonth = userReactionMonth;
        this.userReactionYear = userReactionYear;
        this.userText = userText;
        this.userTextMonth = userTextMonth;
        this.userTextYear = userTextYear;
        this.voiceChannel = voiceChannel;
        this.voiceChannelMonth = voiceChannelMonth;
        this.voiceChannelYear = voiceChannelYear;
    }
}

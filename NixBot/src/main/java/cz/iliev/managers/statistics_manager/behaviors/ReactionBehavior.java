package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class ReactionBehavior {
    public static void behave(String emoji){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing reaction statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.reaction.put(emoji, stats.reaction.getOrDefault(emoji, 0L) + 1);
        stats.reactionDay.put(emoji, stats.reactionDay.getOrDefault(emoji, 0L) + 1);
        stats.reactionMonth.put(emoji, stats.reactionMonth.getOrDefault(emoji, 0L) + 1);

        LogUtils.info("Increased done");
    }
}

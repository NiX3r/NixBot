package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.util.HashMap;

public class UserReactionBehavior {
    public static void behave(long userId, String emoji){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing user reaction statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        if(stats.userReaction.containsKey(userId))
            stats.userReaction.get(userId).put(emoji, stats.userReaction.get(userId).getOrDefault(emoji, 0L) + 1);
        else
            stats.userReaction.put(userId, new HashMap<>() {{ put( emoji, 1L );}});

        if(stats.userReactionMonth.containsKey(userId))
            stats.userReactionMonth.get(userId).put(emoji, stats.userReactionMonth.get(userId).getOrDefault(emoji, 0L) + 1);
        else
            stats.userReactionMonth.put(userId, new HashMap<>() {{ put( emoji, 1L );}});

        if(stats.userReactionYear.containsKey(userId))
            stats.userReactionYear.get(userId).put(emoji, stats.userReactionYear.get(userId).getOrDefault(emoji, 0L) + 1);
        else
            stats.userReactionYear.put(userId, new HashMap<>() {{ put( emoji, 1L );}});

        LogUtils.info("Increased done");
    }
}

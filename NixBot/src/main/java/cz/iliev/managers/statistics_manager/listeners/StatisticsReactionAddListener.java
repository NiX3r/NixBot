package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.statistics_manager.behaviors.ReactionBehavior;
import cz.iliev.managers.statistics_manager.behaviors.UserReactionBehavior;
import cz.iliev.utils.LogUtils;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

public class StatisticsReactionAddListener implements ReactionAddListener {
    @Override
    public void onReactionAdd(ReactionAddEvent reactionAddEvent) {
        ReactionBehavior.behave(reactionAddEvent.getEmoji().toString());
        UserReactionBehavior.behave(reactionAddEvent.getUserId(), reactionAddEvent.getEmoji().toString());
        LogUtils.info("Reaction statistics updated");
    }
}
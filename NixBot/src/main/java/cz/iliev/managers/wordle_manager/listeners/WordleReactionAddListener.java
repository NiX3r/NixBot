package cz.iliev.managers.wordle_manager.listeners;

import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.event.interaction.InteractionCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

public class WordleReactionAddListener implements ReactionAddListener {
    @Override
    public void onReactionAdd(ReactionAddEvent reactionAddEvent) {

        if(!CommonUtils.wordleManager.isGameExistsByMessageId(reactionAddEvent.getMessageId())){
            return;
        }

        if((!reactionAddEvent.getUser().isPresent()) || (!reactionAddEvent.getMessage().isPresent())){
            reactionAddEvent.removeReaction().join();
            return;
        }
        var user = reactionAddEvent.getUser().get();
        var message = reactionAddEvent.getMessage().get();
        var game = CommonUtils.wordleManager.getGameByUserId(user.getId());

        if(game == null || (game.getUserId() != user.getId()) || (!reactionAddEvent.getEmoji().isUnicodeEmoji())){
            reactionAddEvent.removeReaction().join();
            return;
        }

        var emoji = reactionAddEvent.getEmoji().asUnicodeEmoji().get();
        if(!emoji.contains(":regional_indicator_")){
            reactionAddEvent.removeReaction().join();
            return;
        }

        var letter = emoji.replace(":regional_indicator_", "").replace(":", "").charAt(0);
        var response = game.guess(letter);

        if(response == 1 || response == -1){
            reactionAddEvent.removeAllReactionsFromMessage();
            var embed = game.createFinishEmbed(user, response == 1);
            message.createUpdater().removeAllEmbeds().setContent("").setEmbed(embed).applyChanges().join();
            CommonUtils.wordleManager.removeGameByUserId(user.getId());
        }
        else if(response == 0 || response == -3){
            var hidden = game.generateHiddenWord();
            var embed = game.createEmbed(user, hidden);
            var msg = response == 0 ? "Good guess. You're a bit closer to win this game"
                    : "You already tried this letter. Please guess different letter";
            message.createUpdater().setEmbed(embed).setContent("# `" + hidden + "`\n" + msg).applyChanges();
        }

    }
}

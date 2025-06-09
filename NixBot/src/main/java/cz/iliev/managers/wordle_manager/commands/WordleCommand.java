package cz.iliev.managers.wordle_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.wordle_manager.instances.WordleGameInstance;
import cz.iliev.managers.wordle_manager.instances.WordleWordInstance;
import cz.iliev.managers.wordle_manager.utils.WordPickerUtils;
import cz.iliev.managers.wordle_manager.utils.WordleUtils;
import cz.iliev.utils.CommonUtils;
import net.fellbaum.jemoji.EmojiGroup;
import net.fellbaum.jemoji.EmojiManager;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.ArrayList;

public class WordleCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {

        var user = interaction.getUser();
        var game = CommonUtils.wordleManager.getGameByUserId(user.getId());

        // Game not exists. Creating new one
        if(game == null){

            var word = WordPickerUtils.GetWord(0, 99, "cs-cz");
            var newGame = new WordleGameInstance(user.getId(), word, System.currentTimeMillis(), 0);
            var hiddenWord = newGame.generateHiddenWord();

            var message = interaction.getChannel().get().sendMessage("# `" + hiddenWord + "`", newGame.createEmbed(user, hiddenWord)).join();
            newGame.setMessageId(message.getId());
            for(char c = 97; c < 123; c++){
                message.addReaction(WordleUtils.LetterToEmojiMap.get(c));
            }

            interaction.createImmediateResponder().setContent("Game created: " + message.getLink()).setFlags(MessageFlag.EPHEMERAL).respond();
            CommonUtils.wordleManager.putNewGame(newGame);

        }
        // Game already exists. Guessing a letter
        else{

            var message = interaction.getChannel().get().getMessageById(game.getMessageId()).join();
            interaction.createImmediateResponder().setContent("You have already open game. Please refer here: " + message.getLink()).setFlags(MessageFlag.EPHEMERAL).respond();

        }

    }

}

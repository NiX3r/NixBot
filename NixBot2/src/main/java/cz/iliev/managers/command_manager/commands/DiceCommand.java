package cz.iliev.managers.command_manager.commands;

import cz.iliev.managers.command_manager.interfaces.ISlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.Random;

public class DiceCommand implements ISlashCommand {
    private static Random random = new Random();
    @Override
    public void run(SlashCommandInteraction interaction) {
        int messageId = random.nextInt(10);

        if(interaction.getArguments().size() == 0){
            int rolled = random.nextInt(6) + 1;
            interaction.createImmediateResponder().setContent(CommonUtils.messages.getRolledDiceMessages().get(messageId).replace("%i%", String.valueOf(rolled))).respond();
        }
        else {
            long arg = interaction.getArgumentLongValueByIndex(0).get();
            if(arg > Integer.MAX_VALUE)
                arg = Integer.MAX_VALUE;
            if(arg < 2)
                arg = 2;
            int rolled = random.nextInt(Integer.valueOf((int)arg)) + 1;
            interaction.createImmediateResponder().setContent(CommonUtils.messages.getRolledDiceMessages().get(messageId).replace("%i%", String.valueOf(rolled))).respond();
        }
    }
}

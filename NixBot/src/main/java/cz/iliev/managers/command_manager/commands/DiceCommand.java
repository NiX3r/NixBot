package cz.iliev.managers.command_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.Random;

public class DiceCommand implements ISlashCommand {
    private static final Random random = new Random();
    @Override
    public void run(SlashCommandInteraction interaction) {
        int messageId = random.nextInt(10);

        if(interaction.getArguments().isEmpty()){
            int rolled = random.nextInt(6) + 1;
            interaction.createImmediateResponder().setContent(CommonUtils.commandManager.getDiceMessages().get(messageId).replace("%i%", String.valueOf(rolled))).respond();
        }
        else {
            long arg = interaction.getArgumentLongValueByIndex(0).get();
            if(arg > Integer.MAX_VALUE)
                arg = Integer.MAX_VALUE;
            if(arg < 2)
                arg = 2;
            int rolled = random.nextInt(Integer.valueOf((int)arg)) + 1;
            interaction.createImmediateResponder().setContent(CommonUtils.commandManager.getDiceMessages().get(messageId).replace("%i%", String.valueOf(rolled))).respond();
        }

        LogUtils.info("End of dice command by '" + interaction.getUser().getName() + "'");
    }
}

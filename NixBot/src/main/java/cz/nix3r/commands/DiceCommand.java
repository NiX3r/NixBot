package cz.nix3r.commands;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.Random;

public class DiceCommand {

    private static Random random = new Random();

    public static void run(SlashCommandInteraction interaction) {

        int messageId = random.nextInt(10);

        if(interaction.getArguments().size() == 0){
            int rolled = random.nextInt(6) + 1;
            interaction.createImmediateResponder().setContent(CommonUtils.ROLLED_DICE[messageId].replace("%i%", String.valueOf(rolled))).respond();
        }
        else {
            long arg = interaction.getArgumentLongValueByIndex(0).get();
            if(arg > Integer.MAX_VALUE)
                arg = Integer.MAX_VALUE;
            if(arg < 2)
                arg = 2;
            int rolled = random.nextInt(Integer.valueOf((int)arg)) + 1;
            interaction.createImmediateResponder().setContent(CommonUtils.ROLLED_DICE[messageId].replace("%i%", String.valueOf(rolled))).respond();
        }

        LogSystem.log(LogType.INFO, "End of command dice by '" + interaction.getUser().getName() + "'");

    }
}

package cz.iliev.managers.command_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SummonCommand  implements ISlashCommand {

    private final String BOOSTER_ROLE_ID = "765903763470549003";

    @Override
    public void run(SlashCommandInteraction interaction) {

        var server = CommonUtils.getNixCrew();

        if(!CommonUtils.isUserAdmin(interaction.getUser()) && !isBooster(interaction.getUser())){
            interaction.createImmediateResponder().setContent("This command is only for administrators or server boosters!").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        if(!server.getConnectedVoiceChannel(interaction.getUser()).isPresent()){
            interaction.createImmediateResponder().setContent("To use this command you need to be connected in some voice channel").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        var target = interaction.getArgumentUserValueByIndex(0).get();
        var targetChannel = server.getConnectedVoiceChannel(interaction.getUser()).get();

        if(CommonUtils.isUserAdmin(target)){
            interaction.createImmediateResponder().setContent("This user is an admin and you don't want to fuck around with them. Try different user").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        if(!server.getVisibleChannels(target).contains(targetChannel)){
            interaction.createImmediateResponder().setContent("This user can't connect to your channel. Please connect to different voice channel").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        target.sendMessage("Hey " + target.getName() + " :wave: \nUser " + interaction.getUser().getName() + " is summoning you to " + targetChannel.getMentionTag());
        interaction.createImmediateResponder().setContent("User is summoned").setFlags(MessageFlag.EPHEMERAL).respond();

    }

    private boolean isBooster(User user){
        var server = CommonUtils.getNixCrew();
        var roles = user.getRoles(server);

        for (var role : roles){
            if(role.getIdAsString().equals(BOOSTER_ROLE_ID))
                return true;
        }

        return false;
    }
}

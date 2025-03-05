package cz.iliev.managers.weather_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class WeatherCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        switch (interaction.getOptions().get(0).getName()){
            case "subscribe":
                subscribe(interaction);
                break;
            case "unsubscribe":
                unsubscribe(interaction);
                break;
        }
    }

    private void subscribe(SlashCommandInteraction interaction){

        var lat = interaction.getArgumentStringValueByIndex(0).get();
        var lon = interaction.getArgumentStringValueByIndex(1).get();

        if((!lon.contains(".")) || (!(lon.split("\\.")[0].length() == 2)) || (!(lon.split("\\.")[1].length() == 6))){
            interaction.createImmediateResponder().setContent("You need to insert correct longitude. Starts with 2 digits, comma and continuing with another 6 digits").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        if((!lat.contains(".")) || (!(lat.split("\\.")[0].length() == 2)) || (!(lat.split("\\.")[1].length() == 6))){
            interaction.createImmediateResponder().setContent("You need to insert correct latitude. Starts with 2 digits, comma and continuing with another 6 digits").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        CommonUtils.weatherManager.getWeatherSubscribers().put(interaction.getUser().getId(), lat + ":" + lon);
        interaction.createImmediateResponder().setContent("You've successfully subscribe to weather forecast").setFlags(MessageFlag.EPHEMERAL).respond();

    }

    private void unsubscribe(SlashCommandInteraction interaction){

        if(!CommonUtils.weatherManager.getWeatherSubscribers().containsKey(interaction.getUser().getId())){
            interaction.createImmediateResponder().setContent("You're not subscribed").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        CommonUtils.weatherManager.getWeatherSubscribers().remove(interaction.getUser().getId());
        interaction.createImmediateResponder().setContent("You've been unsubscribed from weather forecast").setFlags(MessageFlag.EPHEMERAL).respond();

    }
}

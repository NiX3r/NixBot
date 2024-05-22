package cz.iliev.interfaces;

import org.javacord.api.interaction.SlashCommandInteraction;

public interface IManager {

    public void setup();
    public void kill();
    public boolean restart();
    public void onCommand(SlashCommandInteraction interaction);
    public void onConsoleCommand(Object data);
    public boolean isReady();

}

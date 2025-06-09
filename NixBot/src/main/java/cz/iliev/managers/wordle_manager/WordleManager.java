package cz.iliev.managers.wordle_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.wordle_manager.commands.WordleCommand;
import cz.iliev.managers.wordle_manager.instances.WordleGameInstance;
import cz.iliev.managers.wordle_manager.utils.FileUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.HashMap;

public class WordleManager implements IManager {

    private boolean ready;
    private HashMap<Long, WordleGameInstance> games;

    public WordleManager() { setup(); }

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());
        games = FileUtils.loadGames();
        ready = true;
        LogUtils.info(managerName() + " loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill " + managerName());
        FileUtils.saveGames(games);
        ready = false;
        LogUtils.info(managerName() + " killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {
        new WordleCommand().run(interaction);
    }

    @Override
    public void onConsoleCommand(Object data) {

    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "WordleManager";
    }

    @Override
    public String managerDescription() {
        return "Manager to play Wordle game";
    }

    @Override
    public String color() {
        return "#0a2ccc";
    }

    public WordleGameInstance getGameByUserId(long id){
        return games.get(id);
    }

    public void removeGameByUserId(long id){
        games.remove(id);
    }

    public void putNewGame(WordleGameInstance game){
        games.put(game.getUserId(), game);
    }

    public boolean isGameExistsByMessageId(long id){
        for(WordleGameInstance game : games.values()){
            if(game.getMessageId() == id)
                return true;
        }
        return false;
    }

}

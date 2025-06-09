package cz.iliev.managers.wordle_manager.instances;

import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WordleGameInstance {

    private long userId;
    private WordleWordInstance word;
    private long startTime;
    private long messageId;
    private List<Character> wrongGuesses;
    private List<Character> goodGuesses;

    public WordleGameInstance(long userId, WordleWordInstance word, long startTime, long messageId){
        this.userId = userId;
        this.word = word;
        this.startTime = startTime;
        this.messageId = messageId;
        wrongGuesses = new ArrayList<Character>();
        goodGuesses = new ArrayList<Character>();
    }

    public long getUserId(){ return userId; }
    public WordleWordInstance getWord() { return word; }
    public long getStartTime() { return startTime; }
    public long getMessageId() { return messageId; }
    public List<Character> getWrongGuesses() { return wrongGuesses; }
    public List<Character> getGoodGuesses() { return goodGuesses; }

    public void setMessageId(long messageId){
        this.messageId = messageId;
    }

    // Guess the letter in the word
    // Returns:
    // 0 if guess is correct
    // 1 if user win the game
    // -1 if guess is incorrect
    // -2 if the game already ended
    // -3 if user already guess this letter
    public int guess(char letter){
        if(wrongGuesses.size() == 5)
            return -1;

        if(wrongGuesses.contains(letter) || goodGuesses.contains(letter))
            return -2;

        if(!containsLetter(word.getWord(), letter)){
            wrongGuesses.add(letter);
            return wrongGuesses.size();
        }

        goodGuesses.add(letter);
        return 0;
    }

    public boolean containsLetter(String word, char letter){
        String parsedWord = parseWord(word.toLowerCase());
        return parsedWord.contains(String.valueOf(letter));
    }

    public String parseWord(String word){
        return word.replaceAll("á", "a")
                .replaceAll("č", "c")
                .replaceAll("ď", "d")
                .replaceAll("é", "e")
                .replaceAll("ě", "e")
                .replaceAll("í", "i")
                .replaceAll("ň", "n")
                .replaceAll("ó", "o")
                .replaceAll("ř", "r")
                .replaceAll("š", "s")
                .replaceAll("ť", "t")
                .replaceAll("ú", "u")
                .replaceAll("ů", "u")
                .replaceAll("ý", "y")
                .replaceAll("ž", "z");
    }

    public char parseLetter(char c){
        return switch (c) {
            case 'á' -> 'a';
            case 'č' -> 'c';
            case 'ď' -> 'd';
            case 'é', 'ě' -> 'e';
            case 'í' -> 'i';
            case 'ň' -> 'n';
            case 'ó' -> 'o';
            case 'ř' -> 'r';
            case 'š' -> 's';
            case 'ť' -> 't';
            case 'ú', 'ů' -> 'u';
            case 'ý' -> 'y';
            case 'ž' -> 'z';
            default -> c;
        };
    }

    public String generateHiddenWord(){

        String output = "";

        for (char c : word.getWord().toLowerCase().toCharArray()){
            Character ch = c;
            if(goodGuesses.contains(ch)){
                output += c + " ";
            }
            else if(parseLetter(c) > 96 && parseLetter(c) < 123){
                output += "_ ";
            }
            else{
                output += c + " ";
            }
        }
        output = output.trim();

        return output;

    }

    public String generateWrongGuesses(){
        String output = "";
        for (char c : wrongGuesses){
            output += c + " ";
        }
        return output.trim();
    }

    public EmbedBuilder createEmbed(User user, String hiddenWord){
        return new EmbedBuilder()
                .setTitle(user.getName() + "'s Wordle Game")
                .setColor(Color.decode(CommonUtils.wordleManager.color()))
                .addField("Word", "`" + hiddenWord + "`", true)
                .addField("Time since start", "<t:" + startTime / 1000 + ":R>", true)
                .addField("Wrong guesses", generateWrongGuesses(), false)
                .setDescription("You need to guess the letter. Guess the letter by command `/wordle <letter>`");
    }

    public EmbedBuilder createFinishEmbed(User user, boolean win){
        long calculate = (System.currentTimeMillis() - startTime) / 1000;
        String time = (calculate / 60) + "m " + (calculate % 60) + "s";
        return new EmbedBuilder()
                .setTitle(user.getName() + "'s Wordle Game - " + (win ? "WIN" : "LOSE"))
                .setColor(Color.decode(CommonUtils.wordleManager.color()))
                .addField("Word", "`" + word.getWord() + "`", true)
                .addField("Word score", word.getScore() + "", true)
                .addField("Finished time", time, true)
                .addField("Wrong guesses", generateWrongGuesses(), false)
                .setDescription("This game has ended. Wanna play again? Type `/wordle`");
    }

}

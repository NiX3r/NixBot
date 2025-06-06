package cz.iliev.managers.wordle_manager.instances;

import java.util.ArrayList;
import java.util.List;

public class WordleGameInstance {

    public long userId;
    public WordleWordInstance word;
    public long startTime;
    public List<Character> wrongGuesses;
    public List<Character> goodGuesses;

    public WordleGameInstance(long userId, WordleWordInstance word, long startTime){
        this.userId = userId;
        this.word = word;
        this.startTime = startTime;
        wrongGuesses = new ArrayList<Character>();
        goodGuesses = new ArrayList<Character>();
    }

    public long getUserId(){ return userId; }
    public WordleWordInstance getWord() { return word; }
    public long getStartTime() { return startTime; }

    // Guess the letter in the word
    // Returns:
    // 0 if guess is correct
    // Any positive number is wrong and the number means wrong guesses count
    // -1 if the game already ended
    // -2 if user already guess this letter
    public int guess(char letter){
        if(wrongGuesses.size() == 5)
            return -1;

        if(wrongGuesses.contains(letter) || goodGuesses.contains(letter))
            return -2;

        if(containsLetter(word.getWord(), letter)){
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

    public String generateHiddenWord(){

        String output = "";

        for (char c : word.getWord().toCharArray()){
            Character ch =
            if(goodGuesses.contains())
        }

        return output;

    }

}

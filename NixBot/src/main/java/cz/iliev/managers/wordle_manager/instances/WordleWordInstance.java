package cz.iliev.managers.wordle_manager.instances;

public class WordleWordInstance {

    public int score;
    public String word;

    public WordleWordInstance(int score, String word){
        this.score = score;
        this.word = word;
    }

    public int getScore(){ return score; }
    public String getWord(){ return word; }

}

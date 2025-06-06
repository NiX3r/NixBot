package cz.iliev.managers.wordle_manager.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordPickerUtils {

    private static final String PATH = "./data/{lang}.csv";

    public static String GetWord(int score, String lang){ return GetWord(score, score, lang); }

    public static String GetWord(int fromScore, int toScore, String lang) {

        BufferedReader reader;
        List<String> words = new ArrayList<>();
        try{
            reader = new BufferedReader(new FileReader(PATH.replace("{lang}", lang)));
            String line = reader.readLine(); // Skip first row -> it's column name
            line = reader.readLine();

            while (line != null){
                String[] splitter = line.split(";");
                String stringScore = splitter[0];
                int score = Integer.parseInt(stringScore);
                if(score > toScore)
                    break;
                if(score < fromScore)
                    continue;
                words.add(splitter[1]);
                line = reader.readLine();
            }
            reader.close();

            if(words.isEmpty())
                return "-1";
        } catch (FileNotFoundException e) {
            return "-2";
        } catch (IOException e) {
            return "-3";
        }

        Random r = new Random();
        r.nextInt(words.size());
        return words.get(r.nextInt(words.size()));
    }

}

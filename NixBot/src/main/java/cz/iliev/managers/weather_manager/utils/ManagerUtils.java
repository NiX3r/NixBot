package cz.iliev.managers.weather_manager.utils;

import java.util.ArrayList;

public class ManagerUtils {

    public static String calculateAverageTemperature(ArrayList<Double> list){

        double sum = 0;
        for (Double v : list) {
            sum += v;
        }
        String average = String.valueOf((sum / list.size()));
        int beforeDot = average.substring(0, average.indexOf(".") -1).replace("-", "").length();
        boolean belowZero = average.charAt(0) == '-';

        if(beforeDot == 1)
            average = average.substring(0, belowZero ? 3 : 2);
        else
            average = average.substring(0, belowZero ? 4 : 3);

        return average;

    }

}

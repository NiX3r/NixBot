package cz.iliev.managers.reminder_manager.utils;

import java.util.Calendar;

public class CronUtils {

    public static boolean isCronToday(String cronValue){

        if(!checkFullCronFormat(cronValue))
            return false;

        String[] values = cronValue.split(" ");
        Calendar calendar = Calendar.getInstance();
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        int thisMonth = calendar.get(Calendar.MONTH) + 1;

        boolean day = checkCronWithValue(values[0], thisDay);
        boolean month = checkCronWithValue(values[1], thisMonth);

        return day && month;

    }

    public static boolean checkCronWithValue(String cron, int value){

        if(cron.startsWith("*")){
            if(cron.length() == 1)
                return true;
            else{
                int cronInt = Integer.parseInt(cron.replace("*/", ""));
                return value % cronInt == 0;
            }
        }
        else {
            int cronInt = Integer.parseInt(cron);
            return cronInt == value;
        }

    }

    public static boolean checkFullCronFormat(String cronValue){

        if(!cronValue.contains(" "))
            return false;

        String[] values = cronValue.split(" ");

        if(values.length != 2)
            return false;

        var first = checkCronFormat(values[0]);
        var second = checkCronFormat(values[1]);

        return first && second;

    }

    public static boolean checkCronFormat(String cronValue){

        if (cronValue.startsWith("*")){

            if(cronValue.length() == 1)
                return true;

            if (!cronValue.startsWith("*/"))
                return false;

            return isNumber(cronValue.replace("*/", ""));

        }
        else
            return isNumber(cronValue);

    }

    private static boolean isNumber(String value){
        try{
            int i = Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException ex){
            return false;
        }
    }

}

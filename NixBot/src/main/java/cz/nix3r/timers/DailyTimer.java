package cz.nix3r.timers;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class DailyTimer extends TimerTask {
    @Override
    public void run() {

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

    }

}

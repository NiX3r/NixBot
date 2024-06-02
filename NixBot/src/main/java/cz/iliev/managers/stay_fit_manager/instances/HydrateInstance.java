package cz.iliev.managers.stay_fit_manager.instances;

import java.util.HashMap;

public class HydrateInstance {

    // Key = date in format YYYY-MM-DD | Value = hydrate of current day
    private HashMap<String, Float> data;
    private float dailyGoal;

    public HydrateInstance(HashMap<String, Float> data, float dailyGoal){
        this.data = data;
        this.dailyGoal = dailyGoal;
    }

    public HashMap<String, Float> getData() {
        return data;
    }

    public float getDailyGoal() {
        return dailyGoal;
    }

    public void setDailyGoal(float dailyGoal) {
        this.dailyGoal = dailyGoal;
    }
}

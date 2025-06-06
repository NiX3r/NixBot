package cz.iliev.managers.weather_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.announcement_manager.AnnouncementManager;
import cz.iliev.managers.weather_manager.commands.WeatherCommand;
import cz.iliev.managers.weather_manager.instances.ApiResponse;
import cz.iliev.managers.weather_manager.utils.ApiUtils;
import cz.iliev.managers.weather_manager.utils.ChartUtils;
import cz.iliev.managers.weather_manager.utils.FileUtils;
import cz.iliev.managers.weather_manager.utils.ManagerUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;
import java.util.*;

public class WeatherManager implements IManager {

    private boolean ready;
    private String apiKey;

    private HashMap<Long, String> weatherSubscribers;

    public WeatherManager(){ setup(); }

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());
        apiKey = CommonUtils.settings.getOpenWeatherApiKey();

        weatherSubscribers = FileUtils.loadSubscribers();

        ready = true;

        CommonUtils.getNixCrew().getTextChannelById(AnnouncementManager.WEATHER_CHANNEL_ID).ifPresent(channel -> {
            var message = channel.getMessages(1).join();
            message.getNewestMessage().ifPresent(msg -> {
                var days = System.currentTimeMillis() / 86400000;
                var time = msg.getCreationTimestamp().toEpochMilli() - (days * 86400000);

                if(time > 0)
                    return;

                generateChart();

                for (var key : weatherSubscribers.keySet()){
                    String[] splitter = weatherSubscribers.get(key).split(":");
                    generateChart(splitter[0], splitter[1], key);
                }

            });
        });
        LogUtils.info(managerName() + " loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill " + managerName());
        FileUtils.saveSubscribers(weatherSubscribers);
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
        switch (interaction.getCommandName()){
            case "weather":
                new WeatherCommand().run(interaction);
                break;
        }
    }

    @Override
    public void onConsoleCommand(Object data) {

    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public String managerName() {
        return "WeatherManager";
    }

    @Override
    public String managerDescription() {
        return "Manager for displaying current and future weather";
    }

    @Override
    public String color() {
        return "#04738a";
    }

    private void generateChart(){ generateChart("50.073658", "14.418540", 0); }
    private void generateChart(String latitude, String longitude, long userId){

        var temp = new ArrayList<Double>();
        var feelsLikeTemp = new ArrayList<Double>();

        var dates = new ArrayList<Date>();

        var data = ApiUtils.GetFiveDayForecast(latitude, longitude, apiKey);
        parseData(data, temp, feelsLikeTemp, dates);
        ChartUtils.generate30hChart(temp, feelsLikeTemp, dates, userId, latitude, longitude, callback -> {
            if(callback != null)
                sendChart(userId, temp, feelsLikeTemp, callback);
        });

    }

    private void sendChart(long userId, ArrayList<Double> temp, ArrayList<Double> feelsLikeTemp, byte[] graph){

        if(userId == 0){
            CommonUtils.announcementManager.sendWeather(
                    color(),
                    ManagerUtils.calculateAverageTemperature(temp),
                    ManagerUtils.calculateAverageTemperature(feelsLikeTemp),
                    0,
                    graph
            );
        }
        else{
            CommonUtils.getNixCrew().getMemberById(userId).ifPresent(user -> {
                user.sendMessage(
                        CommonUtils.announcementManager.getWeather(
                                color(),
                                ManagerUtils.calculateAverageTemperature(temp),
                                ManagerUtils.calculateAverageTemperature(feelsLikeTemp),
                                userId,
                                graph
                        )
                ).join();
            });
        }

    }

    private void parseData(ApiResponse data, ArrayList<Double> temp, ArrayList<Double> feelsLikeTemp, ArrayList<Date> dates){
        if(data.getStatusCode() >= 300)
            return;

        for(var item : data.getObject().get("list").getAsJsonArray()){

            var o = item.getAsJsonObject();

            long datetime = o.get("dt").getAsLong() * 1000;
            // Break if weather data is higher than 30h from now
            if(System.currentTimeMillis() + 108000000 < datetime){
                break;
            }

            var main = o.get("main").getAsJsonObject();
            var t = main.get("temp").getAsDouble();
            var f = main.get("feels_like").getAsDouble();
            temp.add(t);
            feelsLikeTemp.add(f);

            dates.add(new Date(datetime));

        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public HashMap<Long, String> getWeatherSubscribers() {
        return weatherSubscribers;
    }
}

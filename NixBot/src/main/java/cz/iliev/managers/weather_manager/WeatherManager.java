package cz.iliev.managers.weather_manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import cz.iliev.interfaces.IManager;
import cz.iliev.managers.announcement_manager.AnnouncementManager;
import cz.iliev.managers.stay_fit_manager.instances.MemberFitInstance;
import cz.iliev.managers.weather_manager.instances.ApiResponse;
import cz.iliev.managers.weather_manager.utils.ApiUtils;
import cz.iliev.managers.weather_manager.utils.ChartUtils;
import cz.iliev.managers.weather_manager.utils.ManagerUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.theme.Theme;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class WeatherManager implements IManager {

    private boolean ready;
    private String apiKey;

    public WeatherManager(){ setup(); }

    @Override
    public void setup() {
        LogUtils.info("Load and start WeatherManager");
        apiKey = CommonUtils.settings.getOpenWeatherApiKey();
        ready = true;

        CommonUtils.getNixCrew().getTextChannelById(AnnouncementManager.WEATHER_CHANNEL_ID).ifPresent(channel -> {
            var message = channel.getMessages(1).join();
            message.getNewestMessage().ifPresent(msg -> {
                var days = System.currentTimeMillis() / 86400000;
                var time = msg.getCreationTimestamp().toEpochMilli() - (days * 86400000);

                if(time > 0)
                    return;

                generateCharts();
            });
        });
        LogUtils.info("WeatherManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill WeatherManager");
        ready = false;
        LogUtils.info("WeatherManager killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {

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
        return "#04a8c9";
    }

    private void generateCharts(){

        var temp = new ArrayList<Double>();
        var feelsLikeTemp = new ArrayList<Double>();

        var dates = new ArrayList<Date>();

        var data = ApiUtils.GetFiveDayForecast("50.073658", "14.418540", apiKey);
        parseData(data, temp, feelsLikeTemp, dates);
        ChartUtils.generate30hChart(temp, feelsLikeTemp, dates);

        CommonUtils.announcementManager.sendWeather(
                color(),
                ManagerUtils.calculateAverageTemperature(temp),
                ManagerUtils.calculateAverageTemperature(feelsLikeTemp)
        );

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
}

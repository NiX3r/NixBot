package cz.iliev.managers.weather_manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import cz.iliev.interfaces.IManager;
import cz.iliev.managers.stay_fit_manager.instances.MemberFitInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.theme.Theme;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class WeatherManager implements IManager {

    private boolean ready;
    private JsonObject data;
    private String apiKey;

    public WeatherManager(){ setup(); }

    @Override
    public void setup() {
        LogUtils.info("Load and start WeatherManager");
        apiKey = CommonUtils.settings.getOpenWeatherApiKey();
        ready = true;
        loadData();
        generateCharts();
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

    public JsonObject getData() {
        return data;
    }

    private void loadData(){

    }

    private void generateCharts(){

        var temp = new ArrayList<Double>();
        var feelsLikeTemp = new ArrayList<Double>();
        var minTemp = new ArrayList<Double>();
        var maxTemp = new ArrayList<Double>();

        var dates = new ArrayList<Date>();

        for(var item : data.get("list").getAsJsonArray()){

            var o = item.getAsJsonObject();

            long datetime = o.get("dt").getAsLong() * 1000;
            // Break if weather data is higher than 30h from now
            if(System.currentTimeMillis() + 108000000 < datetime){
                break;
            }

            var main = o.get("main").getAsJsonObject();
            temp.add(main.get("temp").getAsDouble());
            feelsLikeTemp.add(main.get("feels_like").getAsDouble());
            minTemp.add(main.get("temp_min").getAsDouble());
            maxTemp.add(main.get("temp_min").getAsDouble());

            dates.add(new Date(datetime));

        }

        final XYChart chart = new XYChartBuilder().width(1920).height(720).title("30h předpověd počasí - Praha").xAxisTitle("Datum").yAxisTitle("Teplota (°C)").build();

        chart.getStyler().setChartBackgroundColor(Color.decode("#313338"));
        chart.getStyler().setLegendBackgroundColor(Color.decode("#313338"));
        chart.getStyler().setPlotBackgroundColor(Color.decode("#313338"));
        chart.getStyler().setAnnotationTextPanelFontColor(Color.decode("#e0f5fc"));
        chart.getStyler().setAnnotationTextFontColor(Color.decode("#e0f5fc"));
        chart.getStyler().setXAxisTickLabelsColor(Color.decode("#e0f5fc"));
        chart.getStyler().setYAxisTickLabelsColor(Color.decode("#e0f5fc"));
        chart.getStyler().setChartFontColor(Color.decode("#e0f5fc"));
        chart.getStyler().setDatePattern("dd. MM. yyyy HH:mm");
        chart.getStyler().setLegendFont(new Font("mono", Font.PLAIN, 20));
        chart.getStyler().setChartTitleFont(new Font("mono", Font.PLAIN, 35));
        chart.getStyler().setAxisTitleFont(new Font("mono", Font.PLAIN, 20));
        chart.getStyler().setTimezone(TimeZone.getTimeZone("Europe/Prague"));

        chart.addSeries("Teplota", dates, temp);
        chart.addSeries("Pocitová", dates, feelsLikeTemp);
        chart.addSeries("Minimální", dates, minTemp);
        chart.addSeries("Maximální", dates, maxTemp);

        try {
            BitmapEncoder.saveBitmap(chart, "./test", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getApiKey() {
        return apiKey;
    }
}

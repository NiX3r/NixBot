package cz.iliev.managers.weather_manager.utils;

import cz.iliev.utils.LogUtils;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Consumer;

public class ChartUtils {

    public static void generate30hChart(ArrayList<Double> temp, ArrayList<Double> feelsLikeTemp, ArrayList<Date> dates, long userId, String lat, String lon, Consumer<byte[]> callback){

        final XYChart chart = new XYChartBuilder().width(1920).height(720).title("30h weather forecast - " + (userId == 0 ? "Prague" : ("Lat: " + lat + ", Lon: " + lon))).xAxisTitle("Date").yAxisTitle("Temperature (°C)").build();

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

        chart.addSeries("Temperature", dates, temp);
        chart.addSeries("Feels like", dates, feelsLikeTemp);

        try {
            var bitmap = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.PNG);
            callback.accept(bitmap);
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }

        callback.accept(null);

    }

}

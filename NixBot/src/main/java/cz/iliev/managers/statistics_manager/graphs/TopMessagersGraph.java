package cz.iliev.managers.statistics_manager.graphs;

import com.google.gson.Gson;
import cz.iliev.managers.statistics_manager.enums.GraphTypeEnum;
import cz.iliev.managers.statistics_manager.instances.GraphDataInstance;
import cz.iliev.managers.statistics_manager.instances.GraphInstance;
import cz.iliev.managers.statistics_manager.interfaces.IGraphGenerator;
import io.quickchart.QuickChart;

public class TopMessagersGraph implements IGraphGenerator {
    @Override
    public String generate(GraphDataInstance data) {
        var gson = new Gson();
        QuickChart chart = new QuickChart();
        chart.setWidth(600);
        chart.setHeight(350);
        chart.setConfig(gson.toJson(new GraphInstance(GraphTypeEnum.radar, data)).replaceAll("\"", "'"));
        return chart.getUrl();
    }
}

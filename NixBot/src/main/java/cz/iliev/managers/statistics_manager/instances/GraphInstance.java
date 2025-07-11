package cz.iliev.managers.statistics_manager.instances;

import cz.iliev.managers.statistics_manager.enums.GraphTypeEnum;

public class GraphInstance {

    private GraphTypeEnum type;
    private GraphDataInstance data;

    public GraphInstance(GraphTypeEnum type, GraphDataInstance data){
        this.type = type;
        this.data = data;
    }

    public GraphTypeEnum getType() {
        return type;
    }

    public void setType(GraphTypeEnum type) {
        this.type = type;
    }

    public GraphDataInstance getData() {
        return data;
    }

    public void setData(GraphDataInstance data) {
        this.data = data;
    }
}

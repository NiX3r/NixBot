package cz.iliev.managers.statistics_manager.instances;

public class GraphDataInstance {

    private String[] labels;
    private GraphDatasetInstance[] datasets;

    public GraphDataInstance(String[] labels, GraphDatasetInstance[] datasets){
        this.labels = labels;
        this.datasets = datasets;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public GraphDatasetInstance[] getDatasets() {
        return datasets;
    }

    public void setDatasets(GraphDatasetInstance[] datasets) {
        this.datasets = datasets;
    }
}

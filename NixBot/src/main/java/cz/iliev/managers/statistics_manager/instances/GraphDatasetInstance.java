package cz.iliev.managers.statistics_manager.instances;

public class GraphDatasetInstance {

    private String label;
    private int[] data;

    public GraphDatasetInstance(String label, int[] data){
        this.label = label;
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }
}

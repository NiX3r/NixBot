package cz.nix3r.instances;

public class StreamingPlatformInstance {

    private String name;
    private int current_index;
    private String[] list;

    public StreamingPlatformInstance(String name, int current_index, String[] list) {
        this.name = name;
        this.current_index = current_index;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public int getCurrentIndex() {
        return current_index;
    }

    public void incrementCurrentIndex(){
        current_index++;
        if(current_index == list.length)
            current_index = 0;
    }

    public String[] getList() {
        return list;
    }
}

package cz.iliev.managers.weather_manager.instances;

import com.google.gson.JsonObject;

public class ApiResponse {

    private int statusCode;
    private JsonObject object;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public JsonObject getObject() {
        return object;
    }

    public void setObject(JsonObject object) {
        this.object = object;
    }
}

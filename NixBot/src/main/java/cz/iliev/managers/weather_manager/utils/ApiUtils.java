package cz.iliev.managers.weather_manager.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cz.iliev.managers.weather_manager.instances.ApiResponse;
import cz.iliev.utils.CommonUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiUtils {

    public static ApiResponse GetFiveDayForecast(String latitude, String longitude, String apiKey){

        var response = new ApiResponse();
        String uriBuilder = "api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={api_key}";
        uriBuilder = uriBuilder.replace("{lat}", latitude);
        uriBuilder = uriBuilder.replace("{lon}", longitude);
        uriBuilder = uriBuilder.replace("{api_key}", apiKey);

        try {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriBuilder))
                    .GET() // GET is default
                    .build();

            HttpResponse<String> httpResponse = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            response.setObject(gson.fromJson(httpResponse.body(), JsonObject.class));
            response.setStatusCode(httpResponse.statusCode());

        } catch (IOException | InterruptedException e) {
            CommonUtils.throwException(e);
            response.setObject(null);
            response.setStatusCode(500);
        }

        return response;

    }

}

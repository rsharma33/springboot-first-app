package com.learn.spring.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.learn.spring.model.DateResponse;
import com.learn.spring.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@PropertySource("classpath:application.yml")
public class AppController {
    @Value("${numbersapi.dateEndpoint}")
    public String numberAPIDateEndpoint;

    @GetMapping("date")
    public String dateAPI() throws Exception{
        String numberAPIDateUrlFormat = String.format(numberAPIDateEndpoint, "6","21"); // month, date
        String numberAPIToken = System.getenv("NUMBER_API_RapidAPI_TOKEN");
        String numberAPIHost = System.getenv("NUMBER_API_HOST");
        final List<DateResponse> dateResponseList = new ArrayList<>();
        WebClient webClient = WebClient.create();

        // Create HttpHeaders object and set multiple headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", numberAPIToken);
        headers.add("X-RapidAPI-Host", numberAPIHost);
        String finalResponse;

        long startTime = System.currentTimeMillis();
        finalResponse = webClient.get()
                .uri(numberAPIDateUrlFormat+"?json=true")
                .headers(h -> h.addAll(headers))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonObject jsonObject = new Gson().fromJson(finalResponse, JsonObject.class);

        dateResponseList.add(new DateResponse(jsonObject.get("year").getAsInt(), jsonObject.get("text").getAsString()));
        JsonArray finalJSON = new Gson().toJsonTree(dateResponseList).getAsJsonArray();
        long endTime = System.currentTimeMillis();
        long delayTime = endTime - startTime;

        JsonObject finalResponseJSON =Util.convertMillisecondsToTime(delayTime);
        Util.logDataInJson(finalResponseJSON.toString().split("\n"));

        return finalJSON.toString();
    }
}

package com.learn.spring.util;

import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class Util {
    public static JsonObject convertMillisecondsToTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        JsonObject finalJSON = new JsonObject();
        finalJSON.addProperty("minutes", minutes);
        finalJSON.addProperty("seconds", seconds);

        return finalJSON;
    }

    public static void logDataInJson(String[] args) {
        JsonObject logData = new JsonObject();

        logData.addProperty("timestamp", System.currentTimeMillis());
        logData.addProperty("delay", "min: " + args[0]);

        try (FileWriter file = new FileWriter("log.json", true)) {
            file.write(logData.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.example.tripp;
import java.io.Serializable;

public class TripActivity implements Serializable {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private String date;
    public String getDate() {
        return date;
    }
    private String type;
     private String time;
    private boolean needsReservation;
    public boolean isNeedsReservation() {

        return needsReservation;
    }
    public void setNeedsReservation(boolean needsReservation) {
        this.needsReservation = needsReservation;
    }
    private String priority;
    private String weather;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public TripActivity(String name, String type, String date, String time, boolean needsReservation, String priority, String weather) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.time = time;
        this.needsReservation = needsReservation;
        this.priority = priority;
        this.weather = weather;
    }



    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getPriority() {
        return priority;
    }


    public void setType(String type) {
        this.type = type;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}

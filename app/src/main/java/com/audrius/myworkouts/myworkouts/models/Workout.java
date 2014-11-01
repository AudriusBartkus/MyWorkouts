package com.audrius.myworkouts.myworkouts.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Workout implements Serializable {
    private long id;
    private String name;
    private String time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() { return name + time; }

}

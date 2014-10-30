package com.audrius.myworkouts.myworkouts.models;

import java.text.SimpleDateFormat;

public class Workout {
    private long id;
    private String name;
    private SimpleDateFormat time;

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

    public SimpleDateFormat getTime() {
        return time;
    }

    public void setTime(SimpleDateFormat time) {
        this.time = time;
    }

    @Override
    public String toString(){
        return name;
    }

}

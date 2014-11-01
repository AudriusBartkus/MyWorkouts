package com.audrius.myworkouts.myworkouts.models;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class FinishedWorkout implements Serializable {
    private long id;
    private String name;
    private SimpleDateFormat date;
    private Time workoutTime;

    public Time getWorkoutTime() {
        return workoutTime;
    }

    public void setWorkoutTime(Time workoutTime) {
        this.workoutTime = workoutTime;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

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

    @Override
    public String toString() {
        return name;
    }
}

package com.audrius.myworkouts.myworkouts.models;

import java.io.Serializable;

/**
 * Created by Audrius on 2014-10-31.
 */
public class Set implements Serializable {
    private long id;
    private int reps;
    private int weight;
    private long exerciseId;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public String toString(){
        return "Reps: "+this.reps+" Weight: "+this.weight;

    }
}

package com.audrius.myworkouts.myworkouts.models;

public class Exercise {
    private long id;
    private String name;
    private int weight;
    private int sets;
    private int reps;
    private long workout_id;
    //TODO: connect workout_id to workout when workout insert is complete
/*    public Exercise(long id, String name, int weight, int sets, int reps){
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }*/


    public long getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(long workout_id) {
        this.workout_id = workout_id;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
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

    public int getWeight() { return weight; }

    public int setWeight(int weight) { return this.weight = weight; }

    public int getSets() { return sets; }

    public void setSets(int sets) {
        this.sets = sets;
    }

    // Will be used by the ArrayAdapter in the ListView
   /* @Override
    public String toString() {
        return exercise;
    }*/
}

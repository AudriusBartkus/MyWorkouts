package com.audrius.myworkouts.myworkouts.models;

/**
 * Created by Audrius on 2014-10-08.
 */
public class Exercise {
    private long id;
    private String name;
    private int weight;
    private int sets;
    private int reps;

/*    public Exercise(long id, String name, int weight, int sets, int reps){
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }*/

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

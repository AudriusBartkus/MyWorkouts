package com.audrius.myworkouts.myworkouts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.audrius.myworkouts.myworkouts.models.Exercise;
import com.audrius.myworkouts.myworkouts.models.Workout;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkoutDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.WORKOUTS_ID, MySQLiteHelper.WORKOUTS_NAME, MySQLiteHelper.WORKOUTS_TIME};
    private  ExerciseDataSource exerciseDataSource;

    public WorkoutDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
        exerciseDataSource = new ExerciseDataSource(context);

    }

    public void open() {
        database = dbHelper.getReadableDatabase();
        exerciseDataSource.open();
    }

    public void close() {
        dbHelper.close();
        exerciseDataSource.close();
    }

    public long createWorkout(Workout workout){
        ContentValues values = new ContentValues();
       //
        values.put(MySQLiteHelper.WORKOUTS_NAME, workout.getName());
        if (workout.getTime() != null) {
            values.put(MySQLiteHelper.WORKOUTS_TIME, workout.getTime());
        }
        long last = database.insert(MySQLiteHelper.TABLE_WORKOUTS, null, values);
        return last;
    }


    public void deleteWorkout(Workout workout){
        long id = workout.getId();
        ArrayList<Exercise> exList = exerciseDataSource.getExercisesById(id);
        database.delete(MySQLiteHelper.TABLE_WORKOUTS, MySQLiteHelper.WORKOUTS_ID + " = " + id, null);
        for(Exercise exercise : exList){
            exerciseDataSource.deleteExercise(exercise);
        }
    }

    public ArrayList<Workout> getAllWorkouts(){
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_WORKOUTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            workouts.add(workout);
            cursor.moveToNext();
        }
        cursor.close();
        return workouts;
    }

    public ArrayList<Workout> getWorkoutsWithoutDate(){
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_WORKOUTS + " WHERE " + MySQLiteHelper.WORKOUTS_TIME + " IS NULL", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            workouts.add(workout);
            cursor.moveToNext();
        }
        cursor.close();
        return workouts;
    }

    public ArrayList<Workout> getWorkoutsWithDate(){
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_WORKOUTS + " WHERE " + MySQLiteHelper.WORKOUTS_TIME + " IS NOT NULL", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            workouts.add(workout);
            cursor.moveToNext();
        }
        cursor.close();
        return workouts;
    }

    private Workout cursorToWorkout(Cursor cursor) {
        Workout workout = new Workout();
        workout.setId(cursor.getLong(0));
        workout.setName(cursor.getString(1));
        workout.setTime(cursor.getString(2));
        return workout;
    }

}

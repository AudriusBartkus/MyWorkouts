package com.audrius.myworkouts.myworkouts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.audrius.myworkouts.myworkouts.models.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkoutDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.WORKOUTS_ID, MySQLiteHelper.WORKOUTS_NAME, MySQLiteHelper.WORKOUTS_TIME};

    public WorkoutDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() { database = dbHelper.getReadableDatabase();}

    public void close() { dbHelper.close(); }

    public void createWorkout(Workout workout){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.WORKOUTS_NAME, workout.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        values.put(MySQLiteHelper.WORKOUTS_TIME, dateFormat.format(workout.getTime()));

        database.insert(MySQLiteHelper.TABLE_WORKOUTS, null, values);
    }

    public void deleteWorkout(Workout workout){
        //TODO: check if id is assigned when creating a workout and exercise
        long id = workout.getId();
        database.delete(MySQLiteHelper.TABLE_WORKOUTS, MySQLiteHelper.WORKOUTS_ID + " = " + id, null);
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

    private Workout cursorToWorkout(Cursor cursor) {
        Workout workout = new Workout();
        workout.setId(cursor.getLong(0));
        workout.setName(cursor.getString(1));
        //TODO: finish time inserts and gets
       // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       // workout.setTime(dateFormat.parse(cursor.get(2)));
        return workout;
    }

}

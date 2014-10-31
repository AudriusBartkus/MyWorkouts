package com.audrius.myworkouts.myworkouts.db;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.audrius.myworkouts.myworkouts.models.Exercise;

public class ExerciseDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.EXERCISES_ID,
            MySQLiteHelper.EXERCISES_NAME, MySQLiteHelper.EXERCISES_WEIGHT, MySQLiteHelper.EXERCISES_SETS, MySQLiteHelper.EXERCISES_REPS, MySQLiteHelper.EXERCISES_WORKOUT_ID};

    public ExerciseDataSource(Context context) {

        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createExercise(Exercise exercise) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.EXERCISES_NAME, exercise.getName());
        values.put(MySQLiteHelper.EXERCISES_WEIGHT, exercise.getWeight());
        values.put(MySQLiteHelper.EXERCISES_SETS, exercise.getSets());
        values.put(MySQLiteHelper.EXERCISES_REPS, exercise.getReps());
        values.put(MySQLiteHelper.EXERCISES_WORKOUT_ID, exercise.getWorkout_id());
        database.insert(MySQLiteHelper.TABLE_EXERCISES, null, values);
    }

    public void deleteExercise(Exercise exercise) {
        long id = exercise.getId();
        database.delete(MySQLiteHelper.TABLE_EXERCISES, MySQLiteHelper.EXERCISES_ID
                + " = " + id, null);
    }

    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISES,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            cursor.moveToNext();
        }
        cursor.close();
        return exercises;
    }

    public ArrayList<Exercise> getAllUnassignedExercises() {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_EXERCISES + " WHERE " + MySQLiteHelper.EXERCISES_WORKOUT_ID + " = 0", null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            cursor.moveToNext();
        }
        cursor.close();
        return exercises;
    }

    public ArrayList<Exercise> getExercisesById(long id){
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_EXERCISES + " WHERE " + MySQLiteHelper.EXERCISES_WORKOUT_ID + " = " + (int)id, null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            cursor.moveToNext();
        }
        cursor.close();
        return exercises;
    }


    public void dropDB(){
        database.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_EXERCISES);
        database.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_WORKOUTS);
        String query;
        query = "CREATE TABLE " + MySQLiteHelper.TABLE_EXERCISES + "( " + MySQLiteHelper.EXERCISES_ID + " INTEGER PRIMARY KEY, " + MySQLiteHelper.EXERCISES_NAME + " TEXT, " + MySQLiteHelper.EXERCISES_WEIGHT + " INTEGER, " + MySQLiteHelper.EXERCISES_SETS + " INTEGER, " + MySQLiteHelper.EXERCISES_REPS +" INTEGER, " + MySQLiteHelper.EXERCISES_WORKOUT_ID + " INTEGER)";
        String workoutQuery = "CREATE TABLE " + MySQLiteHelper.TABLE_WORKOUTS + "(_id INTEGER PRIMARY KEY, name TEXT, time TIME)";
        database.execSQL(query);
        database.execSQL(workoutQuery);
    }

    public void assignUnassignedExercises(long id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_EXERCISES + " WHERE " + MySQLiteHelper.EXERCISES_WORKOUT_ID + " = 0", null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            updateExerciseId(cursor.getLong(0), id);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public void updateExerciseId(long exId, long workId){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.EXERCISES_WORKOUT_ID, workId);
        database.update(MySQLiteHelper.TABLE_EXERCISES, values, MySQLiteHelper.EXERCISES_ID+" = "+exId, null);
    }

    private Exercise cursorToExercise(Cursor cursor) {

        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setName(cursor.getString(1));
        exercise.setWeight(Integer.parseInt(cursor.getString(2)));
        exercise.setSets(Integer.parseInt(cursor.getString(3)));
        exercise.setReps(Integer.parseInt(cursor.getString(4)));
        exercise.setWorkout_id(Integer.parseInt(cursor.getString(5)));
        return exercise;
    }


}

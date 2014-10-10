package com.audrius.myworkouts.myworkouts.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.audrius.myworkouts.myworkouts.models.Exercise;

public class ExerciseDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.EXERCISES_ID,
            MySQLiteHelper.EXERCISES_NAME, MySQLiteHelper.EXERCISES_WEIGHT, MySQLiteHelper.EXERCISES_SETS, MySQLiteHelper.EXERCISES_REPS};

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
      //  Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_EXERCISES + " LIMIT 0", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return exercises;
    }

    private Exercise cursorToExercise(Cursor cursor) {

        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setName(cursor.getString(1));
        exercise.setWeight(Integer.parseInt(cursor.getString(2)));
        exercise.setSets(Integer.parseInt(cursor.getString(3)));
        exercise.setReps(Integer.parseInt(cursor.getString(4)));
        return exercise;
    }
}

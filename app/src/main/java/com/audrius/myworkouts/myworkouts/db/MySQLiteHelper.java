package com.audrius.myworkouts.myworkouts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXERCISES = "exercises";
    public static final String EXERCISES_ID = "_id";
    public static final String EXERCISES_NAME = "name";
    public static final String EXERCISES_WEIGHT = "weight";
    public static final String EXERCISES_SETS = "sets";
    public static final String EXERCISES_REPS = "reps";


    public static final String TABLE_WORKOUTS = "workouts";
    public static final String WORKOUTS_ID = "_id";
    public static final String WORKOUTS_NAME = "name";
    public static final String WORKOUTS_TIME = "time";


    private static final String DATABASE_NAME = "myWorkoutsDB";
    private static final int DATABASE_VERSION = 1;


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override public void onCreate(SQLiteDatabase database) {
        System.out.println("kuriam");
        String query;
        query = "CREATE TABLE " + TABLE_EXERCISES + "( " + EXERCISES_ID + " INTEGER PRIMARY KEY, " + EXERCISES_NAME + " TEXT, " + EXERCISES_WEIGHT + " INTEGER, " + EXERCISES_SETS + " INTEGER, " + EXERCISES_REPS +" INTEGER)";
        String workoutQuery = "CREATE TABLE " + TABLE_WORKOUTS + "(_id INTEGER PRIMARY KEY, name TEXT, time TIME)";
        database.execSQL(query);
        database.execSQL(workoutQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }

}
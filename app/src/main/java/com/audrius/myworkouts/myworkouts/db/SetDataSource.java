package com.audrius.myworkouts.myworkouts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.audrius.myworkouts.myworkouts.models.Set;

import java.util.ArrayList;

public class SetDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.SETS_ID, MySQLiteHelper.SETS_WEIGHT, MySQLiteHelper.SETS_REPS, MySQLiteHelper.SETS_EXERCISE_ID};

    public SetDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() { database = dbHelper.getWritableDatabase();}

    public void close() {dbHelper.close();}

    public void createSet(Set set) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.SETS_WEIGHT, set.getWeight());
        values.put(MySQLiteHelper.SETS_REPS, set.getReps());
        values.put(MySQLiteHelper.SETS_EXERCISE_ID, set.getExerciseId());
        database.insert(MySQLiteHelper.TABLE_SETS, null, values);
    }

    public void deleteSet(Set set) {
        long id = set.getId();
        database.delete(MySQLiteHelper.TABLE_SETS, MySQLiteHelper.SETS_ID + " = " + id, null );
    }

    public ArrayList<Set> getAllSets() {
        ArrayList<Set> sets = new ArrayList<Set>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SETS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Set set = cursorToSet(cursor);
            sets.add(set);
            cursor.moveToNext();
        }
        cursor.close();
        return sets;
    }

    public Set getSetById(int id){
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_SETS + " WHERE " + MySQLiteHelper.SETS_ID + " = " + id, null);
        cursor.moveToFirst();
        Set set = cursorToSet(cursor);
        cursor.close();
        return set;
    }

    public ArrayList<Set> getSetsByExerciseId(long id){
        ArrayList<Set> sets = new ArrayList<Set>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_SETS + " WHERE " + MySQLiteHelper.SETS_EXERCISE_ID + " = " + (int)id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Set set = cursorToSet(cursor);
            sets.add(set);
            cursor.moveToNext();
        }
        cursor.close();
        return sets;
    }

    private Set cursorToSet(Cursor cursor){
        Set set = new Set();
        set.setId(cursor.getLong(0));
        set.setWeight(Integer.parseInt(cursor.getString(1)));
        set.setReps(Integer.parseInt(cursor.getString(2)));
        set.setExerciseId(Integer.parseInt(cursor.getString(3)));
        return set;
    }
}

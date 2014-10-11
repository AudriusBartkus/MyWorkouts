package com.audrius.myworkouts.myworkouts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.audrius.myworkouts.myworkouts.db.ExerciseDataSource;
import com.audrius.myworkouts.myworkouts.db.WorkoutDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;
import com.audrius.myworkouts.myworkouts.models.Workout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class newWorkoutActivity extends ActionBarActivity {
    private ExerciseDataSource exDatasource;
    private WorkoutDataSource workDatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);
        exDatasource = new ExerciseDataSource(this);
        exDatasource.open();
        workDatasource = new WorkoutDataSource(this);
        workDatasource.open();
        //exDatasource.dropDB();
        ArrayList<Exercise> values = exDatasource.getAllUnassignedExercises();


//        ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(this,
//                R.layout.list_exercises, values);
        ListView list = (ListView) findViewById(R.id.list);
        ExerciseAdapter adapter = new ExerciseAdapter(this, values);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addExercise(View view){
        Intent intent = new Intent(this, addExerciseActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        exDatasource.open();
        workDatasource.open();

        ArrayList<Exercise> values = exDatasource.getAllUnassignedExercises();


//        ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(this,
//                R.layout.list_exercises, values);
        ListView list = (ListView) findViewById(R.id.list);
        ExerciseAdapter adapter = new ExerciseAdapter(this, values);
        list.setAdapter(adapter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        exDatasource.close();
        workDatasource.close();
        super.onPause();
    }


    public void save(View view){
        Workout workout = new Workout();
        EditText titleInput = (EditText)findViewById(R.id.title_input);
        workout.setName(titleInput.getText().toString());
        long last = workDatasource.createWorkout(workout);
        exDatasource.assignUnassignedExercises(last);
    }

}

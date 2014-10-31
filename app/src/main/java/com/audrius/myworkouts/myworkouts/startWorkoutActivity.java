package com.audrius.myworkouts.myworkouts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.audrius.myworkouts.myworkouts.adapters.WorkoutAdapter;
import com.audrius.myworkouts.myworkouts.db.ExerciseDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;
import com.audrius.myworkouts.myworkouts.models.Workout;

import java.util.ArrayList;


public class startWorkoutActivity extends ActionBarActivity {
    private Workout workout;
    private ExerciseDataSource datasource;
    private ArrayList<Exercise> exercises;
    private ArrayList<String> sets;
    private ArrayList<Object> setList;
    private ExpandableListView list;
    private WorkoutAdapter adapter;
    private Chronometer chronometer;
    private boolean started = false;
    private long timeWhenStopped;
    private Button startPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
        datasource = new ExerciseDataSource(this);
        datasource.open();
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        timeWhenStopped = 0;
        startPauseButton = (Button)findViewById(R.id.startButton);
        workout = (Workout)getIntent().getSerializableExtra("workout");
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(workout.getName());

        exercises = datasource.getExercisesById(workout.getId());
        setList = new ArrayList<Object>();
        for(Exercise exercise : exercises){
            sets = new ArrayList<String>();
            for(int i = 0; i < exercise.getSets(); i++){
                sets.add("Reps: " + String.valueOf(exercise.getReps()) + " Weight: " + String.valueOf(exercise.getWeight()));
            }
            setList.add(sets);
        }
        list = (ExpandableListView)findViewById(R.id.expandableListView);
        list.setGroupIndicator(null);
        list.setClickable(true);
        adapter = new WorkoutAdapter(exercises, setList);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        list.setAdapter(adapter);

    }

    public void onClickSatrtPause(View view){

        if (started == false){
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chronometer.start();
            startPauseButton.setBackgroundResource(R.drawable.ic_action_pause);
            started = true;
        } else {
            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            startPauseButton.setBackgroundResource(R.drawable.ic_action_play);
            started = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_workout, menu);
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


}

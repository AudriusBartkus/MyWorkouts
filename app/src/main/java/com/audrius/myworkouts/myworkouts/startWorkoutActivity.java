package com.audrius.myworkouts.myworkouts;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
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
import com.audrius.myworkouts.myworkouts.db.SetDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;
import com.audrius.myworkouts.myworkouts.models.Set;
import com.audrius.myworkouts.myworkouts.models.Workout;

import java.util.ArrayList;
import java.util.HashMap;


public class startWorkoutActivity extends ActionBarActivity {
    private Workout workout;
    private Set set;
    private ExerciseDataSource datasource;
    private SetDataSource setDatasource;
    private ArrayList<Exercise> exercises;
    private ArrayList<Set> sets;
    private ArrayList<Object> setList;
    private ExpandableListView list;
    private WorkoutAdapter adapter;
    private Chronometer chronometer;
    private boolean started = false;
    private long timeWhenStopped;
    private Button startPauseButton;
    private static int prev = -1;

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
            sets = new ArrayList<Set>();
            Set tempSet;
            for(int i = 0; i < exercise.getSets(); i++){
                tempSet = new Set();
                tempSet.setWeight(exercise.getWeight());
                tempSet.setReps(exercise.getReps());
                tempSet.setExerciseId(exercise.getId());
                //setDatasource.createSet(tempSet);
                sets.add(tempSet);
            }
            setList.add(sets);
        }

        list = (ExpandableListView)findViewById(R.id.expandableListView);
        list.setGroupIndicator(null);
        list.setClickable(true);

        adapter = new WorkoutAdapter(exercises, setList, this);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        list.setAdapter(adapter);

        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                editSet((Set)parent.getAdapter().getItem(childPosition));
                return true;
            }
        });

        list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(prev!=-1 && prev!=groupPosition) {
                    list.collapseGroup(prev);
                }
                prev=groupPosition;
            }
        });

    }

    public void editSet(Set set){
        Intent intent = new Intent(this, editSetActivity.class);
        intent.putExtra("set", set);
        startActivity(intent);

    }

    public void onClickSatrtPause(View view){

        if (!started){
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

    public void onSetClick(View view){
        Intent intent = new Intent(this, editSetActivity.class);
        set = (Set)view.getTag();
        Log.d("------------", set.toString());
        intent.putExtra("set", set);
        //intent.putExtra("array", )
        startActivity(intent);
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

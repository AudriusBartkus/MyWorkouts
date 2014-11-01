package com.audrius.myworkouts.myworkouts;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.audrius.myworkouts.myworkouts.adapters.StatisticsWorkoutAdapter;
import com.audrius.myworkouts.myworkouts.adapters.WorkoutAdapter;
import com.audrius.myworkouts.myworkouts.db.ExerciseDataSource;
import com.audrius.myworkouts.myworkouts.db.SetDataSource;
import com.audrius.myworkouts.myworkouts.db.WorkoutDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;
import com.audrius.myworkouts.myworkouts.models.Set;
import com.audrius.myworkouts.myworkouts.models.Workout;

import java.util.ArrayList;


public class workoutStatisticsActivity extends ActionBarActivity {
    private Workout workout;
    private ExerciseDataSource datasource;
    private WorkoutDataSource workoutDatasource;
    private SetDataSource setDatasource;
    private ExerciseDataSource exerciseDataSource;
    private ArrayList<Exercise> exercises;
    private ArrayList<Set> sets;
    private ArrayList<Object> setList;
    private ExpandableListView list;
    private StatisticsWorkoutAdapter adapter;
    private static int prev = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_statistics);
        datasource = new ExerciseDataSource(this);
        datasource.open();
        workoutDatasource = new WorkoutDataSource(this);
        workoutDatasource.open();
        exerciseDataSource = new ExerciseDataSource(this);
        exerciseDataSource.open();
        setDatasource = new SetDataSource(this);
        setDatasource.open();

        workout = (Workout)getIntent().getSerializableExtra("workout");
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(workout.getName());
        textView = (TextView)findViewById(R.id.timeText);
        textView.setText(workout.getTime().substring(10));
        textView = (TextView)findViewById(R.id.dateTextView);
        textView.setText(workout.getTime().substring(0, 10));
        exercises = datasource.getExercisesById(workout.getId());
        setList = new ArrayList<Object>();

        for(Exercise exercise : exercises){
            sets = new ArrayList<Set>();
            sets = setDatasource.getSetsByExerciseId(exercise.getId());
            setList.add(sets);
        }

        list = (ExpandableListView)findViewById(R.id.expandableListView);
        list.setGroupIndicator(null);
        list.setClickable(true);

        adapter = new StatisticsWorkoutAdapter(exercises, setList, this);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        list.setAdapter(adapter);

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

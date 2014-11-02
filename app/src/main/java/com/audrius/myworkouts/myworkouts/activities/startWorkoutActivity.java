package com.audrius.myworkouts.myworkouts.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.audrius.myworkouts.myworkouts.R;
import com.audrius.myworkouts.myworkouts.adapters.WorkoutAdapter;
import com.audrius.myworkouts.myworkouts.db.ExerciseDataSource;
import com.audrius.myworkouts.myworkouts.db.SetDataSource;
import com.audrius.myworkouts.myworkouts.db.WorkoutDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;
import com.audrius.myworkouts.myworkouts.models.Set;
import com.audrius.myworkouts.myworkouts.models.Workout;

import java.util.ArrayList;
import java.util.Calendar;


public class startWorkoutActivity extends Activity {
    private Workout workout;
    private Set set;
    private ExerciseDataSource datasource;
    private WorkoutDataSource workoutDatasource;
    private SetDataSource setDatasource;
    private ExerciseDataSource exerciseDataSource;
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
    static final int EDIT_RESPONSE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
        datasource = new ExerciseDataSource(this);
        datasource.open();
        workoutDatasource = new WorkoutDataSource(this);
        workoutDatasource.open();
        exerciseDataSource = new ExerciseDataSource(this);
        exerciseDataSource.open();
        setDatasource = new SetDataSource(this);
        setDatasource.open();
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
                editSet((Set)parent.getAdapter().getItem(childPosition), groupPosition, childPosition);
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

    @Override
    protected void onResume(){
        //adapter.notifyDataSetChanged();
        super.onResume();
    }


    public void editSet(Set set, int groupPosition, int childPosition){
        Intent intent = new Intent(this, editSetActivity.class);
        intent.putExtra("set", set);
        intent.putExtra("groupPosition", groupPosition);
        intent.putExtra("childPosition", childPosition);
        startActivityForResult(intent, EDIT_RESPONSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Ateina", "*************");
        Log.e("d", "ds");
        if (requestCode == EDIT_RESPONSE) {
            if (resultCode == RESULT_OK) {

                Set newSet = (Set)data.getSerializableExtra("set");
                int groupPos = (Integer)data.getSerializableExtra("groupPosition");
                int childPos = (Integer)data.getSerializableExtra("childPosition");
                Log.d(String.valueOf(groupPos), String.valueOf(childPos));
                ArrayList<Set> tempSet = (ArrayList<Set>)setList.get(groupPos);
                tempSet.set(childPos, newSet);
                setList.set(groupPos, tempSet);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void onClickSartPause(View view){

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
        intent.putExtra("set", set);
        //intent.putExtra("array", )
        startActivity(intent);
    }

    public void saveWorkout(){
        Workout newWorkout = workout;
        java.util.Date dt = new java.util.Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        String chronoText = chronometer.getText().toString();
        String array[] = chronoText.split(":");
        if(array.length == 2){
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE,Integer.parseInt(array[0]));
            cal.set(Calendar.SECOND, Integer.parseInt(array[1]));
            cal.set(Calendar.MILLISECOND, 0);
        } else if(array.length == 3) {
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(array[0]));
            cal.set(Calendar.MINUTE,Integer.parseInt(array[1]));
            cal.set(Calendar.SECOND, Integer.parseInt(array[2]));
            cal.set(Calendar.MILLISECOND, 0);
        } else {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        dt = cal.getTime();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        newWorkout.setTime(currentTime);
        long newWorkoutId;
        newWorkoutId = workoutDatasource.createWorkout(newWorkout);

        Exercise tempEx;
        for (int u = 0; u < exercises.size(); u++){
            tempEx = exercises.get(u);
            tempEx.setWorkout_id(newWorkoutId);
            long newExId = exerciseDataSource.createExercise(tempEx);
            ArrayList<Set> tempSetList = (ArrayList<Set>)setList.get(u);
            for (int i = 0; i< tempSetList.size(); i++){
                Set tempSet = tempSetList.get(i);
                if(tempSet.isSelected()){

                    tempSet.setExerciseId(newExId);
                    setDatasource.createSet(tempSet);
                }

            }
        }

        this.finish();

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

    @Override
    public void onBackPressed() {
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle(R.string.exit)
                .setMessage(R.string.exit_question)

                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
    public void onFinishPressed(View view){
        AlertDialog dialog = confirmFinish();
        dialog.show();
    }

    public AlertDialog confirmFinish()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle(R.string.end_workout)
                .setMessage(R.string.end_workout_question)

                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        saveWorkout();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }


}

package com.audrius.myworkouts.myworkouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.audrius.myworkouts.myworkouts.db.ExerciseDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;

public class addExerciseActivity extends Activity {
    private ExerciseDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        datasource = new ExerciseDataSource(this);
        datasource.open();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_exercise, menu);
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

    public void changeField(View view){
        boolean checked = ((CheckBox) view).isChecked();
        EditText input;
        switch(view.getId()) {
            case R.id.weight:
                input = (EditText) findViewById(R.id.weightInput);
                input.setFocusableInTouchMode(checked);
                input.setFocusable(checked);
                if (!checked) {
                    input.getText().clear();
                }
                break;
            case R.id.sets:
                input = (EditText) findViewById(R.id.setsInput);
                input.setFocusableInTouchMode(checked);
                input.setFocusable(checked);
                if (!checked) {
                    input.getText().clear();
                }
                break;
            case R.id.reps:
                input = (EditText) findViewById(R.id.repsInput);
                input.setFocusableInTouchMode(checked);
                input.setFocusable(checked);
                if (!checked) {
                    input.getText().clear();
                }
                break;
        }

    }

    public void save(View view){
        // save exercise to the database
        //TODO: refresh list
        Exercise exercise = new Exercise();
        EditText nameInput = (EditText)findViewById(R.id.nameInput);
        EditText weightInput = (EditText)findViewById(R.id.weightInput);
        EditText setsInput  = (EditText)findViewById(R.id.setsInput);
        EditText repsInput = (EditText)findViewById(R.id.repsInput);
        exercise.setName(nameInput.getText().toString());
        exercise.setWeight(Integer.parseInt(weightInput.getText().toString()));
        exercise.setSets(Integer.parseInt(setsInput.getText().toString()));
        exercise.setReps(Integer.parseInt(repsInput.getText().toString()));
        datasource.createExercise(exercise);
        //adapter.add(exercise);
    }
}

package com.audrius.myworkouts.myworkouts.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.audrius.myworkouts.myworkouts.R;
import com.audrius.myworkouts.myworkouts.SwipeDismissListViewTouchListener;
import com.audrius.myworkouts.myworkouts.adapters.ExerciseAdapter;
import com.audrius.myworkouts.myworkouts.db.ExerciseDataSource;
import com.audrius.myworkouts.myworkouts.db.WorkoutDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;
import com.audrius.myworkouts.myworkouts.models.Workout;

import java.util.ArrayList;

public class newWorkoutActivity extends ActionBarActivity {
    private ExerciseDataSource exDatasource;
    private WorkoutDataSource workDatasource;
    private ArrayList<Exercise> values;
    private ListView list;
    private ExerciseAdapter adapter;
    private EditText nameInput;


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);
        exDatasource = new ExerciseDataSource(this);
        exDatasource.open();
        workDatasource = new WorkoutDataSource(this);
        workDatasource.open();
        nameInput = (EditText)findViewById(R.id.title_input);
        nameInput.addTextChangedListener(textWatcher);
        checkFieldsForEmptyValues();
        values = exDatasource.getAllUnassignedExercises();

        list = (ListView) findViewById(R.id.list);
        adapter = new ExerciseAdapter(this, values);
        list.setAdapter(adapter);

        SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
            list, new SwipeDismissListViewTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(int position) {
                    return true;
                }

                @Override
                public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                    for (int position : reverseSortedPositions) {
                        Exercise exercise = values.get(position);
                        exDatasource.deleteExercise(exercise);
                    }
                    adapter.notifyDataSetChanged();
                    onResume();
                }
        });
        list.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        list.setOnScrollListener(touchListener.makeScrollListener());

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

        values = exDatasource.getAllUnassignedExercises();


//        ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(this,
//                R.layout.list_exercises, values);
        list = (ListView) findViewById(R.id.list);
        adapter = new ExerciseAdapter(this, values);
        list.setAdapter(adapter);
        checkFieldsForEmptyValues();
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
        this.finish();
    }

    private void checkFieldsForEmptyValues(){
        Button button = (Button)findViewById(R.id.save_workout);
        if (!nameInput.getText().toString().trim().isEmpty() && !exDatasource.getAllUnassignedExercises().isEmpty()){
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }

    }
}

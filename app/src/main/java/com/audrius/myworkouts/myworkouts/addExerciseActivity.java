package com.audrius.myworkouts.myworkouts;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.audrius.myworkouts.myworkouts.db.ExerciseDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;

public class addExerciseActivity extends Activity {
    private ExerciseDataSource datasource;
    private EditText weightInput;
    private EditText setsInput;
    private EditText repsInput;
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
        setContentView(R.layout.activity_add_exercise);
        datasource = new ExerciseDataSource(this);
        datasource.open();

        weightInput = (EditText)findViewById(R.id.weightInput);
        setsInput = (EditText)findViewById(R.id.setsInput);
        repsInput =(EditText)findViewById(R.id.repsInput);
        nameInput =(EditText)findViewById(R.id.nameInput);

        weightInput.addTextChangedListener(textWatcher);
        setsInput.addTextChangedListener(textWatcher);
        repsInput.addTextChangedListener(textWatcher);
        nameInput.addTextChangedListener(textWatcher);

        checkFieldsForEmptyValues();
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

    private void checkFieldsForEmptyValues(){
        Button button = (Button)findViewById(R.id.save_exercise);
        if (!nameInput.getText().toString().trim().isEmpty() && !weightInput.getText().toString().trim().isEmpty()
                && !setsInput.getText().toString().trim().isEmpty() && !repsInput.getText().toString().trim().isEmpty()){
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
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
        //exercise.setWorkout_id(1);
        datasource.createExercise(exercise);
        //adapter.add(exercise);
        this.finish();
    }
}

package com.audrius.myworkouts.myworkouts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.audrius.myworkouts.myworkouts.db.ExerciseDataSource;
import com.audrius.myworkouts.myworkouts.models.Exercise;

import java.util.ArrayList;

public class newWorkoutActivity extends ActionBarActivity {
    private ExerciseDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        datasource = new ExerciseDataSource(this);
        datasource.open();

        ArrayList<Exercise> values = datasource.getAllExercises();


//        ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(this,
//                R.layout.list_example_entry, values);
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
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    public void save(View view){
        @SuppressWarnings("unchecked")
        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter<Exercise> adapter = (ArrayAdapter<Exercise>) list.getAdapter();



        switch (view.getId()) {
            case R.id.button2:
                // save exercise to the database
                Exercise exercise = new Exercise();
                exercise.setName("kuku");
                exercise.setWeight(1);
                exercise.setReps(2);
                exercise.setSets(3);

                datasource.createExercise(exercise);
                //adapter.add(exercise);
                break;
//            /*case R.id.delete:
//                if (getListAdapter().getCount() > 0) {
//                    comment = (Exercise) getListAdapter().getItem(0);
//                    datasource.deleteExercise(comment);
//                    adapter.remove(comment);
//                }
//                break;*/
        }
        adapter.notifyDataSetChanged();
    }

}
